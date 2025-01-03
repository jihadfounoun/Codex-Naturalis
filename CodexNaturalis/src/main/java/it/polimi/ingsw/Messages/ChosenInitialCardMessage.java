package it.polimi.ingsw.Messages;

/**
 * <strong>ChosenInitialCardMessage</strong>
 * <p>
 * ChosenInitialCardMessage is a message sent to the server to communicate the initial card side chosen by the player.
 * It contains the chosen side and the player's username.
 * It extends the {@link ClientMessage} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ChosenInitialCardMessage extends ClientMessage {
    private final boolean playedSide;

    /**
     * ChosenInitialCardMessage builder
     * @param playedSide the chosen side
     * @param username the player's username
     */
    public ChosenInitialCardMessage(boolean playedSide, String username) {
        super(username);
        this.playedSide = playedSide;
    }

    /**
     * getter for the chosen side
     * @return the chosen side
     */
    public boolean isInitFront() {
        return playedSide;
    }
}
