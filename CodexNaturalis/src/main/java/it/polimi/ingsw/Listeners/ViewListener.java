package it.polimi.ingsw.Listeners;

import it.polimi.ingsw.Messages.Message;

/**
 * <strong>ViewListener</strong>
 * <p>
 * This interface is used to implement the Observer pattern. It is used to notify the server about the player choices.
 * </p>
 *
 * @author Locatelli Mattia
 */
public interface ViewListener {
    /**
     * Method that handles the message sent by the client sending it to the server
     *
     * @param m the message to handle
     */
    void handleMessage(Message m);
}
