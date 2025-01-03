package it.polimi.ingsw.Messages;

/**
 * <strong>CreateGameMessage</strong>
 * <p>
 * CreateGameMessage is a message sent to the server to create a new game.
 * It contains the number of players that will play the game and the player's username.
 * It extends the {@link ClientMessage} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class CreateGameMessage extends ClientMessage {
    private final int numberOfPlayers;

    /**
     * CreateGameMessage builder
     * @param username the player's username
     * @param numberOfPlayers the number of players that will play the game
     */
    public CreateGameMessage(String username, int numberOfPlayers) {
        super(username);

        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * getter for the number of players
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
