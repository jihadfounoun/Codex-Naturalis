package it.polimi.ingsw.Messages;

/**
 * <strong>PlayerLeftMessage</strong>
 * <p>
 * PlayerLeftMessage is a message sent to the client to notify that a player has left the game.
 * It contains the name of the player that has left the game.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class PlayerLeftMessage extends Message {
    private final String player;

    /**
     * PlayerLeftMessage builder
     * @param player the name of the player that has left the game
     */
    public PlayerLeftMessage(String player) {
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
