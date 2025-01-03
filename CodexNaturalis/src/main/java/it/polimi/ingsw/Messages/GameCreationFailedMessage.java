package it.polimi.ingsw.Messages;

/**
 * <strong>GameCreationFailedMessage</strong>
 * <p>
 * GameCreationFailedMessage is a message sent to the client to notify that the game creation has failed.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class GameCreationFailedMessage extends Message {
    private final String content;

    /**
     * GameCreationFailedMessage builder
     */
    public GameCreationFailedMessage() {
        this.content = "Error during game creation\n";
    }

    /**
     * getter for the content
     * @return the content
     */
    public String getContent() {
        return content;
    }
}
