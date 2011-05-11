/**
 * 
 */
package net.digitaltsunami.word.util;

/**
 * A terminal node in a {@link CharTrie} allowing for additional data specific
 * to dictionary.
 * 
 * @author dhagberg
 * 
 */
public interface CharTrieTerminusNode extends CharTrieNode {
	/**
	 * Returns the term for which this node is the terminus. The term is the
	 * unique path from root to this node.
	 * 
	 * @return the term represented by this node.
	 */
	public String getTerm();

}
