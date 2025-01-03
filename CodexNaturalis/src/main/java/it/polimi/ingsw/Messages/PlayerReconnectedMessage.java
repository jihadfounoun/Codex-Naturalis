package it.polimi.ingsw.Messages;

/**
 * <strong>PlayerReconnectedMessage</strong>
 * <p>
 * Message used to notify the reconnection of a player.
 * Extends {@link Message}.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class PlayerReconnectedMessage extends Message {
    String player;

    /**
     * PlayerReconnectedMessage builder
     * @param player the name of the player that has reconnected
     */
    public PlayerReconnectedMessage(String player) {
        this.player = player;
    }

    /**
     * getter for the player
     * @return the player
     */
    public String getPlayer() {
        return player;
    }
}
