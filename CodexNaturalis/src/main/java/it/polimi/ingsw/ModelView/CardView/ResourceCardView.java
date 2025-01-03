package it.polimi.ingsw.ModelView.CardView;

import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class represents a ResourceCardView in the game.
 * It implements Serializable, which means its instances can be saved to a file or sent over a network.
 * It holds the front corners, back corners, played side, type, and points of the resource card.
 *
 * @author Jihad Founoun
 */
public class ResourceCardView implements Serializable {
    // The front corners of the resource card
    private final Corner[] frontCorners;
    // The played side of the resource card
    private final boolean playedSide;
    // The type of the resource card
    private final Resource type;
    // The points of the resource card
    private final int points;
    // The back corners of the resource card
    private final Corner[] backCorners;

    private final String id;

    /**
     * Constructs a new ResourceCardView with the given front corners, played side, type, points, and back corners.
     *
     * @param frontCorners the front corners of the resource card
     * @param playedSide   the played side of the resource card
     * @param type         the type of the resource card
     * @param points       the points of the resource card
     * @param backCorners  the back corners of the resource card
     * @param id the id of the resource card
     */
    public ResourceCardView(Corner[] frontCorners, boolean playedSide, Resource type, int points, Corner[] backCorners, String id) {
        this.frontCorners = frontCorners;
        this.playedSide = playedSide;
        this.type = type;
        this.points = points;
        this.backCorners = backCorners;
        this.id = id;
    }

    /**
     * Returns the id of this ResourceCardView.
     *
     * @return the id of the resource card
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the front corners of this ResourceCardView.
     *
     * @return the front corners of the resource card
     */
    public Corner[] getFrontCorners() {
        return frontCorners;
    }

    /**
     * Returns whether this ResourceCardView is played side.
     *
     * @return true if the resource card is played side, false otherwise
     */
    public boolean isPlayedSide() {
        return playedSide;
    }

    /**
     * Returns the type of this ResourceCardView.
     *
     * @return the type of the resource card
     */
    public Resource getType() {
        return type;
    }

    /**
     * Returns the points of this ResourceCardView.
     *
     * @return the points of the resource card
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns the back corners of this ResourceCardView.
     *
     * @return the back corners of the resource card
     */
    public Corner[] getBackCorners() {
        return backCorners;
    }

    /**
     * Returns a string representation of the front view of this ResourceCardView.
     * The index parameter determines which part of the card's representation to return.
     *
     * @param index the index determining the part of the card's representation to return
     * @return a string representation of the front view of the resource card
     */
    public String viewFront(int index) {
        switch (index) {
            case 0 -> {
                return "ResourceCard:            \t";
            }
            case 1, 9 -> {
                return "+---+-------------------+---+\t";
            }
            case 2 -> {
                return STR."\{this.getFrontCorners()[0].display()}         \{this.getPoints()}         \{this.getFrontCorners()[1].display()}\t";
            }
            case 3, 7 -> {
                return "+---+                   +---+\t";
            }
            case 4, 6, 5 -> {
                return "|                           |\t";
            }
            case 8 -> {
                return STR."\{this.getFrontCorners()[3].display()}                   \{this.getFrontCorners()[2].display()}\t";
            }
            default -> {
                return "";
            }
        }
    }


    /**
     * Returns a string representation of the back view of this ResourceCardView.
     * The index parameter determines which part of the card's representation to return.
     *
     * @param index the index determining the part of the card's representation to return
     * @return a string representation of the back view of the resource card
     */
    public String viewBack(int index) {
        switch (index) {
            case 0 -> {
                return "ResourceCard:                \t";
            }
            case 1, 9 -> {
                return "+---+-------------------+---+\t";
            }
            case 2 -> {
                return STR."\{this.getBackCorners()[0].display()}                   \{this.getBackCorners()[1].display()}\t";
            }
            case 3, 7 -> {
                return "+---+                   +---+\t";
            }
            case 4, 6 -> {
                return "|                           |\t";
            }
            case 5 -> {
                return STR."|            \{this.getType().display()}             |\t";
            }
            case 8 -> {
                return STR."\{this.getBackCorners()[3].display()}                   \{this.getBackCorners()[2].display()}\t";
            }
            default -> {
                return "";
            }
        }
    }

    /**
     * Returns a string representation of this ResourceCardView.
     * If the card is played, it returns the front view, otherwise it returns the back view.
     *
     * @return a string representation of the resource card
     */
    @Override
    public String toString() {
        if (playedSide) {
            return "Corners: " + Arrays.toString(frontCorners);

        }
        return "Corners: " + Arrays.toString(backCorners);
    }
    /**
     * Returns a string representation of the category of this cardView.
     *
     * @return the category of the resource card
     */
    public String getClassType() {
        return "Resource card: ";
    }
}