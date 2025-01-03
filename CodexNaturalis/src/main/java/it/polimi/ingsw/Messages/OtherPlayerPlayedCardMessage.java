package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.CardView.ResourceCardView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

/**
 * <strong>OtherPlayerPlayedCardMessage</strong>
 * <p>
 * OtherPlayerPlayedCardMessage is a message sent to the client to notify that another player has played a card.
 * It contains the player who played the card, the card played and the {@link PublicPlayerView} of the player who played.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class OtherPlayerPlayedCardMessage extends Message {
    private final String username;
    private final boolean hidden;
    private final PublicPlayerView player;
    private final ResourceCardView card;

    /**
     * Constructor for the OtherPlayerPlayedCardMessage class
     *
     * @param player {@link PublicPlayerView} of the player who played the card
     * @param username username of the player who played the card
     * @param hidden true if the card is hidden, false otherwise
     * @param card {@link ResourceCardView} of the card played
     */
    public OtherPlayerPlayedCardMessage(PublicPlayerView player, String username, boolean hidden, ResourceCardView card) {
        this.username = username;
        this.player = player;
        this.hidden = hidden;
        this.card = card;
    }

    /**
     * Get the username of the player who played the card
     * @return username of the player who played the card
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the {@link PublicPlayerView} of the player who played the card
     * @return {@link PublicPlayerView} of the player who played the card
     */
    public PublicPlayerView getPlayer() {
        return player;
    }

    /**
     * Get the {@link ResourceCardView} of the card played
     * @return {@link ResourceCardView} of the card played
     */
    public ResourceCardView getPlayedCard() {
        return card;
    }

    /**
     * Get the hidden attribute of the card played
     * @return true if the card is hidden, false otherwise
     */
    public boolean getHidden() {
        return hidden;
    }
}
