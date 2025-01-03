package it.polimi.ingsw.Messages;

/**
 * <strong>JoinGameMessage</strong>
 * <p>
 * JoinGameMessage is a message sent to the server to notify that a player wants to join a game.
 * It contains the username of the player and the ID of the lobby he wants to join.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class JoinGameMessage extends Message {
    private final String username;
    private final int lobbyID;

    /**
     * JoinGameMessage builder
     * @param username the username of the player
     * @param lobbyID the ID of the lobby
     */
    public JoinGameMessage(String username, int lobbyID) {
        this.username = username;
        this.lobbyID = lobbyID;
    }

    /**
     * getter for the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter for the lobby ID
     * @return the lobby ID
     */
    public int getLobbyID() {
        return lobbyID;
    }
}
