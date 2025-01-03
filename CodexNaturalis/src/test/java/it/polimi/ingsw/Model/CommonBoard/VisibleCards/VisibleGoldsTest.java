package it.polimi.ingsw.Model.CommonBoard.VisibleCards;

import it.polimi.ingsw.Model.Card.GoldCard;
import it.polimi.ingsw.Model.CommonBoard.Deck.GoldDeck;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VisibleGoldsTest {
    private VisibleGolds visibleGolds;
    private GoldDeck goldDeck;

    @BeforeEach
    void setUp() throws IOException {
        goldDeck = new GoldDeck();
        visibleGolds = new VisibleGolds(goldDeck);
    }

    @Test
    void shouldSetVisibleCards() {
        GoldCard visibleCards = (GoldCard) visibleGolds.drawVisible(0);
        visibleGolds.setVisible(goldDeck);
        assertEquals(2, visibleGolds.getGoldCards().size());
    }

    @Test
    void shouldNotSetVisibleCardsIfDeckEmpty() {
        while (goldDeck.getGoldDeck().size() > 0) {
            goldDeck.getGoldDeck().remove(0);
        }
        visibleGolds.getGoldCards().remove(0);
        visibleGolds.getGoldCards().remove(0);
        visibleGolds.setVisible(goldDeck);
        assertEquals(0, visibleGolds.getGoldCards().size());
    }

    @Test
    void shouldGetFirstVisibleCard() {
        GoldCard drawnCard = visibleGolds.getGoldCards().get(0);
        Assert.assertEquals(drawnCard, visibleGolds.getVisible(0));
    }

    @Test
    void shouldGetSecondVisibleCard() {
        GoldCard drawnCard = visibleGolds.getGoldCards().get(1);
        Assert.assertEquals(drawnCard, visibleGolds.getVisible(1));
    }

    @Test
    void shouldThrowExceptionWhenDrawFromEmptyVisible() {
        visibleGolds.drawVisible(0);
        visibleGolds.drawVisible(0);
        assertThrows(IllegalStateException.class, () -> visibleGolds.drawVisible(0));
    }

    @Test
    void runOutOfCards() {
        while (true) {
            try {
                goldDeck.getFirstCard();
            } catch (IllegalStateException e) {
                break;
            }
        }
        int i = 0;
        while (true) {
            try {
                visibleGolds.drawVisible(i);
            } catch (RuntimeException e) {
                break;
            }
        }
        visibleGolds.setVisible(goldDeck);
        assertEquals(0, visibleGolds.getGoldCards().size());
    }

    @Test
    void shouldThrowExceptionWhenIndexOutOfBounds() {
        Assert.assertThrows(IndexOutOfBoundsException.class, () -> visibleGolds.getVisible(2));
    }
}