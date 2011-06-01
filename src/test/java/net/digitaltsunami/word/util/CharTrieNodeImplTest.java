/**
 * 
 */
package net.digitaltsunami.word.util;

import static org.junit.Assert.*;

import java.util.Iterator;

import net.digitaltsunami.word.util.CharTrieNode;
import net.digitaltsunami.word.util.CharTrieNodeImpl;

import org.junit.Test;

/**
 * @author dhagberg
 * 
 */
public class CharTrieNodeImplTest {

	/**
	 * Test adding and retrieval of a child node.
	 */
	@Test
	public void testAddAndGetChild() {
		CharTrieNode root = new CharTrieNodeImpl((char) 0);
		CharTrieNode child = root.addChild('a');
		assertSame(child, root.getChild('a'));
	}

	/**
	 * Test to ensure parent retrieval is correct.
	 */
	@Test
	public void testGetChildParent() {
		CharTrieNode root = new CharTrieNodeImpl((char) 0);
		CharTrieNode child = root.addChild('a');
		assertSame(child, root.getChild('a'));
		assertSame(root, child.getParent());
	}

	/**
	 * Test setting and checking of terminus flag.
	 */
	@Test
	public void testTerminalLogic() {
		CharTrieNodeImpl node = new CharTrieNodeImpl('a');
		assertFalse("terminal should default to false", node.isTerminus());
		node.setTerminus(true);
		assertTrue("terminal shouuld now be true", node.isTerminus());
	}

	/**
	 * Test to ensure correct value is returned.
	 */
	@Test
	public void testGetValue() {
		CharTrieNode node = new CharTrieNodeImpl('a');
		assertEquals('a', node.getValue());
	}

	/**
	 * Test getFirstChild returns appropriate node.
	 */
	@Test
	public void testGetFirstChild() {
		CharTrieNodeImpl node = new CharTrieNodeImpl('h');
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
		CharTrieNodeImpl node = new CharTrieNodeImpl('h');
		CharTrieNodeImpl currentNode = (CharTrieNodeImpl) node.addChild('i');
		node.addChild('o');
		node.addChild('a');
		assertEquals('o', currentNode.getNextSibling().getValue());
	}

	/**
	 * Test getPriorSibling returns appropriate node.
	 */
	@Test
	public void testGetPriorSibling() {
		CharTrieNodeImpl node = new CharTrieNodeImpl('h');
		CharTrieNodeImpl currentNode = (CharTrieNodeImpl) node.addChild('i');
		node.addChild('o');
		node.addChild('a');
		assertEquals('a', currentNode.getPriorSibling().getValue());
	}

	/**
	 * Ensure {@link CharTrieNodeImpl} returns an iterator over the children of
	 * a given node and that the correct children are returned.
	 */
	@Test
	public void testIterator() {
		CharTrieNode root = new CharTrieNodeImpl((char) 0);
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
		CharTrieNodeImpl root = new CharTrieNodeImpl((char) 0);
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
		CharTrieNodeImpl root = new CharTrieNodeImpl(true);
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
		CharTrieNodeImpl root = new CharTrieNodeImpl('0');
		CharTrieNodeImpl firstChild = (CharTrieNodeImpl) root.addChild('b');

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
		CharTrieNodeImpl root = new CharTrieNodeImpl('0');
		CharTrieNodeImpl firstChild = (CharTrieNodeImpl) root.addChild('b');

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
		CharTrieNodeImpl root = new CharTrieNodeImpl('0');
		CharTrieNodeImpl firstChild = (CharTrieNodeImpl) root.addChild('b');
		CharTrieNodeImpl sibling = (CharTrieNodeImpl) root.addChild('d');

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
		CharTrieNodeImpl root = new CharTrieNodeImpl('0');
		CharTrieNodeImpl firstChild = (CharTrieNodeImpl) root.addChild('b');
		CharTrieNodeImpl lastChild = (CharTrieNodeImpl) root.addChild('d');
		CharTrieNodeImpl sibling = (CharTrieNodeImpl) root.addChild('c');

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
		CharTrieNodeImpl root = new CharTrieNodeImpl('0');

		CharTrieNodeImpl origFirstChild = (CharTrieNodeImpl) root.addChild('b');
		CharTrieNodeImpl lastChild = (CharTrieNodeImpl) root.addChild('d');
		CharTrieNodeImpl newFirstChild = (CharTrieNodeImpl) root.addChild('a');

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
		CharTrieNode root = new CharTrieNodeImpl('0');

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
		CharTrieNodeImpl root = new CharTrieNodeImpl('0');
		// Add some single character terms
		root.addChild('d');
		root.addChild('b');
		CharTrieNodeImpl testTerm = (CharTrieNodeImpl) root.addChild('a');
		testTerm.setTerminus(true);
		assertEquals("a", testTerm.getTerm());
	}

	/**
	 * Test that retrieving a term from a terminus node is successful for a
	 * multiple character term.
	 */
	@Test
	public void testGetTermMultiChar() {
		CharTrieNodeImpl root = new CharTrieNodeImpl('0');
		// Add some single character terms
		root.addChild('a').addChild('t').addChild('e');
		root.addChild('c').addChild('a').addChild('b');
		CharTrieNodeImpl testTerm = 
			(CharTrieNodeImpl) root.addChild('c').addChild('a').addChild('n');
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
		CharTrieNodeImpl root = new CharTrieNodeImpl('0');
		CharTrieNodeImpl currentNode = root;
		// Current initial buffer is set to 128
		for(int i = 0; i < 127; i++) {
			currentNode = (CharTrieNodeImpl) currentNode.addChild('a');
		}
		// Test that right before boundry works correctly.
		currentNode.setTerminus(true);
		assertEquals(127, currentNode.getTerm().length());
		
		// Test that right at boundry works correctly.
		currentNode = (CharTrieNodeImpl) currentNode.addChild('b');
		currentNode.setTerminus(true);
		assertEquals(128, currentNode.getTerm().length());
		
		// Test that right past boundry works correctly.
		currentNode = (CharTrieNodeImpl) currentNode.addChild('b');
		currentNode.setTerminus(true);
		assertEquals(129, currentNode.getTerm().length());
		
	}

	/**
	 * Ensure toString returns a value containing the adjacent nodes.
	 */
	@Test
	public void testToString() {
		CharTrieNodeImpl root = new CharTrieNodeImpl('0');
		CharTrieNode node = root.addChild('a');
		assertTrue(node.toString().contains("p=0"));
	}

}
