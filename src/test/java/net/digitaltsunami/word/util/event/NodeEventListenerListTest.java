package net.digitaltsunami.word.util.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.digitaltsunami.word.util.CharTrie;
import net.digitaltsunami.word.util.CharTrieNode;
import net.digitaltsunami.word.util.CharTrieNodeFactory;
import net.digitaltsunami.word.util.CharTrieTerminusNode;
import net.digitaltsunami.word.util.DefaultCharTrieNodeFactory;

import org.junit.Before;
import org.junit.Test;

public class NodeEventListenerListTest {
	private NodeEventListenerList listenerList;
	private CharTrieNode node;
	private CharTrieTerminusNode terminus;

	@Before
	public void setup() {
		listenerList = new NodeEventListenerList();
		CharTrieNodeFactory factory = new DefaultCharTrieNodeFactory();
		node = factory.createNode('a');
		terminus = factory.addChildTerminus(node, 't');
	}
	
	/**
	 * Test adding of a character listener and corresponding dispatch method.
	 */
	@Test
	public void testAddCharacterListener() {
		TestNodeEventListener listener = new TestNodeEventListener();
		listenerList.addCharacterAddedListener(listener);
		listenerList.dispatchCharacterAddedEvent(node);
		assertEquals(1, listener.eventCount);
	}

	/**
	 * Test adding of a terminus character listener and corresponding dispatch method.
	 */
	@Test
	public void testAddTerminusCharacterListener() {
		TestNodeEventListener listener = new TestNodeEventListener();
		listenerList.addTerminusCharacterAddedListener(listener);
		listenerList.dispatchTerminusCharacterAddedEvent(terminus);
		assertEquals(1, listener.eventCount);
	}

	/**
	 * Test adding of a node listener and corresponding dispatch method.
	 */
	@Test
	public void testAddNodeListener() {
		TestNodeEventListener listener = new TestNodeEventListener();
		listenerList.addNodeAddedListener(listener);
		listenerList.dispatchNodeAddedEvent(node);
		assertEquals(1, listener.eventCount);
	}

	/**
	 * Test adding of a terminus node listener and corresponding dispatch method.
	 */
	@Test
	public void testAddTerminusNodeListener() {
		TestNodeEventListener listener = new TestNodeEventListener();
		listenerList.addTerminusNodeAddedListener(listener);
		listenerList.dispatchTerminusNodeAddedEvent(terminus);
		assertEquals(1, listener.eventCount);
	}

	/**
	 * Test removing of a character listener.
	 */
	@Test
	public void testRemoveCharacterListener() {
		TestNodeEventListener listener = new TestNodeEventListener();
		listenerList.addCharacterAddedListener(listener);
		listenerList.dispatchCharacterAddedEvent(node);
		assertEquals(1, listener.eventCount);
		
		listenerList.removeCharacterAddedListener(listener);
		listenerList.dispatchCharacterAddedEvent(node);
		assertEquals(1, listener.eventCount);
	}

	/**
	 * Test removing of a terminus character listener.
	 */
	@Test
	public void testRemoveTerminusCharacterListener() {
		TestNodeEventListener listener = new TestNodeEventListener();
		listenerList.addTerminusCharacterAddedListener(listener);
		listenerList.dispatchTerminusCharacterAddedEvent(terminus);
		assertEquals(1, listener.eventCount);
		
		listenerList.removeTerminusCharacterAddedListener(listener);
		listenerList.dispatchTerminusCharacterAddedEvent(terminus);
		assertEquals(1, listener.eventCount);
	}

	/**
	 * Test removal of a node listener.
	 */
	@Test
	public void testRemoveNodeListener() {
		TestNodeEventListener listener = new TestNodeEventListener();
		listenerList.addNodeAddedListener(listener);
		listenerList.dispatchNodeAddedEvent(node);
		assertEquals(1, listener.eventCount);
		
		listenerList.removeNodeAddedListener(listener);
		listenerList.dispatchNodeAddedEvent(node);
		assertEquals(1, listener.eventCount);
	}

	/**
	 * Test removal of a terminus node listener.
	 */
	@Test
	public void testRemoveTerminusNodeListener() {
		TestNodeEventListener listener = new TestNodeEventListener();
		listenerList.addTerminusNodeAddedListener(listener);
		listenerList.dispatchTerminusNodeAddedEvent(terminus);
		assertEquals(1, listener.eventCount);
		
		listenerList.removeTerminusNodeAddedListener(listener);
		listenerList.dispatchTerminusNodeAddedEvent(terminus);
		assertEquals(1, listener.eventCount);
	}
}
