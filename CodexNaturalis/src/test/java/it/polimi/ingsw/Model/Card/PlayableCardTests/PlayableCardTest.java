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

class PlayableCardTest {
    private PlayableCard playableCard;

    @BeforeEach
    void setUp() {
        Corner[] front=new Corner[]{new Corner(false, true), new Corner(true, false), new ResCorner(Resource.FUNGI), new Corner(true, false)};
        Corner[] back=new Corner[]{new Corner(true, false), new Corner(true, false), new Corner(true, false), new Corner(true, false)};

        playableCard = new PlayableCard("1",front,back);
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
        assertEquals(expectedValue, playableCard.getFrontResources());
    }

    @Test
    void testEquals() {
        PlayableCard otherCard = new PlayableCard("1",new Corner[]{new Corner(false, true), new Corner(true, false), new ResCorner(Resource.FUNGI), new Corner(true, false)},
                new Corner[]{new Corner(true, false), new Corner(true, false), new Corner(true, false), new Corner(true, false)});
        boolean expectedValue = true;
        assertEquals(expectedValue, playableCard.equals(otherCard));
    }
}