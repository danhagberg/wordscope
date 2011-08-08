package net.digitaltsunami.word.trie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeightedCharTrieNodeFactoryTest {
    private static final float TEST_DELTA = 0.0001F;
    private static ExpectedValueWeightTable weightTable;

    private WeightedCharTrieNodeFactory factory;
    private CharTrieNode parentNode;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        weightTable = new ExpectedValueWeightTable(
                ClassLoader.getSystemResourceAsStream("letterFreqTestSample.csv"));
    }

    @Before
    public void setup() {
        factory = new WeightedCharTrieNodeFactory(weightTable);
        parentNode = factory.createRootNode();
    }

    @Test
    public void testCreateNode() {
        CharTrieNode node = factory.createNode('a');
        assertEquals('a', node.getValue());
        assertFalse(node.isTerminus());
    }

    @Test
    public void testAddChildFirst() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) factory.addChild(parentNode, 'a');
        assertEquals('a', node.getValue());
        assertEquals(10.0 / 70.0, node.getWeight(), TEST_DELTA);
        assertSame(parentNode, node.getParent());
        assertFalse(node.isTerminus());
    }

    @Test
    public void testAddChildSecond() {
        WeightedCharTrieNode firstNode = (WeightedCharTrieNode) factory.addChild(parentNode, 'a');
        WeightedCharTrieNode node = (WeightedCharTrieNode) factory.addChild(firstNode, 'b');
        assertEquals('b', node.getValue());
        assertEquals(50.0 / 700.0, node.getWeight(), TEST_DELTA);
        assertSame(firstNode, node.getParent());
        assertSame(parentNode, node.getParent().getParent());
        assertFalse(node.isTerminus());
    }

    @Test
    public void testAddChildTerminusSingleChar() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) factory
                .addChildTerminus(parentNode, 'a');
        assertEquals(10.0 / 70.0, node.getWeight(), TEST_DELTA);
        assertEquals('a', node.getValue());
        assertSame(parentNode, node.getParent());
        assertTrue(node.isTerminus());
    }

    @Test
    public void testAddChildTerminusMultipleChar() {
        CharTrieNode firstNode = factory.addChild(parentNode, 'a');
        WeightedCharTrieNode node = (WeightedCharTrieNode) factory.addChildTerminus(firstNode, 'b');
        assertEquals('b', node.getValue());
        assertEquals(50.0 / 700.0, node.getWeight(), TEST_DELTA);
        assertSame(firstNode, node.getParent());
        assertSame(parentNode, node.getParent().getParent());
        assertTrue(node.isTerminus());
    }

    @Test
    public void testAddChildTerminusSingleCharWithTerm() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) factory.addChildTerminus(parentNode,
                'a', "a");
        assertEquals(10.0 / 70.0, node.getWeight(), TEST_DELTA);
        assertEquals('a', node.getValue());
        assertSame(parentNode, node.getParent());
        assertTrue(node.isTerminus());
    }

    @Test
    public void testAddChildTerminusMultipleCharWithTerm() {
        CharTrieNode firstNode = factory.addChild(parentNode, 'a');
        WeightedCharTrieNode node = (WeightedCharTrieNode) factory.addChildTerminus(firstNode, 'b',
                "ab");
        assertEquals('b', node.getValue());
        assertEquals(50.0 / 700.0, node.getWeight(), TEST_DELTA);
        assertSame(firstNode, node.getParent());
        assertSame(parentNode, node.getParent().getParent());
        assertTrue(node.isTerminus());
    }

    @Test
    public void testConvertToTerminusCharTrieNode() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) factory.addChild(parentNode, 'a');
        assertEquals('a', node.getValue());
        assertEquals(10.0 / 70.0, node.getWeight(), TEST_DELTA);
        assertFalse(node.isTerminus());
        assertSame(parentNode, node.getParent());

        WeightedCharTrieNode convertedNode = (WeightedCharTrieNode) factory.convertToTerminus(node);
        assertTrue(convertedNode.isTerminus());
        assertSame(parentNode, convertedNode.getParent());
        assertEquals("a", convertedNode.getTerm());
        // Ensure that conversion did not change weight
        assertEquals(10.0 / 70.0, convertedNode.getWeight(), TEST_DELTA);
    }

    @Test
    public void testConvertToTerminusCharTrieNodeMultipleChar() {
        CharTrieNode firstNode = factory.addChild(parentNode, 'a');
        CharTrieNode node = factory.addChild(firstNode, 'b');
        assertFalse(node.isTerminus());
        assertSame(firstNode, node.getParent());
        assertSame(parentNode, node.getParent().getParent());

        CharTrieTerminusNode convertedNode = factory.convertToTerminus(node);
        assertSame(firstNode, convertedNode.getParent());
        assertSame(parentNode, convertedNode.getParent().getParent());
        assertTrue(convertedNode.isTerminus());
        assertEquals("ab", convertedNode.getTerm());
    }

    @Test
    public void testConvertToTerminusCharTrieNodeExisting() {
        CharTrieNode firstNode = factory.addChild(parentNode, 'a');
        CharTrieNode node = factory.addChildTerminus(firstNode, 'b');
        assertTrue(node.isTerminus());
        assertSame(firstNode, node.getParent());
        assertSame(parentNode, node.getParent().getParent());

        CharTrieTerminusNode convertedNode = factory.convertToTerminus(node);
        assertSame(firstNode, convertedNode.getParent());
        assertSame(parentNode, convertedNode.getParent().getParent());
        assertTrue(convertedNode.isTerminus());
        assertEquals("ab", convertedNode.getTerm());
    }

    @Test
    public void testConvertToTerminusCharTrieNodeWithTerm() {
        CharTrieNode node = factory.addChild(parentNode, 'a');
        assertEquals('a', node.getValue());
        assertFalse(node.isTerminus());
        assertSame(parentNode, node.getParent());

        CharTrieTerminusNode convertedNode = factory.convertToTerminus(node, "a");
        assertTrue(convertedNode.isTerminus());
        assertSame(parentNode, convertedNode.getParent());
        assertEquals("a", convertedNode.getTerm());
    }

    @Test
    public void testConvertToTerminusCharTrieNodeMultipleCharWithTerm() {
        CharTrieNode firstNode = factory.addChild(parentNode, 'a');
        CharTrieNode node = factory.addChild(firstNode, 'b');
        assertFalse(node.isTerminus());
        assertSame(firstNode, node.getParent());
        assertSame(parentNode, node.getParent().getParent());

        CharTrieTerminusNode convertedNode = factory.convertToTerminus(node, "ab");
        assertSame(firstNode, convertedNode.getParent());
        assertSame(parentNode, convertedNode.getParent().getParent());
        assertTrue(convertedNode.isTerminus());
        assertEquals("ab", convertedNode.getTerm());
    }

    @Test
    public void testConvertToTerminusCharTrieNodeWithTermExisting() {
        CharTrieNode firstNode = factory.addChild(parentNode, 'a');
        CharTrieNode node = factory.addChildTerminus(firstNode, 'b');
        assertTrue(node.isTerminus());
        assertSame(firstNode, node.getParent());
        assertSame(parentNode, node.getParent().getParent());

        CharTrieTerminusNode convertedNode = factory.convertToTerminus(node, "ab");
        assertSame(firstNode, convertedNode.getParent());
        assertSame(parentNode, convertedNode.getParent().getParent());
        assertTrue(convertedNode.isTerminus());
        assertEquals("ab", convertedNode.getTerm());
    }

    @Test
    public void testCreateRootNode() {
        CharTrieNode rootNode = factory.createRootNode();
        assertTrue("Factory did not create root node", rootNode.isRoot());
    }
}
