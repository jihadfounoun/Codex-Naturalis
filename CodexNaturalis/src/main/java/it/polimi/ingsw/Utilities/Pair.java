package it.polimi.ingsw.Utilities;

/**
 * This class represents a pair of two generic types.
 * It is used to hold two related objects together.
 *
 * @param <C1> the type of the first element in the pair
 * @param <C2> the type of the second element in the pair
 * @author Jihad Founoun
 */
public class Pair<C1, C2> {
    // The first element of the pair
    private final C1 first;
    // The second element of the pair
    private final C2 second;

    /**
     * Constructs a new Pair with the given elements.
     *
     * @param first  the first element of the pair
     * @param second the second element of the pair
     */
    public Pair(C1 first, C2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first element of the pair.
     *
     * @return the first element of the pair
     */
    public C1 getFirst() {
        return first;
    }

    /**
     * Returns the second element of the pair.
     *
     * @return the second element of the pair
     */
    public C2 getSecond() {
        return second;
    }
}