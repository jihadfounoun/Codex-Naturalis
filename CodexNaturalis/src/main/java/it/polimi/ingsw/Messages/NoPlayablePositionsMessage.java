package it.polimi.ingsw.Messages;

/**
 * <strong>NoPlayablePositionsMessage</strong>
 * <p>
 * NoPlayablePositionsMessage is a message sent to the client to notify that there are no playable positions.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class NoPlayablePositionsMessage extends Message {
    String username;

    /**
     * NoPlayablePositionsMessage builder
     * @param username the username
     */
    public NoPlayablePositionsMessage(String username) {
        this.username = username;
    }
}
