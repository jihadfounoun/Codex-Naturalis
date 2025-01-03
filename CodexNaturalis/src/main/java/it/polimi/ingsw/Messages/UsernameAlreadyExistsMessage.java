package it.polimi.ingsw.Messages;

/**
 * <strong>UsernameAlreadyExistsMessage</strong>
 * <p>
 * This message is sent to the client that has tried to connect with a username that already exists in the server.
 * It contains a suggested username to use.
 * It extends {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class UsernameAlreadyExistsMessage extends Message {
    private final String suggestedUsername;

    /**
     * UsernameAlreadyExistsMessage builder
     * @param suggestedUsername the suggested username to use
     */
    public UsernameAlreadyExistsMessage(String suggestedUsername) {
        this.suggestedUsername = suggestedUsername;
    }

    /**
     * getter for the suggested username
     * @return the suggested username
     */
    public String getUsername() {
        return suggestedUsername;
    }
}
