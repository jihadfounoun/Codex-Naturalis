package it.polimi.ingsw.Model.CommonBoard;

import it.polimi.ingsw.Model.Card.GoldCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.CommonBoard.Deck.GoalDeck;
import it.polimi.ingsw.Model.CommonBoard.Deck.GoldDeck;
import it.polimi.ingsw.Model.CommonBoard.Deck.InitialDeck;
import it.polimi.ingsw.Model.CommonBoard.Deck.ResourceDeck;
import it.polimi.ingsw.Model.CommonBoard.VisibleCards.VisibleGoal;
import it.polimi.ingsw.Model.CommonBoard.VisibleCards.VisibleGolds;
import it.polimi.ingsw.Model.CommonBoard.VisibleCards.VisibleResources;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <strong>CommonBoard</strong>
 * <p>
 * The common board is a structure that represents all the common items of the game, such as the decks and the visible cards.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class CommonBoard {
    private final ResourceDeck resourceDeck;
    private final GoldDeck goldDeck;
    private final GoalDeck goalDeck;
    private final InitialDeck initialDeck;
    private final VisibleResources resourceCards;
    private final VisibleGolds goldCards;
    private final VisibleGoal commonGoal;

    /**
     * Common Board builder, create the deck for each card type and set the visible cards
     *
     * @throws IOException thrown if the database can't be read
     */
    public CommonBoard() throws IOException {
        resourceDeck = new ResourceDeck();
        goldDeck = new GoldDeck();
        goalDeck = new GoalDeck();
        initialDeck = new InitialDeck();

        resourceDeck.shuffle();
        goldDeck.shuffle();
        goalDeck.shuffle();
        initialDeck.shuffle();

        resourceCards = new VisibleResources(resourceDeck);
        goldCards = new VisibleGolds(goldDeck);
        commonGoal = new VisibleGoal(goalDeck);
    }

    /**
     * getter of the visible resource cards
     * @return the visible resource cards
     */
    public VisibleResources getResourceCards() {
        return resourceCards;
    }

    /**
     * getter of the visible gold cards
     * @return the visible gold cards
     */
    public VisibleGolds getGoldCards() {
        return goldCards;
    }

    /**
     * getter of the visible goal card
     * @return the visible goal card
     */
    public VisibleGoal getCommonGoal() {
        return commonGoal;
    }

    /**
     * getter of the resource deck
     * @return the resource deck
     */
    public ResourceDeck getResourceDeck() {
        return resourceDeck;
    }

    /**
     * getter of the gold deck
     * @return the gold deck
     */
    public GoldDeck getGoldDeck() {
        return goldDeck;
    }

    /**
     * getter of the goal deck
     * @return the goal deck
     */
    public GoalDeck getGoalDeck() {
        return goalDeck;
    }

    /**
     * getter of the initial deck
     * @return the initial deck
     */
    public InitialDeck getInitialDeck() {
        return initialDeck;
    }

    /**
     * methods to see the type of the first card of the resource deck
     *
     * @return the type of the first card
     */
    public Resource seeTypeFirstResCard() {
        try {
            return resourceDeck.seeFirst();
        } catch (IllegalStateException e) {
            return Resource.ANIMAL;
        }
    }

    /**
     * methods to see the type of the first card of the gold deck
     *
     * @return the type of the first card
     */
    public Resource seeTypeFirstGoldCard() {
        try {
            return goldDeck.seeFirst();
        } catch (IllegalStateException e) {
            return Resource.ANIMAL;
        }
    }

    /**
     * method to only see the first card of the resource deck
     *
     * @return the first card of the deck
     * @throws IllegalStateException if the deck is empty
     */
    public ResourceCard seeFirstResourceCard() throws IllegalStateException {
        return (ResourceCard) resourceDeck.seeFirstCard();
    }

    /**
     * method to only see the first card of the gold deck
     *
     * @return the first card of the deck
     * @throws IllegalStateException if the deck is empty
     */
    public GoldCard seeFirstGoldCard() throws IllegalStateException {
        return (GoldCard) goldDeck.seeFirstCard();
    }

    /**
     * method to draw the first card of the resource deck
     *
     * @return the first card of the deck
     * @throws IllegalStateException if the deck is empty
     */
    public ResourceCard drawFirstResourceCard() throws IllegalStateException {
        return (ResourceCard) resourceDeck.getFirstCard();
    }

    /**
     * method to draw the first card of the gold deck
     *
     * @return the first card of the deck
     * @throws IllegalStateException if the deck is empty
     */
    public GoldCard drawFirstGoldCard() throws IllegalStateException {
        return (GoldCard) goldDeck.getFirstCard();
    }

    /**
     * method to draw a card from the drawable decks
     *
     * @param card the card to draw, is an integer from 1 to 6
     * @return the card drawn or null if the input is not valid
     */
    public ResourceCard drawCard(int card) {
        switch (card) {
            case 1:
                return drawFirstGoldCard();
            case 2:
                return drawFirstResourceCard();
            case 3:
                ResourceCard resourceCard_0 = resourceCards.drawVisible(0);
                resourceCards.setVisible(resourceDeck);
                return resourceCard_0;
            case 4:
                ResourceCard resourceCard_1 = resourceCards.drawVisible(1);
                resourceCards.setVisible(resourceDeck);
                return resourceCard_1;
            case 5:
                ResourceCard goldCard_0 = goldCards.drawVisible(0);
                goldCards.setVisible(goldDeck);
                return goldCard_0;
            case 6:
                ResourceCard goldCard_1 = goldCards.drawVisible(1);
                goldCards.setVisible(goldDeck);
                return goldCard_1;
            default:
                return null;
        }
    }

    /**
     * method to get the drawable cards indexes for the casual draw of a disconnected player
     *
     * @return the set of drawable cards indexes
     */
    public Set<Integer> getDrawableCards() {
        Set<Integer> drawableCards = new HashSet<>();
        for (int i = 1; i <= 6; i++) {
            if (isCardDrawable(i)) {
                drawableCards.add(i);
            }
        }
        return drawableCards;
    }

    /**
     * method to check if a card is drawable
     *
     * @param card the card to check, is an integer from 1 to 6
     * @return true if the card is drawable, false otherwise
     */
    public boolean isCardDrawable(int card) {
        switch (card) {
            case 1 -> {
                return !getGoldDeck().getGoldDeck().isEmpty();
            }
            case 2 -> {
                return !getResourceDeck().getResourceDeck().isEmpty();
            }
            case 3 -> {
                return !getResourceCards().getVisibleResources().isEmpty();
            }
            case 4 -> {
                return getResourceCards().getVisibleResources().size() > 1;
            }
            case 5 -> {
                return !getGoldCards().getGoldCards().isEmpty();
            }
            case 6 -> {
                return getGoldCards().getGoldCards().size() > 1;
            }
            default -> {
                return false;
            }
        }
    }
}
