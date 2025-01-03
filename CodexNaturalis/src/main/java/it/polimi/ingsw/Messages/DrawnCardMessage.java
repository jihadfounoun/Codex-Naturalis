package it.polimi.ingsw.Messages;

/**
 * <strong>DrawnCardMessage</strong>
 * <p>
 * DrawnCardMessage is a message sent to the server to communicate the card drawn by the player.
 * It contains the drawn card identified by an index and the player's username.
 * It extends the {@link ClientMessage} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class DrawnCardMessage extends ClientMessage {
    private final int card;

    /**
     * DrawnCardMessage builder
     * @param card the drawn card
     * @param username the player's username
     */
    public DrawnCardMessage(int card, String username) {
        super(username);
        this.card = card;
    }

    /**
     * getter for the drawn card
     * @return the drawn card
     */
    public int getCard() {
        return card;
    }
}
