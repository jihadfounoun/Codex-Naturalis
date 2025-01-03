package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import it.polimi.ingsw.ModelView.CardView.InitialCardView;

import java.util.HashMap;
import java.util.Map;

/**
 * <strong>UpdateInitialCardMessage</strong>
 * <p>
 * UpdateInitialCardMessage is a {@link Message} used to notify the client of the initial card assigned to the player.
 * It contains the {@link InitialCardView} of the card, the inventory of resources and objects of the player.
 * It extends {@link Message} class.
 * </p>
 *
 * @author Locatelli Mattia
 */
public class UpdateInitialCardMessage extends Message {
    private final InitialCardView initialCardView;
    private final int fungiInventoryResources;
    private final int animalInventoryResources;
    private final int insectInventoryResources;
    private final int plantInventoryResources;
    private final int manuscriptInventoryObjects;
    private final int inkwellInventoryObjects;
    private final int quillInventoryObjects;

    /**
     * UpdateInitialCardMessage builder
     * @param initialCardView the initial card assigned to the player
     * @param inventoryResources the inventory of resources of the player
     * @param inventoryObjects the inventory of objects of the player
     */
    public UpdateInitialCardMessage(InitialCardView initialCardView, Map<Resource, Integer> inventoryResources, Map<TypeObject, Integer> inventoryObjects) {
        this.initialCardView = initialCardView;
        this.fungiInventoryResources = inventoryResources.get(Resource.FUNGI);
        this.animalInventoryResources = inventoryResources.get(Resource.ANIMAL);
        this.insectInventoryResources = inventoryResources.get(Resource.INSECT);
        this.plantInventoryResources = inventoryResources.get(Resource.PLANT);

        this.manuscriptInventoryObjects = inventoryObjects.get(TypeObject.MANUSCRIPT);
        this.inkwellInventoryObjects = inventoryObjects.get(TypeObject.INKWELL);
        this.quillInventoryObjects = inventoryObjects.get(TypeObject.QUILL);
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
     * Get the initial card assigned to the player
     * @return the initial card assigned to the player
     */
    public InitialCardView getInitialCard() {
        return initialCardView;
    }


}
