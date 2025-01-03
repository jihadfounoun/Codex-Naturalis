package it.polimi.ingsw.Messages;

import java.io.Serializable;

/**
 * <strong>Message</strong>
 * <p>
 * Message is an abstract class that represents a message sent to the client from the server or vice versa.
 * </p>
 *
 * @author Locatelli Mattia
 */
public abstract class Message implements Serializable {
    static final long serialVersionUID = 42L;

    /**
     * Message builder
     */
    public Message() {
    }
}