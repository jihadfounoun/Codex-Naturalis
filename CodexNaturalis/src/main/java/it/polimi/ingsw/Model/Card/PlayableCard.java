package it.polimi.ingsw.Model.Card;

import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a playable card in the Codex Naturalis application.
 * Each playable card has front and back corners, a front side, a coordinate, and a used status.
 * It extends the Card class.
 *
 * @author Jihad Founoun
 */
public class PlayableCard extends Card {
    private final Corner[] frontCorners;
    private final Corner[] backCorners;
    private boolean front;
    private Coordinate coordinate;

    private boolean used;

    /**
     * Constructs a PlayableCard with the specified identifier, front corners, and back corners.
     * @param id the unique identifier for this card
     * @param frontCorners the front corners of this card
     * @param backCorners the back corners of this card
     */
    public PlayableCard(String id, Corner[] frontCorners, Corner[] backCorners) {
        super(id);
        this.front = true;
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
        this.coordinate = new Coordinate();
        this.used = false;
    }

    /**
     * Returns the used status of this card.
     * @return the used status of this card
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Sets the used status of this card.
     * @param used the used status to set
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

    /**
     * Sets the front side of this card to false.
     */
    public void useBack() {
        this.front = false;
    }


    /**
     * Returns the front side of this card.
     * @return the front side of this card
     */
    public boolean isFront() {
        return front;
    }

    /**
     * Returns the front corners of this card.
     * @return the front corners of this card
     */
    public Corner[] getFrontCorners() {
        return frontCorners;
    }

    /**
     * Returns the back corners of this card.
     * @return the back corners of this card
     */
    public Corner[] getBackCorners() {
        return backCorners;
    }

    /**
     * Returns the front corner at the specified index.
     * @param i the index of the front corner to return
     * @return the front corner at the specified index
     */
    public Corner getFrontCorner(int i) {
        return frontCorners[i];
    }


    /**
     * Returns the coordinate of this card.
     * @return the coordinate of this card
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Sets the coordinate of this card.
     * @param c the coordinate to set
     */
    public void setCoordinate(Coordinate c) {
        this.coordinate = c;
    }

    /**
     * Returns the front resources of this card.
     * @return the front resources of this card
     */
    public List<Resource> getFrontResources() {
        List<Resource> list = new ArrayList<>();
        for (Corner c : frontCorners)
            if (c.getClass().equals(ResCorner.class))
                list.add(((ResCorner) c).getType());
        return list;
    }

    /**
     * Returns a string representation of the card.
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return "\n- Corners : Front " + Arrays.toString(frontCorners) + ", Back " + Arrays.toString(backCorners);
    }

    /**
     * Returns a string representation of the played side of the card
     * @return a string representation of the played side of the card
     */
    public String toStringToPlay() {
        if (front)
            return "\n- Corners : " + Arrays.toString(frontCorners);
        return "\n- Corners : " + Arrays.toString(backCorners);
    }

    /**
     * Returns the playable corners of this card.
     * @return the playable corners of this card
     */
    public Corner[] getPlayableCorners() {
        return front ? frontCorners : backCorners;
    }

    /**
     * Sets the corner at the specified index to hidden.
     * @param i the index of the corner to set to hidden
     */
    public void setHiddenCorner(int i) {
        if (front)
            frontCorners[i].setHidden();
        else
            backCorners[i].setHidden();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayableCard that)) return false;
        if (!super.equals(o)) return false;
        return front == that.front && used == that.used && Objects.deepEquals(frontCorners, that.frontCorners) && Objects.deepEquals(backCorners, that.backCorners) && Objects.equals(coordinate, that.coordinate);
    }


}
