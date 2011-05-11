package net.digitaltsunami.word.util;

import static org.junit.Assert.*;

import net.digitaltsunami.word.util.CharTrieNode;
import net.digitaltsunami.word.util.CharTrieTerminusNode;
import net.digitaltsunami.word.util.DefaultCharTrieNodeFactory;

import org.junit.*;

public class DefaultCharTrieNodeFactoryTest {
	private DefaultCharTrieNodeFactory factory;
		private CharTrieNode parentNode;
	
	@Before
	public void setup() {
		factory = new DefaultCharTrieNodeFactory();
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
		CharTrieNode node = factory.addChild(parentNode, 'a');
		assertEquals('a', node.getValue());
		assertSame(parentNode, node.getParent());
		assertFalse(node.isTerminus());
	}
	
	@Test
	public void testAddChildSecond() {
		CharTrieNode firstNode = factory.addChild(parentNode, 'a');
		CharTrieNode node = factory.addChild(firstNode, 'b');
		assertEquals('b', node.getValue());
		assertSame(firstNode, node.getParent());
		assertSame(parentNode, node.getParent().getParent());
		assertFalse(node.isTerminus());
	}

	@Test
	public void testAddChildTerminusSingleChar() {
		CharTrieTerminusNode node = factory.addChildTerminus(parentNode, 'a');
		assertEquals('a', node.getValue());
		assertSame(parentNode, node.getParent());
		assertTrue(node.isTerminus());
	}

	@Test
	public void testAddChildTerminusMultipleChar() {
		CharTrieNode firstNode = factory.addChild(parentNode, 'a');
		CharTrieTerminusNode node = factory.addChildTerminus(firstNode, 'b');
		assertEquals('b', node.getValue());
		assertSame(firstNode, node.getParent());
		assertSame(parentNode, node.getParent().getParent());
		assertTrue(node.isTerminus());
	}
	
	@Test
	public void testAddChildTerminusSingleCharWithTerm() {
		CharTrieTerminusNode node = factory.addChildTerminus(parentNode, 'a', "a");
		assertEquals('a', node.getValue());
		assertSame(parentNode, node.getParent());
		assertTrue(node.isTerminus());
	}
	
	@Test
	public void testAddChildTerminusMultipleCharWithTerm() {
		CharTrieNode firstNode = factory.addChild(parentNode, 'a');
		CharTrieTerminusNode node = factory.addChildTerminus(firstNode, 'b', "ab");
		assertEquals('b', node.getValue());
		assertSame(firstNode, node.getParent());
		assertSame(parentNode, node.getParent().getParent());
		assertTrue(node.isTerminus());
	}

	@Test
	public void testConvertToTerminusCharTrieNode() {
		CharTrieNode node = factory.addChild(parentNode, 'a');
		assertEquals('a', node.getValue());
		assertFalse(node.isTerminus());
		assertSame(parentNode, node.getParent());
		
		CharTrieTerminusNode convertedNode = factory.convertToTerminus(node);
		assertTrue(convertedNode.isTerminus());
		assertSame(parentNode, convertedNode.getParent());
		assertEquals("a", convertedNode.getTerm());
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
