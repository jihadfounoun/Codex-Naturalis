package it.polimi.ingsw.Messages;

/**
 * <strong>ClientMessage</strong>
 * <p>
 * ClientMessage is an abstract class that represents a message sent by the client to the server.
 * It contains the username of the player that sent the message.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public abstract class ClientMessage extends Message {
    private final String username;

    /**
     * ClientMessage builder
     * @param username the player's username
     */
    public ClientMessage(String username) {
        this.username = username;
    }

    /**
     * getter for the player's username
     * @return the player's username
     */
    public String getUsername() {
        return this.username;
    }
}
