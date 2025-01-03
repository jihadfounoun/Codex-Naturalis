package it.polimi.ingsw.NetWork.Middleware;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.NetWork.Client.Client;
import it.polimi.ingsw.NetWork.Server.ServerImplementation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;


/**
 * <strong>ClientSkeleton</strong>
 * <p>This class is used to create a client skeleton. It is a class that represent the connection of the client to the server. It has methods to receive and send messages
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ClientSkeleton implements Client {
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private final ServerImplementation server;

    /**
     * Constructor of the class, it creates the input and output stream of the client.
     *
     * @param socket the socket of the client
     * @param server the server implementation
     * @throws RemoteException if the input or output stream cannot be created
     */
    public ClientSkeleton(Socket socket, ServerImplementation server) throws RemoteException {
        this.server = server;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create input stream", e);
        }
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create output stream", e);
        }
    }

    /**
     * Methods used to send a message to the server writing it on the output stream.
     *
     * @param m the message to send
     * @throws RemoteException if the message cannot be sent
     */
    @Override
    public void updateClient(Message m) throws RemoteException {
        try {
            oos.writeObject(m);
        } catch (IOException e) {
            throw new RemoteException("Cannot send message to server", e);
        }
    }

    /**
     * Method used to receive a message from the client reading it from the input stream.
     *
     * @throws RemoteException if the message cannot be read
     */
    public void receive() throws RemoteException {
        Message m;
        try {
            m = (Message) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RemoteException("Cannot read message from server", e);
        }
        server.addMessage(m, this);
    }
}
