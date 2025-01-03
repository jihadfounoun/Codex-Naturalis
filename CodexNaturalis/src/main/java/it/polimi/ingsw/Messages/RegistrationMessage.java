package it.polimi.ingsw.Messages;

/**
 * <strong>RegistrationMessage</strong>
 * <p>
 * RegistrationMessage is a message sent to the server to register a new player.
 * It contains the number of players that are going to play the game. If the number of player is between 2 and 4 the server read it as a lobby creation request.
 * If the number of player is 0 the server read it as a join lobby request.
 * It extends the {@link ClientMessage} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class RegistrationMessage extends ClientMessage {
    private final int numberOfPLayers;

    /**
     * RegistrationMessage builder
     * @param numberOfPLayers the number of players that are requested to start the game
     * @param username the username of the player
     */
    public RegistrationMessage(int numberOfPLayers, String username) {
        super(username);
        this.numberOfPLayers = numberOfPLayers;
    }

    /**
     * getter for the number of players
     * @return the number of players
     */
    public int getNumberOfPLayers() {
        return numberOfPLayers;
    }
}
