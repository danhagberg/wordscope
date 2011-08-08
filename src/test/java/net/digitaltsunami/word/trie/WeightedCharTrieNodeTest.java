package net.digitaltsunami.word.trie;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class WeightedCharTrieNodeTest {
    private static final char TEST_VALUE_LOW = 'd';
    private static final char TEST_VALUE = 'm';
    private static final char TEST_VALUE_HIGH = 'x';
    private static final float TEST_WEIGHT_LOW = 0.25F;
    private static final float TEST_WEIGHT = 0.5F;
    private static final float TEST_WEIGHT_HIGH = 0.75F;
    private static final float TEST_DELTA = 0.0001F;
    private WeightedCharTrieNode root;

    @Before
    public void setUp() {
        root = new WeightedCharTrieNode(true);
    }

    @Test
    public void testWeightedCharTrieNodeCharTrieNodeCharFloat() {
        WeightedCharTrieNode node = new WeightedCharTrieNode(root, TEST_VALUE, TEST_WEIGHT);
        assertEquals(root, node.getParent());
        assertEquals(TEST_VALUE, node.getValue());
        assertEquals(TEST_WEIGHT, node.getWeight(), TEST_DELTA);
    }

    @Test
    public void testWeightedCharTrieNodeCharTrieNodeChar() {
        WeightedCharTrieNode node = new WeightedCharTrieNode(root, TEST_VALUE);
        assertEquals(root, node.getParent());
        assertEquals(TEST_VALUE, node.getValue());
        assertEquals(WeightedCharTrieNode.MIN_WEIGHT, node.getWeight(), TEST_DELTA);
    }

    @Test
    public void testWeightedCharTrieNodeCharFloat() {
        WeightedCharTrieNode node = new WeightedCharTrieNode(TEST_VALUE, TEST_WEIGHT);
        assertEquals(TEST_VALUE, node.getValue());
        assertEquals(TEST_WEIGHT, node.getWeight(), TEST_DELTA);
    }

    @Test
    public void testWeightedCharTrieNodeChar() {
        WeightedCharTrieNode node = new WeightedCharTrieNode(TEST_VALUE);
        assertEquals(TEST_VALUE, node.getValue());
        assertEquals(WeightedCharTrieNode.MIN_WEIGHT, node.getWeight(), TEST_DELTA);
    }

    @Test
    public void testWeightedCharTrieNodeBoolean() {
        WeightedCharTrieNode node = new WeightedCharTrieNode(true);
        assertEquals(true, node.isRoot());
        assertEquals(WeightedCharTrieNode.MIN_WEIGHT, node.getWeight(), TEST_DELTA);
    }

    @Test
    public void testAddChildWithoutWeight() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) root.addChild(TEST_VALUE);
        assertEquals(root, node.getParent());
        assertEquals(TEST_VALUE, node.getValue());
        assertEquals(WeightedCharTrieNode.MIN_WEIGHT, node.getWeight(), TEST_DELTA);
    }

    @Test
    public void testAddChildFirstNode() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) root.addChild(TEST_VALUE, TEST_WEIGHT);
        assertEquals(root, node.getParent());
        assertEquals(TEST_VALUE, node.getValue());
        assertEquals(TEST_WEIGHT, node.getWeight(), TEST_DELTA);
    }

    @Test
    public void testGetChild() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) root.addChild(TEST_VALUE, TEST_WEIGHT);
        WeightedCharTrieNode childNode = (WeightedCharTrieNode) root.getChild(TEST_VALUE);
        
        assertEquals(node, childNode);
        assertEquals(TEST_VALUE, childNode.getValue());
        assertEquals(TEST_WEIGHT, childNode.getWeight(), TEST_DELTA);
    }

    @Test
    public void testAddChildLesserWeight() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) root.addChild(TEST_VALUE, TEST_WEIGHT);
        // Lexically higher value, but lower weight
        WeightedCharTrieNode lesserNode = (WeightedCharTrieNode) root.addChild(TEST_VALUE_HIGH,
                TEST_WEIGHT_LOW);

        assertEquals(TEST_VALUE_HIGH, lesserNode.getValue());
        assertEquals(TEST_WEIGHT_LOW, lesserNode.getWeight(), TEST_DELTA);
        assertEquals(root, lesserNode.getParent());
        assertEquals(node, lesserNode.getPriorSibling());
        assertEquals(null, lesserNode.getNextSibling());
        assertEquals(node, root.getFirstChild());
    }

    @Test
    public void testAddChildGreaterWeight() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) root.addChild(TEST_VALUE, TEST_WEIGHT);
        // Lexically lower value, but higher weight
        WeightedCharTrieNode lesserNode = (WeightedCharTrieNode) root.addChild(TEST_VALUE_LOW,
                TEST_WEIGHT_HIGH);

        assertEquals(TEST_VALUE_LOW, lesserNode.getValue());
        assertEquals(TEST_WEIGHT_HIGH, lesserNode.getWeight(), TEST_DELTA);
        assertEquals(root, lesserNode.getParent());
        assertEquals(null, lesserNode.getPriorSibling());
        assertEquals(node, lesserNode.getNextSibling());
        assertEquals(lesserNode, root.getFirstChild());
    }

    @Test
    public void testAddChildEqualWeight() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) root.addChild(TEST_VALUE, TEST_WEIGHT);
        // Lexically lower value, but equal weight
        WeightedCharTrieNode lesserNode = (WeightedCharTrieNode) root.addChild(TEST_VALUE_LOW,
                TEST_WEIGHT);

        assertEquals(TEST_VALUE_LOW, lesserNode.getValue());
        assertEquals(TEST_WEIGHT, lesserNode.getWeight(), TEST_DELTA);
        assertEquals(root, lesserNode.getParent());
        assertEquals(null, lesserNode.getNextSibling());
        assertEquals(node, lesserNode.getPriorSibling());
    }

    @Test
    public void testAddExistingChild() {
        WeightedCharTrieNode node = (WeightedCharTrieNode) root.addChild(TEST_VALUE, TEST_WEIGHT);
        WeightedCharTrieNode node2 = (WeightedCharTrieNode) root.addChild(TEST_VALUE_LOW,
                TEST_WEIGHT_HIGH);
        // Value that was already added. Should have same weight.
        WeightedCharTrieNode existingNode = (WeightedCharTrieNode) root.addChild(TEST_VALUE,
                TEST_WEIGHT);

        assertEquals(node, existingNode);
    }
}
