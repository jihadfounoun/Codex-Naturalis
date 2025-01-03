package it.polimi.ingsw.Messages;

import it.polimi.ingsw.GameLobby.GameLobbyView;

import java.util.ArrayList;

/**
 * <strong>AvailableLobbiesMessage</strong>
 * <p>
 * AvailableLobbiesMessage is a message sent to the client with the available lobbies to join.
 * It contains an {@link ArrayList} of {@link GameLobbyView}.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class AvailableLobbiesMessage extends Message {
    private final ArrayList<GameLobbyView> availableLobbies;

    /**
     * AvailableLobbiesMessage builder
     *
     * @param availableLobbies the available lobbies
     */
    public AvailableLobbiesMessage(ArrayList<GameLobbyView> availableLobbies) {
        this.availableLobbies = availableLobbies;
    }

    /**
     * getter for the available lobbies
     *
     * @return the available lobbies
     */
    public ArrayList<GameLobbyView> getAvailableLobbies() {
        return availableLobbies;
    }
}
