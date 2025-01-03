package it.polimi.ingsw.ModelView.CardView;

import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents an InitialCardView in the game.
 * It implements Serializable, which means its instances can be saved to a file or sent over a network.
 * It holds the front corners, back corners, played side, and permanent resources of the initial card.
 *
 * @author Jihad Founoun
 */
public class InitialCardView implements Serializable {
    // The front corners of the initial card
    private final Corner[] frontCorners;
    // The back corners of the initial card
    private final Corner[] backCorners;
    // The played side of the initial card
    private final boolean playedSide;
    // The permanent resources of the initial card
    private final List<Resource> permanentResources;
    private final String id;

    /**
     * Constructs a new InitialCardView with the given front corners, back corners, played side, and permanent resources.
     *
     * @param frontCorners       the front corners of the initial card
     * @param backCorners        the back corners of the initial card
     * @param playedSide         the played side of the initial card
     * @param permanentResources the permanent resources of the initial card
     * @param id the id of the initial card
     */
    public InitialCardView(Corner[] frontCorners, Corner[] backCorners, boolean playedSide, List<Resource> permanentResources, String id) {
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
        this.playedSide = playedSide;
        this.permanentResources = permanentResources;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * Returns the front corners of this InitialCardView.
     *
     * @return the front corners of the initial card
     */
    public Corner[] getFrontCorners() {
        return frontCorners;
    }

    /**
     * Returns the back corners of this InitialCardView.
     *
     * @return the back corners of the initial card
     */
    public Corner[] getBackCorners() {
        return backCorners;
    }

    /**
     * Returns whether this InitialCardView is played side.
     *
     * @return true if the initial card is played side, false otherwise
     */
    public boolean getPlayedSide() {
        return playedSide;
    }

    /**
     * Returns the permanent resources of this InitialCardView.
     *
     * @return the permanent resources of the initial card
     */
    public List<Resource> getPermanentResources() {
        return permanentResources;
    }

    /**
     * Returns a string representation of the front view of this InitialCardView.
     *
     * @return a string representation of the front view of the initial card
     */
    public String viewFront(int index) {
        switch (index) {
            case 0 -> {
                return "1 - Front                    \t";
            }
            case 1, 9 -> {
                return "+---+-------------------+---+\t";
            }
            case 2 -> {
                return STR."\{this.getFrontCorners()[0].display()}                   \{this.getFrontCorners()[1].display()}\t";
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
     * Returns a string representation of the back view of this InitialCardView.
     *
     * @return a string representation of the back view of the initial card
     */
    public String viewBack(int index) {
        switch (index) {
            case 0 -> {
                return "2 - Back                     \t";
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
            case 4 -> {
                return STR."\{displayPermanentResources(1)}\t";
            }
            case 5 -> {
                return STR."\{displayPermanentResources(2)}\t";
            }
            case 6 -> {
                return STR."\{displayPermanentResources(3)}\t";
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
     * Returns a string representation of this InitialCardView.
     * If the card is played, it returns the front view, otherwise it returns the back view.
     *
     * @return a string representation of the initial card
     */
    @Override
    public String toString() {
        if (playedSide) {
            return "Front corners:  " + Arrays.toString(frontCorners);
        }

        return "Back corners: " + Arrays.toString(backCorners);

    }
    /**
     * Returns a string representation of the permanent resources of this card.
     * @param index the index determining the part of the card's representation to return
     * @return the permanent resources of this card
     */
    public String displayPermanentResources(int index) {
        if (permanentResources.size() == 3) {
            switch (index) {
                case 1 -> {
                    return STR."|            \{permanentResources.get(0).display()}             |";
                }
                case 2 -> {
                    return STR."|            \{permanentResources.get(1).display()}             |";
                }
                case 3 -> {
                    return STR."|            \{permanentResources.get(2).display()}             |";
                }
                default -> {
                    return "";
                }
            }
        } else if (permanentResources.size() == 2) {
            switch (index) {
                case 1 -> {
                    return STR."|            \{permanentResources.get(0).display()}             |";
                }
                case 2 -> {
                    return STR."|            \{permanentResources.get(1).display()}             |";
                }
                default -> {
                    return "|                           |";
                }
            }
        } else {
            switch (index) {
                case 2 -> {
                    return STR."|            \{permanentResources.get(0).display()}             |";
                }
                default -> {
                    return "|                           |";
                }
            }
        }
    }
}