package it.polimi.ingsw.ModelView.CardView.UtilitiesView;

import java.io.Serializable;

/**
 * This class represents a coordinate in a 2D space.
 * It implements Serializable, which means its instances can be saved to a file or sent over a network.
 *
 * @author Jihad Founoun
 */
public class CoordinateView implements Serializable {
    private final int x;
    private final int y;

    private final int boardSize;

    /**
     * Constructs a new CoordinateView with the given x and y coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param boardSize the size of the board
     */
    public CoordinateView(int x, int y, int boardSize) {
        this.x = x;
        this.y = y;
        this.boardSize = boardSize;
    }

    /**
     * Returns the x-coordinate of this CoordinateView.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y-coordinate of this CoordinateView.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return this.y;
    }


    /**
     * Returns a string representation of this coordinate.
     *
     * @return a string representation of the coordinate.
     */
    @Override
    public String toString() {
        int xConverted = x - boardSize / 2;
        int yConverted = (y - boardSize / 2) * (-1);
        return "(" + xConverted + ", " + yConverted + ")";
    }
}