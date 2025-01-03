package it.polimi.ingsw.Messages;

/**
 * <strong>ReconnectMessage</strong>
 * <p>
 * Message used to notify the server that the client is trying to reconnect.
 * It contains the username of the client.
 * Extends {@link ClientMessage}.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ReconnectMessage extends ClientMessage {
    /**
     * ReconnectMessage builder
     * @param username the username of the client
     */
    public ReconnectMessage(String username) {
        super(username);
    }
}
