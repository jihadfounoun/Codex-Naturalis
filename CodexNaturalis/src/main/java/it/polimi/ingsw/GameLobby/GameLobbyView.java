package it.polimi.ingsw.GameLobby;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a view of a game lobby.
 * It implements Serializable, allowing it to be sent over a network or written to disk.
 */
public class GameLobbyView implements Serializable {
    // The name of the lobby
    private final Integer lobbyName;
    // The size of the lobby
    private final int lobbySize;
    // The list of players in the lobby
    private final ArrayList<String> lobbyPlayers;

    /**
     * Constructor for the GameLobbyView class.
     * Initializes the lobby name, size, and list of players.
     *
     * @param lobbyName    The name of the lobby.
     * @param lobbySize    The size of the lobby.
     * @param lobbyPlayers The list of players in the lobby.
     */
    public GameLobbyView(Integer lobbyName, int lobbySize, ArrayList<String> lobbyPlayers) {
        this.lobbyName = lobbyName;
        this.lobbySize = lobbySize;
        this.lobbyPlayers = lobbyPlayers;
    }

    /**
     * Returns the name of the lobby.
     *
     * @return The name of the lobby.
     */
    public Integer getLobbyName() {
        return lobbyName;
    }

    /**
     * Returns the size of the lobby.
     *
     * @return The size of the lobby.
     */
    public int getLobbySize() {
        return lobbySize;
    }

    /**
     * Returns the list of players in the lobby.
     *
     * @return The list of players in the lobby.
     */
    public ArrayList<String> getLobbyPlayers() {
        return lobbyPlayers;
    }
}