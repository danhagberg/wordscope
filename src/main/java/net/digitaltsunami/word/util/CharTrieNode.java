package net.digitaltsunami.word.util;

import java.util.Iterator;

/**
 * Represents a single character value within a sequence of characters for a term.
 * @author dhagberg
 *
 */
public interface CharTrieNode extends Iterable<CharTrieNode> {

	/**
	 * Returns child Node with a value matching the provided value or null if it
	 * does not exist.
	 * 
	 * @param value
	 *            to look for as a direct descendant of this node.
	 * @return Matching node or null if not found.
	 */
	public CharTrieNode getChild(char value);

	/**
	 * Adds the character as a child to the current node. If the value is new
	 * for this node, a new {@link CharTrieNode} will be created and returned.
	 * If that character was already attached to this node, then the existing
	 * node will be returned.
	 * 
	 * @param value
	 *            to add as a direct descendant of this node.
	 * @return CharTrieNode for the provided value.
	 */
	public CharTrieNode addChild(char value);

	/**
	 * Returns value indicating that node is terminus in complete word from root
	 * to this node.
	 * 
	 * @returns true if this node completes a word from root to here.
	 */
	public boolean isTerminus();
	
	/**
	 * Returns value indicating that node is a root node.
	 * 
	 * @returns true if this node represents the root node of the trie.
	 */
	public boolean isRoot();

	/**
	 * Return the character value for this node.
	 * 
	 * @return Return the character value for this node.
	 */
	public char getValue();

	/**
	 * Return the parent for this node or null if root node.
	 * 
	 * @return the parent for this node or null if root node.
	 */
	public CharTrieNode getParent();

	/**
	 * Return the left sibling for this node or null if first node.
	 * 
	 * @return the left sibling for this node or null if first node.
	 */
	public CharTrieNode getPriorSibling();

	/**
	 * Return the right sibling for this node or null if first node.
	 * 
	 * @return the right sibling for this node or null if first node.
	 */
	public CharTrieNode getNextSibling();

	/**
	 * Return the first child node for this node or null if node has no
	 * children.
	 * 
	 * @return the first child node for this node or null if node has no
	 *         children.
	 */
	public CharTrieNode getFirstChild();

	/**
	 * Return an iterator over the children of this node.
	 * 
	 * @return an iterator over the children of this node.
	 */
	public Iterator<CharTrieNode> iterator();

}