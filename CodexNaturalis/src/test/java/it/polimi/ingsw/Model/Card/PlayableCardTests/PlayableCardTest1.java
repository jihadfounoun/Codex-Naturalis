package it.polimi.ingsw.Model.Card.PlayableCardTests;

import it.polimi.ingsw.Model.Card.PlayableCard;
import it.polimi.ingsw.Model.Card.corners.Corner;
import it.polimi.ingsw.Model.Card.corners.ResCorner;
import it.polimi.ingsw.Model.Card.enumerations.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayableCardTest1 {
    private PlayableCard playableCard;

    @BeforeEach
    void setUp() {
        Corner[] front=new Corner[]{new ResCorner(Resource.FUNGI), new Corner(false, true), new ResCorner(Resource.FUNGI), new Corner(true, false)};
        Corner[] back=new Corner[]{new ResCorner(Resource.FUNGI), new ResCorner(Resource.ANIMAL), new ResCorner(Resource.INSECT), new ResCorner(Resource.PLANT)};

        playableCard = new PlayableCard("1",front,back);

        playableCard.setUsed(false);
    }

    @Test
    void testIsUsed() {
        boolean expectedValue = false;
        assertEquals(expectedValue, playableCard.isUsed());
    }

    @Test
    void testIsFront() {
        boolean expectedValue = true;
        assertEquals(expectedValue, playableCard.isFront());
    }

    @Test
    void testGetFrontResources() {
        List<Resource> expectedValue = new ArrayList<>();
        expectedValue.add(Resource.FUNGI);
        expectedValue.add(Resource.FUNGI);
        assertEquals(expectedValue, playableCard.getFrontResources());
    }

    @Test
    void testEquals() {
        PlayableCard someOtherCard = new PlayableCard("1",new Corner[]{new Corner(false, true), new Corner(true, false), new ResCorner(Resource.FUNGI), new Corner(true, false)},
                new Corner[]{new ResCorner(Resource.FUNGI), new ResCorner(Resource.ANIMAL), new ResCorner(Resource.INSECT), new ResCorner(Resource.PLANT)});
        boolean expectedValue = false;
        assertEquals(expectedValue, playableCard.equals(someOtherCard));
    }
}
