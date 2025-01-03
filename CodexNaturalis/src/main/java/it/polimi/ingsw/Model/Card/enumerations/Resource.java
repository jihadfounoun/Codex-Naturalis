package it.polimi.ingsw.Model.Card.enumerations;

import java.io.Serializable;


/**
 * This enum represents a resource in the Codex Naturalis application.
 * The resources include animal, insect, fungi, and plant.
 *
 * @author Jihad Founoun
 */
public enum Resource implements Serializable {
    ANIMAL, INSECT, FUNGI, PLANT;

    /**
     * Returns a string representation of the resource.
     * @return a string representation of the resource
     */
    public String toString() {
        return switch (this) {
            case ANIMAL -> "Animal";
            case INSECT -> "Insect";
            case FUNGI -> "Fungi";
            case PLANT -> "Plant";
        };
    }

    /**
     * Returns a string representation of the resource for display.
     * @return a string representation of the resource for display
     */
    public String display() {
        return switch (this) {
            case ANIMAL -> "\uD83D\uDC3A";
            case INSECT -> "\uD83E\uDD8B";
            case FUNGI -> "\uD83C\uDF44";
            case PLANT -> "\uD83C\uDF31";
        };
    }

}
