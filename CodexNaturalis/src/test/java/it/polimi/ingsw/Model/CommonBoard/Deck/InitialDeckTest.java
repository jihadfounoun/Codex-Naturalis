package it.polimi.ingsw.Model.CommonBoard.Deck;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.Card.InitialCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InitialDeckTest {
    private InitialDeck initialDeck;

    @BeforeEach
    void setUp() throws IOException {
        initialDeck = new InitialDeck();
    }

    @Test
    void shouldShuffleDeck() {
        ArrayList<InitialCard> originalDeck = new ArrayList<>(initialDeck.getInitialDeck());
        initialDeck.shuffle();
        assertNotEquals(originalDeck, initialDeck.getInitialDeck());
    }

    @Test
    void shouldGetFirstCard() {
        InitialCard firstCard = initialDeck.getInitialDeck().get(0);
        Card retrievedCard = initialDeck.getFirstCard();
        assertEquals(firstCard, retrievedCard);
    }

    @Test
    void shouldRemoveFirstCardAfterGet() {
        int originalSize = initialDeck.getInitialDeck().size();
        initialDeck.getFirstCard();
        assertEquals(originalSize - 1, initialDeck.getInitialDeck().size());
    }

    @Test
    void shouldThrowExceptionWhenGetFirstCardFromEmptyDeck() {
        while (!initialDeck.getInitialDeck().isEmpty()) {
            initialDeck.getFirstCard();
        }
        assertThrows(IllegalStateException.class, () -> initialDeck.getFirstCard());
    }

    @Test
    void shouldSeeFirstCard() {
        InitialCard firstCard = initialDeck.getInitialDeck().get(0);
        Card retrievedCard = initialDeck.seeFirstCard();
        assertEquals(firstCard, retrievedCard);
    }

    @Test
    void shouldThrowExceptionWhenSeeFirstCardFromEmptyDeck() {
        while (!initialDeck.getInitialDeck().isEmpty()) {
            initialDeck.getFirstCard();
        }
        assertThrows(IllegalStateException.class, () -> initialDeck.seeFirstCard());
    }

    @Test
    void printInitialDeck() {
        for (Card card : initialDeck.getInitialDeck()) {
            System.out.println(card);
        }
    }
}