package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.CardView.ResourceCardView;
import it.polimi.ingsw.ModelView.CommonBoardView.CommonBoardView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

/**
 * <strong>OtherPlayerDrawnCardMessage</strong>
 * <p>
 * OtherPlayerDrawnCardMessage is a message sent to the client to notify that another player has drawn a card.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class OtherPlayerDrawnCardMessage extends Message {
    private final String username;
    private final boolean hidden;
    private final PublicPlayerView player;
    private final CommonBoardView commonBoardView;
    private final ResourceCardView card;
    private final boolean isCasual;


    /**
     * Constructor for the OtherPlayerDrawnCardMessage class
     *
     * @param player {@link PublicPlayerView} of the player that has drawn the card
     * @param username username of the player that has drawn the card
     * @param commonBoardView {@link CommonBoardView} of the game
     * @param hidden true if the card is hidden, false otherwise
     * @param card {@link ResourceCardView} of the card drawn
     * @param isCasual true if the card is drawn casually (the player is temporarily disconnected), false otherwise
     */
    public OtherPlayerDrawnCardMessage(PublicPlayerView player, String username, CommonBoardView commonBoardView, boolean hidden, ResourceCardView card, boolean isCasual) {
        this.username = username;
        this.player = player;
        this.hidden = hidden;
        this.commonBoardView = commonBoardView;
        this.card = card;
        this.isCasual = isCasual;
    }

    /**
     * Get the username of the player that has drawn the card
     * @return username of the player that has drawn the card
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the {@link PublicPlayerView} of the player that has drawn the card
     * @return {@link PublicPlayerView} of the player that has drawn the card
     */
    public PublicPlayerView getPlayer() {
        return player;
    }

    /**
     * Get the {@link ResourceCardView} of the card drawn
     * @return {@link ResourceCardView} of the card drawn
     */
    public ResourceCardView getDrawnCard() {
        return card;
    }

    /**
     * Get the hidden attribute
     * @return true if the card is hidden, false otherwise
     */
    public boolean getHidden() {
        return hidden;
    }

    /**
     * Get the {@link CommonBoardView} of the game
     * @return {@link CommonBoardView} of the game
     */
    public CommonBoardView getCommonBoardView() {
        return commonBoardView;
    }

    /**
     * Get the isCasual attribute
     * @return true if the card is drawn casually (the player is temporarily disconnected), false otherwise
     */
    public boolean getIsCasual() {
        return isCasual;
    }
}
