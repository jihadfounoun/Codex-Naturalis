package it.polimi.ingsw.Model.GoalCard;

import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Player.PlayerBoard;

import java.io.Serializable;

/**
 * This class represents a Resource Goal in the game, which is a type of Goal Card.
 * A Resource Goal has a specific resource and points associated with it.
 *
 * @author : Amina El Kharouai
 */
public class ResourceGoal extends GoalCard implements Serializable {
    // The specific resource associated with this Resource Goal.
    private final Resource res;

    /**
     * Constructs a new Resource Goal with the specified resource.
     * The points for this Resource Goal are set to 2.
     *
     * @param id  the ID of this Resource Goal
     * @param res the resource associated with this Resource Goal
     */
    public ResourceGoal(String id, Resource res) {
        super(id);
        this.res = res;
        this.setPoints(2);
    }

    /**
     * Returns the resource associated with this Resource Goal.
     *
     * @return the resource associated with this Resource Goal
     */
    public Resource getRes() {
        return this.res;
    }

    /**
     * Checks the points of the PlayerBoard associated with this Resource Goal.
     * The method calculates the scored points based on the number of the resource in the PlayerBoard divided by the points.
     *
     * @param playerBoard the PlayerBoard to check points for
     * @return the scored points
     */
    @Override
    public int checkPoints(PlayerBoard playerBoard) {
        return ((playerBoard.getResourceNumber(this.getRes())) / 3) * this.getPoints();
    }

    /**
     * Returns a string representation of this Resource Goal.
     * The string representation includes the resource, the goal, and the points.
     *
     * @return a string representation of this Resource Goal
     */
    @Override
    public String toString() {
        return "             " + this.res + " Resource Goal\n" +
                "Goal: find groups of three items of " + this.res + " resources.\n" +
                "Points: " + this.getPoints() + "\n";
    }

    /**
     * Returns a boolean indicating whether or not this Resource Goal is equal to the specified object.
     * Two Resource Goals are considered equal if they have the same resource.
     *
     * @param o the object to compare this Resource Goal against
     * @return true if the Resource Goals are equal, false otherwise
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceGoal that)) return false;
        return res == that.res;
    }
}