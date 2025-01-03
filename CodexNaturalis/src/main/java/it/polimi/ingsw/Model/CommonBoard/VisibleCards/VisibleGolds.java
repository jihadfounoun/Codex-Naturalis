package it.polimi.ingsw.Model.CommonBoard.VisibleCards;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.Card.GoldCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.CommonBoard.Deck.DeckInterface;
import it.polimi.ingsw.Model.CommonBoard.Deck.GoldDeck;

import java.util.ArrayList;

/**
 * <strong>GoldDeck</strong>
 * <p>
 * The GoldDeck class represents the deck of gold cards in the game. It is an {@link ArrayList} of gold cards that can be shuffled and drawn from.
 * It implements the {@link DeckInterface} interface.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class VisibleGolds implements VisibleCards<GoldDeck> {
    private static final int n = 2;
    ArrayList<GoldCard> goldCards;

    /**
     * Resource deck builder
     * Firstly, it finds the path of the database.
     * Then, it reads the characteristic of a {@link GoldCard} from the database and creates the deck of gold cards.
     *
     * @param goldDeck the deck of gold cards
     */
    public VisibleGolds(GoldDeck goldDeck) {
        goldCards = new ArrayList<>();
        setVisible(goldDeck);
    }

    /**
     * getter for the visible gold cards
     *
     * @return the visible gold cards
     */
    public ArrayList<GoldCard> getGoldCards() {
        return goldCards;
    }

    @Override
    public void setVisible(GoldDeck deck) {
        while (goldCards.size() < n && !deck.getGoldDeck().isEmpty()) {
            if (!deck.getGoldDeck().isEmpty()) {
                goldCards.add(deck.getGoldDeck().remove(0));
            }
        }
    }

    @Override
    public Card getVisible(int index) throws IndexOutOfBoundsException, IllegalStateException {
        if (goldCards.isEmpty()) {
            throw new IllegalStateException();
        } else if (index >= goldCards.size()) {
            throw new IndexOutOfBoundsException();
        }
        return goldCards.get(index);
    }

    /**
     * pop out from the visible gold cards array the card at given index chosen by a player
     *
     * @param index index of the card to draw
     * @return the selected card
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalStateException     if there are no visible goldCards
     */
    public ResourceCard drawVisible(int index) throws IndexOutOfBoundsException, IllegalStateException {
        if (goldCards.isEmpty()) {
            throw new IllegalStateException();
        } else if (index >= goldCards.size()) {
            throw new IndexOutOfBoundsException();
        }
        return goldCards.remove(index);
    }
}
