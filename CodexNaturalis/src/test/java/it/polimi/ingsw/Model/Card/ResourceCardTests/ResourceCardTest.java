package it.polimi.ingsw.Model.Card.ResourceCardTests;

import it.polimi.ingsw.Model.Card.ResourceCard;

import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ObjCorner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceCardTest {
    private ResourceCard resourceCard;

    @BeforeEach
    void setUp() {
        resourceCard= new ResourceCard("1",
                new Corner[]{new ResCorner(Resource.FUNGI), new Corner(false, true), new ResCorner(Resource.FUNGI), new Corner(true, false)},
                Resource.FUNGI);
    }

    @Test
    void testBackResource(){
        Resource expectedValue = Resource.FUNGI;
        assertEquals(expectedValue, resourceCard.getBackResource());
    }

    @Test
    void testFrontObject(){
        TypeObject expectedValue = null;
        assertEquals(expectedValue, resourceCard.getFrontObject());
    }

    @Test
    void testComputePoints(){
        int expectedValue = 0;
        assertEquals(expectedValue, resourceCard.computePoints());
    }

    @Test
    void testType(){
        Resource expectedValue = Resource.FUNGI;
        assertEquals(expectedValue, resourceCard.getType());
    }

    @Test
    void testEquals(){
        ResourceCard otherCard = new ResourceCard("1",
                new Corner[]{new ResCorner(Resource.FUNGI), new Corner(false, true), new ResCorner(Resource.FUNGI), new Corner(true, false)},
                Resource.FUNGI);
        boolean expectedValue = true;
        assertEquals(expectedValue, resourceCard.equals(otherCard));
    }

}
