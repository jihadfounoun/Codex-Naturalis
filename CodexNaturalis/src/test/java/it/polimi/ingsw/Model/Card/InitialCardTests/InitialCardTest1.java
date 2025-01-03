package it.polimi.ingsw.Model.Card.InitialCardTests;

import it.polimi.ingsw.Model.Card.InitialCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

class InitialCardTest1 {
    private InitialCard initialCard;

    @BeforeEach
    void setUp() {
        initialCard=new InitialCard("20",new Corner[]{new ResCorner(Resource.FUNGI), new Corner(false, true), new ResCorner(Resource.FUNGI), new Corner(true, false)}
                ,new Corner[]{new ResCorner(Resource.FUNGI), new ResCorner(Resource.ANIMAL), new ResCorner(Resource.INSECT), new ResCorner(Resource.PLANT)},
                new ArrayList<>(Arrays.asList(Resource.INSECT, Resource.FUNGI, Resource.PLANT))

        );
    }


    @Test
    void testGetBackResources() {
        ArrayList<Resource> expectedValue = new ArrayList<>(Arrays.asList(Resource.FUNGI, Resource.FUNGI));
        assertEquals(expectedValue, initialCard.getBackResources());
    }

    @Test
    void testGetPermanentResources() {
        ArrayList<Resource> expectedValue = new ArrayList<>(Arrays.asList(Resource.FUNGI, Resource.FUNGI, Resource.PLANT));
        assertNotEquals(expectedValue, initialCard.getPermanentResources());
    }


    @Test
    void testEquals() {
        InitialCard otherCard =new InitialCard("20",new Corner[]{new ResCorner(Resource.FUNGI), new Corner(false, true), new ResCorner(Resource.FUNGI), new Corner(true, false)}
                ,new Corner[]{new ResCorner(Resource.FUNGI), new ResCorner(Resource.ANIMAL), new ResCorner(Resource.INSECT), new ResCorner(Resource.PLANT)},
                new ArrayList<>(Arrays.asList(Resource.INSECT, Resource.FUNGI, Resource.PLANT))

        );
        boolean expectedValue = true;
        assertEquals(expectedValue, initialCard.equals(otherCard));
    }
}