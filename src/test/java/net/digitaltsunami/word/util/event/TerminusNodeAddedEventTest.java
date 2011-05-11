/**
 * 
 */
package net.digitaltsunami.word.util.event;

import static org.junit.Assert.*;

import net.digitaltsunami.word.util.CharTrieNode;
import net.digitaltsunami.word.util.CharTrieNodeFactory;
import net.digitaltsunami.word.util.CharTrieTerminusNode;
import net.digitaltsunami.word.util.DefaultCharTrieNodeFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * @author dhagberg
 *
 */
public class TerminusNodeAddedEventTest {

	private CharTrieNode node;
	private CharTrieTerminusNode terminus;

	@Before
	public void setup() {
		CharTrieNodeFactory factory = new DefaultCharTrieNodeFactory();
		node = factory.createNode('a');
		terminus = factory.addChildTerminus(node, 't');
	}

	/**
	 * Test event creation and node retrieval.
	 */
	@Test
	public void testGetTerminusNode() {
		TerminusNodeAddedEvent event = new TerminusNodeAddedEvent(terminus);
		assertSame(terminus,event.getTerminusNode());
	}
}
