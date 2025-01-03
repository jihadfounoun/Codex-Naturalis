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

public class GoldCardTest1 {
    GoldCard goldCard;

    @BeforeEach
    void setUp() {
        goldCard = new GoldCard("1", new Corner[]{new ObjCorner(TypeObject.INKWELL), new Corner(false, true), new Corner(false, true),new Corner(true, false)},
                Resource.ANIMAL,
                new ArrayList<>(Arrays.asList(Resource.ANIMAL, Resource.ANIMAL, Resource.ANIMAL))
        );
    }

    @Test
    void computePoints() {
        int expectedValue = 3;
        assertEquals(expectedValue, goldCard.computePoints());
    }

    @Test
    void getRequirements() {
        List<Resource> expectedValue = new ArrayList<Resource>(Arrays.asList(Resource.ANIMAL, Resource.ANIMAL));
        assertNotEquals(expectedValue, goldCard.getRequirements());
    }
    @Test
    void testEquals() {
        GoldCard someOtherCard = new GoldCard("1", new Corner[]{new ObjCorner(TypeObject.MANUSCRIPT), new Corner(false, true), new Corner(false, false)},
                Resource.FUNGI,
                new ArrayList<>(Arrays.asList(Resource.FUNGI, Resource.FUNGI, Resource.PLANT))
        );
        boolean expectedValue = false;
        assertEquals(expectedValue, goldCard.equals(someOtherCard));
    }
}