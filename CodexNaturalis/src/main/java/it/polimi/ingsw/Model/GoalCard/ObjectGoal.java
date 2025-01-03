package it.polimi.ingsw.Model.GoalCard;

import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import it.polimi.ingsw.Model.Player.PlayerBoard;

import java.io.Serializable;
import java.util.ArrayList;

import static it.polimi.ingsw.Model.Card.enumerations.TypeObject.*;

/**
 * This class represents an Object Goal in the game, which is a type of Goal Card.
 * An Object Goal has a list of TypeObject and points associated with it.
 *
 * @author : Amina El Kharouai
 */
public class ObjectGoal extends GoalCard implements Serializable {
    // The list of TypeObject associated with this Object Goal.
    private final ArrayList<TypeObject> obj;

    /**
     * Constructs a new Object Goal with the specified TypeObject.
     * The points for this Object Goal are set to 2.
     *
     * @param id the ID of this Object Goal
     * @param x  the TypeObject associated with this Object Goal
     */
    public ObjectGoal(String id, TypeObject x) {
        super(id);
        this.obj = new ArrayList<>(2);
        obj.add(x);
        obj.add(x);
        this.setPoints(2);
    }

    /**
     * Constructs a new Object Goal with the default TypeObject.
     * The points for this Object Goal are set to 3.
     *
     * @param id the ID of this Object Goal
     */
    public ObjectGoal(String id) {
        super(id);
        this.obj = new ArrayList<>(3);
        obj.add(MANUSCRIPT);
        obj.add(INKWELL);
        obj.add(QUILL);
        this.setPoints(3);
    }

    /**
     * Returns the list of TypeObject associated with this Object Goal.
     *
     * @return the list of TypeObject associated with this Object Goal
     */
    public ArrayList<TypeObject> getObj() {
        return this.obj;
    }

    /**
     * Checks the points of the PlayerBoard associated with this Object Goal.
     * The method calculates the scored points based on the size of the obj list and the objects in the PlayerBoard.
     *
     * @param playerBoard the PlayerBoard to check points for
     * @return the scored points
     */
    public int checkPoints(PlayerBoard playerBoard) {
        int scoredPoints = 0;
        if (obj.size() == 3) {
            Integer i = 12;
            for (Integer x : (playerBoard.getObjects().values())) {
                if (x < i) {
                    i = x;
                }
            }
            scoredPoints = i * this.getPoints();
        } else if (obj.size() == 2) {
            scoredPoints = ((playerBoard.getObjects().get(obj.get(0))) / 3) * this.getPoints();
        }
        return scoredPoints;
    }

    /**
     * Returns a string representation of this Object Goal.
     * The string representation includes the goal and the points.
     *
     * @return a string representation of this Object Goal
     */
    @Override
    public String toString() {
        if (this.obj.size() == 3) {
            return "              Trio Object Goal\n" +
                    "Goal: find groups of " + this.obj.get(0) + ", " + this.obj.get(1) + " and " + this.obj.get(2) + ".\n" +
                    "Points: " + this.getPoints();
        } else {
            return "             " + this.obj.get(0) + " Object Goal\n" +
                    "Goal: find groups of two items of " + this.obj.get(0) + " objects.\n" +
                    "Points: " + this.getPoints() + "\n";
        }
    }

    /**
     * Returns a boolean indicating whether or not this Object Goal is equal to the specified object.
     * Two Object Goals are considered equal if they have the same TypeObject.
     *
     * @param o the object to compare this Object Goal against
     * @return true if the Object Goals are equal, false otherwise
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectGoal that)) return false;
        if (!super.equals(o)) return false; //check if the points are the same
        return obj == that.obj;
    }
}