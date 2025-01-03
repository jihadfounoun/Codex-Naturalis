package it.polimi.ingsw.Model.CommonBoard.Deck;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.GoalCard.GoalCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GoalDeckTest {
    private GoalDeck goalDeck;

    @BeforeEach
    void setUp() throws IOException {
        goalDeck = new GoalDeck();
    }

    @Test
    void shouldShuffleDeck() {
        ArrayList<GoalCard> originalDeck = new ArrayList<>(goalDeck.getGoalDeck());
        goalDeck.shuffle();
        assertNotEquals(originalDeck, goalDeck.getGoalDeck());
    }

    @Test
    void shouldGetFirstCard() {
        GoalCard firstCard = goalDeck.getGoalDeck().get(0);
        Card retrievedCard = goalDeck.getFirstCard();
        assertEquals(firstCard, retrievedCard);
    }

    @Test
    void shouldRemoveFirstCardAfterGet() {
        int originalSize = goalDeck.getGoalDeck().size();
        goalDeck.getFirstCard();
        assertEquals(originalSize - 1, goalDeck.getGoalDeck().size());
    }

    @Test
    void shouldThrowExceptionWhenGetFirstCardFromEmptyDeck() {
        while (!goalDeck.getGoalDeck().isEmpty()) {
            goalDeck.getFirstCard();
        }
        assertThrows(IllegalStateException.class, () -> goalDeck.getFirstCard());
    }

    @Test
    void printGoalDeck() {
        System.out.println(goalDeck.getGoalDeck().size());
        for (Card card : goalDeck.getGoalDeck()) {
            System.out.println(card);
        }
    }

    @Test
    void shouldSeeFirstCardWithoutRemovingIt() {
        GoalCard firstCard = goalDeck.getGoalDeck().get(0);
        Card seenCard = goalDeck.seeFirstCard();
        assertEquals(firstCard, seenCard);
        assertEquals(goalDeck.getGoalDeck().size(), goalDeck.getGoalDeck().size());
    }
}