package it.polimi.ingsw.Model.Card;


import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ObjCorner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;

/**
 * This class represents a resource card in the Codex Naturalis application.
 * Each resource card has a type of resource and extends the PlayableCard class.
 *
 * @author Jihad Founoun
 */
public class ResourceCard extends PlayableCard {
    private final Resource type;

    /**
     * Constructs a ResourceCard with the specified identifier, front corners, and type.
     * @param id the unique identifier for this card
     * @param frontCorners the front corners of this card
     * @param type the type of resource this card represents
     */
    public ResourceCard(String id, Corner[] frontCorners, Resource type) {
        super(id, frontCorners, new Corner[]{new Corner(true, false), new Corner(true, false), new Corner(true, false), new Corner(true, false)});
        this.type = type;
    }

    /**
     * Returns the type of resource for this card.
     * @return the type of resource for this card
     */
    public Resource getBackResource() {
        return getType();
    }

    /**
     * Returns the front object of this card.
     * @return the front object of this card
     */
    public TypeObject getFrontObject() {
        for (Corner c : getFrontCorners())
            if (c.getClass().equals(ObjCorner.class))
                return ((ObjCorner) c).getType();

        return null;
    }

    /**
     * Computes the points for this card.
     * @return the computed points
     */
    public int computePoints() {
        int count = 0;
        for (int i = 0; i < this.getFrontCorners().length; i++) {
            if (!getFrontCorner(i).getClass().equals(Corner.class)) {
                if (!getFrontCorner(i).getClass().equals(ResCorner.class))
                    return 0;
                if (((ResCorner) getFrontCorner(i)).getType() != type)
                    return 0;
                else
                    count++;
            }
            if (count > 1)
                return 0;

        }
        return 1;
    }

    /**
     * Returns the type of resource for this card.
     * @return the type of resource for this card
     */
    public Resource getType() {
        return type;
    }

    /**
     * Returns a string representation of the card.
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return cardCategory() + " Card - Type : " + type.toString() + " " + super.toString() +
                "\n- Points : " + computePoints();
    }

    /**
     * Returns the category of the card.
     * @return the category of the card
     */
    public String cardCategory() {
        return "Resource";
    }

    /**
     * Returns a string representation of the played side of the card
     * @return a string representation of the played side of the card
     */
    @Override
    public String toStringToPlay() {
        String playedSide = isFront() ? "Front" : "Back";
        return cardCategory() + " Card - Type : " + type.toString() + " - Played Side : " + playedSide + " " + super.toStringToPlay() +
                "\n- Points : " + computePoints();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ResourceCard that = (ResourceCard) o;
        return type == that.type;
    }


}
