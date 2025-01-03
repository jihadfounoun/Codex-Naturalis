package it.polimi.ingsw.UI;

import it.polimi.ingsw.Listeners.ViewListener;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.RegistrationMessage;
import it.polimi.ingsw.NetWork.Client.Client;
import it.polimi.ingsw.NetWork.Client.ClientApplication;
import it.polimi.ingsw.NetWork.Middleware.ServerStub;
import it.polimi.ingsw.NetWork.Server.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * <strong>PlayerInterface</strong>
 * <p>
 * Abstract class representing the interface of a player.
 * It defines the methods to manage the listeners and the connection with the server.
 * </p>
 *
 * @author Locatelli Mattia
 */
public abstract class PlayerInterface implements Runnable, View {
    protected final ArrayList<ViewListener> listeners;
    private Client client;

    /**
     * Constructs a new PlayerInterface.
     * It initializes the listeners list.
     */
    public PlayerInterface() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addListener(ViewListener listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyListeners(Message m) {
        for (ViewListener listener : listeners) {
            listener.handleMessage(m);
        }
    }

    /**
     * Set up the socket connection with the server, client side.
     * Creates a Thread to listen to the server
     *
     * @param ip   the ip of the server
     * @param port the port of the server
     * @throws RemoteException   if the connection fails
     * @throws IllegalArgumentException if the ip is null or the port is negative
     */
    public void setUpSocketConnection(String ip, int port) throws RemoteException, IllegalArgumentException {
        if (ip == null || port < 0) {
            throw new IllegalArgumentException("Invalid IP or port");
        }
        System.out.println("Setting up the socket connection...");
        ServerStub serverStub = new ServerStub(ip, port);
        this.client = ClientApplication.getClientInstance(serverStub, this);
        System.out.println("Socket connection established.");
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        serverStub.receive(client);
                    } catch (RemoteException e) {
                        System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                        break;
                    }
                }
            }
        }.start();
    }

    /**
     * set up the RMI connection with the server, client side
     *
     * @param ip   the ip of the server
     * @param port the port of the server
     * @throws IllegalArgumentException if the ip is null or the port is negative
     * @throws RemoteException        if the connection fails
     * @throws NotBoundException      if the server is not bound
     */
    public void setUpRMIConnection(String ip, int port) throws IllegalArgumentException, RemoteException, NotBoundException {
        if (ip == null || port < 0) {
            throw new IllegalArgumentException("Invalid IP or port");
        }

        Registry registry = LocateRegistry.getRegistry(ip, port);
        Server server = (Server) registry.lookup("CodexNaturalisServer");
        this.client = ClientApplication.getClientInstance(server, this);

    }

    /**
     * Start the connection with a game
     *
     * @param numberOfPlayer is 0 if the player isn't the first one to connect, is a number between 2 and 4 if the player is the first one to connect
     * @param username       is the username of the player
     */
    public void startConnection(int numberOfPlayer, String username) {
        notifyListeners(new RegistrationMessage(numberOfPlayer, username));
    }
}
