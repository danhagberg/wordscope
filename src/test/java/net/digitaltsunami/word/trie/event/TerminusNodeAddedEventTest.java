/**
 * 
 */
package net.digitaltsunami.word.trie.event;

import static org.junit.Assert.*;

import net.digitaltsunami.word.trie.CharTrieNode;
import net.digitaltsunami.word.trie.CharTrieNodeFactory;
import net.digitaltsunami.word.trie.CharTrieTerminusNode;
import net.digitaltsunami.word.trie.LexCharTrieNodeFactory;
import net.digitaltsunami.word.trie.event.TerminusNodeAddedEvent;

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
        CharTrieNodeFactory factory = new LexCharTrieNodeFactory();
        node = factory.createNode('a');
        terminus = factory.addChildTerminus(node, 't');
    }

    /**
     * Test event creation and node retrieval.
     */
    @Test
    public void testGetTerminusNode() {
        TerminusNodeAddedEvent event = new TerminusNodeAddedEvent(terminus);
        assertSame(terminus, event.getTerminusNode());
    }
}
