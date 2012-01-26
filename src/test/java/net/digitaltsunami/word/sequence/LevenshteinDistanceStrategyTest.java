package net.digitaltsunami.word.sequence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LevenshteinDistanceStrategyTest {
    private static LevenshteinDistanceStrategy distanceCalculator;

    @BeforeClass
    public static void initEditCounter() {
        distanceCalculator = new LevenshteinDistanceStrategy();
    }

    /**
     * Test that the edit distance is the full length of the string when
     * compared to an empty string.
     */
    @Test
    public void testEditDistanceOneEmpty() {
        assertEquals(5, distanceCalculator.getEditDistance("", "efghi"), 0.01);
        assertEquals(5, distanceCalculator.getEditDistance("abcde", ""), 0.01);
    }

    /**
     * Test that the edit distance is 0 when the strings are the same.
     */
    @Test
    public void testEditDistanceSame() {
        assertEquals(0, distanceCalculator.getEditDistance("abcde", "abcde"), 0.01);
    }

    /**
     * Test that the edit distance is equal to the length of the longest string
     * when the the strings are completely different.
     */
    @Test
    public void testEditDistanceCompletelyDifferent() {
        assertEquals(5, distanceCalculator.getEditDistance("abcde", "fghij"), 0.01);
        assertEquals(6, distanceCalculator.getEditDistance("abcde", "fghijk"), 0.01);
        assertEquals(6, distanceCalculator.getEditDistance("0abcde", "fghij"), 0.01);
    }

    /**
     * Test that the edit distance is correct when a mix of edits (delete,
     * insert, update) are applied.
     */
    @Test
    public void testEditDistanceMixOfEdits() {
        assertEquals(5, distanceCalculator.getEditDistance("abcde", "0axdefg"), 0.01);
    }

    /**
     * Test that the edit distance is 1 when the strings differ by one
     * character. Test that the substitution is recognized from either string
     * and in various places.
     */
    @Test
    public void testEditDistanceSubstitute() {
        assertEquals(1, distanceCalculator.getEditDistance("abcde", "xbcde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("xbcde", "abcde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("abcde", "abxde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("abxde", "abcde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("abcdx", "abcde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("abcde", "abcdx"), 0.01);
    }

    /**
     * Test that the edit distance is 1 when the strings differ in length by one
     * character. Test that the insertion/deletion is recognized from either
     * string and in various places.
     */
    @Test
    public void testEditDistanceInsertDelete() {
        assertEquals(1, distanceCalculator.getEditDistance("abcde", "bcde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("bcde", "abcde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("abcde", "abde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("abde", "abcde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("abcd", "abcde"), 0.01);
        assertEquals(1, distanceCalculator.getEditDistance("abcde", "abcd"), 0.01);
    }

    /**
     * Test that the edit distance is 2 when the two adjacent characters are
     * transposed within the strings.
     */
    @Test
    public void testEditDistanceTranspose() {
        assertEquals(2, distanceCalculator.getEditDistance("abcde", "abdce"), 0.01);
        assertEquals(2, distanceCalculator.getEditDistance("abcdef", "bacdef"), 0.01);
        assertEquals(2, distanceCalculator.getEditDistance("abcdef", "abdcef"), 0.01);
        assertEquals(2, distanceCalculator.getEditDistance("abcdef", "abcdfe"), 0.01);
        assertEquals(2, distanceCalculator.getEditDistance("bacdef", "abcdef"), 0.01);
        assertEquals(2, distanceCalculator.getEditDistance("abdcef", "abcdef"), 0.01);
        assertEquals(2, distanceCalculator.getEditDistance("abcdfe", "abcdef"), 0.01);
    }

    /**
     * Test that the edit count is the full length of the string when
     * compared to an empty string.
     */
    @Test
    public void testEditDistance() {
        assertEquals(5, distanceCalculator.getEditCount("", "efghi"));
        assertEquals(5, distanceCalculator.getEditCount("abcde", ""));
    }

    /**
     * Test that the edit count is 0 when the strings are the same.
     */
    @Test
    public void testEditCountSame() {
        assertEquals(0, distanceCalculator.getEditCount("abcde", "abcde"));
    }

    /**
     * Test that the edit count is equal to the length of the longest string
     * when the the strings are completely different.
     */
    @Test
    public void testEditCountCompletelyDifferent() {
        assertEquals(5, distanceCalculator.getEditCount("abcde", "fghij"));
        assertEquals(6, distanceCalculator.getEditCount("abcde", "fghijk"));
        assertEquals(6, distanceCalculator.getEditCount("0abcde", "fghij"));
    }

    /**
     * Test that the edit count is correct when a mix of edits (delete,
     * insert, update) are applied.
     */
    @Test
    public void testEditCountMixOfEdits() {
        assertEquals(5, distanceCalculator.getEditCount("abcde", "0axdefg"));
    }

    /**
     * Test that the edit count is 1 when the strings differ by one
     * character. Test that the substitution is recognized from either string
     * and in various places.
     */
    @Test
    public void testEditCountSubstitute() {
        assertEquals(1, distanceCalculator.getEditCount("abcde", "xbcde"));
        assertEquals(1, distanceCalculator.getEditCount("xbcde", "abcde"));
        assertEquals(1, distanceCalculator.getEditCount("abcde", "abxde"));
        assertEquals(1, distanceCalculator.getEditCount("abxde", "abcde"));
        assertEquals(1, distanceCalculator.getEditCount("abcdx", "abcde"));
        assertEquals(1, distanceCalculator.getEditCount("abcde", "abcdx"));
    }

    /**
     * Test that the edit count is 1 when the strings differ in length by one
     * character. Test that the insertion/deletion is recognized from either
     * string and in various places.
     */
    @Test
    public void testEditCountInsertDelete() {
        assertEquals(1, distanceCalculator.getEditCount("abcde", "bcde"));
        assertEquals(1, distanceCalculator.getEditCount("bcde", "abcde"));
        assertEquals(1, distanceCalculator.getEditCount("abcde", "abde"));
        assertEquals(1, distanceCalculator.getEditCount("abde", "abcde"));
        assertEquals(1, distanceCalculator.getEditCount("abcd", "abcde"));
        assertEquals(1, distanceCalculator.getEditCount("abcde", "abcd"));
    }

    /**
     * Test that the edit count is 2 when the two adjacent characters are
     * transposed within the strings.
     */
    @Test
    public void testEditCountTranspose() {
        assertEquals(2, distanceCalculator.getEditCount("abcde", "abdce"));
        assertEquals(2, distanceCalculator.getEditCount("abcdef", "bacdef"));
        assertEquals(2, distanceCalculator.getEditCount("abcdef", "abdcef"));
        assertEquals(2, distanceCalculator.getEditCount("abcdef", "abcdfe"));
        assertEquals(2, distanceCalculator.getEditCount("bacdef", "abcdef"));
        assertEquals(2, distanceCalculator.getEditCount("abdcef", "abcdef"));
        assertEquals(2, distanceCalculator.getEditCount("abcdfe", "abcdef"));
    }
}
