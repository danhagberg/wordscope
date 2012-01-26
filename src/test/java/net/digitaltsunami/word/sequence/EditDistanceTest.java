package net.digitaltsunami.word.sequence;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EditDistanceTest {
    private static EditDistanceCalculator distanceCalculator;

    @BeforeClass
    public static void setupPriorToTests() {
        // Create default edit distance calculator, for which at the time
        // includes a LevenshteinDistanceStrategy and a TermLengthNormalization
        distanceCalculator = new EditDistanceCalculator();
    }

    /**
     * Test that list of terms is reduced to those terms within the specified 
     * edit distance.
     */
    @Test
    public void testGetAllTermsWithinEditDistance() {
        Collection<String> terms = createTestCollection();
        Collection<String> matchingTerms = EditDistance.getAllWithinDistance(terms, "aaa", 1, distanceCalculator);
        assertEquals(3, matchingTerms.size());
        assertTrue(matchingTerms.contains("aa"));
        assertTrue(matchingTerms.contains("aaa"));
        assertTrue(matchingTerms.contains("aaaa"));
    }

    /**
     * Test that list of terms is reduced to terms whose edit distance is no
     * larger than specified and that they are returned in order by edit
     * distance.
     */
    @Test
    public void testGetAllTermsWithinEditDistanceOrdered() {
        Collection<String> terms = createTestCollection();
        Collection<String> matchingTerms = EditDistance.getAllWithinDistance(terms, "aaa", 2, distanceCalculator);
        assertEquals(5, matchingTerms.size());
        // Terms must be returned by edit distance ascending.
        String[] matching = matchingTerms.toArray(new String[0]);
        assertTrue(matching[0].equals("aaa"));
        assertTrue(matching[1].equals("aa") || matching[1].equals("aaaa"));
        assertTrue(matching[2].equals("aa") || matching[2].equals("aaaa"));
        assertTrue(matching[3].equals("a") || matching[3].equals("aaaaa"));
        assertTrue(matching[4].equals("a") || matching[4].equals("aaaaa"));

        // Test with matching element.
        matchingTerms = EditDistance.getAllWithinDistance(terms, "d", 2, distanceCalculator);
        assertEquals(6, matchingTerms.size());
        // Terms must be returned by edit distance ascending.
        matching = matchingTerms.toArray(new String[0]);
        // Simple substitution: edit distance 1. Matches: 3 Indexes: [0,1,2]
        assertTrue(matching[0].equals("a") || matching[0].equals("b") || matching[0].equals("c"));
        // Simple substitution and insert: edit distance 2. Matches 3 Indexes:
        // [3,4,5]
        assertTrue(matching[3].equals("aa") || matching[3].equals("bb") || matching[3].equals("cc"));
    }

    private Collection<String> createTestCollection() {
        Collection<String> terms = new ArrayList<String>();
        terms.add("a");
        terms.add("aa");
        terms.add("aaa");
        terms.add("aaaa");
        terms.add("aaaaa");
        terms.add("aaaaaa");
        terms.add("b");
        terms.add("bb");
        terms.add("bbb");
        terms.add("bbbb");
        terms.add("bbbbb");
        terms.add("bbbbbb");
        terms.add("c");
        terms.add("cc");
        terms.add("ccc");
        terms.add("cccc");
        terms.add("ccccc");
        terms.add("cccccc");
        return terms;
    }

    /**
     * Test that list of terms is reduced to those terms within the specified 
     * edit distance.
     */
    @Test
    public void testGetAllTermsWithinEditDistanceUseDefault() {
        Collection<String> terms = createTestCollection();
        Collection<String> matchingTerms = EditDistance.getAllWithinDistance(terms, "aaa", 1);
        assertEquals(3, matchingTerms.size());
        assertTrue(matchingTerms.contains("aa"));
        assertTrue(matchingTerms.contains("aaa"));
        assertTrue(matchingTerms.contains("aaaa"));
    }

    /**
     * Test that list of terms is reduced to terms whose edit distance is no
     * larger than specified and that they are returned in order by edit
     * distance.
     */
    @Test
    public void testGetAllTermsWithinEditDistanceOrderedUseDefault() {
        Collection<String> terms = createTestCollection();
        Collection<String> matchingTerms = EditDistance.getAllWithinDistance(terms, "aaa", 2);
        assertEquals(5, matchingTerms.size());
        // Terms must be returned by edit distance ascending.
        String[] matching = matchingTerms.toArray(new String[0]);
        assertTrue(matching[0].equals("aaa"));
        assertTrue(matching[1].equals("aa") || matching[1].equals("aaaa"));
        assertTrue(matching[2].equals("aa") || matching[2].equals("aaaa"));
        assertTrue(matching[3].equals("a") || matching[3].equals("aaaaa"));
        assertTrue(matching[4].equals("a") || matching[4].equals("aaaaa"));
    
        // Test with matching element.
        matchingTerms = EditDistance.getAllWithinDistance(terms, "d", 2, distanceCalculator);
        assertEquals(6, matchingTerms.size());
        // Terms must be returned by edit distance ascending.
        matching = matchingTerms.toArray(new String[0]);
        // Simple substitution: edit distance 1. Matches: 3 Indexes: [0,1,2]
        assertTrue(matching[0].equals("a") || matching[0].equals("b") || matching[0].equals("c"));
        // Simple substitution and insert: edit distance 2. Matches 3 Indexes:
        // [3,4,5]
        assertTrue(matching[3].equals("aa") || matching[3].equals("bb") || matching[3].equals("cc"));
    }

    /**
     * Test that list of terms is reduced to those terms within the specified 
     * edit distance.
     */
    @Test
    public void testGetAllTermsWithinEditDistanceUseNewDefault() {
        EditDistanceCalculator distanceCalculator = new EditDistanceCalculator(new DamerauLevenshteinDistanceStrategy(), null);
        EditDistance.setDefaultEditDistanceCalculator(distanceCalculator);
        Collection<String> terms = createTestCollection();
        Collection<String> matchingTerms = EditDistance.getAllWithinDistance(terms, "aaa", 1);
        assertEquals(3, matchingTerms.size());
        assertTrue(matchingTerms.contains("aa"));
        assertTrue(matchingTerms.contains("aaa"));
        assertTrue(matchingTerms.contains("aaaa"));
    }

    /**
     * Test that list of terms is reduced to terms whose edit distance is no
     * larger than specified and that they are returned in order by edit
     * distance.
     */
    @Test
    public void testGetAllTermsWithinEditDistanceOrderedUseNewDefault() {
        Collection<String> terms = createTestCollection();
        EditDistanceCalculator distanceCalculator = new EditDistanceCalculator(new DamerauLevenshteinDistanceStrategy(), null);
        EditDistance.setDefaultEditDistanceCalculator(distanceCalculator);
        Collection<String> matchingTerms = EditDistance.getAllWithinDistance(terms, "aaa", 2);
        assertEquals(5, matchingTerms.size());
        // Terms must be returned by edit distance ascending.
        String[] matching = matchingTerms.toArray(new String[0]);
        assertTrue(matching[0].equals("aaa"));
        assertTrue(matching[1].equals("aa") || matching[1].equals("aaaa"));
        assertTrue(matching[2].equals("aa") || matching[2].equals("aaaa"));
        assertTrue(matching[3].equals("a") || matching[3].equals("aaaaa"));
        assertTrue(matching[4].equals("a") || matching[4].equals("aaaaa"));
    
        // Test with matching element.
        matchingTerms = EditDistance.getAllWithinDistance(terms, "d", 2, distanceCalculator);
        assertEquals(6, matchingTerms.size());
        // Terms must be returned by edit distance ascending.
        matching = matchingTerms.toArray(new String[0]);
        // Simple substitution: edit distance 1. Matches: 3 Indexes: [0,1,2]
        assertTrue(matching[0].equals("a") || matching[0].equals("b") || matching[0].equals("c"));
        // Simple substitution and insert: edit distance 2. Matches 3 Indexes:
        // [3,4,5]
        assertTrue(matching[3].equals("aa") || matching[3].equals("bb") || matching[3].equals("cc"));
    }

}
