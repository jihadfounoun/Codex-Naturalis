package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player.Color;

/**
 * <strong>UpdateColorMessage</strong>
 * <p>
 * UpdateColorMessage is a message generated when a player changes his color.
 * It notifies the player that his color has been updated.
 * It contains the new color of the player.
 * It extends {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class UpdateColorMessage extends Message {
    private final Color token;

    /**
     * UpdateColorMessage builder
     * @param token the new color of the player
     */
    public UpdateColorMessage(Color token) {
        this.token = token;
    }

    /**
     * getter for the token
     * @return the token
     */
    public Color getToken() {
        return token;
    }
}
