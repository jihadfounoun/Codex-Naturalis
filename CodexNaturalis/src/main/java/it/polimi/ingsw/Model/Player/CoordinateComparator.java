package it.polimi.ingsw.Model.Player;



import it.polimi.ingsw.Model.Card.ResourceCard;

import java.util.Comparator;

public class CoordinateComparator implements  Comparator<ResourceCard>{
    @Override
    public int compare(ResourceCard o1, ResourceCard o2) {
        if(o1.getCoordinate().getY()<o2.getCoordinate().getY())
            return 1;
        if(o1.getCoordinate().getY()>o2.getCoordinate().getY())
            return -1;
        return 0;
    }
}
