package it.polimi.ingsw.Model.CommonBoard.Deck;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.Card.GoldCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GoldDeckTest {
    private GoldDeck goldDeck;

    @BeforeEach
    void setUp() throws IOException {
        goldDeck = new GoldDeck();
    }

    @Test
    void shouldShuffleDeck() {
        ArrayList<GoldCard> originalDeck = new ArrayList<>(goldDeck.getGoldDeck());
        goldDeck.shuffle();
        assertNotEquals(originalDeck, goldDeck.getGoldDeck());
        for (GoldCard card : goldDeck.getGoldDeck()) {
            System.out.println(card);
        }
    }

    @Test
    void shouldGetFirstCard() {
        GoldCard firstCard = goldDeck.getGoldDeck().get(0);
        Card retrievedCard = goldDeck.getFirstCard();
        assertEquals(firstCard, retrievedCard);
    }

    @Test
    void shouldRemoveFirstCardAfterGet() {
        int originalSize = goldDeck.getGoldDeck().size();
        goldDeck.getFirstCard();
        assertEquals(originalSize - 1, goldDeck.getGoldDeck().size());
    }

    @Test
    void shouldThrowExceptionWhenGetFirstCardFromEmptyDeck() {
        while (!goldDeck.getGoldDeck().isEmpty()) {
            goldDeck.getFirstCard();
        }
        assertThrows(IllegalStateException.class, () -> goldDeck.getFirstCard());
    }

    @Test
    void shouldSeeFirstCardType() {
        GoldCard firstCard = goldDeck.getGoldDeck().get(0);
        assertEquals(firstCard.getType(), goldDeck.seeFirst());
    }

    @Test
    void shouldThrowExceptionWhenSeeFirstCardFromEmptyDeck() {
        while (!goldDeck.getGoldDeck().isEmpty()) {
            goldDeck.getFirstCard();
        }
        assertThrows(IllegalStateException.class, () -> goldDeck.seeFirstCard());
    }

    @Test
    void printGoldDeck() {
        System.out.println(goldDeck.getGoldDeck().size());
        for (Card card : goldDeck.getGoldDeck()) {
            System.out.println(card);
        }
    }
}