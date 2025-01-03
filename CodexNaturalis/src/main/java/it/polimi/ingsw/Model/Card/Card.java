package it.polimi.ingsw.Model.Card;

import java.util.Objects;
/**
 * This class represents a card in the Codex Naturalis application.
 * Each card has a unique identifier.
 *
 * @author Jihad Founoun
 */
public abstract class Card {
    private final String id;

    /**
     * Constructs a Card with the specified identifier.
     * @param id the unique identifier for this card
     */
    public Card(String id) {
        this.id = id;
    }

    /**
     * Returns the unique identifier for this card.
     * @return the unique identifier for this card
     */
    public String getId() {
        return id;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return Objects.equals(id, card.id);
    }
}
