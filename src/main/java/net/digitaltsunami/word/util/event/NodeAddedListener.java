/**
 * 
 */
package net.digitaltsunami.word.util.event;

import java.util.EventListener;

import net.digitaltsunami.word.util.CharTrie;
import net.digitaltsunami.word.util.CharTrieNode;
import net.digitaltsunami.word.util.CharTrieTerminusNode;

/**
 * The listener interface for when characters and/or nodes are added. The
 * listener can specify which of the following events to be notified:
 * <ul>
 * <li>Character added regardless of whether or not it had already existed in
 * the trie.</li>
 * <li>Terminus character added regardless of whether or not it had already
 * existed in the trie.</li>
 * <li>New character added that previously had no node.</li>
 * <li>New terminus character added that previously had no node. This represents
 * a new term. This notification will occur if the node is new or an
 * existing non-terminus node was converted to a terminus node.</li>
 * </ul>
 * @see NodeEventListenerList 
 * @see CharTrie
 * 
 * @author dhagberg
 * 
 */
public interface NodeAddedListener extends EventListener {
	/**
	 * Method used to notify listener as each character is added to the
	 * dictionary regardless of prior status.
	 * 
	 * @param event
	 *            containing {@link CharTrieNode} that caused event to be fired.
	 *            interested when a character is added.
	 */
	public void characterAdded(NodeAddedEvent event);

	/**
	 * Method used to notify listener as each terminus character is added to the
	 * dictionary regardless of prior status.
	 * 
	 * @param event
	 *            containing {@link CharTrieTerminusNode} that caused event to
	 *            be fired.
	 */
	public void terminusCharacterAdded(TerminusNodeAddedEvent event);

	/**
	 * Method used to notify listener for each node that is
	 * <strong>added</strong> to the dictionary. This will occur only when the
	 * node did not already exist.
	 * 
	 * @param event
	 *            containing {@link CharTrieTerminusNode} that caused event to
	 *            be fired. interested when a character is added.
	 */
	public void nodeAdded(NodeAddedEvent event);

	/**
	 * Method used to notify listener for each node that is
	 * <strong>added</strong> to the dictionary. This will occur only when the
	 * terminus node did not already exist. If the node existed, but was a
	 * non-terminus node and was converted, then this method will be invoked.
	 * 
	 * @param event
	 *            containing {@link CharTrieTerminusNode} that caused event to
	 *            be fired. interested when a character is added.
	 */
	public void terminusNodeAdded(TerminusNodeAddedEvent event);
}
