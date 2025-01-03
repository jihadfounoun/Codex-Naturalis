package it.polimi.ingsw.Model.Card.corners;


import it.polimi.ingsw.Model.Card.enumerations.Resource;

import java.io.Serializable;

import static java.lang.StringTemplate.STR;

/**
 * This class represents a resource corner .
 * It extends the Corner class and adds a type attribute of Resource enum.
 *
 * @author Jihad Founoun
 */
public class ResCorner extends Corner implements Serializable {
    private final Resource type;

    /**
     * Constructor for the ResCorner class.
     * @param type the type of the resource corner
     */
    public ResCorner(Resource type) {
        super(false, false);
        this.type = type;
    }

    /**
     * Gets the type of the resource corner.
     * @return the type of the resource corner
     */
    public Resource getType() {
        return type;
    }

    /**
     * Returns a string representation of the resource corner.
     * @return "hidden" if the corner is hidden, the type of the resource otherwise
     */
    @Override
    public String toString() {
        if (isHidden())
            return "hidden";
        return type.toString();
    }

    /**
     * Checks if this resource corner is equal to another object.
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ResCorner resCorner = (ResCorner) o;
        return type == resCorner.type;
    }

    /**
     * Returns a string representation of the resource corner for display.
     * @return "|||||" if the corner is hidden, "|   |" if the corner is empty, or the display of the type otherwise
     */
    @Override
    public String display() {
        if (isHidden()) {
            return "|||||";
        } else if (isEmpty()) {
            return "|   |";
        } else {
            return STR."|\{this.type.display()} |";
        }
    }
}
