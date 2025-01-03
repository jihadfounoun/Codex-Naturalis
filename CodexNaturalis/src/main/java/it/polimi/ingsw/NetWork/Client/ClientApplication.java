package it.polimi.ingsw.NetWork.Client;

import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.NetWork.Server.Server;
import it.polimi.ingsw.UI.TUI;
import it.polimi.ingsw.UI.View;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * <strong>ClientApplication</strong>
 * <p>
 * Class that implements the client side of the network, {@link Client} implementation.
 * It is responsible for updating the client side of the network and set the listener for the view.
 * </p>
 *
 * @author Locatelli Mattia
 */

public class ClientApplication extends UnicastRemoteObject implements Client {

    private static ClientApplication clientInstance;
    private final View view;
    private final Server server;

    /**
     * Constructor of the class.
     * It creates the client instance and set the listener for the view.
     * It also set the server instance.
     *
     * @param server the server
     * @param view   the view
     * @throws RemoteException if the server is not reachable
     */
    private ClientApplication(Server server, View view) throws RemoteException {
        super();
        this.view = view;
        this.server = server;
        view.addListener((message) -> {
            try {
                server.handleMessage(message, this);
            } catch (RemoteException e) {
                System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
            }
        });
    }

    /**
     * Method that creates the client instance
     * follow the singleton pattern
     *
     * @param server the server
     * @param view   the view
     * @return clientInstance
     * @throws RemoteException if the server is not reachable
     */
    public static ClientApplication getClientInstance(Server server, View view) throws RemoteException {
        if (clientInstance == null)
            clientInstance = new ClientApplication(server, view);
        return clientInstance;
    }

    /**
     * Main method of the class.
     * @param args the arguments
     * @throws IOException if there is an error in the input/output
     */
    public static void main(String[] args) throws IOException {
        View userInterface = new TUI();
        userInterface.run();
    }

    @Override
    public void updateClient(Message m) {
        this.view.update(m);
    }
}
