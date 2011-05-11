package net.digitaltsunami.word.util;


/**
 * Abstract factory used to create instances of {@link CharTrieNode} and
 * {@link CharTrieTerminusNode} instances specific to the implementation of
 * those interfaces. This factory provides for:
 * <ul>
 * <li>Creating a stand alone node with no relation to any other node.</li>
 * <li>Creating and appending a {@link CharTrieNode} representing a character in
 * a term that is not the last in the term.</li>
 * <li>Creating and appending a {@link CharTrieTerminusNode} representing a
 * character in a term that is the last in the term.</li>
 * <li>Converting an existing {@link CharTrieNode} representing a character in
 * an existing term that was not last in that term, but is now the last
 * character in a newly entered term.
 * <p>
 * The need for the conversion arises when an existing node represents a
 * character that was not the last in the word, but now a new word is being
 * added that ends with this character. For example, given an existing sequence
 * of batter, when the term bat is added, the 't' exists, but is not a terminus
 * node. The existing node must be converted so that the 't' node correctly
 * identifies a term.</li>
 * </ul>
 * 
 * @author dhagberg
 * 
 */
public interface CharTrieNodeFactory {
	/**
	 * Create a new node with the provided character value. The new node will be
	 * stand-alone in that it will not be connected to any other node.
	 * 
	 * @param value
	 * @return the newly created {@link CharTrieNode}.
	 */
	public CharTrieNode createNode(char value);

	/**
	 * Create a new root node.  The node must respond true to {@link CharTrieNode#isRoot}.
	 * @return the newly created root node.
	 */
	public CharTrieNode createRootNode();
	
	/**
	 * Create a new node with the provided character value and add as a child to
	 * the provided node. This node created should not be a terminus for the
	 * term (i.e., should not be for the last character in the word).
	 * 
	 * @param parentNode
	 *            the node to which the newly created node will be added as a
	 *            child.
	 * @param value
	 *            character that the new child node represents.
	 * @return the newly created {@link CharTrieNode} with the applicable values
	 *         set.
	 */
	public CharTrieNode addChild(CharTrieNode parentNode, char value);

	/**
	 * Create a new terminus node with the provided character value and add as a
	 * child to the provided node. This node created will be a terminus for the
	 * term (i.e., the last character in the word).
	 * <p>
	 * Implementors may perform processing specific to the terminus during this
	 * processing. For example a count of terms could be kept.
	 * 
	 * @param parentNode
	 *            the node to which the newly created node will be added as a
	 *            child.
	 * @param value
	 *            character that the new child node represents.
	 * @return the newly created {@link CharTrieTerminusNode} with the
	 *         applicable values set.
	 */
	public CharTrieTerminusNode addChildTerminus(CharTrieNode parentNode,
			char value);

	/**
	 * Create a new terminus node with the provided character value and add as a
	 * child to the provided node. This node created will be a terminus for the
	 * term (i.e., the last character in the word). In addition, the method is
	 * provided the original term as passed in to the framework. The original
	 * term may differ if the term or character filtering has been applied.
	 * <p>
	 * Implementors may perform processing specific to the terminus during this
	 * processing.
	 * 
	 * @param parentNode
	 *            the node to which the newly created node will be added as a
	 *            child.
	 * @param value
	 *            character that the new child node represents.
	 * @param term
	 *            the original term as it was provided to the framework.
	 * @return the newly created {@link CharTrieTerminusNode} with the
	 *         applicable values set.
	 */
	public CharTrieTerminusNode addChildTerminus(CharTrieNode parentNode,
			char value, String term);

	/**
	 * Convert an existing {@link CharTrieNode} to a
	 * {@link CharTrieTerminusNode}. This node will be a terminus for a term
	 * (i.e., the last character in the word). The value will remain the same
	 * and other relationships should not be affected.
	 * <p>
	 * <strong>NOTE:</strong> The node returned may be a new node and the node
	 * passed in should no longer be referenced.
	 * <p>
	 * Note that the provided node may already be a terminus node and it is up
	 * to the implementation to define behavior specific to that occurrence.
	 * <p>
	 * The need for the conversion arises when an existing node represents a
	 * character that was not the last in the word, but now a new word is being
	 * added that ends with this character. For example, given an existing
	 * sequence of batter, when the term bat is added, the 't' exists, but is
	 * not a terminus node. The existing node must be converted so that the 't'
	 * node correctly identifies a term.
	 * <p>
	 * Implementors may perform processing specific to the terminus during this
	 * processing.
	 * 
	 * @param parentNode
	 *            the node to which the newly created node will be added as a
	 *            child.
	 * @return the existing node converted to a {@link CharTrieTerminusNode}
	 *         with the applicable values set.
	 */
	public CharTrieTerminusNode convertToTerminus(CharTrieNode currentNode);

	/**
	 * Convert an existing {@link CharTrieNode} to a
	 * {@link CharTrieTerminusNode}. This node will be a terminus for a term
	 * (i.e., the last character in the word). The value will remain the same
	 * and other relationships should not be affected.
	 * <p>
	 * <strong>NOTE:</strong> The node returned may be a new node and the node
	 * passed in should no longer be referenced.
	 * <p>
	 * Note that the provided node may already be a terminus node and it is up
	 * to the implementation to define behavior specific to that occurrence.
	 * <p>
	 * The need for the conversion arises when an existing node represents a
	 * character that was not the last in the word, but now a new word is being
	 * added that ends with this character. For example, given an existing
	 * sequence of batter, when the term bat is added, the 't' exists, but is
	 * not a terminus node. The existing node must be converted so that the 't'
	 * node correctly identifies a term.
	 * <p>
	 * In addition, the method is provided the original term as passed in to the
	 * framework. The original term may differ if the term or character
	 * filtering has been applied.
	 * <p>
	 * Implementors may perform processing specific to the terminus during this
	 * processing.
	 * 
	 * @param parentNode
	 *            the node to which the newly created node will be added as a
	 *            child.
	 * @param term
	 *            the original term as it was provided to the framework.
	 * @return the existing node converted to a {@link CharTrieTerminusNode}
	 *         with the applicable values set.
	 */
	public CharTrieTerminusNode convertToTerminus(CharTrieNode currentNode,
			String originalTerm);


}
