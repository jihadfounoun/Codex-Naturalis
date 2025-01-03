package it.polimi.ingsw.Messages;

/**
 * <strong>GameCreatedWaitingForPlayersMessage</strong>
 * <p>
 * GameCreatedWaitingForPlayersMessage is a message sent to the client to notify that the game has been created and is waiting for players.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class GameCreatedWaitingForPlayersMessage extends Message {
    private final String content;

    /**
     * GameCreatedWaitingForPlayersMessage builder
     */
    public GameCreatedWaitingForPlayersMessage() {
        this.content = "Game has been created. Waiting for players...";
    }

    /**
     * getter for the content
     * @return the content
     */
    public String getContent() {
        return content;
    }
}
