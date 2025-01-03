package it.polimi.ingsw.Model.Card.GoldCardTests;

import it.polimi.ingsw.Model.Card.GoldCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ObjCorner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import it.polimi.ingsw.Model.Card.enumerations.TypeObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GoldCardTest2 {
    GoldCard goldCard;

    @BeforeEach
    void setUp() {
        goldCard = new GoldCard("1", new Corner[]{new Corner(true, false), new Corner(true, false), new Corner(true, false),new Corner(false, true)},
                Resource.PLANT,
                new ArrayList<>(Arrays.asList(Resource.PLANT, Resource.PLANT, Resource.PLANT,Resource.ANIMAL))
        );
    }

    @Test
    void computePoints() {
        int expectedValue = 2;
        assertEquals(expectedValue, goldCard.computePoints());
    }

    @Test
    void getRequirements() {
        List<Resource> expectedValue = new ArrayList<Resource>(Arrays.asList(Resource.PLANT, Resource.PLANT, Resource.PLANT,Resource.ANIMAL));
        assertEquals(expectedValue, goldCard.getRequirements());
    }
    @Test
    void testEquals() {
        GoldCard someOtherCard = new GoldCard("1", new Corner[]{new ObjCorner(TypeObject.MANUSCRIPT), new Corner(false, true), new Corner(false, false)},
                Resource.PLANT,
                new ArrayList<>(Arrays.asList(Resource.PLANT, Resource.PLANT, Resource.PLANT,Resource.ANIMAL))
        );
        boolean expectedValue = false;
        assertEquals(expectedValue, goldCard.equals(someOtherCard));
    }
}