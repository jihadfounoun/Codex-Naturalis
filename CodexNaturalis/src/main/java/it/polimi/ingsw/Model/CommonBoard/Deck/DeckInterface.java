package it.polimi.ingsw.Model.CommonBoard.Deck;

import it.polimi.ingsw.Model.Card.Card;

/**
 * <strong>DeckInterface</strong>
 * <p>
 * DeckInterface is an interface that defines the methods that the deck must implement.
 * </p>
 *
 * @author Locatelli Mattia
 */
public interface DeckInterface {
    /**
     * Methods to shuffle the deck after its built
     */
    void shuffle();

    /**
     * Method to pop out the first card of the deck
     *
     * @return the first card of the deck
     * @throws IllegalStateException if the deck is empty
     */
    Card getFirstCard() throws IllegalStateException;

    /**
     * Method to see the first card of the deck, without popping it out
     *
     * @return the first card of the deck
     * @throws IllegalStateException if the deck is empty
     */
    Card seeFirstCard() throws IllegalStateException;
}
