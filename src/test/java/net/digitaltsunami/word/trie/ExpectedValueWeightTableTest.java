package net.digitaltsunami.word.trie;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ExpectedValueWeightTableTest {
    private static ExpectedValueWeightTable weightTable;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        weightTable = new ExpectedValueWeightTable(
                ClassLoader.getSystemResourceAsStream("letterFreq.csv"));
    }

    @Test
    public void testGetExpectedValue() {
        assertEquals(0.5, weightTable.getExpectedValue('c', 'a'), 0.001);
        assertEquals(0.05, weightTable.getExpectedValue('c', 'r'), 0.001);
        assertEquals(0.25, weightTable.getExpectedValue('c', 'l'), 0.001);
    }

    @Test
    public void testGetExpectedInitialValue() {
        assertEquals(0.1,
                weightTable.getExpectedValue(ExpectedValueWeightTable.ROOT_CHAR_VAL, 'a'), 0.001);
    }
}
