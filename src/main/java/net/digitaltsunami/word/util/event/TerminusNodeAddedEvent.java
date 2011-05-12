/**
 * 
 */
package net.digitaltsunami.word.util.event;

import net.digitaltsunami.word.util.CharTrieTerminusNode;

/**
 * Event dispatched when a terminus node or character is added.  
 * 
 * @author dhagberg
 */
public class TerminusNodeAddedEvent extends NodeAddedEvent {

	private static final long serialVersionUID = -864256844747069018L;

	/**
	 * Create a new event and store the source for which the event will be dispatched.
	 * @param source causing event to be created.
	 */
	public TerminusNodeAddedEvent(CharTrieTerminusNode source) {
		super(source);
	}
	
	/**
	 * Return the node that caused the event to be dispatched.
	 * @return source that caused event to be dispatched.
	 */
	public CharTrieTerminusNode getTerminusNode() {
		return (CharTrieTerminusNode) getSource();
	}

}
