package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.PlayableCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.CommonBoard.VisibleCards.VisibleGoal;
import it.polimi.ingsw.Model.GoalCard.GoalCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * The objects of this class contain the information to characterize a player.
 * Each player has an ID, to distinguish him from the others, a playerBoard to play the cards on, three playable cards
 * that are the players' hand, the token that graphically allows a distinction between players, the flag first to
 * specify which player begins the rounds, the state of the player and the score, saved in sum as score and distinct
 * as score gained reaching the common goals (commonGoalScores) or the secret goal (secretGoalScore).
 *
 *
 */

public class Player {
    private final String id;
    private final Map<GoalCard, Integer> commonGoalScores;
    private PlayerBoard playerBoard;
    private ArrayList<PlayableCard> hand;
    private Color token;
    private boolean first;
    private int score;
    private State state;
    private int secretGoalScore;

    /**
     * Constructs a new player assigning an id and setting the scores to 0.
     * The other attributes are basically initialise.
     *
     * @param id the id of the player
     */


    public Player(String id) {
        playerBoard = null;
        hand = new ArrayList<>();
        score = 0;
        this.id = id;
        first = false;
        token = Color.NONE;
        state = State.PENDING;
        secretGoalScore = 0;
        commonGoalScores = new HashMap<>(2);
    }

    /**
     * Adds a card to the hand
     *
     * @param card card to be added
     */

    public void addToHand(PlayableCard card) {
        hand.add(card);
    }

    /**
     * Removes a card from the hand
     *
     * @param card card to be removed
     */

    public void removeFromHand(PlayableCard card) {
        hand.remove(card);
    }
    /**
     * Sets the chosen secret goal in the playerBoard
     *
     * @param secretGoal chosen secret goal
     */

    public void setSecretGoalCard(GoalCard secretGoal) {
        playerBoard.goalCardSetUp(secretGoal);
    }
    /**
     * Sets the initial card on the playerBoard with the chosen side
     *
     * @param playedSide 1 if the card is played frontally
     */


    public void setPlayedSideInitialCard(boolean playedSide) {
        playerBoard.initialCardSetUp(playedSide);
    }
    /**
     * It initialises the playerBoard and the hand, that wasn't possible when the constructor was called.
     *
     * @param boardSize   the size of the board
     * @param initialCard the initial card
     * @param hand        the List of the cards that establishes the hand
     */

    public void setUpPlayerItems(int boardSize, InitialCard initialCard, ArrayList<PlayableCard> hand) {
        this.hand = hand;
        this.playerBoard = new PlayerBoard(boardSize, initialCard);
    }

    /**
     * Plays a card on the playerBoard, updating the score.
     *
     * @param card       the card the player wants to play
     * @param playedSide the chosen side of the card
     * @param c          the position where the player wants to play the card
     */
    public void cardSetUp(ResourceCard card, boolean playedSide, Coordinate c) {
        if (!playedSide)
            card.useBack();
        this.score += playerBoard.addToBoard(card, c);
    }

    /**
     * Sets the player as the first in the turn
     */

    public void setFirst() {
        this.first = true;
    }

    /**
     *
     * @return the id of the player
     */

    public String getId() {
        return id;
    }

    /**
     *
     * @return the playerboard of the player
     */

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     *
     * @return the hand
     */

    public ArrayList<PlayableCard> getHand() {
        return hand;
    }

    /**
     *
     * @return the score gained with the cards
     */

    public int getScore() {
        return score;
    }

    /**
     *
     * @return the color of the player
     */

    public Color getToken() {
        return token;
    }

    /**
     * Sets the color of the player
     * @param token color to set
     */

    public void setToken(Color token) {
        this.token = token;
    }
    /**
     * @return 1 if the player begins the turn
     */

    public boolean getFirst() {
        return first;
    }

    /**
     *
     * @return the secret goal
     */

    public GoalCard getSecretGoal() {
        return playerBoard.getSecretGoal();
    }

    /**
     *
     * @return the score gained reaching the secretGoal
     */

    public int getSecretGoalScore() {
        return secretGoalScore;
    }

    /**
     * sets scores gained reaching the secretGoal
     * @param n score to set
     */

    public void setSecretGoalScore(int n) {
        this.secretGoalScore = n;
    }
    /**
     *
     * @return a map that associates the common goal to the points scored reaching them
     */

    public Map<GoalCard, Integer> getCommonGoalScores() {
        return commonGoalScores;
    }

    /**
     * initialize to 0 the score for each common goal
     * @param commonGoals common goals of the player
     */

    public void setCommonGoals(VisibleGoal commonGoals) {
        commonGoalScores.put(commonGoals.getVisibleGoal().get(0), 0);
        commonGoalScores.put(commonGoals.getVisibleGoal().get(1), 0);
    }

    /**
     * sets the score gained reaching a common goal
     * @param goal common goal reached
     * @param n points gained
     */

    public void setCommonGoalScore(GoalCard goal, int n) {
        this.commonGoalScores.put(goal, n);
    }

    /**
     * @return the total score gained by the player
     */

    public int getFinalScore() {
        return this.score + this.secretGoalScore + getCommonGoalScore();
    }

    /**
     *
     * @return the state of the player
     */

    public State getState() {
        return state;
    }

    /**
     * sets the state of the player
     * @param state
     */

    public void setState(State state) {
        this.state = state;
    }

    /**
     *
     * @return the total score of the common goals
     */

    public int getCommonGoalScore() {
        int commonGoalGainedScore = 0;
        for (GoalCard c : this.commonGoalScores.keySet())
            commonGoalGainedScore += this.commonGoalScores.get(c);
        return commonGoalGainedScore;
    }

    /**
     *
     * @return the total score of the goals
     */

    public int getGoalsScore() {
        return getCommonGoalScore() + getSecretGoalScore();
    }

    /**
     *
     * @return the initial card
     */

    public InitialCard getInitialCard() {
        return playerBoard.getInitialCard();
    }

}
