package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Listeners.GameListener;
import it.polimi.ingsw.Mappers.ModelViewMapper;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Model.GoalCard.GoalCard;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Model.Player.State;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

import java.util.*;

/**
 * The Controller class is responsible for managing the game flow and player actions in the Codex Naturalis application.
 * It communicates with the Game model and updates the views (listeners) based on the changes in the game state.
 * <p>
 * The Controller class has several responsibilities:
 * - It manages the game state transitions and player turns.
 * - It handles player actions such as choosing a color, choosing an initial card, and choosing a secret goal.
 * - It manages the reconnection of players.
 * - It handles the end of the game.
 * <p>
 * The Controller class uses several helper methods to perform its responsibilities. These methods include:
 * - `startGame`: Starts the game and manages the game flow.
 * - `chooseSecretGoalFase`: Handles the phase where players choose their secret goal.
 * - `chooseInitialCardFase`: Handles the phase where players choose their initial card.
 * - `chooseColorFase`: Handles the phase where players choose their color.
 * - `setGameState`: Sets the game state.
 * - `endGame`: Ends the game.
 * - `updateListener`: Updates a specific listener with a message.
 * - `waitingForPlayerTurn`: Waits for the player's turn.
 * - `addMessage`: Adds a message to the queue.
 * - `handleMessage`: Handles a received message.
 * - `handlePongMessage`: Handles a received pong message.
 * - `reconnectPlayer`: Reconnects a player to the game.
 * - `isPlayerDisconnected`: Checks if a player is disconnected.
 *
 * @author Jihad Founoun
 */
public class Controller {
    private final ModelViewMapper modelViewMapper;
    private final Map<String, GameListener> listeners;
    private final Game game;
    private final Object lock;
    private final Object gameStateLock;
    private final Object messageLock;
    private final Object reconnectionLock;
    private final String[] roundOrder;
    private final Queue<Message> messagesQueue;
    private final Map<String, GoalCard[]> potentialGoals;
    private final Timer[] pingTimers;
    private final TimerTask[] pingTasks;
    private final int[] sendedPingsIds;
    private final MainController mainController;
    private Timer reconnectionTimer;
    private TimerTask reconnectionTask;
    private boolean reconnectionTimerIsDone;
    private boolean noDrawableCard;
    private int currentPingId;
    private boolean drawCardIsDone, playCardIsDone, wrongPlay, decksAreEmpty;
    private int currentPlayer;

    /**
     * Constructor for the Controller class.
     * @param listeners the game listeners for the players
     * @param numPlayers the number of players in the game
     * @param mainController the main controller of the game
     */
    public Controller(Map<String, GameListener> listeners, int numPlayers, MainController mainController) {
        this.mainController = mainController;
        this.modelViewMapper = new ModelViewMapper();
        this.lock = new Object();
        this.messageLock = new Object();
        this.gameStateLock = new Object();
        this.reconnectionLock = new Object();
        this.game = new Game(numPlayers, new ArrayList<>(listeners.keySet()));
        this.listeners = listeners;
        this.currentPlayer = 0;
        this.playCardIsDone = false;
        this.drawCardIsDone = false;
        this.wrongPlay = false;
        this.noDrawableCard = false;
        this.decksAreEmpty = false;
        this.roundOrder = new String[numPlayers];
        this.messagesQueue = new LinkedList<>();
        this.potentialGoals = new HashMap<>(numPlayers);
        this.pingTimers = new Timer[numPlayers];
        this.pingTasks = new TimerTask[numPlayers];
        this.sendedPingsIds = new int[numPlayers];
        this.currentPingId = 0;
        for (int i = 0; i < numPlayers; i++) {
            roundOrder[i] = game.getPlayers().get(i).getId();
            potentialGoals.put(game.getPlayers().get(i).getId(), game.getSecretGoalsForPlayer());
            sendedPingsIds[i] = -1;
        }
        Collections.shuffle(Arrays.asList(roundOrder));
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    synchronized (messageLock) {
                        try {
                            messageLock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (messagesQueue.isEmpty())
                            continue;
                        Message message = messagesQueue.poll();
                        handleMessage(message);
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    for (int i = 0; i < numPlayers; i++) {

                        if (game.isPlayerDisconnected(roundOrder[i])) {
                            continue;
                        }
                        pingTimers[i] = new Timer();
                        String currentPlayer = roundOrder[i];
                        pingTasks[i] = new TimerTask() {
                            @Override
                            public void run() {
                                disconnectPlayer(currentPlayer);
                            }
                        };
                        pingTimers[i].schedule(pingTasks[i], 7000);
                        sendedPingsIds[i] = currentPingId;
                        PingMessage pingMessage = new PingMessage(currentPingId);
                        listeners.get(roundOrder[i]).update(pingMessage);
                        currentPingId = (currentPingId + 1) % 1000000;
                    }


                }
            }
        }.start();


    }


    /**
     * Gets the list of players in the game.
     * @return the list of players
     */
    public List<String> getPlayers() {
        List<String> players = new ArrayList<>();
        synchronized (listeners) {
            for (String player : listeners.keySet()) {
                players.add(player);
            }
        }

        return players;
    }

    /**
     * Disconnects a player from the game.
     * @param username the username of the player to disconnect
     */
    private void disconnectPlayer(String username) {
        synchronized (lock) {
            game.setPlayerState(username, State.DISCONNECTED);
            updateListeners(new PlayerLeftMessage(username));
            lock.notifyAll();
        }
    }
    /**
     * Starts the game. This method handles the game flow, including the setup phases,
     * the main game loop, and the end of the game. It manages the game state,
     * player turns, and reconnection timers. It also sends updates to the listeners
     * to keep them informed about the game progress.
     *
     * The method follows these steps:
     * 1. Sends the round order to the listeners.
     * 2. Sets the game state to WAITING_FOR_TOKENS_SETUPS and starts the color choice phase.
     * 3. Sets up the players' items and sets the game state to WAITING_FOR_INITIAL_CARDS_SETUPS.
     * 4. Starts the initial card choice phase.
     * 5. Sets the game state to WAITING_FOR_SECRET_GOALS_SETUPS and starts the secret goal choice phase.
     * 6. If there are no active players, the game is cancelled.
     * 7. Sets the game state to STARTED and enters the main game loop.
     * 8. If there is only one active player, a reconnection timer is started.
     * 9. If a player is not disconnected or skipped, the player's turn is handled.
     * 10. If the game state is STARTED and a player has reached 20 points, the game state is set to PRE_FINAL_TURN.
     * 11. If all decks are empty and the game state is STARTED, the game state is set to PRE_FINAL_TURN.
     * 12. If the game state is PRE_FINAL_TURN and it's the first player's turn, the game state is set to FINAL_TURN.
     * 13. If the game state is FINAL_TURN and it's the first player's turn, the game loop is exited.
     * 14. Finally, the game is ended.
     */
    public void startGame() {

        List<PublicPlayerView> othersPlayersView = null;

        updateListeners(new RoundOrderMessage(roundOrder));

        setGameState(GameState.WAITING_FOR_TOKENS_SETUPS);

        chooseColorPhase();

        game.setUpPlayersItems();
        setGameState(GameState.WAITING_FOR_INITIAL_CARDS_SETUPS);

        List<String> players = this.getPlayers();

        chooseInitialCardPhase();


        setGameState(GameState.WAITING_FOR_SECRET_GOALS_SETUPS);
        othersPlayersView = new ArrayList<>();
        for (String player : players) {
            synchronized (modelViewMapper) {
                othersPlayersView.add(modelViewMapper.getPublicPlayerView(game.getPlayerById(player), game.getBoardSize()));
            }
        }

        chooseSecretGoalPhase(othersPlayersView);



        if (game.getNumberOfActivePlayers() == 0) {
            mainController.cancelGame(this);
            return;
        }
        setGameState(GameState.STARTED);

        while (game.getState() != GameState.ENDED && game.getNumberOfActivePlayers() > 0) {
            reconnectionTimerIsDone = false;

            //handle reconnection timer
            if (game.getNumberOfActivePlayers() == 1) {

                updateListeners(new GameInWaitMessage());
                reconnectionTimer = new Timer();
                reconnectionTimerIsDone = false;
                reconnectionTask = new TimerTask() {
                    @Override
                    public void run() {
                        reconnectionTimerIsDone = true;
                        synchronized (reconnectionLock) {
                            reconnectionLock.notifyAll();
                        }
                    }
                };
                reconnectionTimer.schedule(reconnectionTask, 40000);
                while (game.getNumberOfActivePlayers() == 1 && !reconnectionTimerIsDone) {
                    synchronized (reconnectionLock) {
                        try {
                            reconnectionLock.wait();
                        } catch (InterruptedException e) {

                        }

                    }
                }
                if (game.getNumberOfActivePlayers() == 1 && reconnectionTimerIsDone) {
                    break;
                }


            } else if (game.getNumberOfActivePlayers() == 0) {
                mainController.cancelGame(this);
                return;
            }

            if (!game.isPlayerDisconnected(roundOrder[currentPlayer]) && !game.isPlayerSkipped(roundOrder[currentPlayer])) {

                othersPlayersView.clear();

                for (String player : players) {
                    synchronized (modelViewMapper) {
                        othersPlayersView.add(modelViewMapper.getPublicPlayerView(game.getPlayerById(player), game.getBoardSize()));
                    }
                }

                if (game.getPlayerById(roundOrder[currentPlayer]).getPlayerBoard().getPlayablePos().size() == 0) {
                    Message m = new NoPlayablePositionsMessage(roundOrder[currentPlayer]);
                    game.setPlayerState(roundOrder[currentPlayer], State.SKIPPED);
                    updateListeners(m);
                } else {
                    for(String player : players){
                        if(!player.equals(roundOrder[currentPlayer])){
                            updateListener(player,new TurnMessage(roundOrder[currentPlayer]));
                        }
                    }
                    //updateListeners(new TurnMessage(roundOrder[currentPlayer]));

                    synchronized (modelViewMapper) {
                        updateListener(roundOrder[currentPlayer],
                                new PlayCardMessage(modelViewMapper.getPlayerView(game.getPlayerById(roundOrder[currentPlayer]), game.getBoardSize()), othersPlayersView, modelViewMapper.getCommonBoardView(game.getCommonBoard()))
                        );
                    }

                    while (!playCardIsDone && !game.isPlayerDisconnected(roundOrder[currentPlayer])) {
                        waitingForPlayerTurn();
                    }

                    if(!noDrawableCard) {
                        noDrawableCard = game.getCommonBoard().getDrawableCards().isEmpty();
                    }

                    if (!noDrawableCard) {
                        synchronized (modelViewMapper) {
                            updateListener(roundOrder[currentPlayer],
                                    new DrawCardMessage(modelViewMapper.getPlayerView(game.getPlayerById(roundOrder[currentPlayer]), game.getBoardSize()))
                            );
                        }


                        while (!drawCardIsDone && !game.isPlayerDisconnected(roundOrder[currentPlayer])) {
                            waitingForPlayerTurn();
                        }

                        //random draw choice for  disconnected player
                        if (playCardIsDone&&!drawCardIsDone && game.isPlayerDisconnected(roundOrder[currentPlayer])) {
                            int casualDrawSetIndex;
                            int drawIndex;
                            ResourceCard drawnCard;
                            synchronized (modelViewMapper) {
                                casualDrawSetIndex = new Random().nextInt(game.getCommonBoard().getDrawableCards().size());
                                drawIndex = new ArrayList<>(game.getCommonBoard().getDrawableCards()).get(casualDrawSetIndex);
                                drawnCard = game.drawCard(roundOrder[currentPlayer], drawIndex);
                            }
                            drawnCardUpdate(roundOrder[currentPlayer], drawIndex, drawnCard,true);
                        }
                    }else if(noDrawableCard){
                            updateListener(roundOrder[currentPlayer], new NoDrawableCardMessage());
                    }


                    if (game.getState().equals(GameState.STARTED) && game.checkOver20Score()) {
                        setGameState(GameState.PRE_FINAL_TURN);
                        updateListeners(new PlayerReached20(roundOrder[currentPlayer]));
                    }
                }
            }
            drawCardIsDone = false;
            playCardIsDone = false;

            currentPlayer = (currentPlayer + 1) % game.getNumPlayers();

            if (!decksAreEmpty)
                decksAreEmpty = game.decksAreEmpty();

            if (decksAreEmpty && game.getState().equals(GameState.STARTED)) {
                setGameState(GameState.PRE_FINAL_TURN);
                updateListeners(new EmptyDecksMessage());
            }

            if (game.getState().equals(GameState.PRE_FINAL_TURN) && currentPlayer == 0) {
                setGameState(GameState.FINAL_TURN);
                updateListeners(new FinalRoundMessage());
            } else if (game.getState().equals(GameState.FINAL_TURN) && currentPlayer == 0) {
                break;
            }


        }
        endGame();
    }
    /**
     * Handles the phase where players choose their secret goal.
     * @param othersPlayersView the views of the other players
     */
    public void chooseSecretGoalPhase(List<PublicPlayerView> othersPlayersView){
        List<String> players = this.getPlayers();

        for (String player : players) {
            synchronized (modelViewMapper) {
                updateListener(player, new ChooseGoalMessage(modelViewMapper.getGoalCardView(potentialGoals.get(player)[0]), modelViewMapper.getGoalCardView(potentialGoals.get(player)[1]), othersPlayersView, modelViewMapper.getPlayerView(game.getPlayerById(player), game.getBoardSize()), modelViewMapper.getCommonBoardView(game.getCommonBoard())));
            }
        }
        while (!game.secretGoalsSetupIsDone(potentialGoals)) {
            waitingForPlayerTurn();
        }
    }
    /**
     * Handles the phase where players choose their initial card.
     */
    public void chooseInitialCardPhase(){
        List<String> players = this.getPlayers();
        for (String player : players) {
            synchronized (modelViewMapper) {
                updateListener(player,
                        new ChooseInitialCardMessage(modelViewMapper.getInitialCardView(game.getPlayerById(player).getInitialCard()))
                );
            }
        }
        while (!game.initialCardSetupIsDone()) {
            waitingForPlayerTurn();
        }
    }
    /**
     * Handles the phase where players choose their color.
     */
    public void chooseColorPhase(){
        for (String player : roundOrder) {
            Message colorChoice = new ChooseColorMessage(game.getAvailableTokens());
            updateListener(player, colorChoice);
            while (!game.isPlayerDisconnected(player) && game.getPlayerById(player).getToken() == Color.NONE) {
                waitingForPlayerTurn();
            }
            if (game.isPlayerDisconnected(player) && game.getPlayerById(player).getToken() == Color.NONE) {
                game.getPlayerById(player).setToken(game.getAvailableTokens().stream().findAny().get());
            }
        }
    }
    /**
     * Sets the game state.
     * @param gameState the new game state
     */
    public void setGameState(GameState gameState) {
        synchronized (gameStateLock) {
            game.setState(gameState);
            gameStateLock.notifyAll();
        }
    }
    /**
     * Updates the listeners with a message.
     * @param message the message to send
     */
    private void updateListeners(Message message) {
        List<String> players = this.getPlayers();

        for (String player : players) {
            if (game.isPlayerDisconnected(player)) {
                continue;
            }
            synchronized (listeners) {
                listeners.get(player).update(message);
            }
        }
    }
    /**
     * Ends the game.
     */
    private void endGame() {
        setGameState(GameState.ENDED);
        game.computeOutcome();
        EndGameMessage endGameMessage = new EndGameMessage(game.getPlayersViews());
        updateListeners(endGameMessage);
        mainController.cancelGame(this);
    }
    /**
     * Updates a specific listener with a message.
     * @param player the player associated with the listener
     * @param message the message to send
     */
    private void updateListener(String player, Message message) {
        if (game.isPlayerDisconnected(player)) {
            return;
        }
        synchronized (listeners) {
            listeners.get(player).update(message);
        }

    }
    /**
     * Waits for the player's turn.
     */
    public void waitingForPlayerTurn() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                System.err.println("Interrupted while waiting for server: " + e.getMessage());
            }
        }
    }
    /**
     * Adds a message to the queue.
     * @param message the message to add
     */
    public void addMessage(Message message) {

        if (message instanceof PongMessage) {
            handlePongMessage((PongMessage) message);
            return;
        }
        synchronized (messageLock) {
            messagesQueue.add(message);
            messageLock.notify();
        }
    }
    /**
     * Handles a received message.
     * @param m the received message
     */
    public void handleMessage(Message m) {
        if (m instanceof ChosenColorMessage message) {
            if (game.getAvailableTokens().contains(message.getColor())) {
                synchronized (lock) {
                    game.getPlayerById(message.getUsername()).setToken(message.getColor());
                    updateListener(message.getUsername(), new UpdateColorMessage(message.getColor()));
                    lock.notifyAll();
                }

            } else {
                handleErrors(message.getUsername(), 4);
            }
        } else if (m instanceof ChosenInitialCardMessage message) {
            synchronized (lock) {
                game.setInitialCardForPlayer(message.getUsername(), message.isInitFront());
                PublicPlayerView view;
                synchronized (modelViewMapper) {
                    view = modelViewMapper.getPublicPlayerView(game.getPlayerById(message.getUsername()), game.getBoardSize());
                }
                updateListener(message.getUsername(), new UpdateInitialCardMessage(view.getInitialCard(), view.getInventoryResources(), view.getInventoryObjects()));
                lock.notifyAll();
            }
        } else if (m instanceof ChosenGoalMessage message) {
            if (message.getGoalCard() > 2 || message.getGoalCard() < 1) {
                handleErrors(message.getUsername(), 1);
            } else {
                synchronized (lock) {
                    game.setSecretGoalForPlayer(message.getUsername(), potentialGoals.get(message.getUsername())[message.getGoalCard() - 1]);
                    synchronized (modelViewMapper) {
                        updateListener(message.getUsername(), new UpdateSecretGoalMessage(modelViewMapper.getGoalCardView(potentialGoals.get(message.getUsername())[message.getGoalCard() - 1]), modelViewMapper.getPlayerView(game.getPlayerById(message.getUsername()), game.getBoardSize()), modelViewMapper.getPublicPlayerView(game.getPlayerById(message.getUsername()), game.getBoardSize())));
                    }
                    lock.notifyAll();
                }
            }
        } else if (m instanceof PlayedCardMessage message) {
            int outcome = game.checkIncorrectPlay(message.getUsername(), message.getCard() - 1, message.getPlayedSide(), new Coordinate(message.getCoordinate().getX(), message.getCoordinate().getY()));
            if (outcome != 0) {
                handleErrors(message.getUsername(), outcome);
            } else {
                synchronized (lock) {
                    ResourceCard playedCard = game.playCard(message.getUsername(), message.getCard() - 1, message.getPlayedSide(), new Coordinate(message.getCoordinate().getX(), message.getCoordinate().getY()));
                    playCardIsDone = true;
                    synchronized (modelViewMapper) {
                        updateListener(message.getUsername(), new UpdateDoneMessage(modelViewMapper.getCommonBoardView(game.getCommonBoard()), modelViewMapper.getPlayerView(game.getPlayerById(message.getUsername()), game.getBoardSize()), modelViewMapper.getPublicPlayerView(game.getPlayerById(message.getUsername()), game.getBoardSize())));
                    }
                    playedCardUpdate(message.getUsername(), playedCard, message.getPlayedSide());
                    lock.notifyAll();
                }
            }

        } else if (m instanceof DrawnCardMessage message) {
            if (game.getCommonBoard().isCardDrawable(message.getCard())) {
                synchronized (lock) {
                    ResourceCard drawnCard = game.drawCard(message.getUsername(), message.getCard());
                    drawCardIsDone = true;

                    synchronized (modelViewMapper) {
                        updateListener(message.getUsername(), new UpdateDoneMessage(modelViewMapper.getCommonBoardView(game.getCommonBoard()), modelViewMapper.getPlayerView(game.getPlayerById(message.getUsername()), game.getBoardSize()), modelViewMapper.getPublicPlayerView(game.getPlayerById(message.getUsername()), game.getBoardSize())));
                    }
                    drawnCardUpdate(message.getUsername(), message.getCard(), drawnCard,false);
                    lock.notifyAll();
                }
            } else
                handleErrors(message.getUsername(), 5);

        }


    }
    /**
     * Handles a received pong message.
     * @param message the received pong message
     */
    public void handlePongMessage(PongMessage message) {
        int currentIndex = -1;
        for (int i = 0; i < game.getNumPlayers(); i++) {
            if (sendedPingsIds[i] == message.getPongNumber()) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1) {
            return;
        }
        pingTasks[currentIndex].cancel();
        pingTimers[currentIndex].cancel();
    }
    /**
     * Reconnects a player to the game.
     * @param player the player to reconnect
     * @param listener the game listener for the player
     */
    public void reconnectPlayer(String player, GameListener listener) {
        new Thread() {
            @Override
            public void run() {

                if (reconnectionTimer != null) {
                    reconnectionTimer.cancel();
                }
                if (reconnectionTask != null) {
                    reconnectionTask.cancel();
                }


                while (game.getState() != GameState.STARTED) {
                    synchronized (gameStateLock) {
                        try {
                            gameStateLock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }

                game.setPlayerState(player, it.polimi.ingsw.Model.Player.State.PENDING);
                synchronized (reconnectionLock) {
                    reconnectionLock.notifyAll();
                }
                synchronized (listeners) {
                    listeners.put(player, listener);
                }
                synchronized (modelViewMapper) {
                    updateListener(player, new ReconnectionSuccessMessage(modelViewMapper.getCommonBoardView(game.getCommonBoard()), modelViewMapper.getPlayerView(game.getPlayerById(player), game.getBoardSize()), modelViewMapper.getPublicPlayersViews(game.getPlayers(), game.getBoardSize()), roundOrder));
                }
                List<String> players = getPlayers();
                for (String p : players) {
                    if (!p.equals(player)) {
                        updateListener(p, new PlayerReconnectedMessage(player));
                    }

                }


            }
        }.start();

    }
    /**
     * Updates all players about a card drawn by a specific player.
     *
     * @param player The ID of the player who drew the card.
     * @param cardIndex The index of the card in the common board.
     * @param drawnCard The card that was drawn.
     * @param isCasual A boolean indicating if the draw was casual.
     */
    private void drawnCardUpdate(String player, int cardIndex, ResourceCard drawnCard,boolean isCasual) {
        OtherPlayerDrawnCardMessage m;
        synchronized (modelViewMapper) {
            m = new OtherPlayerDrawnCardMessage(modelViewMapper.getPublicPlayerView(game.getPlayerById(player), game.getBoardSize()),
                    player, modelViewMapper.getCommonBoardView(game.getCommonBoard()), cardIndex < 3, modelViewMapper.getResourceCardView(drawnCard),isCasual);
        }
        List<String> players = this.getPlayers();
        for (String p : players) {
            if (!p.equals(player)) {
                updateListener(p, m);
            }

        }
    }

    /**
     * Updates all players about a card played by a specific player.
     *
     * @param player The ID of the player who played the card.
     * @param playedCard The card that was played.
     * @param playedSide A boolean indicating the side of the card that was played.
     */
    private void playedCardUpdate(String player, ResourceCard playedCard, boolean playedSide) {
        OtherPlayerPlayedCardMessage m;
        synchronized (modelViewMapper) {
            m = new OtherPlayerPlayedCardMessage(modelViewMapper.getPublicPlayerView(game.getPlayerById(player), game.getBoardSize()),
                    player, !playedSide, modelViewMapper.getResourceCardView(playedCard));
        }
        List<String> players = this.getPlayers();
        for (String p : players) {
            if (!p.equals(player)) {
                updateListener(p, m);
            }

        }
    }

    /**
     * Handles errors during the game by sending an error message to the player who caused the error.
     *
     * @param playerId The ID of the player who caused the error.
     * @param error The error code.
     */
    private void handleErrors(String playerId, int error) {
        wrongPlay = true;
        Message m = null;
        switch (error) {
            case 1:
                m = new WrongPlayMessage(ErrorMessages.cardNotFound);
                break;
            case 2:
                m = new WrongPlayMessage(ErrorMessages.cardNonPlayable);
                break;
            case 3:
                m = new WrongPlayMessage(ErrorMessages.positionNonPlayable);
                break;
            case 4:
                m = new WrongPlayMessage(ErrorMessages.colorNotAvailable);
                break;
            case 5:
                m = new WrongPlayMessage(ErrorMessages.cardNonDrawable);
        }
        updateListener(playerId, m);

    }
    /**
     * Checks if a player is disconnected.
     * @param player the player to check
     * @return true if the player is disconnected, false otherwise
     */
    public boolean isPlayerDisconnected(String player) {
        return game.isPlayerDisconnected(player);
    }

}
