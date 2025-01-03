package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.PlayerView.PlayerView;

/**
 * <strong>DrawCardMessage</strong>
 * <p>
 * DrawCardMessage is a message sent to the client to ask the player to draw a card.
 * It contains the {@link PlayerView} of the player that has to draw the card.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class DrawCardMessage extends Message {
    private final PlayerView player;

    /**
     * DrawCardMessage builder
     *
     * @param player the player that has to draw the card
     */
    public DrawCardMessage(PlayerView player) {
        this.player = player;

    }

    /**
     * getter for the player
     *
     * @return the player
     */
    public PlayerView getPlayer() {
        return player;
    }

}
