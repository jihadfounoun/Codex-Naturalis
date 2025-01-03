package it.polimi.ingsw.NetWork.Server;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.GameLobby.GameLobby;
import it.polimi.ingsw.GameLobby.GameLobbyView;
import it.polimi.ingsw.GameLobby.LobbiesController;
import it.polimi.ingsw.Listeners.GameListener;
import it.polimi.ingsw.Messages.*;
import it.polimi.ingsw.NetWork.Client.Client;
import it.polimi.ingsw.NetWork.Middleware.ClientSkeleton;
import it.polimi.ingsw.Utilities.MessageQueue.MessageQueue;
import it.polimi.ingsw.Utilities.Pair;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ServerImplementation
 * The ServerImplementation class is the main server-side class for the Codex Naturalis game.
 * It is responsible for managing all network-related tasks such as handling client connections,
 * receiving and processing messages from clients, and managing game lobbies.
 * This class is a singleton, meaning there can only be one instance of it throughout the application.
 * It extends UnicastRemoteObject and implements the Server interface, allowing it to be used for remote method invocation (RMI).
 * The ServerImplementation class maintains a message queue for storing incoming messages from clients,
 * a MainController for processing these messages, and maps for tracking connected players, usernames, and game lobbies.
 * It also maintains an external lobby for players who are waiting to join a game.
 * The class uses an ExecutorService for managing threads, allowing it to handle multiple client connections and messages concurrently.
 * The ServerImplementation class provides methods for starting socket and RMI connections, registering players, creating games,
 * and joining games. It also provides methods for adding messages to the message queue and handling incoming messages from clients.
 * The main method of the ServerImplementation class creates an instance of the server and starts threads for handling socket and RMI connections.
 *
 * @author : Amina El Kharouai
 */
public class ServerImplementation extends UnicastRemoteObject implements Server {

    private static ServerImplementation serverInstance = null;
    private static MainController mainController;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final MessageQueue messages;
    private final Object messageLock = new Object();
    private final Map<String, Pair<Client, GameListener>> usernames;
    private final Map<String, Boolean> connectedPlayers;
    private final Object connectedPlayersLock = new Object();

    private final LobbiesController lobbiesController;

    /**
     * Constructs a new ServerImplementation.
     * It initializes the message queue, main controller, connected players, usernames, lobbies, and external lobby.
     * It also starts a new thread for handling messages and a new thread for sending ping messages.
     *
     * @throws RemoteException if the superclass's constructor throws a RemoteException
     */
    private ServerImplementation() throws RemoteException {
        super();
        messages = new MessageQueue();
        connectedPlayers = new HashMap<>();
        usernames = new HashMap<>();
        lobbiesController = new LobbiesController();


        // Start a new thread for handling messages
        new Thread(() -> {
            while (true) {
                synchronized (messageLock) {
                    try {
                        messageLock.wait();
                        if (!messages.isEmpty()) {
                            // Get the message and client pair from the message queue
                            Pair<Message, Client> pair = messages.pop();
                            try {
                                handleMessage(pair.getFirst(), pair.getSecond());
                            } catch (RemoteException e) {
                                System.err.println("Cannot handle message");
                            }
                        }
                    } catch (InterruptedException e) {
                        System.err.println("Cannot wait for message");
                    }
                }

            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(500);
                synchronized (messageLock) {
                    messageLock.notifyAll();
                }
            } catch (InterruptedException e) {
                System.err.println("Cannot wait for message");
            }

        }).start();


    }

    /**
     * The main method for the ServerImplementation class.
     * It creates an instance of the server and starts threads for handling socket and RMI connections.
     *
     * @param args the command-line arguments
     * @throws RemoteException if the getServerInstance method throws a RemoteException
     */
    public static void main(String[] args) throws RemoteException {

        // Create an instance of the server
        getServerInstance();
        mainController = new MainController();
        // Start a thread for handling socket connections
        Thread socketThread = new Thread(ServerImplementation::startSocketConnection);
        socketThread.start();

        // Start a thread for handling RMI connections
        String hostname = "localhost";
        if (args.length > 0) {
            hostname = args[0];
        }
        Thread rmiThread = new Thread(() -> {
            try {
                startRMIConnection();
            } catch (RemoteException | AlreadyBoundException e) {
                throw new RuntimeException(e);
            }
        });
        System.setProperty("java.rmi.server.hostname", hostname);
        rmiThread.start();
    }

    /**
     * Returns the single instance of the ServerImplementation class.
     * If the instance does not exist, it creates a new one.
     *
     * @return the single instance of the ServerImplementation class
     * @throws RemoteException if the ServerImplementation constructor throws a RemoteException
     */
    public static ServerImplementation getServerInstance() throws RemoteException {
        if (serverInstance == null) {
            serverInstance = new ServerImplementation();
        }
        return serverInstance;
    }

    /**
     * Starts a socket connection for the server.
     * It creates a new server socket and waits for client connections.
     * When a client connects, it creates a new client skeleton and starts a new thread for receiving messages from the client.
     */
    private static void startSocketConnection() {
        // Create a new server socket
        int player = 0;
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                System.out.println("Waiting for client connection...");
                Socket socket = serverSocket.accept();
                player++;
                System.out.println("Client connected");
                int finalPlayer = player;
                getServerInstance().executorService.execute(() -> {
                    try {
                        // Create a new client skeleton
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket, getServerInstance());
                        while (true) {
                            // Receive messages from the client
                            try {
                                clientSkeleton.receive();
                            } catch (RemoteException e) {
                                System.err.println("Cannot receive message from client " + e.getMessage());
                                break;
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Cannot create client skeleton");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                });
            }
        } catch (IOException e) {
            System.err.println("Cannot create server socket");
        }
    }

    /**
     * Starts an RMI connection for the server.
     * It creates a new registry and binds the server instance to it.
     *
     * @throws RemoteException       if the LocateRegistry.createRegistry method throws a RemoteException
     * @throws AlreadyBoundException if the registry.rebind method throws an AlreadyBoundException
     */
    private static void startRMIConnection() throws RemoteException, AlreadyBoundException {
        // Create a new registry and bind the server instance to it
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("CodexNaturalisServer", getServerInstance());
    }


    /**
     * Registers a player with the server.
     * It generates a unique username for the player and adds the player to the connected players and usernames maps.
     * It then checks if the player can join an existing game or if a new game needs to be created.
     *
     * @param client            the client to register
     * @param requestedUsername the requested username of the player
     * @param numOfPlayers      the number of players in the game
     * @param listener          the game listener for the player
     * @throws RemoteException if the listener.update method throws a RemoteException
     */
    private void registerPlayer(Client client, String requestedUsername, int numOfPlayers, GameListener listener) throws RemoteException {
        // If requestedUsername is already in the usernames map, ask the player to choose another username
        if (usernames.containsKey(requestedUsername)) {
            //Calcolo un nuovo username da dare al player come consiglio
            String username = requestedUsername + (int) (Math.random() * 100);
            //check if the new username is already in the usernames map
            while (usernames.containsKey(username)) {
                username = requestedUsername + (int) (Math.random() * 100);
            }
            listener.update(new UsernameAlreadyExistsMessage(username));
            return;
        }
        Pair<Client, GameListener> pair = new Pair<>(client, listener);
        usernames.put(requestedUsername, pair);
        // Add the player to the connected players and usernames maps


        if (numOfPlayers == 0) {
            sendAvailableLobbies(listener);
        } else if (numOfPlayers > 1 && numOfPlayers < 5) {

            createNewGame(requestedUsername, numOfPlayers, listener);
        } else {
            listener.update(new InvalidNumberOfPlayersMessage());
        }
    }

    /**
     * Sends a message to the player with the available lobbies.
     * If there are no available lobbies, it sends a message to the player indicating that there are no available lobbies.
     * If there are available lobbies, it sends a message to the player with the available lobbies.
     *
     * @param listener the game listener for the player
     * @throws RemoteException if the listener.update method throws a RemoteException
     */
    private void sendAvailableLobbies(GameListener listener) {
        ArrayList<GameLobby> lobbies = lobbiesController.getAvailableLobbies();
        if (lobbies.isEmpty()) {
            listener.update(new NoAvailableLobbiesMessage());
        } else {
            ArrayList<GameLobbyView> lobbiesView = new ArrayList<>();

            for (GameLobby lobby : lobbies) {
                ArrayList<String> players = new ArrayList<>(lobby.getPlayers().keySet());
                lobbiesView.add(new GameLobbyView(lobby.getLobbyId(), lobby.getNumOfPlayers(), players));
            }
            listener.update(new AvailableLobbiesMessage(lobbiesView));
        }
    }

    /**
     * Creates a new game.
     * It generates a unique lobby ID for the game and creates a new game lobby.
     * It then adds players from the external lobby to the game lobby and checks if the game lobby is ready to start.
     * If the game lobby is ready to start, it creates the game and removes the players from the external lobby and the lobby from the lobbies map.
     *
     * @param username     the username of the player
     * @param numOfPlayers the number of players in the game
     * @param listener     the game listener for the player
     * @throws RemoteException if the listener.update method throws a RemoteException
     */
    private void createNewGame(String username, int numOfPlayers, GameListener listener) throws RemoteException {
        try {
            //Create a new game lobby and add it to the lobbies map
            GameLobby lobby = new GameLobby(lobbiesController.getAvailableLobbyId(), numOfPlayers, username, listener);
            lobbiesController.addLobby(lobby);
            listener.update(new GameCreatedWaitingForPlayersMessage());
        } catch (IOException e) {
            listener.update(new GameCreationFailedMessage());
        }
    }

    /**
     * Allows a player to join a game.
     * It checks if the player can join an existing game lobby.
     * If the player can join a game lobby, it adds the player to the game lobby and checks if the game lobby is ready to start.
     * If the game lobby is ready to start, it creates the game and removes the players from the connected players map and the lobby from the lobbies map.
     * If the player cannot join a game lobby, it adds the player to the external lobby.
     *
     * @param username the username of the player
     * @param listener the game listener for the player
     * @throws RemoteException if the listener.update method throws a RemoteException
     */
    private void joinGame(String username, GameListener listener, int lobbyID) throws RemoteException {
        GameLobby lobby = lobbiesController.getLobbyById(lobbyID);
        if (lobby != null) {
            lobby.insertPlayer(username, listener);
            listener.update(new PlayerInGameLobbyMessage());
            if (lobby.isGameReadyToStart()) {
                mainController.createGame(lobby);
                synchronized (connectedPlayersLock) {
                    for (String player : lobby.getPlayers().keySet()) {
                        connectedPlayers.put(player, true);
                    }
                }
                lobbiesController.removeLobby(lobbyID);
                listener.update(new GameCreatedReadyToStartMessage());
            }
        } else {
            listener.update(new JoinGameFailedMessage());
            deletePlayer(username);
        }
    }

    /**
     * Deletes a player from the connected players and usernames maps.
     *
     * @param username the username of the player to delete
     */
    private void deletePlayer(String username) {
        synchronized (usernames) {
            usernames.remove(username);
        }
    }

    /**
     * Deletes a list of players from the connected players and usernames maps.
     *
     * @param players the list of players to delete
     */
    public void deletePlayers(List<String> players) {
        synchronized (usernames) {
            for (String player : players) {
                usernames.remove(player);
            }
        }

    }

    /**
     * Adds a message to the message queue.
     * It adds the message and client pair to the message queue and notifies all waiting threads.
     *
     * @param message the message to add
     * @param client  the client to add
     * @throws RemoteException if the client.updateClient method throws a RemoteException
     */
    public void addMessage(Message message, Client client) throws RemoteException {
        if (message instanceof PongMessage) {
            mainController.addMessage(message);
            return;
        }
        synchronized (messageLock) {
            // Add the message and client pair to the message queue
            messages.push(new Pair<>(message, client));
            messageLock.notifyAll();
        }
    }

    /**
     * Handles incoming messages from clients.
     * If the message is a registration message, it starts a new thread to register the player with the server.
     * If the message is not a registration message, it starts a new thread to add the message to the main controller.
     *
     * @param message the incoming message from the client
     * @param client  the client from which the message is received
     * @throws RemoteException if an I/O error occurs while performing a remote operation
     */
    public void handleMessage(Message message, Client client) throws RemoteException {
        if (message instanceof RegistrationMessage) {
            new Thread(() -> {

                try {
                    registerPlayer(client, ((RegistrationMessage) message).getUsername(), ((RegistrationMessage) message).getNumberOfPLayers(), ((m) -> {
                        try {
                            client.updateClient(m);

                        } catch (RemoteException e) {
                            System.err.println("Cannot update client");
                        }
                    }));
                } catch (RemoteException e) {
                    System.err.println("Cannot register player");
                }
            }).start();

        } else if (message instanceof JoinGameMessage) {
            new Thread(() -> {
                try {
                    joinGame(((JoinGameMessage) message).getUsername(), usernames.get(((JoinGameMessage) message).getUsername()).getSecond(), ((JoinGameMessage) message).getLobbyID());
                } catch (RemoteException e) {
                    System.err.println("Cannot join game");
                }
            }).start();

        } else if (message instanceof CreateGameMessage) {
            new Thread(() -> {
                try {
                    int numOfPlayers = ((CreateGameMessage) message).getNumberOfPlayers();
                    if (numOfPlayers > 1 && numOfPlayers < 5) {
                        createNewGame(((CreateGameMessage) message).getUsername(), ((CreateGameMessage) message).getNumberOfPlayers(), usernames.get(((CreateGameMessage) message).getUsername()).getSecond());

                    } else {
                        usernames.get(((CreateGameMessage) message).getUsername()).getSecond().update(new InvalidNumberOfPlayersMessage());
                    }

                } catch (RemoteException e) {
                    System.err.println("Cannot create the game");
                }
            }).start();
        } else if (message instanceof PongMessage) {
            mainController.addMessage(message);
        } else if (message instanceof ReconnectMessage) {
            new Thread(() -> {
                GameListener listener = m -> {
                    try {
                        client.updateClient(m);

                    } catch (RemoteException e) {
                        System.err.println("Cannot update client");
                    }
                };
                if (!isUsernameUsed(((ClientMessage) message).getUsername()) || !mainController.isPlayerDisconnected(((ClientMessage) message).getUsername())) {

                    listener.update(new ReconnectionFailedMessage());

                } else {
                    synchronized (usernames) {
                        usernames.put(((ClientMessage) message).getUsername(), new Pair<>(client, listener));
                    }
                    mainController.reconnectPlayer(((ClientMessage) message).getUsername(), listener);
                }


            }).start();
        } else {
            new Thread(() -> {
                mainController.addMessage(message);
            }).start();
        }

    }

    /**
     * Checks if a username is already used.
     *
     * @param username the username to check
     * @return true if the username is already used, false otherwise
     */
    public boolean isUsernameUsed(String username) {
        synchronized (usernames) {
            return usernames.containsKey(username);
        }
    }
}
