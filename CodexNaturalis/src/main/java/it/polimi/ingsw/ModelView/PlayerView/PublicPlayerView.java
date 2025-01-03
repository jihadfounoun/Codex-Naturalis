package it.polimi.ingsw.ModelView.PlayerView;

import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Model.Player.State;
import it.polimi.ingsw.ModelView.CardView.GoldCardView;
import it.polimi.ingsw.ModelView.CardView.InitialCardView;
import it.polimi.ingsw.ModelView.CardView.ResourceCardView;
import it.polimi.ingsw.ModelView.CardView.UtilitiesView.CoordinateView;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a PublicPlayerView in the game.
 * It implements Serializable, which means its instances can be saved to a file or sent over a network.
 * It holds the back hand of cards, username, token, score, initial card, played cards, inventory resources, and inventory objects of the player.
 *
 * @author Jihad Founoun
 */
public class PublicPlayerView implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // The back hand of cards of the player
    private final ArrayList<ResourceCardView> backResourceHand;
    private final ArrayList<GoldCardView> backGoldHand;
    // The username of the player
    private final String username;
    // The token of the player
    private final Color token;
    // The score of the player
    private final int score;
    // The initial card of the player
    private final InitialCardView initialCard;
    // The played cards of the player
    private final Map<ResourceCardView, CoordinateView> playedCards;

    private final State playerState;

    private final int fungiInventoryResources;
    private final int animalInventoryResources;
    private final int insectInventoryResources;
    private final int plantInventoryResources;



    private final int manuscriptInventoryObjects;
    private final int inkwellInventoryObjects;
    private final int quillInventoryObjects;

    /**
     * Constructs a new PublicPlayerView with the given back hand, username, token, score, initial card, played cards, inventory resources, and inventory objects.
     *
     * @param username           the username of the player
     * @param token              the token of the player
     * @param score              the score of the player
     * @param initialCard        the initial card of the player
     * @param playedCards        the played cards of the player
     * @param inventoryResources the inventory resources of the player
     * @param inventoryObjects   the inventory objects of the player
     * @param backResourceHand   the back hand of the player
     * @param backGoldHand   the back hand of the player
     * @param state              the state of the player
     */
    public PublicPlayerView(ArrayList<ResourceCardView> backResourceHand, ArrayList<GoldCardView> backGoldHand, String username, Color token, int score, InitialCardView initialCard, Map<ResourceCardView, CoordinateView> playedCards, Map<Resource, Integer> inventoryResources, Map<TypeObject, Integer> inventoryObjects, State state) {
        this.backResourceHand = backResourceHand;
        this.backGoldHand = backGoldHand;

        this.username = username;
        this.token = token;
        this.score = score;
        this.initialCard = initialCard;
        this.playedCards = playedCards;

        this.fungiInventoryResources = inventoryResources.get(Resource.FUNGI);
        this.animalInventoryResources = inventoryResources.get(Resource.ANIMAL);
        this.insectInventoryResources = inventoryResources.get(Resource.INSECT);
        this.plantInventoryResources = inventoryResources.get(Resource.PLANT);

        this.manuscriptInventoryObjects = inventoryObjects.get(TypeObject.MANUSCRIPT);
        this.inkwellInventoryObjects = inventoryObjects.get(TypeObject.INKWELL);
        this.quillInventoryObjects = inventoryObjects.get(TypeObject.QUILL);

        this.playerState = state;
    }
    /**
     * Returns the resource back hand of this PublicPlayerView.
     *
     * @return the resource back hand of the PublicPlayerView
     */
    public ArrayList<ResourceCardView> getBackResourceHand() {
        return backResourceHand;
    }

    public ArrayList<GoldCardView> getBackGoldHand() {
        return backGoldHand;
    }

    /**
     * Returns the username of this PublicPlayerView.
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the token of this PublicPlayerView.
     *
     * @return the token of the player
     */
    public Color getToken() {
        return token;
    }

    /**
     * Returns the score of this PublicPlayerView.
     *
     * @return the score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the played cards of this PublicPlayerView.
     *
     * @return the played cards of the player
     */
    public Map<ResourceCardView, CoordinateView> getPlayedCards() {
        return playedCards;
    }

    /**
     * Returns the initial card of this PublicPlayerView.
     *
     * @return the initial card of the player
     */
    public InitialCardView getInitialCard() {
        return initialCard;
    }

    /**
     * Returns the inventory resources of this PublicPlayerView.
     *
     * @return the inventory resources of the player
     */
    public Map<Resource, Integer> getInventoryResources() {
        Map<Resource, Integer> inventoryResources = new HashMap<>();
        inventoryResources.put(Resource.FUNGI, fungiInventoryResources);
        inventoryResources.put(Resource.ANIMAL, animalInventoryResources);
        inventoryResources.put(Resource.INSECT, insectInventoryResources);
        inventoryResources.put(Resource.PLANT, plantInventoryResources);
        return inventoryResources;
    }

    /**
     * Returns the inventory objects of this PublicPlayerView.
     *
     * @return the inventory objects of the player
     */
    public Map<TypeObject, Integer> getInventoryObjects() {
        Map<TypeObject, Integer> inventoryObjects = new HashMap<>();
        inventoryObjects.put(TypeObject.MANUSCRIPT, manuscriptInventoryObjects);
        inventoryObjects.put(TypeObject.INKWELL, inkwellInventoryObjects);
        inventoryObjects.put(TypeObject.QUILL, quillInventoryObjects);
        return inventoryObjects;
    }

    /**
     * Returns the state of this player.
     *
     * @return the state of the player
     */
    public State getPlayerState() {
        return playerState;
    }
}