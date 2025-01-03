package it.polimi.ingsw.Messages;

/**
 * <strong>TurnMessage</strong>
 * <p>
 * This message is sent to all the players still connected to a lobby that contains the username of the player that has to play his turn.
 * It extends {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class TurnMessage extends Message {
    private final String username;

    /**
     * TurnMessage builder
     * @param username the username of the player that has to play his turn
     */
    public TurnMessage(String username) {
        this.username = username;
    }

    /**
     * getter for the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

}
