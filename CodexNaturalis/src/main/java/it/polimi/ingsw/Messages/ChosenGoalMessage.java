package it.polimi.ingsw.Messages;

/**
 * <strong>ChosenGoalMessage</strong>
 * <p>
 * ChosenGoalMessage is a message sent to the server to communicate the goal card chosen by the player.
 * It contains the chosen goal card identified by an index and the player's username.
 * It extends the {@link ClientMessage} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ChosenGoalMessage extends ClientMessage {
    private final int goalCard;

    /**
     * ChosenGoalMessage builder
     * @param goalCard the chosen goal card
     * @param username the player's username
     */
    public ChosenGoalMessage(int goalCard, String username) {
        super(username);
        this.goalCard = goalCard;
    }

    /**
     * getter for the chosen goal card
     * @return the chosen goal card
     */
    public int getGoalCard() {
        return goalCard;
    }
}
