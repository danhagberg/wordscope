package net.digitaltsunami.word.trie.event;

import static org.junit.Assert.assertSame;
import net.digitaltsunami.word.trie.CharTrieNode;
import net.digitaltsunami.word.trie.CharTrieNodeFactory;
import net.digitaltsunami.word.trie.LexCharTrieNodeFactory;

import org.junit.Before;
import org.junit.Test;

public class NodeAddedEventTest {

    private CharTrieNode node;

    @Before
    public void setup() {
        CharTrieNodeFactory factory = new LexCharTrieNodeFactory();
        node = factory.createNode('a');
    }

    /**
     * Test event creation and node retrieval.
     */
    @Test
    public void testGetNode() {
        NodeAddedEvent event = new NodeAddedEvent(node);
        assertSame(node, event.getNode());
    }

}
