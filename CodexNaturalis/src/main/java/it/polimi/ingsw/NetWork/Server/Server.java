package it.polimi.ingsw.NetWork.Server;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.NetWork.Client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Server interface
 * <p>
 * The Server interface represents the server-side component of the Codex Naturalis game.
 * It extends the Remote interface, allowing its methods to be invoked from a non-local JVM.
 * </p>
 *
 * <p>
 * This interface provides methods for handling and adding messages, which are used for communication between the server and clients.
 * </p>
 *
 * @author Amina El Kharouai
 */
public interface Server extends Remote {
    /**
     * Handles a message from a client.
     *
     * @param message the message to be handled
     * @param client  the client from which the message originates
     * @throws RemoteException if an error occurs during remote method invocation
     */
    void handleMessage(Message message, Client client) throws RemoteException;

    /**
     * Adds a message from a client to the server's message queue.
     *
     * @param message the message to be added
     * @param client  the client from which the message originates
     * @throws RemoteException if an error occurs during remote method invocation
     */
    void addMessage(Message message, Client client) throws RemoteException;
}