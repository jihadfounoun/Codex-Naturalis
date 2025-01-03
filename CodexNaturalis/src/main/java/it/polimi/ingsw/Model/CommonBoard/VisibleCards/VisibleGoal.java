package it.polimi.ingsw.Model.CommonBoard.VisibleCards;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.CommonBoard.Deck.GoalDeck;
import it.polimi.ingsw.Model.GoalCard.GoalCard;

import java.util.ArrayList;

/**
 * <strong>VisibleGoal</strong>
 * <p>
 * VisibleGoal is a structure to represent the visible common goals in the common board
 * It implements the {@link VisibleCards} interface.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class VisibleGoal implements VisibleCards<GoalDeck> {
    private static final int n = 2;
    private final ArrayList<GoalCard> goalCards;

    /**
     * VisibleGoal builder
     *
     * @param goalDeck from which take cards using setVisible
     */
    public VisibleGoal(GoalDeck goalDeck) {
        goalCards = new ArrayList<GoalCard>();
        setVisible(goalDeck);
    }

    /**
     * getter for the visible goal cards
     *
     * @return the visible goal cards
     */
    public ArrayList<GoalCard> getVisibleGoal() {
        return goalCards;
    }

    /**
     * set the visible goal cards up to n = 2, only if there are cards in the deck
     *
     * @param goalDeck from which take cards
     */
    @Override
    public void setVisible(GoalDeck goalDeck) {
        while (goalCards.size() < n && !goalDeck.getGoalDeck().isEmpty()) {
            if (!goalDeck.getGoalDeck().isEmpty()) {
                goalCards.add(goalDeck.getGoalDeck().remove(0));
            }
        }
    }


    @Override
    public Card getVisible(int index) throws IndexOutOfBoundsException, IllegalStateException {
        if (goalCards.isEmpty()) {
            throw new IllegalStateException();
        } else if (index >= goalCards.size()) {
            throw new IndexOutOfBoundsException();
        }
        return goalCards.get(index);
    }
}
