package it.polimi.ingsw.Model.Card.corners;

import java.io.Serializable;
import java.util.Objects;


/**
 * This class represents the card's corner.
 * It is a base class for other types of corners like ObjCorner and ResCorner.
 *
 * @author Jihad Founoun
 */
public class Corner implements Serializable {
    private final boolean empty;
    private boolean hidden;

    /**
     * Constructor for the Corner class.
     * @param empty indicates if the corner is empty
     * @param hidden indicates if the corner is hidden
     */
    public Corner(boolean empty, boolean hidden) {
        this.empty = empty;
        this.hidden = hidden;
    }

    /**
     * Checks if the corner is empty.
     * @return true if the corner is empty, false otherwise
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Checks if the corner is hidden.
     * @return true if the corner is hidden, false otherwise
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Sets the corner as hidden.
     */
    public void setHidden() {
        this.hidden = true;
    }

    /**
     * Returns a string representation of the corner.
     * @return "hidden" if the corner is hidden, "empty" otherwise
     */
    @Override
    public String toString() {
        if (hidden)
            return "hidden";
        return "empty";
    }

    /**
     * Checks if this corner is equal to another object.
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Corner corner)) return false;
        return empty == corner.empty && hidden == corner.hidden;
    }

    /**
     * Returns a hash code value for the corner.
     * @return a hash code value for the corner
     */
    @Override
    public int hashCode() {
        return Objects.hash(empty, hidden);
    }

    /**
     * Returns a string representation of the corner for display.
     * @return "|||||" if the corner is hidden, "|   |" otherwise
     */
    public String display() {
        if (hidden) {
            return "|||||";
        } else {
            return "|   |";
        }
    }
}
