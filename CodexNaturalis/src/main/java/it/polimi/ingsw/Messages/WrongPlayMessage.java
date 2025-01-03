package it.polimi.ingsw.Messages;

/**
 * <strong>WrongPlayMessage</strong>
 * <p>
 * This message is sent to the player that has tried to make an invalid play.
 * It contains a message that explains why the play is invalid.
 * It extends {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class WrongPlayMessage extends Message {
    private final String message;

    /**
     * WrongPlayMessage builder
     * @param message the message that explains why the play is invalid
     */
    public WrongPlayMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
