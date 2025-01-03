package it.polimi.ingsw.Utilities.MessageQueue;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.NetWork.Client.Client;
import it.polimi.ingsw.Utilities.Pair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a message queue in the game.
 * It holds the messages that have to be sent to the clients.
 *
 * @author Amina El Kharouai
 */
public class MessageQueue {
    // Queue to hold pairs of messages and clients
    private final Queue<Pair<Message, Client>> queue;

    /**
     * Constructs a new MessageQueue.
     * Initializes the queue as a LinkedList.
     */
    public MessageQueue() {
        this.queue = new LinkedList<>();
    }

    /**
     * Adds a pair of message and client to the queue.
     *
     * @param pair The pair of message and client to be added to the queue.
     */
    public void push(Pair<Message, Client> pair) {
        queue.add(pair);
    }

    /**
     * Removes and returns the pair at the front of the queue.
     *
     * @return The pair at the front of the queue, or null if the queue is empty.
     */
    public Pair<Message, Client> pop() {
        return queue.poll();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return The number of elements in the queue.
     */
    public int size() {
        return queue.size();
    }
}