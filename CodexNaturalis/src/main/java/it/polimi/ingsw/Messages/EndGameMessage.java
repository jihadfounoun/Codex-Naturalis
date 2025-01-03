package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.PlayerView.PlayerView;

import java.util.List;

/**
 * <strong>EndGameMessage</strong>
 * <p>
 * EndGameMessage is a message sent to the client to notify the end of the game.
 * It contains the final {@link PlayerView}s of the players.
 * It extends the {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class EndGameMessage extends Message {
    private final List<PlayerView> finalPVs;

    /**
     * EndGameMessage builder
     * @param par the final {@link PlayerView}s of the players
     */
    public EndGameMessage(List<PlayerView> par) {
        this.finalPVs = par;
    }

    /**
     * getter for the final {@link PlayerView}s
     * @return the final {@link PlayerView}s
     */
    public List<PlayerView> getFinalPVs() {
        return finalPVs;
    }
}
