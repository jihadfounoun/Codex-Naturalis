package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Player.Color;

/**
 * <strong>ChosenColorMessage</strong>
 * <p>
 * ChosenColorMessage is a message sent to the server to communicate the color chosen by the player.
 * It contains the chosen {@link Color} and the player's username.
 * It extends the {@link ClientMessage} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ChosenColorMessage extends ClientMessage {
    private final Color color;

    /**
     * ChosenColorMessage builder
     * @param col the chosen color
     * @param username the player's username
     */
    public ChosenColorMessage(Color col, String username) {
        super(username);
        this.color = col;
    }

    /**
     * getter for the chosen color
     * @return the chosen color
     */
    public Color getColor() {
        return this.color;
    }

}
