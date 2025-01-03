package it.polimi.ingsw.Messages;

/**
 * <strong>PlayerReached20</strong>
 * <p>
 * PlayerReached20 is a message sent to the client to notify that a player has reached 20 points.
 * It contains the username of the player that has reached 20 points.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class PlayerReached20 extends Message {
    private final String player20;

    /**
     * PlayerReached20 builder
     * @param username the username of the player that has reached 20 points
     */
    public PlayerReached20(String username) {
        this.player20 = username;
    }

    /**
     * getter for the player20
     * @return the player20
     */
    public String getPlayer20() {
        return player20;
    }
}
