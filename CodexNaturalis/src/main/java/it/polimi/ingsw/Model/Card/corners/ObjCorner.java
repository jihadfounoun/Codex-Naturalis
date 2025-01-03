package it.polimi.ingsw.Model.Card.corners;


import it.polimi.ingsw.Model.Card.enumerations.TypeObject;

import java.io.Serializable;

import static java.lang.StringTemplate.STR;

/**
 * This class represents an object corner.
 * It extends the Corner class and adds a type attribute of TypeObject enum.
 *
 * @author Jihad Founoun
 */
public class ObjCorner extends Corner implements Serializable {
    private final TypeObject type;

    /**
     * Constructor for the ObjCorner class.
     * @param type the type of the object corner
     */
    public ObjCorner(TypeObject type) {
        super(false, false);
        this.type = type;
    }

    /**
     * Gets the type of the object corner.
     * @return the type of the object corner
     */
    public TypeObject getType() {
        return type;
    }

    /**
     * Returns a string representation of the object corner.
     * @return "hidden" if the corner is hidden, the type of the object otherwise
     */
    @Override
    public String toString() {
        if (isHidden())
            return "hidden";
        return type.toString();
    }

    /**
     * Checks if this object corner is equal to another object.
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObjCorner objCorner = (ObjCorner) o;
        return type == objCorner.type;
    }

    /**
     * Returns a string representation of the object corner for display.
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
