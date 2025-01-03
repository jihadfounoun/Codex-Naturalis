package it.polimi.ingsw.Messages;

/**
 * <strong>PongMessage</strong>
 * <p>
 * PongMessage is a message sent to the server to notify that the client has received the ping message.
 * It contains the username of the player and the pong number.
 * It extends the {@link ClientMessage} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class PongMessage extends ClientMessage {
    private final int pongNumber;

    /**
     * PongMessage builder
     * @param username the username of the player
     * @param pongNumber the pong number
     */
    public PongMessage(String username, int pongNumber) {
        super(username);
        this.pongNumber = pongNumber;
    }

    /**
     * getter for the pong number
     * @return the pong number
     */
    public int getPongNumber() {
        return pongNumber;
    }
}
