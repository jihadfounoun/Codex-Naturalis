package it.polimi.ingsw.Model.CommonBoard.Deck;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.Card.ResourceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResourceDeckTest {
    private ResourceDeck resourceDeck;

    @BeforeEach
    void setUp() throws IOException {
        resourceDeck = new ResourceDeck();
    }

    @Test
    void shouldShuffleDeck() {
        ArrayList<ResourceCard> originalDeck = new ArrayList<>(resourceDeck.getResourceDeck());
        resourceDeck.shuffle();
        assertNotEquals(originalDeck, resourceDeck.getResourceDeck());
    }

    @Test
    void shouldSeeFirstCardType() {
        ResourceCard firstCard = resourceDeck.getResourceDeck().get(0);
        assertEquals(firstCard.getType(), resourceDeck.seeFirst());
    }

    @Test
    void shouldGetFirstCard() {
        ResourceCard firstCard = resourceDeck.getResourceDeck().get(0);
        Card retrievedCard = resourceDeck.getFirstCard();
        assertEquals(firstCard, retrievedCard);
    }

    @Test
    void shouldRemoveFirstCardAfterGet() {
        int originalSize = resourceDeck.getResourceDeck().size();
        resourceDeck.getFirstCard();
        assertEquals(originalSize - 1, resourceDeck.getResourceDeck().size());
    }

    @Test
    void shouldThrowExceptionWhenGetFirstCardFromEmptyDeck() {
        while (!resourceDeck.getResourceDeck().isEmpty()) {
            resourceDeck.getFirstCard();
        }
        assertThrows(IllegalStateException.class, () -> resourceDeck.getFirstCard());
    }

    @Test
    void shouldThrowExceptionWhenSeeFirstCardFromEmptyDeck() {
        while (!resourceDeck.getResourceDeck().isEmpty()) {
            resourceDeck.getFirstCard();
        }
        assertThrows(IllegalStateException.class, () -> resourceDeck.seeFirstCard());
    }

    @Test
    void printResourceDeck() {
        System.out.println(resourceDeck.getResourceDeck().size());
        for (Card card : resourceDeck.getResourceDeck()) {
            System.out.println(card);
        }
    }
}