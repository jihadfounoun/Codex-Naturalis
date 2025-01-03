package it.polimi.ingsw.Model;

import it.polimi.ingsw.Mappers.ModelViewMapper;
import it.polimi.ingsw.Model.Card.*;
import it.polimi.ingsw.Model.CommonBoard.CommonBoard;
import it.polimi.ingsw.Model.CommonBoard.Deck.GoldDeck;
import it.polimi.ingsw.Model.CommonBoard.Deck.InitialDeck;
import it.polimi.ingsw.Model.CommonBoard.Deck.ResourceDeck;
import it.polimi.ingsw.Model.GoalCard.GoalCard;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.State;
import it.polimi.ingsw.ModelView.PlayerView.PlayerView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * This class represents the game in the Codex Naturalis application.
 * It manages the game state, player actions, and game state transitions.
 * @author Jihad Founoun
 */
public class Game {
    private final ArrayList<Player> players;
    private final int numPlayers;
    private final CommonBoard commonBoard;
    private final int TOTAL_NUMBER_CARDS = 80;
    private final ModelViewMapper modelViewMapper;
    private final int boardSize;
    private GameState state;

    /**
     * Constructor for the Game class.
     * @param numPlayers the number of players in the game
     * @param players the list of players in the game
     */
    public Game(int numPlayers, ArrayList<String> players) {
        this.modelViewMapper = new ModelViewMapper();
        this.numPlayers = numPlayers;
        this.boardSize = 81;
        try {
            this.commonBoard = new CommonBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.players = new ArrayList<>(numPlayers);
        for (String player : players) {
            this.players.add(new Player(player));
        }
        this.state = GameState.WAITING;

    }
    /**
     * Gets the common board of the game.
     * @return the common board
     */
    public CommonBoard getCommonBoard() {
        return commonBoard;
    }
    /**
     * Sets up the players' items for the game.
     */
    public void setUpPlayersItems() {
        InitialDeck initialDeck = commonBoard.getInitialDeck();
        GoldDeck goldDeck = commonBoard.getGoldDeck();
        ResourceDeck resourceDeck = commonBoard.getResourceDeck();
        for (Player player : players) {
            ArrayList<PlayableCard> hand = new ArrayList<>();
            hand.add((PlayableCard) goldDeck.getFirstCard());
            for (int i = 0; i < 2; i++) {
                hand.add((PlayableCard) resourceDeck.getFirstCard());
            }
            player.setUpPlayerItems(boardSize,
                    (InitialCard) initialDeck.getFirstCard(), hand);
        }
    }
    /**
     * Gets the secret goals for a player.
     * @return an array of secret goals
     */
    public GoalCard[] getSecretGoalsForPlayer() {
        GoalCard[] secretGoals = new GoalCard[2];
        secretGoals[0] = (GoalCard) commonBoard.getGoalDeck().getFirstCard();
        secretGoals[1] = (GoalCard) commonBoard.getGoalDeck().getFirstCard();
        return secretGoals;
    }
    /**
     * Sets the secret goal for a player.
     * @param player the player to set the secret goal for
     * @param secretGoal the secret goal to set
     */
    public void setSecretGoalForPlayer(String player, GoalCard secretGoal) {
        getPlayerById(player).setSecretGoalCard(secretGoal);
    }
    /**
     * Sets the initial card for a player.
     * @param player the player to set the initial card for
     * @param playedSide the side of the card to play
     */
    public void setInitialCardForPlayer(String player, boolean playedSide) {
        getPlayerById(player).setPlayedSideInitialCard(playedSide);
    }
    /**
     * Sets the token for a player.
     * @param player the player to set the token for
     * @param token the token to set
     */
    public void setToken(String player, Color token) {
        getPlayerById(player).setToken(token);
    }

    /**
     * Sets up a card for a player.
     * @param playerID the ID of the player to set the card for
     * @param card the card to set
     * @param cor the coordinate to set the card at
     * @param playedSide the side of the card to play
     */

    public void setUpCardForPlayer(String playerID, ResourceCard card, Coordinate cor, boolean playedSide) {
        Player player = getPlayerById(playerID);
        player.removeFromHand(card);
        player.cardSetUp(card, playedSide, cor);

    }
    /**
     * Plays a card for a player.
     * @param username the username of the player to play the card for
     * @param cardIndex the index of the card to play
     * @param playedSide the side of the card to play
     * @param playedPosition the position to play the card at
     * @return the card that was played
     */

    public ResourceCard playCard(String username, int cardIndex, boolean playedSide, Coordinate playedPosition) {
        Player player = getPlayerById(username);
        ResourceCard card = (ResourceCard) player.getHand().get(cardIndex);
        setUpCardForPlayer(username, card, playedPosition, playedSide);
        return card;
    }
    /**
     * Gets the number of players in the game.
     * @return the number of players
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * Gets a player by their ID.
     * @param id the ID of the player to get
     * @return the player with the given ID
     */
    public Player getPlayerById(String id) {
        for (Player player : players) {
            if (player.getId().equals(id)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Sets up a drawn card for a player.
     * @param playerId the ID of the player to set the drawn card for
     * @param card the drawn card to set
     */
    public void setUpDrawnCard(String playerId, ResourceCard card) {
        Player player = getPlayerById(playerId);
        player.addToHand(card);
    }

    /**
     * Draws a card for a player.
     * @param username the username of the player to draw the card for
     * @param cardIndex the index of the card to draw
     * @return the card that was drawn
     */
    public ResourceCard drawCard(String username, int cardIndex) {
        ResourceCard drawnCard = commonBoard.drawCard(cardIndex);
        setUpDrawnCard(username, drawnCard);
        return drawnCard;
    }
    /**
     * Sets up the common goals for the game.
     */
    public void setUpCommonGoals() {
        for (Player player : players) {
            for (int i = 0; i < 2; i++) {
                player.setCommonGoals(commonBoard.getCommonGoal());
            }
        }
    }
    /**
     * Computes the outcome of the game.
     */
    public void computeOutcome() {
        updateGoalScores();
        List<Player> winners;
        List<Player> players = this.players.stream().filter((x) -> !x.getState().equals(State.DISCONNECTED)).toList();
        int maxScore = players.stream().filter((x) -> !x.getState().equals(State.DISCONNECTED)).map((x) -> x.getFinalScore()).reduce(0, Math::max);
        List<Player> max_score_players = players.stream().filter((x) -> !x.getState().equals(State.DISCONNECTED))
                .filter((x) -> x.getFinalScore() == maxScore).collect(Collectors.toList());
        if (max_score_players.size() > 1) {
            int maxGoalScore = max_score_players.stream().map((x) -> x.getGoalsScore()).reduce(0, Math::max);
            winners = max_score_players.stream().filter((x) -> x.getGoalsScore() == maxGoalScore).collect(Collectors.toList());
        } else
            winners = max_score_players;
        for (Player player : players) {
            if (isPlayerDisconnected(player.getId()))
                continue;
            if (winners.contains(player))
                player.setState(State.WINNER);
            else
                player.setState(State.LOSER);
        }
    }

    /**
     * Updates the goal scores for each player in the game.
     * This method iterates over each player and, if the player is not disconnected,
     * it sets the common goal score and the secret goal score for the player.
     * The common goal score is set based on the visible goals from the common goal.
     * The secret goal score is set based on the player's secret goal.
     * If a player is disconnected, they are skipped and their goal scores are not updated.
     */
    private void updateGoalScores() {
        for (Player player : players) {
            if (isPlayerDisconnected(player.getId()))
                continue;
            for (int i = 0; i < 2; i++) {
                player.setCommonGoalScore(commonBoard.getCommonGoal().getVisibleGoal().get(i), commonBoard.getCommonGoal().getVisibleGoal().get(i).checkPoints(player.getPlayerBoard()));
            }
            player.setSecretGoalScore(player.getSecretGoal().checkPoints(player.getPlayerBoard()));
        }
    }
    /**
     * Gets the current state of the game.
     * @return the current game state
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets the state of the game.
     * @param state the new game state
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * Gets the players in the game.
     * @return a list of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the available tokens for the game.
     * @return a list of available tokens
     */
    public List<Color> getAvailableTokens() {
        List<Color> tokens = Color.getColors();
        for (Player player : players) {
            tokens.remove(player.getToken());
        }

        return tokens;
    }

    /**
     * Checks if the initial card setup is done.
     * @return true if the initial card setup is done, false otherwise
     */
    public boolean initialCardSetupIsDone() {
        for (Player player : players) {
            if (player.getPlayerBoard().cardInPosition(boardSize / 2, boardSize / 2) == null && isPlayerDisconnected(player.getId())) {
                setInitialCardForPlayer(player.getId(), new Random().nextBoolean());
            }else if(player.getPlayerBoard().cardInPosition(boardSize / 2, boardSize / 2) == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the views of the players in the game.
     * @return a list of player views
     */
    public List<PlayerView> getPlayersViews() {
        List<PlayerView> playersViews = new ArrayList<>();
        for (Player p : players) {
            if(this.isPlayerDisconnected(p.getId())){
                continue;
            }
            playersViews.add(modelViewMapper.getPlayerView(p, boardSize));
        }
        return playersViews;

    }

    /**
     * Checks if the secret goals setup is done.
     * @param potentialGoals the potential goals for the players
     * @return true if the secret goals setup is done, false otherwise
     */
    public boolean secretGoalsSetupIsDone(Map<String, GoalCard[]> potentialGoals) {
        for (Player player : players) {
            if (player.getSecretGoal() == null&&isPlayerDisconnected(player.getId())) {
                setSecretGoalForPlayer(player.getId(), potentialGoals.get(player.getId())[new Random().nextInt(2)]);
            }else if (player.getSecretGoal() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the public views of the players in the game.
     * @return a list of public player views
     */
    public List<PublicPlayerView> getPublicPlayersView() {
        List<PublicPlayerView> othersPlayersView = new ArrayList<>();
        for (Player player : players) {
            if (isPlayerDisconnected(player.getId())) {
                continue;
            }
            othersPlayersView.add(modelViewMapper.getPublicPlayerView(player, boardSize));
        }
        return othersPlayersView;
    }

    /**
     * Checks if a play is incorrect.
     * @param playerId the ID of the player to check
     * @param cardIndex the index of the card to check
     * @param playedSide the side of the card to check
     * @param playedPosition the position of the card to check
     * @return an integer representing the type of error, if any
     */
    public int checkIncorrectPlay(String playerId, int cardIndex, boolean playedSide, Coordinate playedPosition) {
        Player player = this.getPlayerById(playerId);
        if (cardIndex > player.getHand().size())
            return 1;
        if (playedSide && ((ResourceCard) player.getHand().get(cardIndex)).cardCategory().equals("Gold") &&
                player.getPlayerBoard().getInventory().missingResources(((GoldCard) player.getHand().get(cardIndex)).getRequirements()) != null)
            return 2;
        if (!player.getPlayerBoard().getPlayablePos().contains(playedPosition))
            return 3;
        return 0;
    }


    /**
     * Checks if a player's score is over 20.
     * @return true if a player's score is over 20, false otherwise
     */
    public boolean checkOver20Score() {
        for (Player player : players) {
            if (player.getFinalScore() >= 20)
                return true;
        }
        return false;
    }

    /**
     * Checks if the gold deck is empty.
     * @return true if the gold deck is empty, false otherwise
     */
    public boolean isGoldDeckEmpty() {
        return commonBoard.getGoldDeck().getGoldDeck().isEmpty();
    }
    /**
     * Checks if the resource deck is empty.
     * @return true if the resource deck is empty, false otherwise
     */
    public boolean isResourceDeckEmpty() {
        return commonBoard.getResourceDeck().getResourceDeck().isEmpty();
    }
    /**
     * Checks if the decks are empty.
     * @return true if the decks are empty, false otherwise
     */
    public boolean decksAreEmpty() {
        return isGoldDeckEmpty() && isResourceDeckEmpty();
    }
    /**
     * Sets the state of a player.
     * @param player the player to set the state for
     * @param state the new state for the player
     */
    public void setPlayerState(String player, State state) {
        getPlayerById(player).setState(state);
    }

    /**
     * Gets the state of a player.
     * @param player the player to get the state for
     * @return the state of the player
     */
    public State getPlayerState(String player) {
        return getPlayerById(player).getState();
    }
    /**
     * Checks if a player is disconnected.
     * @param player the player to check
     * @return true if the player is disconnected, false otherwise
     */
    public boolean isPlayerDisconnected(String player) {
        return getPlayerById(player).getState() == State.DISCONNECTED;
    }
    /**
     * Gets the number of active players in the game.
     * @return the number of active players
     */
    public int getNumberOfActivePlayers() {
        int count = 0;
        for (Player player : players) {
            if (!isPlayerDisconnected(player.getId())) {
                count++;
            }
        }


        return count;
    }



    /**
     * Gets the size of the board.
     * @return the size of the board
     */
    public int getBoardSize() {
        return boardSize;
    }
    /**
     * Checks if a player is skipped.
     * @param player the player to check
     * @return true if the player is skipped, false otherwise
     */
    public boolean isPlayerSkipped(String player) {
        return getPlayerState(player).equals(State.SKIPPED);
    }
}
