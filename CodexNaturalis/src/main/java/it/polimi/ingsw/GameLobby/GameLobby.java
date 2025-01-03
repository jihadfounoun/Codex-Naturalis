package it.polimi.ingsw.GameLobby;

import it.polimi.ingsw.Listeners.GameListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a game lobby where players can join before the game starts.
 *
 * @author : Amina El Kharouai
 */
public class GameLobby implements Serializable {
    private final int lobbyId;
    private final int numOfPlayers;
    private final Map<String, GameListener> players;
    private boolean gameReadyToStart;

    /**
     * Constructs a new GameLobby with the specified number of players and the first player's details.
     *
     * @param numOfPlayers        the number of players in the game
     * @param firstPlayerUsername the username of the first player
     * @param firstGameListener   the game listener of the first player
     * @throws IOException if an I/O error occurs
     */
    public GameLobby(int lobbyId, int numOfPlayers, String firstPlayerUsername, GameListener firstGameListener) throws IOException {

        this.lobbyId = lobbyId;
        this.numOfPlayers = numOfPlayers;
        this.players = new HashMap<>();
        this.gameReadyToStart = false;

        players.put(firstPlayerUsername, firstGameListener);
    }

    /**
     * Inserts a new player into the game lobby.
     *
     * @param username     the username of the player
     * @param gameListener the game listener of the player
     * @throws IllegalArgumentException if the game is already full
     */
    public void insertPlayer(String username, GameListener gameListener) {
        if (players.size() < numOfPlayers) {
            players.put(username, gameListener);
        }
        if (players.size() == numOfPlayers) {
            this.gameReadyToStart = true;
            System.out.println("Game is ready to start!");
        }
    }

    /**
     * Returns the players in the game lobby.
     *
     * @return the players
     */
    public Map<String, GameListener> getPlayers() {
        return this.players;
    }

    /**
     * Checks if the game is ready to start.
     *
     * @return true if the game is ready to start, false otherwise
     */
    public boolean isGameReadyToStart() {
        return this.gameReadyToStart;
    }

    /**
     * Returns the lobby ID.
     *
     * @return the lobby ID
     */
    public Integer getLobbyId() {
        return this.lobbyId;
    }

    /**
     * Returns the number of players in the game lobby.
     *
     * @return the number of players
     */
    public Integer getNumOfPlayers() {
        return this.numOfPlayers;
    }
}