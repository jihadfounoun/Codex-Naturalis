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

class InitialCardTest3 {
    private InitialCard initialCard;

    @BeforeEach
    void setUp() {
        initialCard=new InitialCard("45",new Corner[]{new Corner(true, false),new Corner(true, false),new Corner(false, true),new Corner(false, true)}
                ,new Corner[]{ new ResCorner(Resource.PLANT),new ResCorner(Resource.INSECT),new ResCorner(Resource.FUNGI), new ResCorner(Resource.ANIMAL) },
                new ArrayList<>(Arrays.asList(Resource.INSECT, Resource.ANIMAL, Resource.PLANT))

        );
    }


    @Test
    void testGetBackResources() {
        ArrayList<Resource> expectedValue = new ArrayList<>();
        assertEquals(expectedValue, initialCard.getBackResources());
    }

    @Test
    void testGetPermanentResources() {
        ArrayList<Resource> expectedValue = new ArrayList<>(Arrays.asList(Resource.INSECT, Resource.ANIMAL, Resource.PLANT,Resource.ANIMAL));
        assertNotEquals(expectedValue, initialCard.getPermanentResources());
    }


    @Test
    void testEquals() {
        InitialCard otherCard =new InitialCard("45",new Corner[]{new Corner(true, false),new Corner(true, false),new Corner(false, true),new Corner(false, true)}
                ,new Corner[]{ new ResCorner(Resource.PLANT),new ResCorner(Resource.INSECT),new ResCorner(Resource.FUNGI), new ResCorner(Resource.ANIMAL) },
                new ArrayList<>(Arrays.asList(Resource.INSECT, Resource.ANIMAL, Resource.PLANT))

        );
        boolean expectedValue = true;
        assertEquals(expectedValue, initialCard.equals(otherCard));
    }
}