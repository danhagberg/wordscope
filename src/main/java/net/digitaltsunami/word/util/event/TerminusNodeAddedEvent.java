/**
 * 
 */
package net.digitaltsunami.word.util.event;

import net.digitaltsunami.word.util.CharTrieTerminusNode;

/**
 * @author dhagberg
 * 
 */
public class TerminusNodeAddedEvent extends NodeAddedEvent {

	private static final long serialVersionUID = -864256844747069018L;

	/**
	 * @param source
	 */
	public TerminusNodeAddedEvent(CharTrieTerminusNode source) {
		super(source);
	}
	
	public CharTrieTerminusNode getTerminusNode() {
		return (CharTrieTerminusNode) getSource();
	}

}
