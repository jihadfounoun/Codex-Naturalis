package it.polimi.ingsw.Messages;

/**
 * <strong>PingMessage</strong>
 * <p>
 * PingMessage is a message sent to the client to symbolize a ping from the server.
 * It contains a ping number that is used to check the connection status.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class PingMessage extends Message {
    private final int pingNumber;

    /**
     * PingMessage builder
     * @param pingNumber the ping number
     */
    public PingMessage(int pingNumber) {
        this.pingNumber = pingNumber;
    }

    /**
     * getter for the ping number
     * @return the ping number
     */
    public int getPingNumber() {
        return pingNumber;
    }
}
