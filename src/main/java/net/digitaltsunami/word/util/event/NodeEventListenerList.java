package net.digitaltsunami.word.util.event;

import java.util.ArrayList;

import net.digitaltsunami.word.util.CharTrieNode;
import net.digitaltsunami.word.util.CharTrieTerminusNode;

public class NodeEventListenerList {
	private final java.util.List<NodeAddedListener> characterListeners;
	private final java.util.List<NodeAddedListener> nodeListeners;
	private final java.util.List<NodeAddedListener> terminusNodeListeners;
	private final java.util.List<NodeAddedListener> terminusCharacterListeners;

	public NodeEventListenerList() {
		characterListeners = new ArrayList<NodeAddedListener>();
		nodeListeners = new ArrayList<NodeAddedListener>();
		terminusCharacterListeners = new ArrayList<NodeAddedListener>();
		terminusNodeListeners = new ArrayList<NodeAddedListener>();
	}

	public void addCharacterAddedListener(NodeAddedListener listener) {
		characterListeners.add(listener);
		terminusCharacterListeners.add(listener);
		nodeListeners.add(listener);
		terminusNodeListeners.add(listener);

	}

	public void removeCharacterAddedListener(NodeAddedListener listener) {
		characterListeners.remove(listener);
		terminusCharacterListeners.remove(listener);
		nodeListeners.remove(listener);
		terminusNodeListeners.remove(listener);
	}

	public void addTerminusCharacterAddedListener(NodeAddedListener listener) {
		terminusCharacterListeners.add(listener);
		terminusNodeListeners.add(listener);

	}

	public void removeTerminusCharacterAddedListener(NodeAddedListener listener) {
		terminusCharacterListeners.remove(listener);
		terminusNodeListeners.remove(listener);
	}

	public void addNodeAddedListener(NodeAddedListener listener) {
		nodeListeners.add(listener);
		terminusNodeListeners.add(listener);
	}

	public void removeNodeAddedListener(NodeAddedListener listener) {
		nodeListeners.remove(listener);
		terminusNodeListeners.remove(listener);

	}

	public void addTerminusNodeAddedListener(NodeAddedListener listener) {
		terminusNodeListeners.add(listener);
	}

	public void removeTerminusNodeAddedListener(NodeAddedListener listener) {
		terminusNodeListeners.remove(listener);
	}

	public void dispatchCharacterAddedEvent(CharTrieNode node) {
		if (characterListeners.size() > 0) {
			NodeAddedEvent event = new NodeAddedEvent(node);
			for (NodeAddedListener listener : characterListeners) {
				listener.characterAdded(event);
			}
		}
	}

	public void dispatchTerminusCharacterAddedEvent(CharTrieTerminusNode node) {
		if (terminusCharacterListeners.size() > 0) {
			TerminusNodeAddedEvent event = new TerminusNodeAddedEvent(node);
			for (NodeAddedListener listener : terminusCharacterListeners) {
				listener.terminusCharacterAdded(event);
			}
		}
	}

	public void dispatchNodeAddedEvent(CharTrieNode node) {
		if (nodeListeners.size() > 0) {
			NodeAddedEvent event = new NodeAddedEvent(node);
			for (NodeAddedListener listener : nodeListeners) {
				listener.nodeAdded(event);
			}
		}
	}

	public void dispatchTerminusNodeAddedEvent(CharTrieTerminusNode node) {
		if (terminusNodeListeners.size() > 0) {
			TerminusNodeAddedEvent event = new TerminusNodeAddedEvent(node);
			for (NodeAddedListener listener : terminusNodeListeners) {
				listener.terminusNodeAdded(event);
			}
		}
	}
}
