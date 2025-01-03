package it.polimi.ingsw.Mappers;

import it.polimi.ingsw.Model.Card.Coordinate;
import it.polimi.ingsw.Model.Card.GoldCard;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.CommonBoard.CommonBoard;
import it.polimi.ingsw.Model.GoalCard.GoalCard;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.ModelView.CardView.GoalCardView;
import it.polimi.ingsw.ModelView.CardView.GoldCardView;
import it.polimi.ingsw.ModelView.CardView.InitialCardView;
import it.polimi.ingsw.ModelView.CardView.ResourceCardView;
import it.polimi.ingsw.ModelView.CardView.UtilitiesView.CoordinateView;
import it.polimi.ingsw.ModelView.CommonBoardView.CommonBoardView;
import it.polimi.ingsw.ModelView.PlayerView.PlayerView;
import it.polimi.ingsw.ModelView.PlayerView.PublicPlayerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for mapping the model to the view.
 * It extends the Mapper class and provides methods to convert model objects to their corresponding view objects.
 *
 * @author : Amina El Kharouai
 */
public class ModelViewMapper extends Mapper {

    /**
     * Default constructor for the ModelViewMapper class.
     */
    public ModelViewMapper() {
    }

    /**
     * Converts a CommonBoard object to a CommonBoardView object.
     *
     * @param commonBoard The CommonBoard object to be converted.
     * @return The converted CommonBoardView object.
     */
    public CommonBoardView getCommonBoardView(CommonBoard commonBoard) {
        ArrayList<ResourceCardView> resourceCardViews = new ArrayList<>();
        for (int i = 0; i < commonBoard.getResourceCards().getVisibleResources().size(); i++) {
            resourceCardViews.add(this.getResourceCardView((ResourceCard) commonBoard.getResourceCards().getVisible(i)));
        }
        ArrayList<GoldCardView> goldCardViews = new ArrayList<>();
        for (int i = 0; i < commonBoard.getGoldCards().getGoldCards().size(); i++) {
            goldCardViews.add(this.getGoldCardView((GoldCard) commonBoard.getGoldCards().getVisible(i)));
        }
        ArrayList<GoalCardView> goalCardViews = new ArrayList<>();
        for (int i = 0; i < commonBoard.getCommonGoal().getVisibleGoal().size(); i++) {
            goalCardViews.add(this.getGoalCardView((GoalCard) commonBoard.getCommonGoal().getVisible(i)));
        }
        ResourceCardView firstResourceCard;
        GoldCardView firstGoldCard;
        try {
            firstResourceCard = this.getResourceCardView(commonBoard.seeFirstResourceCard());
        } catch (IllegalStateException e) {
            firstResourceCard = null;
        }
        try {
            firstGoldCard = this.getGoldCardView(commonBoard.seeFirstGoldCard());
        } catch (IllegalStateException e) {
            firstGoldCard = null;
        }
        return new CommonBoardView(resourceCardViews, goldCardViews, goalCardViews, commonBoard.seeTypeFirstResCard().toString(), commonBoard.seeTypeFirstGoldCard().toString(), commonBoard.getResourceDeck().getResourceDeck().isEmpty(), commonBoard.getGoldDeck().getGoldDeck().isEmpty(), firstResourceCard, firstGoldCard);
    }

    /**
     * Converts a GoalCard object to a GoalCardView object.
     *
     * @param goalCard The GoalCard object to be converted.
     * @return The converted GoalCardView object.
     */
    public GoalCardView getGoalCardView(GoalCard goalCard) {
        if (goalCard == null) {
            return null;
        }
        return new GoalCardView(goalCard.toString(), goalCard.getId());
    }

    /**
     * Converts a GoldCard object to a GoldCardView object.
     *
     * @param goldCard The GoldCard object to be converted.
     * @return The converted GoldCardView object.
     */
    public GoldCardView getGoldCardView(GoldCard goldCard) {
        return new GoldCardView(goldCard.computePoints(), goldCard.getType(), goldCard.getRequirements(), goldCard.getFrontCorners(), goldCard.getBackCorners(), goldCard.isFront(), goldCard.getId());
    }

    /**
     * Converts a ResourceCard object to a ResourceCardView object.
     *
     * @param resourceCard The ResourceCard object to be converted.
     * @return The converted ResourceCardView object.
     */
    public ResourceCardView getResourceCardView(ResourceCard resourceCard) {
        if (resourceCard instanceof GoldCard)
            return this.getGoldCardView((GoldCard) resourceCard);
        return new ResourceCardView(resourceCard.getFrontCorners(), resourceCard.isFront(), resourceCard.getType(), resourceCard.computePoints(), resourceCard.getBackCorners(), resourceCard.getId());
    }

    /**
     * Converts an InitialCard object to an InitialCardView object.
     *
     * @param initialCard The InitialCard object to be converted.
     * @return The converted InitialCardView object.
     */
    public InitialCardView getInitialCardView(InitialCard initialCard) {
        return new InitialCardView(initialCard.getFrontCorners(), initialCard.getBackCorners(), initialCard.isFront(), initialCard.getPermanentResources(), initialCard.getId());
    }

    /**
     * Converts a Coordinate object to a CoordinateView object.
     *
     * @param coordinate The Coordinate object to be converted.
     * @param boardSize  The size of the board.
     * @return The converted CoordinateView object.
     */
    public CoordinateView getCoordinateView(Coordinate coordinate, int boardSize) {
        return new CoordinateView(coordinate.getX(), coordinate.getY(), boardSize);
    }

    /**
     * Converts a Player object to a PlayerView object.
     *
     * @param player    The Player object to be converted.
     * @param boardSize The size of the board.
     * @return The converted PlayerView object.
     */
    public PlayerView getPlayerView(Player player, int boardSize) {
        ArrayList<ResourceCardView> hand = new ArrayList<>();
        for (int i = 0; i < player.getHand().size(); i++) {
            if (player.getHand().get(i).getClass() == ResourceCard.class) {
                hand.add(this.getResourceCardView((ResourceCard) player.getHand().get(i)));
            } else if (player.getHand().get(i).getClass() == GoldCard.class) {
                hand.add(this.getGoldCardView((GoldCard) player.getHand().get(i)));
            }
        }
        Map<GoalCardView, Integer> commonGoalScores = new HashMap<>();
        for (GoalCard goalCard : player.getCommonGoalScores().keySet()) {
            commonGoalScores.put(this.getGoalCardView(goalCard), player.getCommonGoalScores().get(goalCard));
        }
        ArrayList<CoordinateView> playablePositions = new ArrayList<>();
        for (Coordinate coordinate : player.getPlayerBoard().getPlayablePos()) {
            playablePositions.add(this.getCoordinateView(coordinate, boardSize));
        }
        return new PlayerView(hand, player.getId(), player.getToken(), player.getScore(), player.getFinalScore(), commonGoalScores, player.getState(), this.getGoalCardView(player.getSecretGoal()), player.getSecretGoalScore(), playablePositions);
    }

    /**
     * Converts a Player object to a PublicPlayerView object.
     *
     * @param player    The Player object to be converted.
     * @param boardSize The size of the board.
     * @return The converted PublicPlayerView object.
     */
    public PublicPlayerView getPublicPlayerView(Player player, int boardSize) {
        ArrayList<ResourceCardView> resourceBackHand = new ArrayList<>();
        for (int i = 0; i < player.getHand().size(); i++) {
            if (player.getHand().get(i).getClass() == ResourceCard.class)
                resourceBackHand.add(this.getResourceCardView((ResourceCard) player.getHand().get(i)));
        }
        ArrayList<GoldCardView> goldBackHand = new ArrayList<>();
        for (int i = 0; i < player.getHand().size(); i++) {
            if (player.getHand().get(i).getClass() == GoldCard.class)
                goldBackHand.add(this.getGoldCardView((GoldCard) player.getHand().get(i)));
        }
        Map<ResourceCardView, CoordinateView> playedCards = new HashMap<>();
        for (Resource resource : player.getPlayerBoard().getPlayedCards().keySet()) {
            for (ResourceCard resourceCard : player.getPlayerBoard().getPlayedCards().get(resource)) {
                playedCards.put(this.getResourceCardView(resourceCard), this.getCoordinateView(resourceCard.getCoordinate(), boardSize));
            }
        }
        return new PublicPlayerView(resourceBackHand, goldBackHand, player.getId(), player.getToken(), player.getScore(), getInitialCardView(player.getInitialCard()), playedCards, player.getPlayerBoard().getInventory().getResources(), player.getPlayerBoard().getInventory().getObjects(), player.getState());
    }

    /**
     * Converts a list of Player objects to a list of PublicPlayerView objects.
     *
     * @param players   The list of Player objects to be converted.
     * @param boardSize The size of the board.
     * @return The list of converted PublicPlayerView objects.
     */
    public List<PublicPlayerView> getPublicPlayersViews(List<Player> players, int boardSize) {
        ArrayList<PublicPlayerView> publicPlayerViews = new ArrayList<>();
        for (Player player : players) {
            publicPlayerViews.add(this.getPublicPlayerView(player, boardSize));
        }
        return publicPlayerViews;
    }

}
