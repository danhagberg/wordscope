/**
 * 
 */
package net.digitaltsunami.word.sequence;

import static org.junit.Assert.*;

import net.digitaltsunami.word.sequence.Sequence;

import org.junit.Test;

/**
 * @author dhagberg
 * 
 */
public class SequenceTest {

    /**
     * Test that the longest common subsequence of two empty strings is an empty
     * string.
     */
    @Test
    public void testLCSBothEmpty() {
        assertEquals("", Sequence.getALongestCommonSubsequence("", ""));
    }

    /**
     * Test that the longest common subsequence of an empty strings and any
     * other string is an empty string.
     */
    @Test
    public void testLCSBothOneEmpty() {
        assertEquals("", Sequence.getALongestCommonSubsequence("", "abcd"));
        assertEquals("", Sequence.getALongestCommonSubsequence("abcd", ""));
    }

    /**
     * Test that the longest common subsequence of two identical strings is the
     * input string.
     */
    @Test
    public void testLCSBothSame() {
        assertEquals("abcd", Sequence.getALongestCommonSubsequence("abcd", "abcd"));
    }

    /**
     * Test that the longest common subsequence of one string entirely contained
     * within another is the shortest string.
     */
    @Test
    public void testLCSOneSubsequenceFullLength() {
        assertEquals("abcd", Sequence.getALongestCommonSubsequence("abcd", "axlxbxcd"));
    }

    /**
     * Test that the longest common subsequence of two strings with no common
     * characters is an empty string.
     */
    @Test
    public void testLCSNoCommonCharacters() {
        assertEquals("", Sequence.getALongestCommonSubsequence("abcd", "efghi"));
    }

    /**
     * Test long sequence of characters containing spaces and many repeated
     * characters.
     */
    @Test
    public void testLCSLongSequence() {
        String s1 = "hNBfwkheVTZr1cK or4z 1n1dLhLTPKpu tfE8FcMrr3aT X dz9 4Ub0wEaOx9ooJxdJb6WCNYKmN";
        String s2 = "j W THxW0z9YP CMr1ncK o67craZB tTxxr3a6NjUCynkT WEMYRb0ZKJCFV Boo1l3nfR24qjrXkz";

        // Should return "Tr1cK or Tr3aT b0oo", but is allowed to return another
        // string of the
        // same length if the trace back algorithm changes.
        assertEquals(19, Sequence.getALongestCommonSubsequence(s1, s2).length());
    }
}