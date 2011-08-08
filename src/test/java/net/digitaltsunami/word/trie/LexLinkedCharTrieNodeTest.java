/**
 * 
 */
package net.digitaltsunami.word.trie;

import static org.junit.Assert.*;

import java.util.Iterator;

import net.digitaltsunami.word.trie.CharTrieNode;
import net.digitaltsunami.word.trie.LexLinkedCharTrieNode;

import org.junit.Test;

/**
 * Test class for {@link LexLinkedCharTrieNode} as well as
 * {@link LinkedCharTrieNode}. The tests contained within this class test both
 * the base functions provided by {@link LinkedCharTrieNode} as well as the
 * specialization added by {@link LexLinkedCharTrieNode}.
 * {@link LinkedCharTrieNode} is tested here as it is abstract and requires a
 * concrete implmenation for testing.
 * 
 * @author dhagberg
 * 
 */
public class LexLinkedCharTrieNodeTest {

    /**
     * Test adding and retrieval of a child node.
     */
    @Test
    public void testAddAndGetChild() {
        CharTrieNode root = new LexLinkedCharTrieNode((char) 0);
        CharTrieNode child = root.addChild('a');
        assertSame(child, root.getChild('a'));
    }

    /**
     * Test to ensure parent retrieval is correct.
     */
    @Test
    public void testGetChildParent() {
        CharTrieNode root = new LexLinkedCharTrieNode((char) 0);
        CharTrieNode child = root.addChild('a');
        assertSame(child, root.getChild('a'));
        assertSame(root, child.getParent());
    }

    /**
     * Test setting and checking of terminus flag.
     */
    @Test
    public void testTerminalLogic() {
        LexLinkedCharTrieNode node = new LexLinkedCharTrieNode('a');
        assertFalse("terminal should default to false", node.isTerminus());
        node.setTerminus(true);
        assertTrue("terminal shouuld now be true", node.isTerminus());
    }

    /**
     * Test to ensure correct value is returned.
     */
    @Test
    public void testGetValue() {
        CharTrieNode node = new LexLinkedCharTrieNode('a');
        assertEquals('a', node.getValue());
    }

    /**
     * Test getFirstChild returns appropriate node.
     */
    @Test
    public void testGetFirstChild() {
        LexLinkedCharTrieNode node = new LexLinkedCharTrieNode('h');
        node.addChild('i');
        node.addChild('o');
        node.addChild('a');
        assertEquals('a', node.getFirstChild().getValue());
    }

    /**
     * Test getNextSibling returns appropriate node.
     */
    @Test
    public void testGetNextSibling() {
        LexLinkedCharTrieNode node = new LexLinkedCharTrieNode('h');
        LexLinkedCharTrieNode currentNode = (LexLinkedCharTrieNode) node.addChild('i');
        node.addChild('o');
        node.addChild('a');
        assertEquals('o', currentNode.getNextSibling().getValue());
    }

    /**
     * Test getPriorSibling returns appropriate node.
     */
    @Test
    public void testGetPriorSibling() {
        LexLinkedCharTrieNode node = new LexLinkedCharTrieNode('h');
        LexLinkedCharTrieNode currentNode = (LexLinkedCharTrieNode) node.addChild('i');
        node.addChild('o');
        node.addChild('a');
        assertEquals('a', currentNode.getPriorSibling().getValue());
    }

    /**
     * Ensure {@link LexLinkedCharTrieNode} returns an iterator over the
     * children of a given node and that the correct children are returned.
     */
    @Test
    public void testIterator() {
        CharTrieNode root = new LexLinkedCharTrieNode((char) 0);
        int count = 0;

        for (CharTrieNode node : root) {
            count++;
        }
        assertEquals(0, count);

        root.addChild('a');
        root.addChild('b');
        root.addChild('c');

        for (CharTrieNode node : root) {
            count++;
        }
        assertEquals(3, count);
    }

    /**
     * Ensure that an exception is raised if an attempt to remove the current
     * node in an iteration.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode((char) 0);
        int count = 0;

        for (CharTrieNode node : root) {
            count++;
        }
        assertEquals(0, count);

        root.addChild('a');
        root.addChild('b');
        root.addChild('c');

        Iterator<CharTrieNode> iter = root.iterator();
        while (iter.hasNext()) {
            iter.next();
            iter.remove(); // Should throw exception
        }
        fail("remove should have thrown UnsupportedOperationException(\"Remove not supported\")");
    }

    /** Create new node with no children or parents */
    @Test
    public void createRootNode() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode(true);
        assertTrue("Root node should have answered true to isRoot", root.isRoot());
        assertEquals(null, root.getPriorSibling());
        assertEquals(null, root.getNextSibling());
        assertEquals(null, root.getFirstChild());

    }

    /**
     * Attach to parent, a new 'b' node. Test that node is first child and child
     * can reach parent. Left and right should be null. FirstChild should be
     * null.
     */
    @Test
    public void addFirstChildNodeViaAddChild() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode('0');
        LexLinkedCharTrieNode firstChild = (LexLinkedCharTrieNode) root.addChild('b');

        assertSame(firstChild, root.getFirstChild());
        assertEquals(null, firstChild.getPriorSibling());
        assertEquals(null, firstChild.getNextSibling());
        assertEquals(null, firstChild.getFirstChild());

    }

    /**
     * Attach to parent, a new 'b' node. Test that node is first child and child
     * can reach parent. Left and right should be null. Attempt to add same
     * value. Ensure that same node returned and pointers are still correct.
     */
    @Test
    public void addDuplicateChildNodeViaAddChild() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode('0');
        LexLinkedCharTrieNode firstChild = (LexLinkedCharTrieNode) root.addChild('b');

        assertSame(firstChild, root.getFirstChild());
        assertEquals(null, firstChild.getPriorSibling());
        assertEquals(null, firstChild.getNextSibling());
        assertEquals(null, firstChild.getFirstChild());

        CharTrieNode dup = root.addChild('b');
        assertSame(firstChild, dup);
        assertSame(firstChild, root.getFirstChild());
        assertEquals(null, firstChild.getPriorSibling());
        assertEquals(null, firstChild.getNextSibling());
        assertEquals(null, firstChild.getFirstChild());

    }

    /**
     * Attach to parent, a new 'b' node. Attach new 'd' node. Ensure that b.left
     * is null, b.right is d, d.left is b, d.right is null, parent firstNode is
     * still 'b'
     * 
     */
    @Test
    public void appendSiblingViaAdd() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode('0');
        LexLinkedCharTrieNode firstChild = (LexLinkedCharTrieNode) root.addChild('b');
        LexLinkedCharTrieNode sibling = (LexLinkedCharTrieNode) root.addChild('d');

        assertSame(firstChild, root.getFirstChild());
        assertEquals(null, firstChild.getPriorSibling());
        assertSame(sibling, firstChild.getNextSibling());
        assertEquals(null, sibling.getFirstChild());
        assertSame(firstChild, sibling.getPriorSibling());
    }

    /**
     * Attach to parent, a new 'b' node. Attach new 'd' node. Attach new 'c'
     * node. Ensure that b.left is null, b.right is c, c.left is b, c.right is d
     * and d.right is null. Ensure parent firstNode is still 'b'
     */
    @Test
    public void insertSiblingViaAddChild() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode('0');
        LexLinkedCharTrieNode firstChild = (LexLinkedCharTrieNode) root.addChild('b');
        LexLinkedCharTrieNode lastChild = (LexLinkedCharTrieNode) root.addChild('d');
        LexLinkedCharTrieNode sibling = (LexLinkedCharTrieNode) root.addChild('c');

        assertEquals(null, sibling.getFirstChild());
        assertSame(firstChild, root.getFirstChild());

        assertEquals(null, firstChild.getPriorSibling());
        assertSame(sibling, firstChild.getNextSibling());

        assertSame(firstChild, sibling.getPriorSibling());
        assertSame(lastChild, sibling.getNextSibling());

        assertSame(sibling, lastChild.getPriorSibling());
        assertEquals(null, lastChild.getNextSibling());
    }

    /**
     * Attach to parent, a new 'b' node. Attach new 'd' node. Attach new 'a'
     * node. Ensure that a.left is null, a.right is b, b.left is a, b.right is d
     * and d.right is null. Ensure parent firstNode is now 'a'
     */
    @Test
    public void prependSiblingViaAdd() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode('0');

        LexLinkedCharTrieNode origFirstChild = (LexLinkedCharTrieNode) root.addChild('b');
        LexLinkedCharTrieNode lastChild = (LexLinkedCharTrieNode) root.addChild('d');
        LexLinkedCharTrieNode newFirstChild = (LexLinkedCharTrieNode) root.addChild('a');

        assertSame(newFirstChild, root.getFirstChild());

        assertEquals(null, newFirstChild.getPriorSibling());
        assertSame(origFirstChild, newFirstChild.getNextSibling());

        assertSame(newFirstChild, origFirstChild.getPriorSibling());
        assertSame(lastChild, origFirstChild.getNextSibling());

        assertSame(origFirstChild, lastChild.getPriorSibling());
        assertEquals(null, lastChild.getNextSibling());
    }

    /**
     * Test the various conditions for getChild not found: empty list; less than
     * first node, greater than last node, falls in middle.
     */
    @Test
    public void testGetChildNotFound() {
        CharTrieNode root = new LexLinkedCharTrieNode('0');

        // Empty
        assertNull(root.getChild('a'));

        // Less than first node
        root.addChild('b');
        assertNull(root.getChild('a'));

        // Greater than last node
        root.addChild('d');
        assertNull(root.getChild('e'));

        // In the middle
        assertNull(root.getChild('c'));

    }

    /**
     * Test that retrieving a term from a terminus node is successful for a
     * single character term.
     */
    @Test
    public void testGetTermSingleChar() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode('0');
        // Add some single character terms
        root.addChild('d');
        root.addChild('b');
        LexLinkedCharTrieNode testTerm = (LexLinkedCharTrieNode) root.addChild('a');
        testTerm.setTerminus(true);
        assertEquals("a", testTerm.getTerm());
    }

    /**
     * Test that retrieving a term from a terminus node is successful for a
     * multiple character term.
     */
    @Test
    public void testGetTermMultiChar() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode('0');
        // Add some single character terms
        root.addChild('a').addChild('t').addChild('e');
        root.addChild('c').addChild('a').addChild('b');
        LexLinkedCharTrieNode testTerm = (LexLinkedCharTrieNode) root.addChild('c').addChild('a')
                .addChild('n');
        root.addChild('c').addChild('a').addChild('t');
        testTerm.setTerminus(true);
        assertEquals("can", testTerm.getTerm());
    }

    /**
     * Test that getTerm exceeding default buffer size correctly increases
     * buffer and does not error.
     */
    @Test
    public void testGetTermLarge() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode('0');
        LexLinkedCharTrieNode currentNode = root;
        // Current initial buffer is set to 128
        for (int i = 0; i < 127; i++) {
            currentNode = (LexLinkedCharTrieNode) currentNode.addChild('a');
        }
        // Test that right before boundry works correctly.
        currentNode.setTerminus(true);
        assertEquals(127, currentNode.getTerm().length());

        // Test that right at boundry works correctly.
        currentNode = (LexLinkedCharTrieNode) currentNode.addChild('b');
        currentNode.setTerminus(true);
        assertEquals(128, currentNode.getTerm().length());

        // Test that right past boundry works correctly.
        currentNode = (LexLinkedCharTrieNode) currentNode.addChild('b');
        currentNode.setTerminus(true);
        assertEquals(129, currentNode.getTerm().length());

    }

    /**
     * Ensure toString returns a value containing the adjacent nodes.
     */
    @Test
    public void testToString() {
        LexLinkedCharTrieNode root = new LexLinkedCharTrieNode('0');
        CharTrieNode node = root.addChild('a');
        assertTrue(node.toString().contains("p=0"));
    }

}
