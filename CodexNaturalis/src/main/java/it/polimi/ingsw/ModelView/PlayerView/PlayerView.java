package it.polimi.ingsw.ModelView.PlayerView;

import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Model.Player.State;
import it.polimi.ingsw.ModelView.CardView.GoalCardView;
import it.polimi.ingsw.ModelView.CardView.ResourceCardView;
import it.polimi.ingsw.ModelView.CardView.UtilitiesView.CoordinateView;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents a PlayerView in the game.
 * It implements Serializable, which means its instances can be saved to a file or sent over a network.
 * It holds the hand of cards, username, token, score, final score, common goals score, state, secret goal, secret goal score, and playable positions of the player.
 *
 * @author Jihad Founoun
 */
public class PlayerView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ArrayList<ResourceCardView> hand;
    // The username of the player
    private final String username;
    // The token of the player
    private final Color token;
    // The score of the player
    private final int score;
    // The final score of the player
    private final int finalScore;
    // The common goals score of the player
    private final Map<GoalCardView, Integer> commonGoalsScore;
    // The state of the player
    private final State state;
    // The secret goal of the player
    private final GoalCardView secretGoal;
    // The secret goal score of the player
    private final int secretGoalScore;
    // The playable positions of the player
    private final ArrayList<CoordinateView> playablePositions;

    /**
     * Constructs a new PlayerView with the given hand, username, token, score, final score, common goals score, state, secret goal, secret goal score, and playable positions.
     *
     * @param username          the username of the player
     * @param token             the token of the player
     * @param score             the score of the player
     * @param finalScore        the final score of the player
     * @param commonGoalsScore  the common goals score of the player
     * @param state             the state of the player
     * @param secretGoal        the secret goal of the player
     * @param secretGoalScore   the secret goal score of the player
     * @param playablePositions the playable positions of the player
     * @param hand              the hand of the player
     */
    public PlayerView(ArrayList<ResourceCardView> hand, String username, Color token, int score, int finalScore, Map<GoalCardView, Integer> commonGoalsScore, State state, GoalCardView secretGoal, int secretGoalScore, ArrayList<CoordinateView> playablePositions) {

        this.hand = hand;
        this.username = username;
        this.token = token;
        this.score = score;
        this.finalScore = finalScore;
        this.commonGoalsScore = commonGoalsScore;
        this.state = state;
        this.secretGoal = secretGoal;
        this.secretGoalScore = secretGoalScore;
        this.playablePositions = playablePositions;
    }

    /**
     * Returns the hand of this PlayerView.
     *
     * @return the hand of the player
     */
    public ArrayList<ResourceCardView> getHand() {
        return hand;
    }

    /**
     * Returns the username of this PlayerView.
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the token of this PlayerView.
     *
     * @return the token of the player
     */
    public Color getToken() {
        return token;
    }

    /**
     * Returns the score of this PlayerView.
     *
     * @return the score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the final score of this PlayerView.
     *
     * @return the final score of the player
     */
    public int getFinalScore() {
        return finalScore;
    }

    /**
     * Returns the common goals score of this PlayerView.
     *
     * @return the common goals score of the player
     */
    public Map<GoalCardView, Integer> getCommonGoalsScore() {
        return commonGoalsScore;
    }

    /**
     * Returns the state of this PlayerView.
     *
     * @return the state of the player
     */
    public State getState() {
        return state;
    }

    /**
     * Returns the secret goal of this PlayerView.
     *
     * @return the secret goal of the player
     */
    public GoalCardView getSecretGoal() {
        return secretGoal;
    }

    /**
     * Returns the secret goal score of this PlayerView.
     *
     * @return the secret goal score of the player
     */
    public int getSecretGoalScore() {
        return secretGoalScore;
    }

    /**
     * Returns the playable positions of this PlayerView.
     *
     * @return the playable positions of the player
     */
    public ArrayList<CoordinateView> getPlayablePositions() {
        return playablePositions;
    }

}