package it.polimi.ingsw.Model.CommonBoard.VisibleCards;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.CommonBoard.Deck.DeckInterface;

/**
 * <strong>VisibleCards</strong>
 * <p>
 * VisibleCards is an interface that defines the methods that the visible cards must implement.
 * It is used to set the visible cards for each type of card (resource, gold and goal).
 * </p>
 *
 * @param <T> generic deck type
 * @author Locatelli Mattia
 */
public interface VisibleCards<T extends DeckInterface> {
    /**
     * set the visible cards up to n = 2, only if there are cards in the deck
     *
     * @param deck from which take cards
     */
    void setVisible(T deck) throws IllegalStateException;

    /**
     * pop out from the visible cards array the card at given index chosen by a player
     *
     * @param index index of the card to draw
     * @return the selected card
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalStateException     if there are no visible cards
     */
    Card getVisible(int index) throws IndexOutOfBoundsException, IllegalStateException;
}
