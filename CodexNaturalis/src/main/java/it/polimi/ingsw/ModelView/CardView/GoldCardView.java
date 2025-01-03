package it.polimi.ingsw.ModelView.CardView;

import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a GoldCardView in the game.
 * It extends the ResourceCardView and implements Serializable, which means its instances can be saved to a file or sent over a network.
 * It holds the requirements of the gold card.
 *
 * @author Jihad Founoun
 */
public class GoldCardView extends ResourceCardView implements Serializable {
    // The requirements of the gold card
    private final List<Resource> requirements;

    /**
     * Constructs a new GoldCardView with the given points, type, requirements, frontCorners, backCorners, and playedSide.
     *
     * @param points       the points of the gold card
     * @param type         the type of the gold card
     * @param requirements the requirements of the gold card
     * @param frontCorners the front corners of the gold card
     * @param backCorners  the back corners of the gold card
     * @param playedSide   the played side of the gold card
     * @param id the id of the gold card
     */
    public GoldCardView(int points, Resource type, List<Resource> requirements, Corner[] frontCorners, Corner[] backCorners, boolean playedSide, String id) {
        super(frontCorners, playedSide, type, points, backCorners, id);
        this.requirements = requirements;
    }

    /**
     * Returns the requirements of this GoldCardView.
     *
     * @return the requirements of the gold card
     */
    public List<Resource> getRequirements() {
        return requirements;
    }

    /**
     * Returns a string representation of the front view of this GoldCardView.
     * The index parameter determines which part of the card's representation to return.
     *
     * @param index the index determining the part of the card's representation to return
     * @return a string representation of the front view of the gold card
     */
    public String viewFront(int index) {
        switch (index) {
            case 0 -> {
                return "GoldCard:                    \t";
            }
            case 1 -> {
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
            case 9 -> {
                return STR."+---+\{this.displayRequirements()}+---+\t";
            }
            default -> {
                return "";
            }
        }
    }

    /**
     * Returns a string representation of the back view of this GoldCardView.
     * The index parameter determines which part of the card's representation to return.
     *
     * @param index the index determining the part of the card's representation to return
     * @return a string representation of the back view of the gold card
     */
    public String viewBack(int index) {
        switch (index) {
            case 0 -> {
                return "GoldCard:                \t";
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
     * Returns a string representation of this GoldCardView.
     * If the card is played, it returns the front view, otherwise it returns the back view.
     *
     * @return a string representation of the gold card
     */
    @Override
    public String toString() {
        if (this.isPlayedSide()) {
            return "Corners: " + Arrays.toString(this.getFrontCorners()) + ", points: " + this.getPoints() +
                    ", requirements: " + requirements;
        }
        return "Corners: " + Arrays.toString(this.getBackCorners());
    }

    /**
     * Returns the class type of this GoldCardView.
     *
     * @return the class type of the gold card
     */
    public String getClassType() {
        return "Gold card: ";
    }

    /**
     * Returns a string representation of the requirements of this GoldCardView.
     * The representation depends on the size of the requirements list.
     *
     * @return a string representation of the requirements of the gold card
     */
    public String displayRequirements() {
        if (this.requirements.size() == 3) {
            return STR."------\{this.requirements.get(0).display()}\{this.requirements.get(1).display()}\{this.requirements.get(2).display()}-------";
        } else if (this.requirements.size() == 4) {
            return STR."-----\{this.requirements.get(0).display()}\{this.requirements.get(1).display()}\{this.requirements.get(2).display()}\{this.requirements.get(3).display()}------";
        } else {
            return STR."----\{this.requirements.get(0).display()}\{this.requirements.get(1).display()}\{this.requirements.get(2).display()}\{this.requirements.get(3).display()}\{this.requirements.get(4).display()}-----";
        }
    }


}