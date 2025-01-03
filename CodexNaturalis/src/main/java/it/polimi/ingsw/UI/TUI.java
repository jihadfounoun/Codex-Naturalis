package it.polimi.ingsw.UI;

import it.polimi.ingsw.GameLobby.GameLobbyView;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.ModelView.CardView.GoldCardView;
import it.polimi.ingsw.ModelView.CardView.InitialCardView;
import it.polimi.ingsw.ModelView.CardView.ResourceCardView;
import it.polimi.ingsw.ModelView.CardView.UtilitiesView.CoordinateView;
import it.polimi.ingsw.ModelView.CommonBoardView.CommonBoardView;
import it.polimi.ingsw.ModelView.PlayerView.PlayerView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static it.polimi.ingsw.Model.Player.State.WINNER;
import static it.polimi.ingsw.UI.ViewState.*;

/**
 * <strong>TUI</strong>
 * <p>
 * The Textual User Interface, {@link PlayerInterface} implementation.
 * </p>
 * <p>
 * It implements a run method which calls a series of method for the setting of the game and the player's turn.
 * It also implements a method to handle the messages received from the server.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class TUI extends PlayerInterface {
    /**
     * The lock object for the view state.
     */
    private final Object lock;
    /**
     * The lock object for the messages queue.
     */
    private final Object messageLock;
    /**
     * The queue of messages received from the server.
     */
    private Queue<Message> messages;

    private boolean flag = true;
    /**
     * The array of players in the game.
     */
    private String[] players;
    /**
     * The username of the player.
     */
    private String username;
    /**
     * The list of {@link PublicPlayerView} of all the players in the game.
     */
    private List<PublicPlayerView> publicPlayerViews;
    /**
     * The {@link CommonBoardView}.
     */
    private CommonBoardView commonBoardView;
    /**
     * The {@link PlayerView} of the player.
     */
    private PlayerView playerView;
    /**
     * The list of {@link PlayerView} of all the players in the game.
     * It is used only for the endGame method to print the final results.
     */
    private List<PlayerView> endGameView;
    /**
     * The {@link ChooseGoalMessage} received from the server for the initialization of the game.
     */
    private ChooseGoalMessage secretGoalCards;
    /**
     * The {@link ChooseInitialCardMessage} received from the server for the initialization of the game.
     */
    private ChooseInitialCardMessage getInitialCard;
    /**
     * The {@link PlayedCardMessage} received from the server for the player's turn.
     */
    private PlayedCardMessage playCard;
    /**
     * The list of {@link Color} available for the player to choose.
     */
    private List<Color> chooseColor;
    /**
     * The {@link ViewState} of the view.
     */
    private ViewState state;
    /**
     * The {@link PlayerState} of the player.
     */
    private PlayerState playerState;
    private Timer timer;
    private TimerTask timerTask;
    private boolean skipDrawFlag;
    private boolean reconnectionFlag;

    /**
     * Constructor of the class.
     * It starts a new thread to handle the messages received from the server.
     */
    public TUI() {
        this.messageLock = new Object();
        this.lock = new Object();
        this.reconnectionFlag = false;
        this.skipDrawFlag = false;
        initTUI();
        new Thread(() -> {
            while (true) {
                synchronized (messageLock) {
                    while (messages.isEmpty()) {
                        try {
                            messageLock.wait();
                        } catch (InterruptedException e) {
                            System.err.println("Interrupted while waiting for messages: " + e.getMessage());
                        }
                    }
                    Message m = messages.poll();
                    handleMessage(m);
                }
            }
        }).start();
    }

    /**
     * Method to initialise the messages queue and the two states used by the TUI.
     */
    private void initTUI() {
        this.messages = new LinkedList<>();
        this.state = WAITING_FOR_OUTCOME;
        this.playerState = PlayerState.WAITING;
    }

    /**
     * get the view state
     *
     * @return the view state
     */
    public ViewState getViewState() {
        synchronized (lock) {
            return state;
        }
    }

    /**
     * set the view state
     *
     * @param state the view state
     */
    public void setViewState(ViewState state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    /**
     * getter of the player state
     *
     * @return the player state
     */
    public PlayerState getPlayerState() {
        return playerState;
    }

    /**
     * setter of the player state
     *
     * @param state the player state
     */
    public void setPlayerState(PlayerState state) {
        this.playerState = state;
    }

    @Override
    public void run() {
        displayTitle();
        chooseUsername();
        while (flag) {
            chooseConnection();
        }
        chooseGame();
        setViewState(WAITING_FOR_OUTCOME);
        blocker();
        if (!reconnectionFlag) {
            selectColor();
            setViewState(WAITING_FOR_OUTCOME);
            blocker();
            selectInitial();
            setViewState(WAITING_FOR_OUTCOME);
            blocker();
            clear();
            selectSecretGoal();
            setViewState(WAITING_FOR_OUTCOME);
        }
        while (true) {
            blocker();
            System.out.println("--- NEW TURN ---");
            for (PublicPlayerView p : publicPlayerViews) {
                if (p != null && !p.getUsername().equals(this.username)) {
                    seeOtherPlayerBoard(p);
                }
            }
            clear();
            displayPlayerBoard();
            displayCommonBoard();
            playCard();
            setViewState(WAITING_FOR_OUTCOME);
            blocker();
            if (!skipDrawFlag) {
                clear();
                displayPlayerBoard();
                drawCard();
                setViewState(WAITING_FOR_OUTCOME);
                blocker();
                clear();
                displayPlayerBoard();
                displayPlayerHand();
                displayCommonBoard();
            }
            setViewState(WAITING_FOR_OUTCOME);
            setPlayerState(PlayerState.WAITING);
        }
    }

    /**
     * Handle the messages received from the server.
     *
     * @param m message to handle
     */
    public void handleMessage(Message m) {
        if (m == null) {
            return;
        }
        if (m instanceof UpdateInitialCardMessage) {
            InitialCardView card = ((UpdateInitialCardMessage) m).getInitialCard();
            System.out.println("\nYour initial card has been updated to: ");
            for (int i = 0; i <= 9; i++) {
                if (card.getPlayedSide())
                    System.out.println(card.viewFront(i));
                else
                    System.out.println(card.viewBack(i));
            }
            System.out.println("\nYour inventory:");
            displayInventory(((UpdateInitialCardMessage) m).getInventoryObjects(), ((UpdateInitialCardMessage) m).getInventoryResources());
        } else if (m instanceof UpdateSecretGoalMessage) {
            System.out.println("\nYour secret goal has been updated to: ");
            System.out.println(((UpdateSecretGoalMessage) m).getSecretGoal());
        } else if (m instanceof UpdateColorMessage) {
            System.out.println("\nYour color has been updated to " + ((UpdateColorMessage) m).getToken() + "\n");
        } else if (m instanceof RoundOrderMessage) {
            System.out.println(m);
            int index = 0;
            this.players = new String[((RoundOrderMessage) m).getRoundOrder().length - 1];
            for (int i = 0; i < ((RoundOrderMessage) m).getRoundOrder().length; i++) {
                if (!((RoundOrderMessage) m).getRoundOrder()[i].equals(this.username)) {
                    players[index] = ((RoundOrderMessage) m).getRoundOrder()[i];
                    index++;
                }
            }
        } else if (m instanceof PlayerInGameLobbyMessage) {
            System.out.println(((PlayerInGameLobbyMessage) m).getContent());
        } else if (m instanceof GameCreationFailedMessage) {
            System.out.println(((GameCreationFailedMessage) m).getContent());
        } else if (m instanceof FinalRoundMessage) {
            System.out.println("\nThis is the final round.\n");
        } else if (m instanceof GameCreatedReadyToStartMessage) {
            System.out.println(((GameCreatedReadyToStartMessage) m).getContent());
        } else if (m instanceof GameCreatedWaitingForPlayersMessage) {
            System.out.println(((GameCreatedWaitingForPlayersMessage) m).getContent());
        } else if (m instanceof InvalidNumberOfPlayersMessage) {
            System.out.println(((InvalidNumberOfPlayersMessage) m).getContent());
            startConnection(chooseNumberOfPlayers(), this.username);
        } else if (m instanceof ReconnectionSuccessMessage reconnectionSuccessMessage) {
            reconnectionFlag = true;
            playerView = reconnectionSuccessMessage.getPlayer();
            players = reconnectionSuccessMessage.getRoundOrder();
            publicPlayerViews = reconnectionSuccessMessage.getOthersPlayersView();
            commonBoardView = reconnectionSuccessMessage.getCommonBoard();
            System.out.println("\nReconnection successful.\n");
        } else if (m instanceof ChooseColorMessage) {
            chooseColor = ((ChooseColorMessage) m).getAvailableColors();
            setViewState(WAITING_FOR_PLAYER);
        } else if (m instanceof ChooseGoalMessage) {
            secretGoalCards = (ChooseGoalMessage) m;
            playerView = secretGoalCards.getPlayer();
            publicPlayerViews = secretGoalCards.getOthersPlayersView();
            commonBoardView = secretGoalCards.getCommonBoard();
            setViewState(WAITING_FOR_PLAYER);
        } else if (m instanceof ChooseInitialCardMessage) {
            getInitialCard = (ChooseInitialCardMessage) m;
            setViewState(WAITING_FOR_PLAYER);
        } else if (m instanceof PlayCardMessage) {
            commonBoardView = ((PlayCardMessage) m).getCommonBoard();
            playerView = ((PlayCardMessage) m).getPlayer();
            publicPlayerViews.stream().filter((x) -> x != null).collect(Collectors.toList()).replaceAll(publicPlayerView -> (((PlayCardMessage) m).getOtherPlayers(publicPlayerView.getUsername())));
            setViewState(WAITING_FOR_PLAYER);
        } else if (m instanceof DrawCardMessage) {
            playerView = ((DrawCardMessage) m).getPlayer();
            setViewState(WAITING_FOR_PLAYER);
        } else if (m instanceof PlayerReached20) {
            if (!this.username.equals(((PlayerReached20) m).getPlayer20())) {
                System.out.println("\n" + ((PlayerReached20) m).getPlayer20() + " has reached 20 points. The current round is the second-last round.\n");
            } else {
                System.out.println("\nYou have reached 20 points. The current round is the second-last round.\n");
            }
        } else if (m instanceof UpdateDoneMessage) {
            commonBoardView = ((UpdateDoneMessage) m).getCommonBoardView();
            playerView = ((UpdateDoneMessage) m).getPlayerView();
            for (int k = 0; k < publicPlayerViews.size(); k++) {
                if (publicPlayerViews.get(k) != null && publicPlayerViews.get(k).getUsername().equals(this.username)) {
                    publicPlayerViews.set(k, ((UpdateDoneMessage) m).getPublicPlayerView());
                }
            }
            if (playerState == PlayerState.DRAWING_CARD) {
                setViewState(WAITING_FOR_PLAYER);
            }
        } else if (m instanceof OtherPlayerPlayedCardMessage) {
            clear();
            printLine();
            System.out.println("\nPlayer " + ((OtherPlayerPlayedCardMessage) m).getPlayer().getUsername() + " played\n");
            for (int i = 0; i <= 9; i++) {
                if (((OtherPlayerPlayedCardMessage) m).getHidden()) {
                    System.out.println(colourString(((OtherPlayerPlayedCardMessage) m).getPlayedCard().viewBack(i), ((OtherPlayerPlayedCardMessage) m).getPlayedCard().getType()));
                } else {
                    System.out.println(colourString(((OtherPlayerPlayedCardMessage) m).getPlayedCard().viewFront(i), ((OtherPlayerPlayedCardMessage) m).getPlayedCard().getType()));
                }
            }
            for (int k = 0; k < publicPlayerViews.size(); k++) {
                if (publicPlayerViews.get(k) != null && publicPlayerViews.get(k).getUsername().equals(((OtherPlayerPlayedCardMessage) m).getUsername())) {
                    publicPlayerViews.set(k, ((OtherPlayerPlayedCardMessage) m).getPlayer());
                }
            }
            seeOtherPlayerBoard(((OtherPlayerPlayedCardMessage) m).getPlayer());
        } else if (m instanceof OtherPlayerDrawnCardMessage) {
            clear();
            printLine();
            if (((OtherPlayerDrawnCardMessage) m).getIsCasual()) {
                System.out.println("\nA casual card has been drawn for the player " + ((OtherPlayerDrawnCardMessage) m).getPlayer().getUsername() + "\n");
            } else {
                System.out.println("\nPlayer " + ((OtherPlayerDrawnCardMessage) m).getPlayer().getUsername() + " has drawn:\n");
            }
            for (int i = 0; i <= 9; i++) {
                if (((OtherPlayerDrawnCardMessage) m).getHidden()) {
                    System.out.println(colourString(((OtherPlayerDrawnCardMessage) m).getDrawnCard().viewBack(i), ((OtherPlayerDrawnCardMessage) m).getDrawnCard().getType()));
                } else {
                    System.out.println(colourString(((OtherPlayerDrawnCardMessage) m).getDrawnCard().viewFront(i), ((OtherPlayerDrawnCardMessage) m).getDrawnCard().getType()));
                }
            }
            for (int k = 0; k < publicPlayerViews.size(); k++) {
                if (publicPlayerViews.get(k) != null && publicPlayerViews.get(k).getUsername().equals(((OtherPlayerDrawnCardMessage) m).getUsername())) {
                    publicPlayerViews.set(k, ((OtherPlayerDrawnCardMessage) m).getPlayer());
                }
            }
            commonBoardView = ((OtherPlayerDrawnCardMessage) m).getCommonBoardView();
            displayCommonBoard();
        } else if (m instanceof WrongPlayMessage) {
            System.out.println(m);
            switch (getPlayerState()) {
                case PLAYING_CARD -> playCard();
                case DRAWING_CARD -> drawCard();
                case SETTING_COLOR -> selectColor();
                case SETTING_INITIAL -> selectInitial();
                case SETTING_GOAL -> selectSecretGoal();
            }
        } else if (m instanceof TurnMessage) {
            System.out.println("\n" + ((TurnMessage) m).getUsername() + " is playing.\n");
        } else if (m instanceof EmptyDecksMessage) {
            System.err.println("\nThe decks are empty. This will be the second last turn.\n");
        } else if (m instanceof NoPlayablePositionsMessage) {
            System.err.println("\nYou have no more playable positions. From now on you will be a spectator.\n");
        } else if (m instanceof AvailableLobbiesMessage) {
            chooseLobby(((AvailableLobbiesMessage) m).getAvailableLobbies());
        } else if (m instanceof NoAvailableLobbiesMessage) {
            System.out.println("\n" + m + " Please create a new game.\n");
            notifyListeners(new CreateGameMessage(this.username, chooseNumberOfPlayers()));
        } else if (m instanceof PlayerLeftMessage) {
            printLine();
            System.out.println(((PlayerLeftMessage) m).getPlayer() + " has left the game.");
        } else if (m instanceof UsernameAlreadyExistsMessage) {
            System.out.println("\nUsername already exists. Please choose a new one.");
            System.out.println("Suggestions: " + ((UsernameAlreadyExistsMessage) m).getUsername());
            chooseUsername();
            chooseGame();
        } else if (m instanceof ReconnectionFailedMessage) {
            System.out.println("\nReconnection failed. Please join or create a game.\n");
            chooseGame();
        } else if (m instanceof PlayerReconnectedMessage) {
            System.out.println("\nPlayer " + ((PlayerReconnectedMessage) m).getPlayer() + " has reconnected to the game.\n");
        } else if (m instanceof GameInWaitMessage) {
            System.out.println("\nOther players have left the game. Wait for them to reconnect in order to continue\n");
        } else if (m instanceof JoinGameFailedMessage) {
            System.out.println("\nJoining the game failed. Please try again.\n");
            chooseGame();
        } else if (m instanceof NoDrawableCardMessage) {
            System.err.println("\nThere are no more cards to be drawn. You'll skip the draw phase.\n");
            skipDrawFlag = true;
            setViewState(WAITING_FOR_PLAYER);
        }
    }

    @Override
    public void update(Message m) {
        if (m instanceof PingMessage) {
            handlePingMessage((PingMessage) m);
            return;
        } else if (m instanceof EndGameMessage) {
            endGameView = ((EndGameMessage) m).getFinalPVs();
            setViewState(GAME_ENDED);
            clear();
            endGame();
            return;
        }
        synchronized (messageLock) {
            messages.add(m);
            messageLock.notifyAll();
        }
    }

    /**
     * handle the ping message.
     * It sends a {@link PongMessage} to the server and sets a timer to check if the server is still reachable.
     * If the server is not reachable, it closes the client.
     *
     * @param m the ping message
     */
    public void handlePingMessage(PingMessage m) {
        notifyListeners(new PongMessage(this.username, m.getPingNumber()));
        if (timer != null) {
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Unable to reach the server. Please try to reconnect.");
                System.exit(0);
            }
        };
        timer.schedule(timerTask, 17000);
    }

    /**
     * Methods to block the run method until a message is received from the server.
     */
    private void blocker() {
        while (getViewState() != WAITING_FOR_PLAYER) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.err.println("Interrupted while waiting for server: " + e.getMessage());
                }
            }
        }
    }

    /**
     * It displays the title of the game.
     */
    private void displayTitle() {
        System.out.println("    _______        ,-----.      ______          .-''-.    _____     __");
        System.out.println("   /   __  \\     .'  \u001B[32m.-,\u001B[0m  '.   |    \u001B[34m_\u001B[0m `''.    .'\u001B[35m_ _\u001B[0m   \\   \\   \u001B[31m_\u001B[0m\\   /  /");
        System.out.println("  | \u001B[31m,_\u001B[0m/  \\__)   / \u001B[32m,-.|  \\ _\u001B[0m \\  | \u001B[34m_ | ) _\u001B[0m  \\  / \u001B[35m( ` )\u001B[0m   '  \u001B[31m.-./ ).\u001B[0m /  '");
        System.out.println("\u001B[31m,-./  )\u001B[0m        ;  \u001B[32m\\  '_ /  |\u001B[0m : |\u001B[34m( ''_'  )\u001B[0m | . \u001B[35m(_ o _)\u001B[0m  |  \u001B[31m\\ '_ .')\u001B[0m .'");
        System.out.println("\u001B[31m\\  '_ '`)\u001B[0m      |  \u001B[32m_`,/ \\ _/\u001B[0m  | | \u001B[34m. (_) `.\u001B[0m | |  \u001B[35m(_,_)\u001B[0m___| \u001B[31m(_ (_) _)\u001B[0m '");
        System.out.println("\u001B[31m > (_)  )\u001B[0m  __  : \u001B[32m(  '\\_/ \\\u001B[0m   ; |\u001B[34m(_    ._)\u001B[0m ' '  \\    .--.   \u001B[31m/    \\\u001B[0m   \\");
        System.out.println("\u001B[31m(  .  .-'\u001B[0m_/  )  \\ \u001B[32m`\"/  \\  )\u001B[0m /  |  \u001B[34m(_.\\\u001B[0m.' /   \\  \\   |  /   \u001B[31m`-'`-'\u001B[0m    \\");
        System.out.println("\u001B[31m `-'`-'\u001B[0m     /    '. \u001B[32m\\_/``\"\u001B[0m.'   |       .'     \\  `-'  /   /  /   \\    \\");
        System.out.println("  `._____.')       '-----'     '-----'`        `'-..-'   '--'     '----'");
        System.out.println(",---.   .--.    ____     ,---------.    ___    _  .-------.        ____       .---.      \u001B[31m.-./`)\u001B[0m     .-'''-.  ");
        System.out.println("|    \\  |  |  /   __ `.  \\          \\ .'   |  | | |  \u001B[32m_ _\u001B[0m   \\     /   __ `.    | \u001B[35m,_\u001B[0m|      \u001B[31m\\ .-.')\u001B[0m   / \u001B[32m_\u001B[0m     \\ ");
        System.out.println("|  ,  \\ |  | /  /   \\  \\  `--.   ,--' |   .'  | | | \u001B[32m( ' )\u001B[0m  |    /  |   \\  \\ \u001B[35m,-./  )\u001B[0m      \u001B[31m/ `-' \\\u001B[0m  \u001B[32m(`' )\u001B[0m/`--' ");
        System.out.println("|  |\\\u001B[32m_\u001B[0m \\|  | |__|   /  |     |   |    |   '_  | | |\u001B[32m(_ o _)\u001B[0m /    |__|   /  | \u001B[35m\\  '_ '`)\u001B[0m     \u001B[31m`-'`\"`\u001B[0m \u001B[32m(_ o _)\u001B[0m.    ");
        System.out.println("|  \u001B[32m_( )_\u001B[0m\\  |    _.-`   |     |\u001B[35m_ _\u001B[0m|    |   \u001B[31m( \\.-.\u001B[0m| | \u001B[32m(_,_)\u001B[0m.' __     _.-`   |  \u001B[35m> (_)  )\u001B[0m     .---.   \u001B[32m(_,_)\u001B[0m. '.  ");
        System.out.println("| \u001B[32m(_ o _)\u001B[0m  | .'   \u001B[34m_\u001B[0m    |     \u001B[35m(_I_)\u001B[0m    | \u001B[31m( \\ _` /\u001B[0m| |  |\\ \\  |  | .'   \u001B[34m_\u001B[0m    | \u001B[35m(  .  .-'\u001B[0m     |   |  .---.  \\  : ");
        System.out.println("|  \u001B[032m(_,_)\u001B[0m\\  | |  \u001B[34m_( )_\u001B[0m  |    \u001B[35m(_(=)_)\u001B[0m   | \u001B[31m(_ (_) _)\u001B[0m |  | \\ `'   / |  \u001B[34m_( )_\u001B[0m  |  \u001B[35m`-'`-'\u001B[0m|___   |   |  \\    `-'  | ");
        System.out.println("|  |    |  | \\ \u001B[34m(_ o _)\u001B[0m /     \u001B[35m(_I_)\u001B[0m     \\ \u001B[31m/  . \\\u001B[0m / |  |  \\    /  \\ \u001B[34m(_ o _)\u001B[0m /   |        \\  |   |   \\       /  ");
        System.out.println("'--'    '--'  '.\u001B[34m(_,_)\u001B[0m.'      '---'      `\u001B[31m`-'`-'\u001B[0m'  '--'   `--'    '.\u001B[34m(_,_)\u001B[0m.'    `--------`  '---'    `-...-'");
        System.out.println("\nWelcome to the game of Codex Naturalis!\n");
    }

    /**
     * Method used to set the username of the player.
     */
    private void chooseUsername() {
        Scanner s = new Scanner(System.in);
        do {
            System.out.println(colourRequestsToPlayer("Choose a Username"));
            this.username = s.next();
            if (this.username.contains(" ")) {
                System.out.println("Invalid username. Please choose a username without spaces.");
            }
        } while (this.username.contains(" ") || this.username.isEmpty());
    }

    /**
     * Method used to choose to start, to join or to reconnect to a game.
     */
    private void chooseGame() {
        Scanner s = new Scanner(System.in);
        System.out.println(colourRequestsToPlayer("Choose to start a new game [1] , join an already existing game [2] or reconnect to an already existing game [3]"));
        while (true) {
            String input = s.next();
            try {
                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > 3) {
                    System.out.println("Invalid choice. Please choose a valid alternative between 1 and 2");
                } else {
                    if (choice == 1) {
                        startConnection(chooseNumberOfPlayers(), this.username);
                    } else if (choice == 2) {
                        startConnection(0, this.username);
                    } else {
                        System.out.println("Reconnecting...");
                        notifyListeners(new ReconnectMessage(this.username));
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose a number between 1 and 2");
            }
        }
    }

    /**
     * Method used to select the lobby to join.
     *
     * @param gameLobbies the list of available game lobbies
     */
    private void chooseLobby(ArrayList<GameLobbyView> gameLobbies) {
        Scanner s = new Scanner(System.in);
        System.out.println(colourRequestsToPlayer("Choose a lobby to join: "));
        for (int i = 0; i < gameLobbies.size(); i++) {
            System.out.println(colourRequestsToPlayer(String.valueOf(i + 1)) + " - ");
            System.out.println("Number of players requested: " + gameLobbies.get(i).getLobbySize());
            System.out.println("Players in the lobby: ");
            for (String player : gameLobbies.get(i).getLobbyPlayers()) {
                System.out.println(player);
            }
        }
        while (true) {
            String input = s.next();
            try {
                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > gameLobbies.size()) {
                    System.out.println("Invalid choice. Please choose a valid alternative between 1 and " + gameLobbies.size());
                } else {
                    notifyListeners(new JoinGameMessage(this.username, gameLobbies.get(choice - 1).getLobbyName()));
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose a number between 1 and " + gameLobbies.size());
            }
        }
    }

    /**
     * Method used to choose the number of players, if the player has created a new game.
     */
    private int chooseNumberOfPlayers() {
        Scanner s = new Scanner(System.in);
        System.out.println(colourRequestsToPlayer("Choose the number of players: "));
        while (true) {
            String input = s.next();
            try {
                int players = Integer.parseInt(input);
                if (players < 2 || players > 4) {
                    System.out.println("Invalid number of players. Please choose a number between 2 and 4.");
                } else {
                    return players;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose a number between 2 and 4.");
            }
        }
    }

    /**
     * Method to choose the connection type and call the right setup method.
     */
    private void chooseConnection() {
        Scanner s = new Scanner(System.in);
        System.out.println(colourRequestsToPlayer("Choose connection: "));
        System.out.println(colourRequestsToPlayer("1 - ") + "Socket");
        System.out.println(colourRequestsToPlayer("2 - ") + "RMI");
        while (true) {
            String input = s.next();
            try {
                int connection = Integer.parseInt(input);
                if (connection < 1 || connection > 2) {
                    System.out.println("Invalid connection number. Please choose a connection between 1 and 2.");
                } else {
                    System.out.println(colourRequestsToPlayer("Enter the server IP address: "));
                    while (true) {
                        String ip = s.next();
                        try {
                            if (ip == null || ip.isEmpty()) {
                                System.out.println("Invalid IP address. Please enter a valid IP address.");
                            } else if (connection == 1) {
                                System.out.println("Connecting with socket...");
                                try {
                                    setUpSocketConnection(ip, 1234);
                                    flag = false;
                                    break;
                                } catch (RemoteException e) {
                                    System.out.println("Connection failed. Invalid IP address or server not reachable. Please try again.");
                                    flag = true;
                                    break;
                                }
                            } else {
                                System.out.println("Connecting with RMI...");
                                try {
                                    setUpRMIConnection(ip, 1099);
                                    flag = false;
                                    break;
                                } catch (IllegalArgumentException e) {
                                    throw new IllegalArgumentException(e);
                                } catch (RemoteException e) {
                                    System.out.println("Connection failed. Invalid IP address or server not reachable. Please try again.");
                                    flag = true;
                                    break;
                                } catch (NotBoundException e) {
                                    System.out.println("Connection failed. Server not reachable. Please try again.");
                                    flag = true;
                                    break;
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please choose a connection between 1 and 2.");
                        }
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose a connection between 1 and 2.");
            }
        }

    }

    /**
     * Method used to select the color of the player and send the {@link ChosenColorMessage} to the server
     */
    private void selectColor() {
        setPlayerState(PlayerState.SETTING_COLOR);
        Scanner s = new Scanner(System.in);
        System.out.println(colourRequestsToPlayer("\nSelect your color: "));
        for (int i = 0; i < chooseColor.size(); i++) {
            System.out.println(colourRequestsToPlayer(String.valueOf((i + 1))) + colourRequestsToPlayer(" - ") + chooseColor.get(i));
        }
        while (true) {
            String input = s.next();
            try {
                int color = Integer.parseInt(input);
                if (color < 1 || color > chooseColor.size()) {
                    System.out.println("Invalid color number. Please choose a color between 1 and 4.");
                } else {
                    notifyListeners(new ChosenColorMessage(chooseColor.get(color - 1), this.username));
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose a color between 1 and 4.");
            }
        }
    }

    /**
     * Method used to select the play side of the initial card and send the {@link ChosenInitialCardMessage} to the server
     */
    private void selectInitial() {
        setPlayerState(PlayerState.SETTING_INITIAL);
        Scanner s = new Scanner(System.in);
        System.out.println("Select the play side of your initial card: ");
        for (int i = 0; i <= 9; i++) {
            System.out.println(getInitialCard.getInitialCard().viewFront(i) + getInitialCard.getInitialCard().viewBack(i));
        }
        while (true) {
            String input = s.next();
            try {
                int side = Integer.parseInt(input);
                if (side < 1 || side > 2) {
                    System.out.println("Invalid side number. Please choose a side between 1 and 2.");
                } else {
                    if (side == 1) {
                        notifyListeners(new ChosenInitialCardMessage(true, this.username));
                    } else {
                        notifyListeners(new ChosenInitialCardMessage(false, this.username));
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose a side between 1 and 2.");
            }
        }
    }

    /**
     * Method used to select the secret goal of the player and send the {@link ChosenGoalMessage} to the server
     */
    private void selectSecretGoal() {
        setPlayerState(PlayerState.SETTING_GOAL);
        Scanner s = new Scanner(System.in);
        for (PublicPlayerView publicPlayerView : secretGoalCards.getOthersPlayersView()) {
            if (publicPlayerView != null && !publicPlayerView.getUsername().equals(this.username)) {
                seeOtherPlayerBoard(publicPlayerView);
            }
        }
        displayPlayerBoard();
        System.out.println("\n");
        displayCommonBoard();
        System.out.println(colourRequestsToPlayer("Select your secret goal: ") +
                colourRequestsToPlayer("\n1 -\n") + secretGoalCards.getGoals()[0].getGoalCard() +
                colourRequestsToPlayer("\n2 -\n") + secretGoalCards.getGoals()[1].getGoalCard());
        while (true) {
            String input = s.next();
            try {
                int goal = Integer.parseInt(input);
                if (goal < 1 || goal > 2) {
                    System.out.println("Invalid goal number. Please choose a goal between 1 and 2.");
                } else {
                    notifyListeners(new ChosenGoalMessage(goal, this.username));
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose a goal between 1 and 2.");
            }
        }
    }

    /**
     * Method that displays the player board
     */
    private void displayPlayerBoard() {
        printLine();
        printCenteredLine(makeBold("Your board"));
        printLine();
        System.out.println("Points: " + playerView.getScore());
        PublicPlayerView ppv = null;
        for (PublicPlayerView p : publicPlayerViews) {
            if (p != null && p.getUsername().equals(this.username)) {
                ppv = p;
            }
        }
        System.out.println(makeItalics("\nPlayed cards: "));
        assert ppv != null;
        System.out.println("Your" + makeBold(" Initial card ") + "is played at (0;0) coordinate, " + ppv.getInitialCard().toString());
        if (ppv.getPlayedCards() == null) {
            System.out.println("No played cards yet");
        } else {
            for (ResourceCardView c : ppv.getPlayedCards().keySet()) {
                System.out.println("Type: " + colourString(String.valueOf(c.getType()), c.getType()) + ", " + c + " at " + ppv.getPlayedCards().get(c) + " coordinate");
            }
        }

        System.out.println(makeItalics("\nResources: "));
        for (Resource r : Resource.values()) {
            System.out.println("Total number of " + colourString(r.toString(), r) + " resources: " + ppv.getInventoryResources().get(r));
        }
        System.out.println(makeItalics("\nObjects: "));
        for (TypeObject o : TypeObject.values()) {
            System.out.println("Total number of " + o + " objects: " + ppv.getInventoryObjects().get(o));
        }
        if (playerView.getSecretGoal() != null) {
            System.out.println(makeItalics("\nSecret Goal: \n") + playerView.getSecretGoal());
        }
        System.out.println(makeItalics("\nPlayable positions: "));
        for (CoordinateView c : playerView.getPlayablePositions()) {
            System.out.println(c);
        }
    }

    /**
     * Method that displays the common board using the {@link CommonBoardView}
     */
    private void displayCommonBoard() {
        printLine();
        printCenteredLine(makeBold("Common board"));
        printLine();
        System.out.println(makeItalics("Gold deck"));
        if (commonBoardView.isGoldDeckEmpty()) {
            System.out.println("\n\tGold deck is empty");
        } else {
            for (int i = 1; i <= 9; i++) {
                System.out.println(colourString(commonBoardView.getFirstGoldDeckCard().viewBack(i), commonBoardView.getFirstGoldCard()));
            }
        }
        System.out.println(makeItalics("\nResource deck"));
        if (commonBoardView.isResourceDeckEmpty()) {
            System.out.println("\n\tResource deck is empty");
        } else {
            for (int i = 1; i <= 9; i++) {
                System.out.println(colourString(commonBoardView.getFirstResourceDeckCard().viewBack(i), commonBoardView.getFirstResCard()));
            }
        }
        System.out.println(makeItalics("\nVisible Resource Cards: "));
        if (commonBoardView.getVisibleResources().isEmpty()) {
            System.out.println("\n\tNo visible resource cards");
        } else {
            for (int j = 1; j <= 9; j++) {
                for (int i = 0; i < commonBoardView.getVisibleResources().size(); i++) {
                    System.out.print(colourString(commonBoardView.getVisibleResources().get(i).viewFront(j), commonBoardView.getVisibleResources().get(i).getType()));
                }
                System.out.print("\n");
            }
        }
        System.out.println(makeItalics("\nVisible Gold Cards: "));
        if (commonBoardView.getVisibleGold().isEmpty()) {
            System.out.println("\n\tNo visible gold cards");
        } else {
            for (int j = 1; j <= 9; j++) {
                for (int i = 0; i < commonBoardView.getVisibleGold().size(); i++) {
                    System.out.print(colourString(commonBoardView.getVisibleGold().get(i).viewFront(j), commonBoardView.getVisibleGold().get(i).getType()));
                }
                System.out.print("\n");
            }
        }
        System.out.println(makeItalics("\nCommon goals: \n\n") + commonBoardView.getCommonGoals().get(0) + "\n\n" + commonBoardView.getCommonGoals().get(1));
        printLine();
    }

    /**
     * Method that makes the player play a card from his hand and send the {@link PlayedCardMessage} to the server.
     * It checks if the card is playable and if the position is valid.
     */
    private void playCard() {
        setPlayerState(PlayerState.PLAYING_CARD);
        int coordinateX = 0;
        int coordinateY;
        CoordinateView coordinate;
        Scanner s = new Scanner(System.in);
        System.out.println(colourRequestsToPlayer("Play a card"));
        System.out.println("Hand: ");
        int i = 0;
        for (i = 0; i <= 9; i++) {
            for (int j = 0; j < playerView.getHand().size(); j++) {
                if (i == 0) {
                    System.out.print(STR."\{j + 1} - \{colourString(playerView.getHand().get(j).viewFront(i), playerView.getHand().get(j).getType())}");
                } else {
                    System.out.print(STR."\{colourString(playerView.getHand().get(j).viewFront(i), playerView.getHand().get(j).getType())}");
                }
            }
            System.out.print("\n");
        }
        while (true) {
            String input = s.next();
            try {
                int card = Integer.parseInt(input);
                if (card < 1 || card > i + 2) {
                    System.out.println("Invalid card number. Please choose a card between 1 and 3.");
                } else {
                    System.out.println("Playable positions: ");
                    for (CoordinateView c : playerView.getPlayablePositions()) {
                        System.out.println(c);
                    }
                    System.out.println("Select the position to play the card on: ");
                    while (true) {
                        System.out.println("X: ");
                        input = s.next();
                        try {
                            coordinateX = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please choose a valid position.");
                        }
                        System.out.println("Y: ");
                        input = s.next();
                        try {
                            coordinateY = Integer.parseInt(input);
                            int boardSize = 81;
                            coordinate = new CoordinateView(coordinateX + boardSize / 2, coordinateY * (-1) + boardSize / 2, boardSize);
                            boolean playCardFlag = true;
                            for (CoordinateView c : playerView.getPlayablePositions()) {
                                if (c.getX() == coordinateX + boardSize / 2 && c.getY() == coordinateY * (-1) + boardSize / 2) {
                                    playCardFlag = false;
                                    break;
                                }
                            }
                            if (playCardFlag) {
                                System.out.println("Invalid coordinate. Please choose a valid position.");
                            } else {
                                System.out.println("Select the play side of the card: \n1- front \n2- back");
                                while (true) {
                                    input = s.next();
                                    try {
                                        int side = Integer.parseInt(input);
                                        if (side < 1 || side > 2) {
                                            System.out.println("Invalid number. Please choose a valid side.");
                                        } else {
                                            if (side == 1) {
                                                playCard = new PlayedCardMessage(this.username, coordinate, card, true);
                                            } else {
                                                playCard = new PlayedCardMessage(this.username, coordinate, card, false);
                                            }
                                            notifyListeners(playCard);
                                            break;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid input. Please choose a valid position.");
                                    }
                                }
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please choose a valid position.");
                        }
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please choose a card between 1 and 3.");
            }
        }

    }

    /**
     * Methods that makes the player draw a card from the {@link CommonBoardView} and send the {@link DrawnCardMessage} to the server.
     * It checks how many cards are available. If there are no cards available, the player skips the draw phase.
     */
    private void drawCard() {
        printLine();
        setPlayerState(PlayerState.DRAWING_CARD);
        Scanner s = new Scanner(System.in);
        Set<Integer> choice = new HashSet<>(Set.of(1, 2, 3, 4, 5, 6));
        System.out.println(colourRequestsToPlayer("Draw a card: "));
        if (!commonBoardView.isGoldDeckEmpty()) {
            System.out.println(colourRequestsToPlayer("\n1 - ") + "Gold Deck: ");
            for (int i = 1; i <= 9; i++) {
                System.out.println(colourString(commonBoardView.getFirstGoldDeckCard().viewBack(i), commonBoardView.getFirstGoldCard()));
            }
        } else {
            System.out.println("\nGold deck is empty");
            choice.remove(1);
        }
        if (!commonBoardView.isResourceDeckEmpty()) {
            System.out.println(colourRequestsToPlayer("\n2 - ") + "Resource Deck: ");
            for (int i = 1; i <= 9; i++) {
                System.out.println(colourString(commonBoardView.getFirstResourceDeckCard().viewBack(i), commonBoardView.getFirstResCard()));
            }
        } else {
            System.out.println("\nResource deck is empty");
            choice.remove(2);
        }
        if (commonBoardView.getVisibleResources().isEmpty()) {
            System.out.println("\nNo visible resource cards");
            choice.remove(3);
            choice.remove(4);
        } else {
            System.out.println("\nVisible Resource Cards: ");
            for (int j = 0; j <= 9; j++) {
                for (int i = 0; i < commonBoardView.getVisibleResources().size(); i++) {
                    if (j == 0) {
                        System.out.print(colourRequestsToPlayer(String.valueOf(i + 3)) + colourRequestsToPlayer(" -                          \t"));
                    } else {
                        System.out.print(colourString(commonBoardView.getVisibleResources().get(i).viewFront(j), commonBoardView.getVisibleResources().get(i).getType()));
                    }
                }
                System.out.print("\n");
            }
            if (commonBoardView.getVisibleResources().size() < 2) {
                choice.remove(4);
            }
        }
        if (commonBoardView.getVisibleGold().isEmpty()) {
            System.out.println("\nNo visible gold cards");
            choice.remove(5);
            choice.remove(6);
        } else {
            System.out.println("\nVisible Gold Cards: ");
            for (int j = 0; j <= 9; j++) {
                for (int i = 0; i < commonBoardView.getVisibleGold().size(); i++) {
                    if (j == 0) {
                        System.out.print(colourRequestsToPlayer(String.valueOf(i + 5)) + colourRequestsToPlayer(" -                          \t"));
                    } else {
                        System.out.print(colourString(commonBoardView.getVisibleGold().get(i).viewFront(j), commonBoardView.getVisibleGold().get(i).getType()));
                    }
                }
                System.out.print("\n");
            }
            if (commonBoardView.getVisibleGold().size() < 2) {
                choice.remove(6);
            }
        }
        while (true) {
            if( choice.isEmpty() ) {
                System.err.println("\nThere are no more cards to be drawn. You'll skip the draw phase.");
                break;
            }
            String input = s.next();
            try {
                int card = Integer.parseInt(input);
                if (!choice.contains(card)) {
                    System.out.println("Invalid card number. Please choose a valid card.");
                } else {
                    notifyListeners(new DrawnCardMessage(card, this.username));
                    break;
                }
            } catch (
                    NumberFormatException e) {
                System.out.println("Invalid input. Please choose a valid card.");
            }
        }
    }

    /**
     * Method that shows the other players' board
     */
    private void seeOtherPlayerBoard(PublicPlayerView p) {
        if (p.getUsername().equals(this.username)) {
            return;
        }
        printLine();
        printCenteredLine(makeBold(p.getUsername() + "'s board"));
        printLine();
        System.out.println("Player " + makeItalics(p.getUsername()) + " has " + p.getScore() + " points,");

        displayInventory(p.getInventoryObjects(), p.getInventoryResources());

        System.out.println(makeItalics("\nHand: "));

        for (int i = 0; i <= 9; i++) {
            for (ResourceCardView r : p.getBackResourceHand()) {
                System.out.print(STR."\{colourString(r.viewBack(i), r.getType())}");
            }
            for (GoldCardView g : p.getBackGoldHand()) {
                System.out.print(STR."\{colourString(g.viewBack(i), g.getType())}");
            }
            System.out.print("\n");
        }

        System.out.println(makeItalics("\nPlayed cards: "));
        if (p.getPlayedCards().isEmpty()) {
            System.out.println("No played cards yet\n");
        } else {
            for (ResourceCardView c : p.getPlayedCards().keySet()) {
                System.out.println("Type: " + colourString(String.valueOf(c.getType()), c.getType()) + ", " + c + " at " + p.getPlayedCards().get(c) + " coordinate");
            }
        }
    }

    /**
     * Method that shows the inventory of a player.
     *
     * @param objects   a map with the three types of object and the quantity held by the player for each type
     * @param resources a map with the four types of resource and the quantity held by the player for each type
     */
    private void displayInventory(Map<TypeObject, Integer> objects, Map<Resource, Integer> resources) {
        System.out.println(makeItalics("\nResources: "));
        for (Resource r : Resource.values()) {
            System.out.println("Total number of " + colourString(r.toString(), r) + " resources: " + resources.get(r));
        }
        System.out.println(makeItalics("\nObjects: "));
        for (TypeObject o : TypeObject.values()) {
            System.out.println("Total number of " + o + " objects: " + objects.get(o));
        }
    }

    /**
     * Method that shows the player's hand
     */
    private void displayPlayerHand() {
        System.out.println("\nYour hand:");
        for (int i = 1; i <= 9; i++) {
            for (int j = 0; j < playerView.getHand().size(); j++) {
                System.out.print(STR."\{colourString(playerView.getHand().get(j).viewFront(i), playerView.getHand().get(j).getType())}");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    /**
     * Method that displays to console the result of the game, with the score of each player and the winner/s
     */
    private void endGame() {
        printLine();
        System.out.println("\nGame ended");
        for (PlayerView p : endGameView) {
            System.out.println("Player " + p.getToken() + " " + p.getUsername() + " has " + p.getScore() + " points. \n" +
                    "Secret goal: \n" + p.getSecretGoal() + " with " + p.getSecretGoalScore() + " points. \n" +
                    "Common goal: \n" + commonBoardView.getCommonGoals().get(0) + " with " + p.getCommonGoalsScore().get(commonBoardView.getCommonGoals().get(0)) + " points. \n" +
                    "Common goal: \n" + commonBoardView.getCommonGoals().get(1) + " with " + p.getCommonGoalsScore().get(commonBoardView.getCommonGoals().get(1)) + " points. \n");
        }
        List<PlayerView> winners;
        winners = endGameView.stream()
                .filter(p -> p.getState().equals(WINNER))
                .collect(Collectors.toList());
        if (winners.size() == 1) {
            System.out.println("The winner is: " + winners.get(0).getUsername() + " " + winners.get(0).getToken());
        } else {
            System.out.println("The winners are: ");
            for (PlayerView p : winners) {
                System.out.println(p.getUsername() + " " + p.getToken());
            }
        }
        System.exit(0);
    }

    /**
     * Method used to print a line with a string in the middle.
     *
     * @param line the string to print
     */
    private void printCenteredLine(String line) {
        int totalWidth = 180;
        int paddingSize = (totalWidth - line.length()) / 2;
        String padding = " ".repeat(paddingSize);
        System.out.println(padding + line + padding);
    }

    /**
     * Method used to print a line used as a separator.
     */
    private void printLine() {
        System.out.println("-".repeat(180));
    }

    /**
     * Method used to make a string bold.
     *
     * @param s the string to make bold
     * @return the bold string
     */
    private String makeBold(String s) {
        return "\033[1m" + s + "\033[0m";
    }

    /**
     * Method used to make a string italic.
     *
     * @param s the string to make italic
     * @return the italic string
     */
    private String makeItalics(String s) {
        return "\033[3m" + s + "\033[0m";
    }

    /**
     * Method used to colour a string based on the type of the card saved as Resource.
     *
     * @param s      the string to colour
     * @param colour the type of the card
     * @return the coloured string
     */
    private String colourString(String s, Resource colour) {
        return switch (colour) {
            case FUNGI -> "\u001B[31m" + s + "\u001B[0m";
            case PLANT -> "\u001B[32m" + s + "\u001B[0m";
            case ANIMAL -> "\u001B[34m" + s + "\u001B[0m";
            case INSECT -> "\u001B[35m" + s + "\u001B[0m";
        };
    }

    /**
     * Method used to colour a string based on the type of the card saved as a string.
     *
     * @param s      the string to colour
     * @param colour the type of the card
     * @return the coloured string
     */
    private String colourString(String s, String colour) {
        return switch (colour) {
            case "Fungi" -> "\u001B[31m" + s + "\u001B[0m";
            case "Plant" -> "\u001B[32m" + s + "\u001B[0m";
            case "Animal" -> "\u001B[34m" + s + "\u001B[0m";
            case "Insect" -> "\u001B[35m" + s + "\u001B[0m";
            default -> throw new IllegalStateException("Unexpected value: " + colour);
        };
    }

    /**
     * Method used to colour a title string.
     *
     * @param s the string to colour
     * @return the coloured string
     */
    private String colourRequestsToPlayer(String s) {
        return "\u001B[33m" + s + "\u001B[0m";
    }

    /**
     * Method used to clear the console after every step of the game.
     */
    private void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                System.out.println("\033[H\033[2J");
        } catch (Exception ignored) {
        }
    }
}