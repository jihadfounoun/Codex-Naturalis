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
import java.util.List;

class InitialCardTest2 {
    private InitialCard initialCard;

    @BeforeEach
    void setUp() {
        initialCard=new InitialCard("45",new Corner[]{new Corner(true, false),new ResCorner(Resource.FUNGI),new Corner(false, true),new Corner(false, true)}
                ,new Corner[]{ new ResCorner(Resource.PLANT), new ResCorner(Resource.ANIMAL),new ResCorner(Resource.FUNGI), new ResCorner(Resource.INSECT)},
                new ArrayList<>(List.of(Resource.FUNGI))

        );
    }


    @Test
    void testGetBackResources() {
        ArrayList<Resource> expectedValue = new ArrayList<>(Arrays.asList(Resource.FUNGI, Resource.FUNGI));
        assertNotEquals(expectedValue, initialCard.getBackResources());
    }

    @Test
    void testGetPermanentResources() {
        ArrayList<Resource> expectedValue = new ArrayList<>(List.of(Resource.FUNGI));
        assertEquals(expectedValue, initialCard.getPermanentResources());
    }


    @Test
    void testEquals() {
        InitialCard otherCard =new InitialCard("45",new Corner[]{new Corner(true, false),new ResCorner(Resource.FUNGI),new Corner(false, true),new Corner(true, false)}
                ,new Corner[]{ new ResCorner(Resource.PLANT), new ResCorner(Resource.ANIMAL),new ResCorner(Resource.FUNGI), new ResCorner(Resource.INSECT)},
                new ArrayList<>(List.of(Resource.FUNGI))

        );
        boolean expectedValue = false;
        assertEquals(expectedValue, initialCard.equals(otherCard));
    }
}