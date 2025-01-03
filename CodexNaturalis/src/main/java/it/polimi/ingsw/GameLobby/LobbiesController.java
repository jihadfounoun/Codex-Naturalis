package it.polimi.ingsw.GameLobby;

import java.util.ArrayList;

/**
 * This class is responsible for managing the game lobbies.
 * It maintains a list of all lobbies and provides methods to manipulate and retrieve information about these lobbies.
 *
 * @author : Amina El Kharouai
 */
public class LobbiesController {
    // List of all game lobbies
    private final ArrayList<GameLobby> lobbies;

    /**
     * Constructor for the LobbiesController class.
     * Initializes the list of lobbies.
     */
    public LobbiesController() {
        this.lobbies = new ArrayList<>();
    }

    /**
     * Returns the list of all game lobbies.
     *
     * @return ArrayList of GameLobby objects.
     */
    public ArrayList<GameLobby> getLobbies() {
        return lobbies;
    }

    /**
     * Adds a new game lobby to the list of lobbies.
     *
     * @param lobby The GameLobby object to be added.
     */
    public void addLobby(GameLobby lobby) {
        lobbies.add(lobby);
    }

    /**
     * Removes a game lobby from the list of lobbies based on its id.
     *
     * @param id The id of the lobby to be removed.
     */
    public void removeLobby(int id) {
        for (int i = 0; i < lobbies.size(); i++) {
            if (lobbies.get(i) != null && lobbies.get(i).getLobbyId() == id) {
                lobbies.remove(i);
                return;
            }
        }
    }

    /**
     * Retrieves a game lobby based on its id.
     *
     * @param lobbyId The id of the lobby to be retrieved.
     * @return The GameLobby object with the given id, or null if no such lobby exists.
     */
    public GameLobby getLobbyById(int lobbyId) {
        for (GameLobby lobby : lobbies) {
            if (lobby.getLobbyId() == lobbyId && !lobby.isGameReadyToStart()) {
                return lobby;
            }
        }
        return null;
    }

    /**
     * Finds an available id for a new game lobby.
     *
     * @return The id for the new lobby.
     */
    public int getAvailableLobbyId() {
        int lobbyId = 0;
        while (getLobbyById(lobbyId) != null) {
            lobbyId++;
        }
        return lobbyId;
    }

    /**
     * Retrieves a list of all available game lobbies.
     *
     * @return ArrayList of GameLobby objects that are available.
     */
    public ArrayList<GameLobby> getAvailableLobbies() {
        ArrayList<GameLobby> availableLobbies = new ArrayList<>();
        for (GameLobby lobby : lobbies) {
            if (!lobby.isGameReadyToStart()) {
                availableLobbies.add(lobby);
            }
        }
        return availableLobbies;
    }
}