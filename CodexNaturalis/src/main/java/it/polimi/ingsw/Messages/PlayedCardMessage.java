package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.CardView.UtilitiesView.CoordinateView;

/**
 * <strong>PlayedCardMessage</strong>
 * <p>
 * PlayedCardMessage is a message sent to the server to notify that a card has been played.
 * It contains the {@link CoordinateView} of the card played, the card played and the side of the card played.
 * It extends the {@link ClientMessage} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class PlayedCardMessage extends ClientMessage {
    private final CoordinateView coordinate;
    private final int card;
    private final boolean playedSide;

    /**
     * PlayedCardMessage builder
     * @param username the username of the player
     * @param coordinate the {@link CoordinateView} of the card played
     * @param card the card played
     * @param playedSide the side of the card played
     */
    public PlayedCardMessage(String username, CoordinateView coordinate, int card, boolean playedSide) {
        super(username);
        this.coordinate = coordinate;
        this.card = card;
        this.playedSide = playedSide;
    }

    /**
     * getter for the {@link CoordinateView} of the card played
     * @return the {@link CoordinateView} of the card played
     */
    public CoordinateView getCoordinate() {
        return coordinate;
    }

    /**
     * getter for the card played
     * @return the card played
     */
    public int getCard() {
        return card;
    }

    /**
     * getter for the side of the card played
     * @return the side of the card played
     */
    public boolean getPlayedSide() {
        return playedSide;
    }
}
