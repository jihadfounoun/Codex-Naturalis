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

public class PlayableCardTest2 {
    private PlayableCard playableCard;

    @BeforeEach
    void setUp() {
        Corner[] front=new Corner[]{new Corner(false, true), new Corner(true, false), new Corner(false, true), new Corner(true, false)};
        Corner[] back=new Corner[]{new ResCorner(Resource.FUNGI), new ResCorner(Resource.ANIMAL), new ResCorner(Resource.INSECT), new ResCorner(Resource.PLANT)};

        playableCard = new PlayableCard("1",front,back);
        playableCard.useBack();
    }

    @Test
    void testIsUsed() {
        boolean expectedValue = false;
        assertEquals(expectedValue, playableCard.isUsed());
    }

    @Test
    void testIsNotFront() {
        boolean expectedValue = false;
        assertEquals(expectedValue, playableCard.isFront());
    }

    @Test
    void testGetFrontResources() {
        List<Resource> expectedValue = new ArrayList<>();
        assertEquals(expectedValue, playableCard.getFrontResources());
    }

    @Test
    void testEquals() {
        PlayableCard otherCard = new PlayableCard("1",new Corner[]{new Corner(false, true), new Corner(true, false), new Corner(false, true), new Corner(true, false)}
                ,new Corner[]{new ResCorner(Resource.FUNGI), new ResCorner(Resource.ANIMAL), new ResCorner(Resource.INSECT), new ResCorner(Resource.PLANT)});
        otherCard.useBack();
        boolean expectedValue = true;
        assertEquals(expectedValue, playableCard.equals(otherCard));
    }
}
