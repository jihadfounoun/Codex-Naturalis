package it.polimi.ingsw.Model.GoalCard;

import it.polimi.ingsw.Model.Card.Card;
import it.polimi.ingsw.Model.Player.PlayerBoard;

import java.io.Serializable;

/**
 * This abstract class represents a Goal Card in the game.
 * A Goal Card is a type of Card that has points associated with it.
 *
 * @author : Amina El Kharouai
 */
public abstract class GoalCard extends Card implements Serializable {
    // The points associated with this Goal Card.
    private int points;

    /**
     * Constructs a new Goal Card with the specified ID.
     *
     * @param id the ID of this Goal Card
     */
    public GoalCard(String id) {
        super(id);
    }

    /**
     * Returns the points associated with this Goal Card.
     *
     * @return the points associated with this Goal Card
     */
    public Integer getPoints() {
        return this.points;
    }

    /**
     * Sets the points associated with this Goal Card.
     *
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Checks the points of the PlayerBoard associated with this Goal Card.
     *
     * @param playerBoard the PlayerBoard to check points for
     * @return the points of the PlayerBoard
     */
    public abstract int checkPoints(PlayerBoard playerBoard);

    /**
     * Checks if the given object is equal to this Goal Card.
     * The equality is based on the points of the Goal Card.
     *
     * @param o the object to compare with this Goal Card
     * @return true if the given object is a Goal Card with the same points, false otherwise
     */
    public boolean equals(Object o) {
        GoalCard goalCard = (GoalCard) o;
        return points == goalCard.points;
    }

    @Override
    public String toString() {
        return "";
    }

}