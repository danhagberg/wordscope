package net.digitaltsunami.word.util.event;

import java.util.ArrayList;

import net.digitaltsunami.word.util.CharTrie;
import net.digitaltsunami.word.util.CharTrieNode;
import net.digitaltsunami.word.util.CharTrieTerminusNode;

/**
 * Maintains notification lists allow for additional processing as characters
 * are added. Notification lists are provided for the following events:
 * <ul>
 * <li><em>Character added</em> - Will be fired for each character as it is
 * added. Will be fired whether or not the character had previously been added.
 * <p>
 * <strong>NOTE:</strong> This event also includes all other events (terminus
 * characters, new nodes, new terminus nodes).</li>
 * <li><em>Node added</em> - Will be fired for each node that is added. Will not
 * fire if a node existed for the character being added. Note that this includes
 * terminus nodes.
 * <p>
 * <strong>NOTE:</strong> This event also includes new terminus nodes.</li>
 * <li><em>Terminus Character added</em> - Will be fired as each terminus
 * character is added (i.e., fire only upon the last character in the term)
 * <p>
 * <strong>NOTE:</strong> This event also includes new terminus nodes.</li>
 * <li><em>Terminus Node added</em> - Will be fired for each terminus node that
 * is added (i.e., as each new term is added). This will fire for both new nodes
 * and for existing non-terminus nodes that are converted see
 * {@link #addTerminusNodeAddedListener(NodeAddedListener)} for more information
 * regarding conversion.
 * </ul>
 * 
 * @author dhagberg
 * 
 */
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

	/**
	 * Add a listener for events fired as each character is added.
	 * <p>
	 * Listeners on this event will be notified as each character that is added
	 * to the dictionary regardless of prior status. Notification will be
	 * through the invocation of
	 * {@link NodeAddedListener#characterAdded(NodeAddedEvent)}.
	 * <p>
	 * <strong>NOTE:</strong> Events fired for characters being added will
	 * include terminus characters as well. Adding the same listener for both
	 * character and terminus character events will result in two invocations on
	 * the listener for terminus characters.
	 * 
	 * @param listener
	 *            interested when a character is added.
	 */
	public void addCharacterAddedListener(NodeAddedListener listener) {
		characterListeners.add(listener);
		terminusCharacterListeners.add(listener);
		nodeListeners.add(listener);
		terminusNodeListeners.add(listener);

	}

	/**
	 * Remove the specified listener for events fired as each character is
	 * added.
	 */
	public void removeCharacterAddedListener(NodeAddedListener listener) {
		characterListeners.remove(listener);
		terminusCharacterListeners.remove(listener);
		nodeListeners.remove(listener);
		terminusNodeListeners.remove(listener);
	}

	/**
	 * Add a listener for events fired when a new terminus character is added.
	 * <p>
	 * Listeners on this event will be invoked for each terminus character that
	 * is added to the dictionary regardless of prior status. Notification will
	 * be through the invocation of
	 * {@link NodeAddedListener#terminusCharacterAdded(net.digitaltsunami.word.util.event.TerminusNodeAddedEvent)}.
	 * <p>
	 * <strong>NOTE:</strong> Events fired for characters being added will
	 * include terminus characters as well. Adding the same listener for both
	 * character and terminus character events will result in two invocations on
	 * the listener for terminus characters.
	 * 
	 * @param listener
	 *            interested when a terminus character is added.
	 */
	public void addTerminusCharacterAddedListener(NodeAddedListener listener) {
		terminusCharacterListeners.add(listener);
		terminusNodeListeners.add(listener);

	}

	/**
	 * Remove the specified listener for events fired when a terminus character
	 * is added.
	 */
	public void removeTerminusCharacterAddedListener(NodeAddedListener listener) {
		terminusCharacterListeners.remove(listener);
		terminusNodeListeners.remove(listener);
	}

	/**
	 * Add a listener for events fired when a new node is added.
	 * <p>
	 * Listeners on this event will be invoked for each node that is
	 * <strong>added</strong> to the dictionary. This will occur only when the
	 * node did not already exist. Notification will be through the invocation
	 * of {@link NodeAddedListener#nodeAdded(NodeAddedEvent)}.
	 * <p>
	 * <strong>NOTE:</strong> Events fired for characters being added will
	 * include terminus characters as well. Adding the same listener for both
	 * character and terminus character events will result in two invocations on
	 * the listener for terminus characters.
	 * 
	 * @param listener
	 *            interested when a node is added.
	 */
	public void addNodeAddedListener(NodeAddedListener listener) {
		nodeListeners.add(listener);
		terminusNodeListeners.add(listener);
	}

	/**
	 * Remove the specified listener for events fired when a node is added.
	 */
	public void removeNodeAddedListener(NodeAddedListener listener) {
		nodeListeners.remove(listener);
		terminusNodeListeners.remove(listener);

	}

	/**
	 * Add a listener for events fired when a new terminus node is added or an
	 * existing non-terminus node is converted to a terminus node.
	 * <p>
	 * Listeners on this event will be notified for each node that is
	 * <strong>added</strong> to the dictionary. This will occur only when the
	 * terminus node did not already exist. If the node existed, but was a
	 * non-terminus node and was converted, then the listener will be notified.
	 * Notification will be through the invocation of
	 * {@link NodeAddedListener#terminusNodeAdded(TerminusNodeAddedEvent)}.
	 * <p>
	 * <strong>NOTE:</strong> Events fired for characters being added will
	 * include terminus characters as well. Adding the same listener for both
	 * character and terminus character events will result in two invocations on
	 * the listener for terminus characters.
	 * 
	 * @param listener
	 *            interested when a terminus node is added.
	 */
	public void addTerminusNodeAddedListener(NodeAddedListener listener) {
		terminusNodeListeners.add(listener);
	}

	/**
	 * Add a listener for events fired when a new terminus node is added or an
	 * existing non-terminus node is converted to a terminus node.
	 * <p>
	 * Listeners on this event will be notified for each node that is
	 * <strong>added</strong> to the dictionary. This will occur only when the
	 * terminus node did not already exist. If the node existed, but was a
	 * non-terminus node and was converted, then the listener will be notified.
	 * Notification will be through the invocation of
	 * {@link NodeAddedListener#terminusNodeAdded(TerminusNodeAddedEvent)}.
	 * <p>
	 * <strong>NOTE:</strong> Events fired for characters being added will
	 * include terminus characters as well. Adding the same listener for both
	 * character and terminus character events will result in two invocations on
	 * the listener for terminus characters.
	 * 
	 * @param listener
	 *            interested when a terminus node is added.
	 */
	public void removeTerminusNodeAddedListener(NodeAddedListener listener) {
		terminusNodeListeners.remove(listener);
	}

	/**
	 * Notify all character added listeners that a character has been added and
	 * provide the node for the character.
	 * 
	 * @param node
	 *            Node containing character.
	 */
	public void dispatchCharacterAddedEvent(CharTrieNode node) {
		if (characterListeners.size() > 0) {
			NodeAddedEvent event = new NodeAddedEvent(node);
			for (NodeAddedListener listener : characterListeners) {
				listener.characterAdded(event);
			}
		}
	}

	/**
	 * Notify all terminus character added listeners that a terminus character
	 * has been added and provide the terminus node for the character.
	 * 
	 * @param node
	 *            Node containing character.
	 */
	public void dispatchTerminusCharacterAddedEvent(CharTrieTerminusNode node) {
		if (terminusCharacterListeners.size() > 0) {
			TerminusNodeAddedEvent event = new TerminusNodeAddedEvent(node);
			for (NodeAddedListener listener : terminusCharacterListeners) {
				listener.terminusCharacterAdded(event);
			}
		}
	}

	/**
	 * Notify all node added listeners that a new node has been added and
	 * provide the newly added node.
	 * 
	 * @param node
	 *            Newly added node.
	 */
	public void dispatchNodeAddedEvent(CharTrieNode node) {
		if (nodeListeners.size() > 0) {
			NodeAddedEvent event = new NodeAddedEvent(node);
			for (NodeAddedListener listener : nodeListeners) {
				listener.nodeAdded(event);
			}
		}
	}

	/**
	 * Notify all terminus node added listeners that a new terminus node has
	 * been added and provide the newly added node.
	 * 
	 * @param node
	 *            Newly added terminus node.
	 */
	public void dispatchTerminusNodeAddedEvent(CharTrieTerminusNode node) {
		if (terminusNodeListeners.size() > 0) {
			TerminusNodeAddedEvent event = new TerminusNodeAddedEvent(node);
			for (NodeAddedListener listener : terminusNodeListeners) {
				listener.terminusNodeAdded(event);
			}
		}
	}
}
