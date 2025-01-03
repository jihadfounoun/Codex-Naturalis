package it.polimi.ingsw.Model.GoalCard;

import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.PlayableCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Player.PlayerBoard;

import java.io.Serializable;

/**
 * This class represents a Diagonal Goal in the game, which is a type of Disposition Goal.
 * A Diagonal Goal has a main resource and a boolean indicating if it's a main diagonal or not.
 *
 * @author : Amina El Kharouai
 */
public class DiagonalGoal extends DispositionGoal implements Serializable {
    // Indicates if this Diagonal Goal is a main diagonal or not.
    private final boolean mainDiagonal;

    /**
     * Constructs a new Diagonal Goal with the specified main resource and main diagonal.
     *
     * @param id           the ID of this Diagonal Goal
     * @param mainResource the main resource associated with this Diagonal Goal
     * @param mainDiagonal indicates if this Diagonal Goal is a main diagonal or not
     */
    public DiagonalGoal(String id, Resource mainResource, boolean mainDiagonal) {
        super(id, mainResource);
        this.mainDiagonal = mainDiagonal;
        this.setPoints(2);
    }

    /**
     * Checks the points of the PlayerBoard associated with this Diagonal Goal.
     * The method iterates over the played cards of the main resource and checks if they form a diagonal.
     * If they do, the method sets the cards as used and increments the scored points.
     * After checking all the cards, the method resets the used status of the cards and returns the scored points.
     *
     * @param playerBoard the PlayerBoard to check points for
     * @return the scored points
     */
    public int checkPoints(PlayerBoard playerBoard) {
        int scoredPoints = 0;
        Coordinate iCard = new Coordinate();
        Coordinate c1 = new Coordinate();
        Coordinate c2 = new Coordinate();

        for (ResourceCard i : playerBoard.getPlayedCards().get(mainResource)) {
            iCard.setX(i.getCoordinate().getX());
            iCard.setY(i.getCoordinate().getY());
            if (mainDiagonal) {
                c1.setX(iCard.getX() + 1);
                c1.setY(iCard.getY() - 1);
                c2.setX(iCard.getX() + 2);
                c2.setY(iCard.getY() - 2);
            } else {
                c1.setX(iCard.getX() - 1);
                c1.setY(iCard.getY() - 1);
                c2.setX(iCard.getX() - 2);
                c2.setY(iCard.getY() - 2);
            }
            if (!i.isUsed()) {
                if ((playerBoard.cardInPosition(c1) != null) && (playerBoard.cardInPosition(c1).getClass() != (InitialCard.class)) && (((ResourceCard) playerBoard.cardInPosition(c1)).getType() == mainResource) && (!(playerBoard.cardInPosition(c1)).isUsed())) {
                    if ((playerBoard.cardInPosition(c2) != null) && (playerBoard.cardInPosition(c2).getClass() != (InitialCard.class)) && (((ResourceCard) playerBoard.cardInPosition(c2)).getType() == mainResource) && (!(playerBoard.cardInPosition(c2)).isUsed())) {
                        i.setUsed(true);
                        playerBoard.cardInPosition(c1).setUsed(true);
                        playerBoard.cardInPosition(c2).setUsed(true);
                        scoredPoints = scoredPoints + 2;
                    }
                }
            }
        }
        for (PlayableCard i : playerBoard.getPlayedCards().get(mainResource)) {
            i.setUsed(false);
        }
        return scoredPoints;
    }

    /**
     * Returns a string representation of this Diagonal Goal.
     *
     * @return a string representation of this Diagonal Goal
     */
    @Override
    public String toString() {
        return switch (mainResource) {
            case FUNGI -> ("\033[31m" + "                                   ___\n" + "\033[31m" +
                    "\033[0m" + "             Fungi             " + "\033[0m" + "\033[31m" + "___|___|\n" + "\033[31m" +
                    "\033[0m" + "            Diagonal:      " + "\033[0m" + "\033[31m" + "___|___|\n" +
                    "\033[31m" + "                          |___|\n" + "\033[31m" +
                    "\n" +
                    "\033[0m" +
                    "Goal: dispose three Fungi cards as shown in the figure." + "\nPoints: " + this.getPoints() + "\n" + "\033[0m");
            case ANIMAL -> ("\033[34m" + "                                   ___\n" + "\033[34m" +
                    "\033[0m" + "             Animal            " + "\033[0m" + "\033[34m" + "___|___|\n" + "\033[34m" +
                    "\033[0m" + "            Diagonal:      " + "\033[0m" + "\033[34m" + "___|___|\n" +
                    "\033[34m" + "                          |___|\n" + "\033[34m" +
                    "\n" +
                    "\033[0m" +
                    "Goal: dispose three Animal cards as shown in the figure." + "\nPoints: " + this.getPoints() + "\n" + "\033[0m");
            case PLANT -> ("\033[32m" + "                           ___\n" + "\033[32m" +
                    "\033[0m" + "             Plant        " + "\033[0m" + "\033[32m" + "|___|___\n" + "\033[32m" +
                    "\033[0m" + "            Diagonal:         " + "\033[0m" + "\033[32m" + "|___|___\n" +
                    "\033[32m" + "                                  |___|\n" + "\033[32m" +
                    "\n" +
                    "\033[0m" +
                    "Goal: dispose three Plant cards as shown in the figure.\n" + "\nPoints: " + this.getPoints() + "\n" + "\033[0m");
            case INSECT -> ("\033[35m" + "                           ___\n" + "\033[35m" +
                    "\033[0m" + "             Insect       " + "\033[0m" + "\033[35m" + "|___|___\n" + "\033[35m" +
                    "\033[0m" + "            Diagonal:         " + "\033[0m" + "\033[35m" + "|___|___\n" +
                    "\033[35m" + "                                  |___|\n" + "\033[35m" +
                    "\n" +
                    "\033[0m" +
                    "Goal: dispose three Insect cards as shown in the figure.\n" + "\nPoints: " + this.getPoints() + "\n" + "\033[0m");
            default -> "Invalid DiagonalGoal";
        };
    }
/*
    public String toString() {
        return switch (mainResource) {
            case FUNGI -> ("Fungi");
            case ANIMAL -> ("Animal");
            case PLANT -> ("Plant");
            case INSECT -> ("Insect");
            default -> "Invalid DiagonalGoal";
        };
    }*/

    /**
     * Checks if this Diagonal Goal is equal to another object.
     * Two Diagonal Goals are equal if they have the same main resource and main diagonal.
     *
     * @param o the object to compare this Diagonal Goal to
     * @return true if the Diagonal Goals are equal, false otherwise
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiagonalGoal that)) return false;
        if (!super.equals(o)) return false;
        return mainDiagonal == that.mainDiagonal;
    }
}