package it.polimi.ingsw.ModelView.CardView;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a GoalCardView in the game.
 * It holds the description of the goal card.
 *
 * @author Jihad Founoun
 */
public class GoalCardView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String description;

    private final String id;

    /**
     * Constructs a new GoalCardView with the given description.
     *
     * @param description the description of the goal card
     * @param id the id of the goal card
     */
    public GoalCardView(String description, String id) {
        this.id = id;
        this.description = description;
    }
    /**
     * Returns the id of this goal card.
     *
     * @return the id of this goal card
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the description of this GoalCardView.
     *
     * @return the description of the goal card
     */
    public String getGoalCard() {
        return description;
    }

    /**
     * Returns a string representation of this GoalCardView.
     *
     * @return a string representation of the goal card
     */
    @Override
    public String toString() {
        return description;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoalCardView that = (GoalCardView) o;
        return Objects.equals(description, that.description);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(description);
    }
}