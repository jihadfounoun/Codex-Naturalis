package it.polimi.ingsw.Model.Card;


import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a gold card in the Codex Naturalis application.
 * Each gold card has a list of requirements and extends the ResourceCard class.
 *
 * @author Jihad Founoun
 */
public class GoldCard extends ResourceCard {
    private final List<Resource> requirements;

    /**
     * Constructs a GoldCard with the specified identifier, corners, type, and requirements.
     * @param id the unique identifier for this card
     * @param corners the corners of this card
     * @param type the type of resource this card represents
     * @param requirements the list of requirements for this card
     */
    public GoldCard(String id, Corner[] corners, Resource type, List<Resource> requirements) {
        super(id, corners, type);
        this.requirements = requirements;
    }

    /**
     * Computes the points for this card.
     * @return the computed points
     */
    public int computePoints() {
        int num_type, num_other;
        num_type = (int) requirements.stream().filter((x) -> x.equals(getType())).count();
        num_other = (int) requirements.stream().filter((x) -> !x.equals(getType())).count();
        if (num_other == 0)
            return num_type == 5 ? 5 : 3;
        return num_type == 3 ? 2 : 1;
    }

    /**
     * Returns the requirements for this card.
     * @return the requirements for this card
     */
    public List<Resource> getRequirements() {
        return requirements;
    }

    /**
     * Returns a string representation of the card.
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return super.toString() + pointRule() +
                "\n- Requirements: " + requirements
                ;

    }

    public String pointRule() {
        int points = computePoints();
        String description = "";
        switch (points) {
            case 1:
                description = " per object of type " + getFrontObject().toString() + " on your board";
                break;
            case 2:
                description = " per covered corner";
                break;
            case 3:
                description = "";
                break;
            case 5:
                description = "";
                break;
        }
        return description;
    }

    /**
     * Returns a string representation of the played side of the card
     * @return a string representation of the played side of the card
     */
    public String toStringToPlay() {
        String playedSide = isFront() ? "Front" : "Back";
        return cardCategory() + " Card - Type : " + getType().toString() + " - Played Side : " + playedSide + " " + super.toStringToPlay()
                + pointRule() + "\n- Requirements: " + requirements
                ;
    }

    /**
     * Returns the category of the card.
     * @return the category of the card
     */
    public String cardCategory() {
        return "Gold";
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
        GoldCard goldCard = (GoldCard) o;
        return Objects.equals(requirements, goldCard.requirements) && this.getType().equals(goldCard.getType());
    }

}
