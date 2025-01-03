package it.polimi.ingsw.NetWork.Client;

import it.polimi.ingsw.Messages.Message;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <strong>Client</strong>
 * <p>
 * Client is an interface that defines the methods that the client side must implement.
 * </p>
 * <p>
 * It extends both the {@link Remote} and {@link Serializable} interfaces, so that it can be used in a RMI connection and sent over the network.
 * </p>
 *
 * @author Locatelli Mattia
 */
public interface Client extends Remote, Serializable {

    /**
     * receive a message from the server side to update the client side
     *
     * @param m the message to receive
     * @throws RemoteException if there is a connection error
     */
    void updateClient(Message m) throws RemoteException;
}
