package it.polimi.ingsw.Model.Card;

import java.util.Objects;

/**
 * This class represents the card's coordinate in the playing board.
 * Each coordinate has an x and y value.
 *
 * @author Jihad Founoun
 */
public class Coordinate {
    private int x, y;

    /**
     * Constructs a Coordinate with default values.
     */
    public Coordinate() {
        this.x = -1;
        this.y = -1;
    }

    /**
     * Constructs a Coordinate with the specified x and y values.
     * @param x the x value for this coordinate
     * @param y the y value for this coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the x and y values for this coordinate.
     * @param x the x value to set
     * @param y the y value to set
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the y value for this coordinate.
     * @return the y value for this coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y value for this coordinate.
     * @param y the y value to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the x value for this coordinate.
     * @return the x value for this coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x value for this coordinate.
     * @param x the x value to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns a string representation of the coordinate.
     * @return a string representation of the coordinate
     */
    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
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
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
