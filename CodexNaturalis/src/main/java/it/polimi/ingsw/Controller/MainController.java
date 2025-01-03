package it.polimi.ingsw.Controller;

import it.polimi.ingsw.GameLobby.GameLobby;
import it.polimi.ingsw.Listeners.GameListener;
import it.polimi.ingsw.Messages.ClientMessage;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.PongMessage;
import it.polimi.ingsw.NetWork.Server.ServerImplementation;
import it.polimi.ingsw.Utilities.Pair;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the main controller for the game.
 * It manages the connection and disconnection of players, as well as the creation, start, and end of games.
 *
 * @author Jihad Founoun
 */
public class MainController {
    private static ServerImplementation server;
    private static MainController instance;
    private final List<Pair<Map<String, GameListener>, Controller>> games;

    /**
     * Constructor for the MainController class.
     * @throws RemoteException if a communication-related error occurred during the execution of a remote method call
     */
    public MainController() throws RemoteException {
        this.games = new ArrayList<>();
        server = ServerImplementation.getServerInstance();
    }

    /**
     * Cancels a game.
     * @param controller the controller of the game to be cancelled
     */
    public void cancelGame(Controller controller) {
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getFirst().containsKey(controller.getPlayers().get(0))) {
                games.remove(i);
                break;
            }

        }
        server.deletePlayers(controller.getPlayers());
    }
    /**
     * Checks if a player is disconnected.
     * @param player the player to check
     * @return true if the player is disconnected, false otherwise
     */
    public boolean isPlayerDisconnected(String player) {
        Controller controller = getController(player);
        if (controller != null) {
            return controller.isPlayerDisconnected(player);
        }
        return false;
    }

    /**
     * Reconnects a player.
     * @param player the player to reconnect
     * @param listener the game listener for the player
     */
    public void reconnectPlayer(String player, GameListener listener) {
        Controller controller = getController(player);
        if (controller != null) {
            controller.reconnectPlayer(player, listener);
        }
    }

    /**
     * Gets the controller for a player.
     * @param player the player to get the controller for
     * @return the controller for the player
     */
    public Controller getController(String player) {
        for (Pair<Map<String, GameListener>, Controller> game : games) {
            if (game.getFirst().containsKey(player)) {
                return game.getSecond();
            }
        }
        return null;
    }

    /**
     * Adds a message to the controller.
     * @param message the message to add
     */
    public void addMessage(Message message) {
        ClientMessage clientMessage = (ClientMessage) message;
        Controller controller = getController(clientMessage.getUsername());
        if (controller != null) {
            controller.addMessage(clientMessage);
        } //else ignore the message
    }

    /**
     * Creates a game.
     * @param gameLobby the game lobby for the game to be created
     */
    public void createGame(GameLobby gameLobby) {
        Controller controller = new Controller(gameLobby.getPlayers(), gameLobby.getPlayers().size(), this);
        games.add(new Pair<>(gameLobby.getPlayers(), controller));
        controller.startGame();
    }

}