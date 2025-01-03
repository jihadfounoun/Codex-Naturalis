package it.polimi.ingsw.UI;

import it.polimi.ingsw.Listeners.ViewListener;
import it.polimi.ingsw.Messages.EndGameMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.PingMessage;

/**
 * <strong>View</strong>
 * <p>
 * Interface for the view.
 * It defines the methods used to manage the {@link ViewListener} and to manage the turn cycle.
 * </p>
 *
 * @author Locatelli Mattia
 */
public interface View {
    /**
     * run method, which wait for client connection to take player choices.
     * It is divided in two parts: the first part is for the setting of the game, the second part is a loop of calls to methods for the player's turn choices.
     */
    void run();

    /**
     * Add a listener to the listeners list
     *
     * @param listener the {@link ViewListener} to add
     */
    void addListener(ViewListener listener);

    /**
     * Notify all the listeners with a message
     *
     * @param m the message to send
     */
    void notifyListeners(Message m);

    /**
     * update method, which adds the message to the queue and notifies the thread to handle it.
     * It also handles the {@link PingMessage} and the {@link EndGameMessage} with priority.
     *
     * @param m message to add to the queue.
     */
    void update(Message m);

}
