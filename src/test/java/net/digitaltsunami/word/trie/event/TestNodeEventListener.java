package net.digitaltsunami.word.trie.event;


public class TestNodeEventListener implements NodeAddedListener {
    public int eventCount;

    @Override
    public void characterAdded(NodeAddedEvent event) {
        eventCount++;
    }

    @Override
    public void terminusCharacterAdded(TerminusNodeAddedEvent event) {
        eventCount++;
    }

    @Override
    public void nodeAdded(NodeAddedEvent event) {
        eventCount++;
    }

    @Override
    public void terminusNodeAdded(TerminusNodeAddedEvent event) {
        eventCount++;
    }
}