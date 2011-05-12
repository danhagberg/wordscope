package net.digitaltsunami.word.util.event;

import java.util.EventObject;

import net.digitaltsunami.word.util.CharTrieNode;

/**
 * Event dispatched when a non-terminus node or character is added.  
 *
 * @author dhagberg
 */
public class NodeAddedEvent extends EventObject {

	private static final long serialVersionUID = 1091049728144325287L;
	
	
	/**
	 * Create a new event and store the source for which the event will be dispatched.
	 * @param source causing event to be created.
	 */
	public NodeAddedEvent(CharTrieNode source) {
		super(source);
	}
	
	/**
	 * Return the node that caused the event to be dispatched.
	 * @return source that caused event to be dispatched.
	 */
	public CharTrieNode getNode() {
		return (CharTrieNode) getSource();
	}
}
