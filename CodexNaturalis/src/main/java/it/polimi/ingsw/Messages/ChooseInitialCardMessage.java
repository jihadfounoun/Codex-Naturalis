package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.CardView.InitialCardView;

/**
 * <strong>ChooseInitialCardMessage</strong>
 * <p>
 * ChooseInitialCardMessage is a message sent to the client to ask the player to choose an initial card side.
 * It contains the {@link InitialCardView} to choose from.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ChooseInitialCardMessage extends Message {
    private final InitialCardView initialCard;

    /**
     * ChooseInitialCardMessage builder
     * @param initialCard the initial card
     */
    public ChooseInitialCardMessage(InitialCardView initialCard) {
        this.initialCard = initialCard;
    }

    /**
     * getter for the initial card
     * @return the initial card
     */
    public InitialCardView getInitialCard() {
        return initialCard;
    }
}
