package it.polimi.ingsw.Model.Card;


import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class represents an initial card in the Codex Naturalis application.
 * Each initial card has a list of permanent resources and extends the PlayableCard class.
 *
 * @author Jihad Founoun
 */
public class InitialCard extends PlayableCard {
    private final List<Resource> permanentResources;

    /**
     * Constructs an InitialCard with the specified identifier, back corners, front corners, and permanent resources.
     * @param id the unique identifier for this card
     * @param backCorners the back corners of this card
     * @param frontCorners the front corners of this card
     * @param permanentResources the list of permanent resources for this card
     */
    public InitialCard(String id, Corner[] backCorners, Corner[] frontCorners, List<Resource> permanentResources) {
        super(id, frontCorners, backCorners);

        this.permanentResources = permanentResources;
    }

    /**
     * Returns the back resources for this card.
     * @return the back resources for this card
     */
    public List<Resource> getBackResources() {
        return Arrays.stream(getBackCorners()).filter((x) -> x.getClass().equals(ResCorner.class)).map((x) -> ((ResCorner) x).getType()).collect(Collectors.toList());
    }

    /**
     * Returns the permanent resources for this card.
     * @return the permanent resources for this card
     */
    public List<Resource> getPermanentResources() {
        return permanentResources;
    }

    /**
     * Returns a string representation of the card.
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return "Initial Card " + super.toString() +
                "\n- Permanent resources: " + permanentResources;
    }

    /**
     * Returns a string representation of the played side of the card
     * @return a string representation of the played side of the card
     */
    @Override
    public String toStringToPlay() {
        String res = "";
        if (!isFront())
            res = "\n- Permanent resources: " + permanentResources;
        return cardCategory() + " Card - Played Side : " + (isFront() ? "Front" : "Back") + super.toStringToPlay() + res;
    }

    /**
     * Returns the category of the card.
     * @return the category of the card
     */
    public String cardCategory() {
        return "Initial";
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
        InitialCard that = (InitialCard) o;
        return Objects.equals(permanentResources, that.permanentResources);
    }
}
