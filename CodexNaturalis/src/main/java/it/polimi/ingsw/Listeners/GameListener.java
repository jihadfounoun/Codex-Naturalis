package it.polimi.ingsw.Listeners;

import it.polimi.ingsw.Messages.Message;

/**
 * <strong>GameListener</strong>
 * <p>
 * This interface is used to implement the Observer pattern. It is used to notify the client of changes in the game.
 * </p>
 *
 * @author Locatelli Mattia
 */
public interface GameListener {

    /**
     * Method that updates the client with the message
     *
     * @param m the message to send
     */
    void update(Message m);
}
