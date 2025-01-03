package it.polimi.ingsw.Messages;

import it.polimi.ingsw.ModelView.CommonBoardView.CommonBoardView;
import it.polimi.ingsw.ModelView.PlayerView.PlayerView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

/**
 * <strong>UpdateDoneMessage</strong>
 * <p>
 * This message is sent to the player that has just finished his turn and contains the updated views of the common board, the player and the public player.
 * It extends {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class UpdateDoneMessage extends Message {
    private final CommonBoardView commonBoardView;
    private final PlayerView playerView;
    private final PublicPlayerView publicPlayerView;

    /**
     * UpdateDoneMessage builder
     * @param commonBoardView the updated {@link CommonBoardView}
     * @param playerView the updated {@link PlayerView}
     * @param publicPlayerView the updated {@link PublicPlayerView}
     */
    public UpdateDoneMessage(CommonBoardView commonBoardView, PlayerView playerView, PublicPlayerView publicPlayerView) {
        this.commonBoardView = commonBoardView;
        this.playerView = playerView;

        this.publicPlayerView = publicPlayerView;
    }

    /**
     * Get the updated {@link CommonBoardView}
     * @return the updated {@link CommonBoardView}
     */
    public CommonBoardView getCommonBoardView() {
        return commonBoardView;
    }

    /**
     * Get the updated {@link PlayerView}
     * @return the updated {@link PlayerView}
     */
    public PlayerView getPlayerView() {
        return playerView;
    }

    /**
     * Get the updated {@link PublicPlayerView}
     * @return the updated {@link PublicPlayerView}
     */
    public PublicPlayerView getPublicPlayerView() {
        return publicPlayerView;
    }
}
