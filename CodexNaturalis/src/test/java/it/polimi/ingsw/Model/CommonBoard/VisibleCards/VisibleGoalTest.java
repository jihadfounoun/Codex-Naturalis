package it.polimi.ingsw.Model.CommonBoard.VisibleCards;

import it.polimi.ingsw.Model.CommonBoard.Deck.GoalDeck;
import it.polimi.ingsw.Model.GoalCard.GoalCard;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VisibleGoalTest {
    private VisibleGoal visibleGoal;
    private GoalDeck goalDeck;

    @BeforeEach
    void setUp() throws IOException {
        goalDeck = new GoalDeck();
        visibleGoal = new VisibleGoal(goalDeck);
    }

    @Test
    void shouldSetVisibleCards() {
        GoalCard visibleCards = visibleGoal.getVisibleGoal().remove(0);
        visibleGoal.setVisible(goalDeck);
        assertEquals(2, visibleGoal.getVisibleGoal().size());
    }

    @Test
    void shouldNotSetVisibleCardsIfDeckEmpty() {
        while (goalDeck.getGoalDeck().size() > 0) {
            goalDeck.getGoalDeck().remove(0);
        }
        visibleGoal.getVisibleGoal().remove(0);
        visibleGoal.getVisibleGoal().remove(0);
        visibleGoal.setVisible(goalDeck);
        assertEquals(0, visibleGoal.getVisibleGoal().size());
    }

    @Test
    void shouldGetFirstVisibleCard() {
        GoalCard drawnCard = (GoalCard) visibleGoal.getVisible(0);
        Assert.assertEquals(drawnCard, visibleGoal.getVisible(0));
    }

    @Test
    void shouldGetSecondVisibleCard() {
        GoalCard drawnCard = (GoalCard) visibleGoal.getVisible(1);
        Assert.assertEquals(drawnCard, visibleGoal.getVisible(1));
    }

    @Test
    void shouldThrowExceptionWhenDrawFromEmptyVisible() {
        visibleGoal.getVisibleGoal().remove(0);
        visibleGoal.getVisibleGoal().remove(0);
        assertThrows(IllegalStateException.class, () -> visibleGoal.getVisible(0));
    }

    @Test
    void shouldThrowExceptionWhenIndexOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> visibleGoal.getVisible(2));
    }
}