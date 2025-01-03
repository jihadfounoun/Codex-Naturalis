package it.polimi.ingsw.Model.Player;


import it.polimi.ingsw.Model.Card.GoldCard;
import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.ResourceCard;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import it.polimi.ingsw.Utilities.Pair;

import java.util.*;

/**
 * The objects of this class contain the resources and the objects that a player gains during his game and
 * can utilize to play the gold cards.
 *
 *
 */

public class Inventory {
    private final Map<TypeObject, Integer> objects;
    private final Map<Resource, Integer> resources;
    /**
     * constructs the inventory setting the number of Resources and the number of Objects to zero.
     */


    public Inventory() {
        objects = new HashMap<TypeObject, Integer>();
        for (TypeObject res : TypeObject.values())
            objects.put(res, 0);
        resources = new HashMap<Resource, Integer>();
        for (Resource res : Resource.values())
            resources.put(res, 0);
    }
    /**
     * Refreshes the inventory, adding resources or objects and removing them if they are located in a
     * corner that has become hidden.
     *
     * @param card the card has just been played
     */

    public int updateInv(ResourceCard card) {
        int count = 0;
        if (card.isFront()) {
            for (Resource res : card.getFrontResources())
                addResource(res);
            TypeObject obj = card.getFrontObject();
            if (obj != null) {
                addObject(obj);
                if(card instanceof GoldCard)
                    count=getObject(obj);
            }
        } else
            addResource(card.getBackResource());
        return count;
    }
    /**
     * Refreshes the inventory, adding resources or objects and removing them if they are located in a
     * corner that has become hidden.
     *
     * @param card the played initial card
     */

    public void updateInv(InitialCard card) {
        if (card.isFront()) {
            for (Resource res : card.getFrontResources())
                addResource(res);

        } else {
            for (Resource res : card.getBackResources())
                addResource(res);
            for (Resource res : card.getPermanentResources())
                addResource(res);
        }

    }

    /**
     * increments the number of a specific resource of one unit
     * @param res the resource to add
     */

    public void addResource(Resource res) {
        resources.put(res, resources.get(res) + 1);
    }

    /**
     * increments the number of a specific object of one unit
     * @param obj the object to add
     */

    public void addObject(TypeObject obj) {
        objects.put(obj, objects.get(obj) + 1);
    }

    /**
     * decrements the number of a specific resource of one unit, if present
     * @param res the resource to subtract
     */

    public void subResource(Resource res) {
        resources.put(res, resources.get(res) > 0 ? resources.get(res) - 1 : 0);
    }

    /**
     * decrements the number of a specific object of one unit, if present
     * @param obj the object to subtract
     */

    public void subObject(TypeObject obj) {
        objects.put(obj, objects.get(obj) > 0 ?  objects.get(obj) - 1 : 0);
    }
    /**
     * @param res type of resource
     * @return the number of resources of a certain type
     */

    public int getResource(Resource res) {
        return resources.get(res);
    }
    /**
     * @param obj type of object
     * @return the number of objects of a certain type
     */

    public int getObject(TypeObject obj) {
        return objects.get(obj);
    }

    /**
     * returns the map of the resources and the number of them
     * @return the map of the resources and the number of them
     */

    public Map<Resource, Integer> getResources() {
        return resources;
    }

    /**
     * returns the map of the objects and the number of them
     * @return the map of the objects and the number of them
     */

    public Map<TypeObject, Integer> getObjects() {
        return objects;
    }

    public String toString() {
        String resourcesString = "";
        String objectsString = "";
        for (Resource res : resources.keySet())
            resourcesString += res + " : " + resources.get(res) + " ";
        for (TypeObject obj : objects.keySet())
            objectsString += obj + " : " + objects.get(obj) + " ";
        return "\n Resources, " + resourcesString + "\n Objects, " + objectsString + "\n";

    }
    /**
     * @param req requirements of the gold card
     * @return how many resources of each type are missing to play a certain gold card
     */
    public Set<Pair<Resource, Integer>> missingResources(List<Resource> req) {
        Set<Pair<Resource, Integer>> missingResources = null;
        for (Resource res : Resource.values()) {
            int numRes = (int) req.stream().filter((x) -> x.equals(res)).count();
            if (numRes > this.getResource(res)) {
                if (missingResources == null)
                    missingResources = new HashSet<>();
                missingResources.add(new Pair<>(res, numRes - this.getResource(res)));
            }
        }
        return missingResources;
    }
}
