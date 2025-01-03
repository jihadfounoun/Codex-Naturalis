package it.polimi.ingsw.ModelView.CommonBoardView;

import it.polimi.ingsw.ModelView.CardView.GoalCardView;
import it.polimi.ingsw.ModelView.CardView.GoldCardView;
import it.polimi.ingsw.ModelView.CardView.ResourceCardView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a CommonBoardView in the game.
 * It implements Serializable, which means its instances can be saved to a file or sent over a network.
 * It holds the visible resources, visible gold, common goals, first resource card, first gold card, player score, and the status of resource and gold decks.
 *
 * @author Jihad Founoun
 */
public class CommonBoardView implements Serializable {
    // The visible resources on the board
    private final ArrayList<ResourceCardView> visibleResources;
    // The visible gold on the board
    private final ArrayList<GoldCardView> visibleGold;
    // The common goals on the board
    private final ArrayList<GoalCardView> commonGoals;
    // The first resource card on the board
    private final String firstResCard;
    // The first gold card on the board
    private final String firstGoldCard;
    // The status of the resource deck (true if empty, false otherwise)
    private final boolean resourceDeckEmpty;
    // The status of the gold deck (true if empty, false otherwise)
    private final boolean goldDeckEmpty;
    // The first card of the resource deck
    private final ResourceCardView FirstResourceDeckCard;
    // The first card of the gold deck
    private final GoldCardView FirstGoldDeckCard;

    /**
     * Constructs a new CommonBoardView with the given visible resources, visible gold, common goals, first resource card, first gold card, player score, and the status of resource and gold decks.
     *
     * @param visibleResources  the visible resources on the board
     * @param visibleGold       the visible gold on the board
     * @param commonGoals       the common goals on the board
     * @param firstResCard      the first resource card on the board
     * @param firstGoldCard     the first gold card on the board
     * @param resourceDeckEmpty the status of the resource deck (true if empty, false otherwise)
     * @param goldDeckEmpty     the status of the gold deck (true if empty, false otherwise)
     * @param firstResourceDeckCard the first card of the resource deck
     * @param firstGoldDeckCard the first card of the gold deck
     *
     */
    public CommonBoardView(ArrayList<ResourceCardView> visibleResources, ArrayList<GoldCardView> visibleGold, ArrayList<GoalCardView> commonGoals, String firstResCard, String firstGoldCard, boolean resourceDeckEmpty, boolean goldDeckEmpty, ResourceCardView firstResourceDeckCard, GoldCardView firstGoldDeckCard) {
        this.visibleResources = visibleResources;
        this.visibleGold = visibleGold;
        this.commonGoals = commonGoals;
        this.firstResCard = firstResCard;
        this.firstGoldCard = firstGoldCard;
        this.resourceDeckEmpty = resourceDeckEmpty;
        this.goldDeckEmpty = goldDeckEmpty;
        FirstResourceDeckCard = firstResourceDeckCard;
        FirstGoldDeckCard = firstGoldDeckCard;
    }

    /**
     * Returns the first card of the resource deck.
     *
     * @return the first card of the resource deck
     */
    public ResourceCardView getFirstResourceDeckCard() {
        return FirstResourceDeckCard;
    }

    /**
     * Returns the first card of the gold deck.
     *
     * @return the first card of the gold deck
     */
    public GoldCardView getFirstGoldDeckCard() {
        return FirstGoldDeckCard;
    }

    /**
     * Returns the visible resources of this CommonBoardView.
     *
     * @return the visible resources on the board
     */
    public ArrayList<ResourceCardView> getVisibleResources() {
        return visibleResources;
    }

    /**
     * Returns the visible gold of this CommonBoardView.
     *
     * @return the visible gold on the board
     */
    public ArrayList<GoldCardView> getVisibleGold() {
        return visibleGold;
    }

    /**
     * Returns the common goals of this CommonBoardView.
     *
     * @return the common goals on the board
     */
    public ArrayList<GoalCardView> getCommonGoals() {
        return commonGoals;
    }

    /**
     * Returns the first resource card of this CommonBoardView.
     *
     * @return the first resource card on the board
     */
    public String getFirstResCard() {
        return firstResCard;
    }

    /**
     * Returns the first gold card of this CommonBoardView.
     *
     * @return the first gold card on the board
     */
    public String getFirstGoldCard() {
        return firstGoldCard;
    }



    /**
     * Returns whether the resource deck is empty in this CommonBoardView.
     *
     * @return true if the resource deck is empty, false otherwise
     */
    public boolean isResourceDeckEmpty() {
        return resourceDeckEmpty;
    }

    /**
     * Returns whether the gold deck is empty in this CommonBoardView.
     *
     * @return true if the gold deck is empty, false otherwise
     */
    public boolean isGoldDeckEmpty() {
        return goldDeckEmpty;
    }
}