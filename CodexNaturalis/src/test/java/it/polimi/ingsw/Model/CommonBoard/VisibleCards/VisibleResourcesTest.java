package it.polimi.ingsw.Model.CommonBoard.VisibleCards;

import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.CommonBoard.Deck.ResourceDeck;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VisibleResourcesTest {
    private VisibleResources visibleResources;
    private ResourceDeck resourceDeck;

    @BeforeEach
    void setUp() throws IOException {
        resourceDeck = new ResourceDeck();
        visibleResources = new VisibleResources(resourceDeck);
    }

    @Test
    void shouldSetVisibleCards() {
        ResourceCard visibleCards = visibleResources.drawVisible(0);
        visibleResources.setVisible(resourceDeck);
        assertEquals(2, visibleResources.getVisibleResources().size());
    }

    @Test
    void shouldNotSetVisibleCardsIfDeckEmpty() {
        while (resourceDeck.getResourceDeck().size() > 0) {
            resourceDeck.getResourceDeck().remove(0);
        }
        visibleResources.getVisibleResources().remove(0);
        visibleResources.getVisibleResources().remove(0);
        visibleResources.setVisible(resourceDeck);
        assertEquals(0, visibleResources.getVisibleResources().size());
    }

    @Test
    void shouldDrawVisibleCard() {
        ResourceCard drawnCard = visibleResources.drawVisible(0);
        assertFalse(visibleResources.getVisibleResources().contains(drawnCard));
    }

    @Test
    void shouldGetFirstVisibleCard() {
        ResourceCard drawnCard = visibleResources.getVisibleResources().get(0);
        assertEquals(drawnCard, visibleResources.getVisible(0));
    }

    @Test
    void shouldGetSecondVisibleCard() {
        ResourceCard drawnCard = visibleResources.getVisibleResources().get(1);
        assertEquals(drawnCard, visibleResources.getVisible(1));
    }

    @Test
    void shouldThrowExceptionWhenDrawFromEmptyVisible() {
        visibleResources.drawVisible(0);
        visibleResources.drawVisible(0);
        assertThrows(IllegalStateException.class, () -> visibleResources.drawVisible(0));
    }

    @Test
    void shouldThrowExceptionWhenIndexOutOfBounds() {
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> visibleResources.getVisible(2));
    }

    @Test
    void runOutOfCards() {
        while (true) {
            try {
                resourceDeck.getFirstCard();
            } catch (IllegalStateException e) {
                break;
            }
        }
        int i = 0;
        while (true) {
            try {
                visibleResources.drawVisible(i);
            } catch (RuntimeException e) {
                break;
            }
        }
        visibleResources.setVisible(resourceDeck);
        assertEquals(0, visibleResources.getVisibleResources().size());
    }
}