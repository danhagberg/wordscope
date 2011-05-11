package net.digitaltsunami.word.util.event;

import static org.junit.Assert.*;
import net.digitaltsunami.word.util.CharTrieNode;
import net.digitaltsunami.word.util.CharTrieNodeFactory;
import net.digitaltsunami.word.util.CharTrieTerminusNode;
import net.digitaltsunami.word.util.DefaultCharTrieNodeFactory;

import org.junit.Before;
import org.junit.Test;

public class NodeAddedEventTest {

	private CharTrieNode node;

	@Before
	public void setup() {
		CharTrieNodeFactory factory = new DefaultCharTrieNodeFactory();
		node = factory.createNode('a');
	}

	/**
	 * Test event creation and node retrieval.
	 */
	@Test
	public void testGetNode() {
		NodeAddedEvent event = new NodeAddedEvent(node);
		assertSame(node,event.getNode());
	}

}
