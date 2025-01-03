package it.polimi.ingsw.Messages;

/**
 * <strong>NoAvailableLobbiesMessage</strong>
 * <p>
 * NoAvailableLobbiesMessage is a message sent to the client to notify that there are no available lobbies.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class NoAvailableLobbiesMessage extends Message {
    private final String message = "No available lobbies.";

    /**
     * NoAvailableLobbiesMessage builder
     */
    public NoAvailableLobbiesMessage() {
    }

    /**
     * getter for the message
     * @return the message
     */
    @Override
    public String toString() {
        return message;
    }
}
