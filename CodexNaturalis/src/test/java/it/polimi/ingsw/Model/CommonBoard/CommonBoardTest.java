package it.polimi.ingsw.Model.CommonBoard;

import it.polimi.ingsw.Model.Card.GoldCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommonBoardTest {
    private CommonBoard commonBoard;

    @BeforeEach
    void setUp() throws IOException {
        commonBoard = new CommonBoard();
    }

    @Test
    void shouldInitializeDecks() {
        assertNotNull(commonBoard.getResourceDeck());
        assertNotNull(commonBoard.getGoldDeck());
        assertNotNull(commonBoard.getGoalDeck());
        assertNotNull(commonBoard.getInitialDeck());
    }

    @Test
    void shouldSeeTypeFirstGoldCard() {
        Resource expectedType = commonBoard.getGoldDeck().seeFirst();
        Resource actualType = commonBoard.seeTypeFirstGoldCard();
        assertEquals(expectedType, actualType);
    }

    @Test
    void shouldSeeFirstCardWithoutRemovingIt() {
        ResourceCard firstCard = (ResourceCard) commonBoard.getResourceDeck().seeFirstCard();
        ResourceCard seenCard = commonBoard.seeFirstResourceCard();
        assertEquals(firstCard, seenCard);
        assertEquals(commonBoard.getResourceDeck().getResourceDeck().size(), commonBoard.getResourceDeck().getResourceDeck().size());
    }

    @Test
    void shouldThrowExceptionWhenSeeFirstCardFromEmptyDeck() {
        while (!commonBoard.getResourceDeck().getResourceDeck().isEmpty()) {
            commonBoard.getResourceDeck().getFirstCard();
        }
        assertThrows(IllegalStateException.class, () -> commonBoard.seeFirstResourceCard());
    }

    @Test
    void shouldDrawFirstCard() {
        int originalSize = commonBoard.getResourceDeck().getResourceDeck().size();
        commonBoard.drawFirstResourceCard();
        assertEquals(originalSize - 1, commonBoard.getResourceDeck().getResourceDeck().size());
    }

    @Test
    void shouldThrowExceptionWhenDrawFirstCardFromEmptyDeck() {
        while (!commonBoard.getResourceDeck().getResourceDeck().isEmpty()) {
            commonBoard.getResourceDeck().getFirstCard();
        }
        assertThrows(IllegalStateException.class, () -> commonBoard.drawFirstResourceCard());
    }

    @Test
    void shouldDrawCardForAllInputs() {
        for (int i = 1; i <= 6; i++) {
            if (commonBoard.isCardDrawable(i)) {
                ResourceCard drawnCard = commonBoard.drawCard(i);
                assertNotNull(drawnCard);
            } else {
                int finalI = i;
                assertThrows(IllegalStateException.class, () -> commonBoard.drawCard(finalI));
            }
        }
    }

    @Test
    void shouldDrawCorrectCardForAllInputs() {
        for (int i = 1; i <= 7; i++) {
            if (commonBoard.isCardDrawable(i)) {
                if (i == 1) {
                    GoldCard firstGoldCard = commonBoard.seeFirstGoldCard();
                    GoldCard drawnCard = (GoldCard) commonBoard.drawCard(i);
                    assertEquals(firstGoldCard, drawnCard);
                } else if (i == 2) {
                    ResourceCard firstResourceCard = commonBoard.seeFirstResourceCard();
                    ResourceCard drawnCard = commonBoard.drawCard(i);
                    assertEquals(firstResourceCard, drawnCard);
                } else if (i == 3 || i == 4) {
                    ResourceCard visibleResourceCard = commonBoard.getResourceCards().getVisibleResources().get(i - 3);
                    ResourceCard drawnCard = commonBoard.drawCard(i);
                    assertEquals(visibleResourceCard, drawnCard);
                } else if (i == 5 || i == 6) {
                    GoldCard visibleGoldCard = commonBoard.getGoldCards().getGoldCards().get(i - 5);
                    GoldCard drawnCard = (GoldCard) commonBoard.drawCard(i);
                    assertEquals(visibleGoldCard, drawnCard);
                }
            } else {
                int finalI = i;
                assertNull(commonBoard.drawCard(finalI));
            }
        }
    }

    @Test
    void shouldCheckIfCardIsDrawable() {
        for (int i = 1; i <= 7; i++) {
            boolean isDrawable = commonBoard.isCardDrawable(i);
            if (i == 1) {
                assertEquals(!commonBoard.getGoldDeck().getGoldDeck().isEmpty(), isDrawable);
            } else if (i == 2) {
                assertEquals(!commonBoard.getResourceDeck().getResourceDeck().isEmpty(), isDrawable);
            } else if (i == 3) {
                assertEquals(!commonBoard.getResourceCards().getVisibleResources().isEmpty(), isDrawable);
            } else if (i == 4) {
                assertEquals(commonBoard.getResourceCards().getVisibleResources().size() > 1, isDrawable);
            } else if (i == 5) {
                assertEquals(!commonBoard.getGoldCards().getGoldCards().isEmpty(), isDrawable);
            } else if (i == 6) {
                assertEquals(commonBoard.getGoldCards().getGoldCards().size() > 1, isDrawable);
            } else {
                assertFalse(isDrawable);
            }
        }
    }

    @Test
    void shouldGetDrawableCards() {
        Set<Integer> drawableCards = commonBoard.getDrawableCards();
        for (int i = 1; i <= 7; i++) {
            assertEquals(commonBoard.isCardDrawable(i), drawableCards.contains(i));
        }
    }
}