package net.digitaltsunami.word.sequence;

import static org.junit.Assert.*;

import org.junit.Test;

public class EditDistanceCalculatorTest {

    @Test
    public void testDefaultConstructor() {
        EditDistanceCalculator calc = new EditDistanceCalculator();
        // Test that transpose is counted as two. Shows default Levenshtein in
        // use.
        assertEquals(2, calc.getEditCount("abcd", "acbd"));
        // Test that 2 edits are normalized on length.
        assertEquals(0.5, calc.getNormalizedEditDistance("abcd", "acbd"), 0.001);
    }

    @Test
    public void testDefaultEditDistanceConstructor() {
        // Create a distance calc with a specific normalizer.
        EditDistanceCalculator calc = new EditDistanceCalculator(new EditDistanceNormalization() {
            @Override
            public double getNormalizedEditDistance(double editCount, String fromTerm, String toTerm) {
                return editCount * 100;
            }
        });
        // Test that transpose is counted as two. Shows default Levenshtein in
        // use.
        assertEquals(2, calc.getEditCount("abcd", "acbd"));
        // Test that 2 edits are normalized using the override.
        assertEquals(200.0, calc.getNormalizedEditDistance("abcd", "acbd"), 0.001);
    }

    @Test
    public void testDefaultDistanceNormalizerConstructor() {
        // Create a distance calc with a specific normalizer.
        EditDistanceCalculator calc = new EditDistanceCalculator(
                new DamerauLevenshteinDistanceStrategy());
        // Test that transpose is counted as one. Shows using
        // Damerau-Levenshtein provided via constructor.
        assertEquals(1, calc.getEditCount("abcd", "acbd"));
        // Test that 1 edit is normalized on length.
        assertEquals(0.75, calc.getNormalizedEditDistance("abcd", "acbd"), 0.001);
    }

    @Test
    public void testGetEditCount() {
        EditDistanceCalculator calc = new EditDistanceCalculator();
        assertEquals(2, calc.getEditCount("abcd", "acbd"));
    }

    @Test
    public void testGetEditDistance() {
        EditDistanceCalculator calc = new EditDistanceCalculator();
        assertEquals(2.0, calc.getEditDistance("abcd", "acbd"), 0.001);
    }

    @Test
    public void testGetNormalizedEditDistance() {
        EditDistanceCalculator calc = new EditDistanceCalculator();
        assertEquals(0.5, calc.getNormalizedEditDistance("abcd", "acbd"), 0.001);
    }

}
