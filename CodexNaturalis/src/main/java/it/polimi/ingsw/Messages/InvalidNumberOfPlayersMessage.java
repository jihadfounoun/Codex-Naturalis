package it.polimi.ingsw.Messages;

/**
 * <strong>InvalidNumberOfPlayersMessage</strong>
 * <p>
 * InvalidNumberOfPlayersMessage is a message sent to the client to notify that the number of players chosen by the game creator is wrong.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class InvalidNumberOfPlayersMessage extends Message {
    private final String content;

    /**
     * InvalidNumberOfPlayersMessage builder
     */
    public InvalidNumberOfPlayersMessage() {
        this.content = "The number of the players is wrong!";
    }

    /**
     * getter for the content
     * @return the content
     */
    public String getContent() {
        return content;
    }
}
