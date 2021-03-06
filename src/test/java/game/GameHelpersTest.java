package game;

import game.elements.Hex;
import game.elements.HexImpl;
import game.elements.HexValue;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static game.GameHelpers.*;
import static junit.framework.Assert.*;

public class GameHelpersTest {

    @Test
    public void testGetNeighboursReturnsExpectedHexesSimple() throws Exception{
        HexImpl testHex = new HexImpl(0,0);
        testHex.setHexValue(HexValue.BLUE);
        Set<Hex> neighbourHexes = new HashSet<>();
        addHexToSet(neighbourHexes, 0, -1, HexValue.BLUE);
        addHexToSet(neighbourHexes, 1, -1, HexValue.BLUE);
        addHexToSet(neighbourHexes, 1, 0, HexValue.BLUE);
        addHexToSet(neighbourHexes, 0, 1, HexValue.BLUE);
        addHexToSet(neighbourHexes, -1, 1, HexValue.BLUE);
        addHexToSet(neighbourHexes, -1, 0, HexValue.BLUE);
        Set<Hex> currentState = new HashSet<>();
        currentState.addAll(neighbourHexes);
        currentState.add(testHex);
        Map<Hex, Hex> hexMap = getHashMapFromSet(currentState);
        assertEquals(neighbourHexes, getNeighboursOfSameValue(hexMap, testHex));
    }

    @Test
    public void testGetNeighboursReturnsExpectedHexesWithGotchas() throws Exception{
        HexImpl testHex = new HexImpl(0,0);
        testHex.setHexValue(HexValue.BLUE);
        Set<Hex> neighbourHexes = new HashSet<>();
        addHexToSet(neighbourHexes, 0, -1, HexValue.BLUE);
        addHexToSet(neighbourHexes, -1, 1, HexValue.BLUE);
        addHexToSet(neighbourHexes, -1, 0, HexValue.BLUE);
        Set<Hex> currentState = new HashSet<>();
        currentState.addAll(neighbourHexes);
        currentState.add(testHex);
        addHexToSet(currentState, 1, -1, HexValue.RED);
        addHexToSet(currentState, 1, 0, HexValue.EMPTY);
        addHexToSet(currentState, 0, 1, HexValue.RED);
        Map<Hex, Hex> hexMap = getHashMapFromSet(currentState);
        assertEquals(neighbourHexes, getNeighboursOfSameValue(hexMap, testHex));
    }

    @Test
    public void testGetNeighboursReturnsExpectedHexesWhenOnSide() throws Exception{
        HexImpl testHex = new HexImpl(-1,0);
        testHex.setHexValue(HexValue.BLUE);
        Set<Hex> neighbourHexes = new HashSet<>();
        addHexToSet(neighbourHexes, 0, -1, HexValue.BLUE);
        addHexToSet(neighbourHexes, -1, 1, HexValue.BLUE);
        addHexToSet(neighbourHexes, 0, 0, HexValue.BLUE);
        Set<Hex> currentState = new HashSet<>();
        currentState.addAll(neighbourHexes);
        currentState.add(testHex);
        addHexToSet(currentState, 1, -1, HexValue.RED);
        addHexToSet(currentState, 1, 0, HexValue.EMPTY);
        addHexToSet(currentState, 0, 1, HexValue.RED);
        Map<Hex, Hex> hexMap = getHashMapFromSet(currentState);
        assertEquals(neighbourHexes, getNeighboursOfSameValue(hexMap, testHex));
    }

    @Test
    public void testGetNeighboursReturnsEmptyWhenNone() throws Exception{
        HexImpl testHex = new HexImpl(0,0);
        testHex.setHexValue(HexValue.BLUE);
        Set<Hex> neighbourHexes = new HashSet<>();
        Set<Hex> currentState = new HashSet<>();
        currentState.add(testHex);
        addHexToSet(currentState, 0, -1, HexValue.RED);
        addHexToSet(currentState, -1, 1, HexValue.RED);
        addHexToSet(currentState, -1, 0, HexValue.EMPTY);
        addHexToSet(currentState, 1, -1, HexValue.RED);
        addHexToSet(currentState, 1, 0, HexValue.EMPTY);
        addHexToSet(currentState, 0, 1, HexValue.RED);
        Map<Hex, Hex> hexMap = getHashMapFromSet(currentState);
        assertEquals(neighbourHexes, getNeighboursOfSameValue(hexMap, testHex));
    }

    @Test
    public void testHasPathToReturnsTrueWithSimplePath() throws Exception{
        HexImpl testHexFrom = new HexImpl(0,0);
        testHexFrom.setHexValue(HexValue.BLUE);
        HexImpl testHexTo = new HexImpl(0,2);
        testHexTo.setHexValue(HexValue.BLUE);
        Set<Hex> currentState = new HashSet<>();
        currentState.add(testHexFrom);
        currentState.add(testHexTo);
        addHexToSet(currentState, 0, 1, HexValue.BLUE);
        assertTrue(hasPathTo(currentState, testHexFrom, testHexTo));
    }

    @Test
    public void testHasPathToReturnsTrueWithMoreComplicatedPath() throws Exception{
        HexImpl testHexFrom = new HexImpl(1,0);
        testHexFrom.setHexValue(HexValue.BLUE);
        HexImpl testHexTo = new HexImpl(0,-1);
        testHexTo.setHexValue(HexValue.BLUE);
        Set<Hex> currentState = new HashSet<>();
        currentState.add(testHexFrom);
        currentState.add(testHexTo);
        addHexToSet(currentState, 0, 1, HexValue.BLUE);
        addHexToSet(currentState,-1,1,HexValue.BLUE);
        addHexToSet(currentState,-1,0,HexValue.BLUE);
        addHexToSet(currentState,0,0,HexValue.RED);
        addHexToSet(currentState,1,-1,HexValue.EMPTY);
        assertTrue(hasPathTo(currentState, testHexFrom, testHexTo));
    }

    @Test
    public void testHasPathToReturnsFalseWhenNoPath() throws Exception{
        HexImpl testHexFrom = new HexImpl(1,0);
        testHexFrom.setHexValue(HexValue.BLUE);
        HexImpl testHexTo = new HexImpl(0,-1);
        testHexTo.setHexValue(HexValue.BLUE);
        Set<Hex> currentState = new HashSet<>();
        addHexToSet(currentState, 0, 1, HexValue.BLUE);
        addHexToSet(currentState,-1,1,HexValue.BLUE);
        addHexToSet(currentState,-1,0,HexValue.RED);
        addHexToSet(currentState,0,0,HexValue.RED);
        addHexToSet(currentState, 1, -1, HexValue.EMPTY);
        assertFalse(hasPathTo(currentState, testHexFrom, testHexTo));
    }

    @Test
    public void testAllHexesConnectedToHexReturnsAllHexesWhenAllSameColour() throws Exception{
        for (int boardSize = 2; boardSize < 20; boardSize++) {
            Set<HexImpl> gameBoard = new HexGenerator(boardSize).generateHexes();
            Set<Hex> currentState = new HashSet<>();
            for(HexImpl hex: gameBoard){
                hex.setHexValue(HexValue.BLUE);
                currentState.add(hex);
            }
            HexImpl move = new HexImpl(0,0);
            move.setHexValue(HexValue.BLUE);
            assertEquals(currentState,allHexesConnectedToHex(currentState,move));
        }
    }

    @Test
    public void testGetUnconnected() throws Exception{
        Set<HexImpl> gameBoard = new HexGenerator(3).generateHexes();
        Set<Hex> currentState = new HashSet<>();
        Set<Set<Hex>> unconnected = new HashSet<>();
        Set<Hex> loop = new HashSet<>();
        addHexToSet(loop,-1,0,HexValue.BLUE);
        addHexToSet(loop,0,-1,HexValue.BLUE);
        addHexToSet(loop,1,-1,HexValue.BLUE);
        addHexToSet(loop,1,0,HexValue.BLUE);
        addHexToSet(loop,0,1,HexValue.BLUE);
        addHexToSet(loop,-1,1,HexValue.BLUE);
        for(HexImpl hex: gameBoard){
            if(loop.contains(hex)){
                hex.setHexValue(HexValue.BLUE);
            }
            currentState.add(hex);
        }
        HexImpl testHex = new HexImpl(-1,0);
        testHex.setHexValue(HexValue.BLUE);
        //unconnected = outer ring + 1 in middle
        Set<Hex> outerRing = createOuterRing();
        Set<Hex> middle = new HashSet<>();
        addHexToSet(middle,0,0,HexValue.EMPTY);
        unconnected.add(outerRing);
        unconnected.add(middle);
        assertEquals(unconnected,GameHelpers.getAllUnconnectedHexes(currentState,testHex));
    }
    private Set<Hex> createOuterRing(){
        Set<Hex> outerRing = new HashSet<>();
        addHexToSet(outerRing,0,-2,HexValue.EMPTY);
        addHexToSet(outerRing,1,-2,HexValue.EMPTY);
        addHexToSet(outerRing,2,-2,HexValue.EMPTY);
        addHexToSet(outerRing,2,-1,HexValue.EMPTY);
        addHexToSet(outerRing,2,0,HexValue.EMPTY);
        addHexToSet(outerRing,1,1,HexValue.EMPTY);
        addHexToSet(outerRing,0,2,HexValue.EMPTY);
        addHexToSet(outerRing,-1,2,HexValue.EMPTY);
        addHexToSet(outerRing,-2,2,HexValue.EMPTY);
        addHexToSet(outerRing,-2,1,HexValue.EMPTY);
        addHexToSet(outerRing,-2,0,HexValue.EMPTY);
        addHexToSet(outerRing,-1,-1,HexValue.EMPTY);
        return outerRing;
    }
}
