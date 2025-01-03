package it.polimi.ingsw.Messages;

/**
 * <strong>GameCreatedReadyToStartMessage</strong>
 * <p>
 * GameCreatedReadyToStartMessage is a message sent to the client to notify that the game is ready to start.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class GameCreatedReadyToStartMessage extends Message {
    private final String content;

    /**
     * GameCreatedReadyToStartMessage builder
     */
    public GameCreatedReadyToStartMessage() {
        this.content = "Ready to start the game!";
    }

    /**
     * getter for the content
     * @return the content
     */
    public String getContent() {
        return content;
    }
}
