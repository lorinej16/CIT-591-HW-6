import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void end2endTest() {
        ArrayList<String> wordList = new ArrayList<String>();
        wordList.add("echo");
        wordList.add("belt");
        wordList.add("heal");
        wordList.add("peel");
        wordList.add("hazy");
        Solution s = new Solution(4, wordList);
        assertEquals("____", s.getCurrentTemplate());
        assertEquals("solution should not be solved", false, s.isSolved());
        s.addGuess('e');
        assertEquals("_e__", s.getCurrentTemplate());
        assertEquals("solution should not be solved", false, s.isSolved());
        // add another guess with irregular letter
        s.addGuess('p');
        assertEquals("_e__", s.getCurrentTemplate());
        assertEquals("solution should not be solved", false, s.isSolved());
        // In this case, Though heal and belt are in two families with same size 1, but to be a evil test case, we should
        // choose the one just as the user guess is wrong.
        s.addGuess('a');
        assertEquals("_e__", s.getCurrentTemplate());
        assertEquals("solution should not be solved", false, s.isSolved());
        s.addGuess('b');
        assertEquals("be__", s.getCurrentTemplate());
        assertEquals("solution should not be solved", false, s.isSolved());
        s.addGuess('l');
        assertEquals("bel_", s.getCurrentTemplate());
        assertEquals("solution should not be solved", false, s.isSolved());
        s.addGuess('t');
        assertEquals("belt", s.getCurrentTemplate());
        assertEquals("solution should be solved", true, s.isSolved());
        assertEquals("belt", s.getTarget());
    }
    @Test
    public void innerTestConstruct(){
        // test the construct method
        ArrayList<String> wordList = new ArrayList<String>();
        wordList.add("echo");
        wordList.add("belt");
        wordList.add("heal");
        wordList.add("peel");
        wordList.add("hazy");
        Solution s = new Solution(4, wordList);
        HashMap<String, ArrayList<String>> map = s.constructFamiliesMap('e');
        assertEquals(4, map.size());
        assertEquals(2, map.get("_e__").size());
        assertEquals(1, map.get("e___").size());
        assertEquals(1, map.get("____").size());
        assertEquals(1, map.get("_ee_").size());
    }
    @Test
    public void innerTestUpdate() {
        // test the update method
        ArrayList<String> wordList = new ArrayList<String>();
        wordList.add("echo");
        Solution s = new Solution(4, wordList);
        // we update the state with a fake map
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        ArrayList<String> family1 = new ArrayList<String>();
        family1.add("echo");
        family1.add("emir");
        family1.add("emit");
        map.put("e___", family1);
        ArrayList<String> family2 = new ArrayList<String>();
        family2.add("heal");
        family2.add("belt");
        family2.add("real");
        map.put("_e__", family2);
        ArrayList<String> family3 = new ArrayList<String>();
        family3.add("roes");
        family3.add("rhea");
        family3.add("shed");
        family3.add("when");
        map.put("__e_", family3);
        ArrayList<String> family4 = new ArrayList<String>();
        family4.add("abce");
        map.put("___e", family4);
        ArrayList<String> family5 = new ArrayList<String>();
        family5.add("hazy");
        map.put("____", family5);
        s.updateUsingMap(map);
        assertEquals("__e_", new String(s.getCurrentTemplate()));
    }
}
