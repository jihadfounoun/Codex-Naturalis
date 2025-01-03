package it.polimi.ingsw.Model.GoalCard;

import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.PlayableCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Player.PlayerBoard;

import java.io.Serializable;

/**
 * This class represents an LGoal in the game, which is a type of Disposition Goal.
 * An LGoal has a main resource, a secondary resource, and an extCorner associated with it.
 *
 * @author : Amina El Kharouai
 */
public class LGoal extends DispositionGoal implements Serializable {
    // The secondary resource associated with this LGoal.
    private final Resource secondaryResource;
    // The extCorner associated with this LGoal.
    private final int extCorner;

    /**
     * Constructs a new LGoal with the specified main resource, secondary resource, and extCorner.
     *
     * @param id                the ID of this LGoal
     * @param mainResource      the main resource associated with this LGoal
     * @param secondaryResource the secondary resource associated with this LGoal
     * @param extCorner         the extCorner associated with this LGoal
     */
    public LGoal(String id, Resource mainResource, Resource secondaryResource, int extCorner) {
        super(id, mainResource);
        this.secondaryResource = secondaryResource;
        this.extCorner = extCorner;
        this.setPoints(3);
    }

    /**
     * Checks the points of the PlayerBoard associated with this LGoal.
     * The method iterates over the played cards of the main resource and checks if they form an L shape with the secondary resource.
     * If they do, the method sets the cards as used and increments the scored points.
     * After checking all the cards, the method resets the used status of the cards and returns the scored points.
     *
     * @param playerBoard the PlayerBoard to check points for
     * @return the scored points
     */
    public int checkPoints(PlayerBoard playerBoard) {
        int scoredPoints = 0;
        Coordinate iCard = new Coordinate();

        for (PlayableCard i : playerBoard.getPlayedCards().get(mainResource)) {
            iCard.setX(i.getCoordinate().getX());
            iCard.setY(i.getCoordinate().getY());

            if (!i.isUsed()) {
                Coordinate c1 = new Coordinate(iCard.getX(), iCard.getY() - 2);
                if ((playerBoard.cardInPosition(c1) != null) && (playerBoard.cardInPosition(c1).getClass() != (InitialCard.class)) && (((ResourceCard) playerBoard.cardInPosition(c1)).getType() == mainResource) && (!(playerBoard.cardInPosition(c1)).isUsed())) {
                    Coordinate c2 = new Coordinate();
                    switch (extCorner) {
                        case 0:
                            c2.setX(iCard.getX() - 1);
                            c2.setY(iCard.getY() - 3);
                            break;
                        case 1:
                            c2.setX(iCard.getX() + 1);
                            c2.setY(iCard.getY() - 3);
                            break;
                        case 2:
                            c2.setX(iCard.getX() + 1);
                            c2.setY(iCard.getY() + 1);
                            break;
                        case 3:
                            c2.setX(iCard.getX() - 1);
                            c2.setY(iCard.getY() + 1);
                            break;
                    }
                    if ((playerBoard.cardInPosition(c2) != null) && (playerBoard.cardInPosition(c2).getClass() != (InitialCard.class)) && (((ResourceCard) playerBoard.cardInPosition(c2)).getType() == secondaryResource) && (!(playerBoard.cardInPosition(c2)).isUsed())) {
                        i.setUsed(true);
                        playerBoard.cardInPosition(c1).setUsed(true);
                        playerBoard.cardInPosition(c2).setUsed(true);
                        scoredPoints = scoredPoints + 3;
                    }
                }
            }
        }
        for (PlayableCard i : playerBoard.getPlayedCards().get(mainResource)) {
            i.setUsed(false);
        }
        for (PlayableCard i : playerBoard.getPlayedCards().get(secondaryResource)) {
            i.setUsed(false);
        }
        return scoredPoints;
    }

    /**
     * Returns a string representation of this LGoal.
     *
     * @return a string representation of this LGoal
     */
    @Override
    public String toString() {
        return switch (mainResource) {
            case FUNGI -> ("\033[31m" + "                           ___\n" + "\033[31m" +
                    "\033[0m" + "             Fungi        " + "\033[0m" + "\033[31m" + "|___|\n" + "\033[31m" +
                    "\033[0m" + "               L:          " + "\033[0m" + "\033[31m" + "___\n" + "\033[31m" +
                    "\033[31m" + "                          |___|" + "\033[31m" + "\033[32m" + "___\n" + "\033[32m" +
                    "\033[32m" + "                              |___|\n" + "\033[32m" +
                    "\033[0m" + "\n" +
                    "Goal: dispose two Fungi cards one above the other; one Plant card on the low-right corner as shown in the figure." + "\nPoints: " + this.getPoints() + "\n" + "\033[0m");
            case ANIMAL -> ("\033[31m" + "                               ___\n" + "\033[31m" +
                    "                           " + "\033[34m" + "___" + "\033[34m" + "\033[31m" + "|___|\n" + "\033[31m" +
                    "\033[0m" + "             Animal       " + "\033[0m" + "\033[34m" + "|___|\n" + "\033[34m" +
                    "\033[0m" + "               L:          " + "\033[0m" + "\033[34m" + "___\n" + "\033[34m" +
                    "\033[34m" + "                          |___|\n" + "\033[34m" +
                    "\033[0m" + "\n" +
                    "Goal: dispose two Animal cards one above the other; one Fungi card on the up-right corner as shown in the figure." + "\nPoints: " + this.getPoints() + "\n" + "\033[0m");
            case PLANT -> ("\033[32m" + "                           ___\n" + "\033[32m" +
                    "\033[0m" + "             Plant        " + "\033[0m" + "\033[32m" + "|___|\n" + "\033[32m" +
                    "\033[0m" + "               L:          " + "\033[0m" + "\033[32m" + "___\n" + "\033[32m" +
                    "\033[35m" + "                       ___" + "\033[35m" + "\033[32m" + "|___|\n" + "\033[32m" +
                    "\033[35m" + "                      |___|\n" + "\033[35m" +
                    "\033[0m" + "\n" +
                    "Goal: dispose two Plant cards one above the other; one Insect card on the low-left corner as shown in the figure." + "\nPoints: " + this.getPoints() + "\n" + "\033[0m");
            case INSECT -> ("\033[34m" + "                       ___\n" + "\033[34m" +
                    "\033[34m" + "                      |___|" + "\033[34m" + "\033[35m" + "___\n" + "\033[35m" +
                    "\033[0m" + "             Insect       " + "\033[0m" + "\033[35m" + "|___|\n" + "\033[35m" +
                    "\033[0m" + "               L:          " + "\033[0m" + "\033[35m" + "___\n" + "\033[35m" +
                    "\033[35m" + "                          |___|\n" + "\033[35m" +
                    "\033[0m" + "\n" +
                    "Goal: dispose two Insect cards one above the other; one Animal card on the up-left corner as shown in the figure." + "\nPoints: " + this.getPoints() + "\n" + "\033[0m");
            default -> "Invalid LGoal";
        };
    }
/*
    public String toString() {
        return "LGoal{" +
                "mainResource=" + mainResource +
                ", secondaryResource=" + secondaryResource +
                ", extCorner=" + extCorner +
                '}';
    }*/

    /**
     * Returns a boolean indicating whether or not this LGoal is equal to the specified object.
     * Two LGoals are considered equal if they have the same main resource, secondary resource, and extCorner.
     *
     * @param o the object to compare this LGoal against
     * @return true if the LGoals are equal, false otherwise
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LGoal that)) return false;
        if (!super.equals(o)) return false;
        return this.extCorner == that.extCorner && this.secondaryResource == that.secondaryResource;
    }
}
