package it.polimi.ingsw.NetWork.Middleware;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.NetWork.Client.Client;
import it.polimi.ingsw.NetWork.Server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * ServerStub class
 * <p>
 * The ServerStub class represents the client-side stub of the Server interface.
 * It implements the Server interface and provides methods for sending and receiving messages to and from the server.
 * </p>
 */
public class ServerStub implements Server {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    String ip;
    int port;
    private Socket socket;

    /**
     * Default constructor for the ServerStub class.
     *
     * @param ip   the IP address of the server
     * @param port the port number of the server
     * @throws RemoteException if an error occurs during remote method invocation
     */
    public ServerStub(String ip, int port) throws RemoteException {
        this.ip = ip;
        this.port = port;
        try {
            Socket socket = new Socket(ip, port);

            try {
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create the output stream");
            }

            try {
                this.ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create the input stream");
            }

        } catch (IOException e) {
            throw new RemoteException("Unable to connect to the server");
        }

    }

    /**
     * Handles a message from a client.
     *
     * @param message the message to be handled
     * @param client  the client from which the message originates
     * @throws RemoteException if an error occurs during remote method invocation
     */
    @Override
    public void handleMessage(Message message, Client client) throws RemoteException {
        try {
            oos.writeObject(message);
        } catch (Exception e) {
            throw new RemoteException("Unable to send the message");
        }
    }

    /**
     * Adds a message from a client to the server's message queue.
     *
     * @param message the message to be added
     * @param client  the client from which the message originates
     * @throws RemoteException if an error occurs during remote method invocation
     */
    @Override
    public void addMessage(Message message, Client client) throws RemoteException {
        handleMessage(message, client);
    }

    /**
     * Receives a message from the server.
     *
     * @param client the client to which the message is to be sent
     * @throws RemoteException if an error occurs during remote method invocation
     */
    public void receive(Client client) throws RemoteException {
        try {
            Message message = (Message) ois.readObject();
            client.updateClient(message);
        } catch (Exception e) {
            throw new RemoteException("Unable to receive the message");
        }
    }

}
