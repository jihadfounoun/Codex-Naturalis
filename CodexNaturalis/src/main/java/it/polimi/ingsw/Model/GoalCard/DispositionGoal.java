package it.polimi.ingsw.Model.GoalCard;

import it.polimi.ingsw.Model.Card.enumerations.Resource;

import java.io.Serializable;

/**
 * This abstract class represents a Disposition Goal in the game.
 * A Disposition Goal is a type of Goal Card that has a main resource associated with it.
 *
 * @author : Amina El Kharouai
 */
public abstract class DispositionGoal extends GoalCard implements Serializable {
    // The main resource associated with this Disposition Goal.
    public final Resource mainResource;

    /**
     * Constructs a new Disposition Goal with the specified main resource.
     *
     * @param id           the ID of this Disposition Goal
     * @param mainResource the main resource associated with this Disposition Goal
     */
    public DispositionGoal(String id, Resource mainResource) {
        super(id);
        this.mainResource = mainResource;
    }

    /**
     * Checks if the given object is equal to this Disposition Goal.
     * The equality is based on the main resource of the Disposition Goal.
     *
     * @param o the object to compare with this Disposition Goal
     * @return true if the given object is a Disposition Goal with the same main resource, false otherwise
     */
    public boolean equals(Object o) {
        DispositionGoal that = (DispositionGoal) o;
        return mainResource == that.mainResource;
    }
}