package it.polimi.ingsw.Messages;

/**
 * <strong>Responsibility</strong>
 * <p>
 * This message is sent to all the players still connected to a lobby that contains the playing order of the players for the game.
 * It extends {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class RoundOrderMessage extends Message {
    private final String message = "Round order: ";
    private final String[] roundOrder;

    /**
     * RoundOrderMessage builder
     * @param roundOrder the playing order of the players
     */
    public RoundOrderMessage(String[] roundOrder) {
        this.roundOrder = roundOrder;
    }

    /**
     * getter for the round order
     * @return the round order
     */
    public String[] getRoundOrder() {
        return roundOrder;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(message);
        for (String player : roundOrder) {
            stringBuilder.append(player).append(" ");
        }
        return stringBuilder.toString();
    }
}
