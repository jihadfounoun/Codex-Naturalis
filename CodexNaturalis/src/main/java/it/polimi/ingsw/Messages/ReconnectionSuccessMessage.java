package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.CommonBoardView.CommonBoardView;
import it.polimi.ingsw.ModelView.PlayerView.PlayerView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

import java.util.List;

/**
 * <strong>ReconnectionSuccessMessage</strong>
 * <p>
 * This class represents the message that notifies the client that its reconnection has been successful.
 * It contains the updated views of the player and the other players, the common board view and the round order.
 * Extends {@link Message}.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class ReconnectionSuccessMessage extends Message {
    private final CommonBoardView commonBoardView;
    private final PlayerView playerView;
    private final List<PublicPlayerView> othersPlayersView;
    private final String[] roundOrder;

    /**
     * Constructor for the ReconnectionSuccessMessage class
     *
     * @param commonBoardView the {@link CommonBoardView} of the game
     * @param playerView the {@link PlayerView} of the player
     * @param othersPlayersView list of {@link PublicPlayerView} of all the players
     * @param roundOrder the order of the players in the round
     */
    public ReconnectionSuccessMessage(CommonBoardView commonBoardView, PlayerView playerView, List<PublicPlayerView> othersPlayersView, String[] roundOrder) {
        this.commonBoardView = commonBoardView;
        this.playerView = playerView;
        this.roundOrder = roundOrder;
        this.othersPlayersView = othersPlayersView;
    }

    /**
     * Get the order of the players in the round
     * @return the order of the players in the round
     */
    public String[] getRoundOrder() {
        return roundOrder;
    }

    /**
     * Get the {@link CommonBoardView} of the game
     * @return the {@link CommonBoardView} of the game
     */
    public CommonBoardView getCommonBoard() {
        return commonBoardView;
    }

    /**
     * Get the {@link PlayerView} of the player
     * @return the {@link PlayerView} of the player
     */
    public PlayerView getPlayer() {
        return playerView;
    }

    /**
     * Get the list of {@link PublicPlayerView} of all the players
     * @return the list of {@link PublicPlayerView} of all the players
     */
    public List<PublicPlayerView> getOthersPlayersView() {
        return othersPlayersView;
    }
}
