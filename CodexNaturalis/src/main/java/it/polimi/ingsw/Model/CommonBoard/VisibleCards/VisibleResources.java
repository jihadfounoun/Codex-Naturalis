package it.polimi.ingsw.Model.CommonBoard.VisibleCards;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.CommonBoard.Deck.ResourceDeck;

import java.util.ArrayList;

/**
 * <strong>VisibleResources</strong>
 * <p>
 * VisibleResources is a structure to represent the visible resource cards to be drawn out in the common board.
 * It implements the {@link VisibleCards} interface.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class VisibleResources implements VisibleCards<ResourceDeck> {

    private static final int n = 2;
    private final ArrayList<ResourceCard> resourceCards;

    /**
     * VisibleResources builder
     *
     * @param resourceDeck the ResourceDeck from which take cards
     */
    public VisibleResources(ResourceDeck resourceDeck) {
        resourceCards = new ArrayList<>();
        setVisible(resourceDeck);
    }

    /**
     * getter for the visible resource cards
     *
     * @return the visible resource cards
     */
    public ArrayList<ResourceCard> getVisibleResources() {
        return resourceCards;
    }

    @Override
    public void setVisible(ResourceDeck deck) {
        while (resourceCards.size() < n && !deck.getResourceDeck().isEmpty()) {
            if (!deck.getResourceDeck().isEmpty()) {
                resourceCards.add(deck.getResourceDeck().remove(0));
            }
        }
    }

    /**
     * return from the visible cards array the card at given index chosen by a player
     *
     * @param index index of the card to draw
     * @return the selected card
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalStateException     if there are no visible resourceCards
     */
    @Override
    public Card getVisible(int index) throws IndexOutOfBoundsException, IllegalStateException {
        if (resourceCards.isEmpty()) {
            throw new IllegalStateException();
        } else if (index >= resourceCards.size()) {
            throw new IndexOutOfBoundsException();
        }
        return resourceCards.get(index);
    }

    /**
     * pop out from the visible cards array the card at given index chosen by a player
     *
     * @param index index of the card to draw
     * @return the selected card
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws IllegalStateException     if there are no visible resourceCards
     */
    public ResourceCard drawVisible(int index) throws IndexOutOfBoundsException, IllegalStateException {
        if (resourceCards.isEmpty()) {
            throw new IllegalStateException();
        } else if (index >= resourceCards.size()) {
            throw new IndexOutOfBoundsException();
        }
        return resourceCards.remove(index);
    }
}
