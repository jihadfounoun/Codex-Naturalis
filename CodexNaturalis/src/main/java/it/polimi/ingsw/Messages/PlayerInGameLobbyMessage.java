package it.polimi.ingsw.Messages;

/**
 * <strong>PlayerInGameLobbyMessage</strong>
 * <p>
 * PlayerInGameLobbyMessage is a message sent to the client to notify that the player is in the Game Lobby.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class PlayerInGameLobbyMessage extends Message {
    private final String content;

    /**
     * PlayerInGameLobbyMessage builder
     */
    public PlayerInGameLobbyMessage() {
        this.content = "You are in the Game Lobby!";
    }

    /**
     * getter for the content
     * @return the content
     */
    public String getContent() {
        return content;
    }
}
