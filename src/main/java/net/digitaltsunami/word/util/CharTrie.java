/**
 * 
 */
package net.digitaltsunami.word.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.digitaltsunami.word.util.event.NodeAddedEvent;
import net.digitaltsunami.word.util.event.NodeAddedListener;
import net.digitaltsunami.word.util.event.NodeEventListenerList;

/**
 * A dictionary of terms stored using a Trie structure.
 * <p>
 * case insensitive
 * <p>
 * ordered results
 * <p>
 * filters
 * <p>
 * filter term applied before char
 * 
 * <p>
 * To allow for additional processing as characters are added, a notification
 * framework is provided. Event processing will be done using post filter
 * processing, so excluded characters will not fire events. Events fired are
 * listed below in descending order of frequency.
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
 */
public class CharTrie {
	/** Empty node used as root of trie. */
	private final CharTrieNode root;
	/**
	 * Optional character filter that will be applied to each input character if
	 * provided.
	 */
	private final CharFilter charFilter;
	/**
	 * Optional term filter that will be applied to each input term if provided.
	 */
	private final TermFilter termFilter;

	/** Number of unique words contained within this dictionary. */
	private int wordCount;
	/**
	 * Implementation of abstract factory used as delegate when adding nodes.
	 */
	private final CharTrieNodeFactory nodeFactory;
	/**
	 * Handles event firing as new characters/nodes are added.
	 */
	private final NodeEventListenerList listenerList = new NodeEventListenerList();

	/**
	 * Create a default dictionary. All terms will be added without any
	 * filtering applied.
	 * <p>
	 * All nodes will be created using the {@link DefaultCharTrieNodeFactory}.
	 */
	public CharTrie() {
		this(null, null);
	}

	/**
	 * Create a dictionary using the provided character filter. All terms added
	 * to this dictionary will be passed through the filter prior to being
	 * added.
	 * <p>
	 * All nodes will be created using the {@link DefaultCharTrieNodeFactory}.
	 * 
	 * @param charFilter
	 */
	public CharTrie(CharFilter charFilter) {
		this(charFilter, null);
	}

	/**
	 * Create a dictionary using the provided term filter. All terms added to
	 * this dictionary will be passed through the filter prior to being added.
	 * <p>
	 * All nodes will be created using the {@link DefaultCharTrieNodeFactory}.
	 * 
	 * @param termFilter
	 */
	public CharTrie(TermFilter termFilter) {
		this(null, termFilter);
	}

	/**
	 * Create a dictionary using the provided term and character filters. All
	 * terms added to this dictionary will be passed through these filters prior
	 * to being added. The term filter will be applied first, followed by the
	 * character filter.
	 * <p>
	 * All nodes will be created using the {@link DefaultCharTrieNodeFactory}.
	 * 
	 * @param charFilter
	 * @param termFilter
	 */
	public CharTrie(CharFilter charFilter, TermFilter termFilter) {
		this(charFilter, termFilter, new DefaultCharTrieNodeFactory());
	}

	/**
	 * Create a dictionary using the provided term and character filters. All
	 * terms added to this dictionary will be passed through these filters prior
	 * to being added. The term filter will be applied first, followed by the
	 * character filter.
	 * <p>
	 * All nodes will be created using the provided {@link CharTrieNodeFactory}.
	 * 
	 * @param charFilter
	 * @param termFilter
	 * @param nodeFactory
	 */
	public CharTrie(CharFilter charFilter, TermFilter termFilter,
			CharTrieNodeFactory nodeFactory) {
		root = nodeFactory.createRootNode();
		this.charFilter = charFilter;
		this.termFilter = termFilter;
		this.nodeFactory = nodeFactory;
	}

	/**
	 * Add a term to the dictionary. The characters will be added, starting at
	 * the root of the dictionary. Both term and character filters will be
	 * applied if applicable, which may alter the term prior to entering the
	 * nodes.
	 * <p>
	 * All characters will be converted to lower case prior to placing in the
	 * dictionary. This will happen regardless of the filters applied as this
	 * operation is done after the filters have modified the term.
	 * <p>
	 * All nodes will be created using the current {@link CharTrieNodeFactory}
	 * set for this trie. Terminus nodes will be created using the same factory
	 * and will pass the original term to the node.
	 * 
	 * @param term
	 *            A string of characters to add.
	 */
	public void addTerm(String term) {
		String originalTerm = term;
		CharTrieNode currentNode = root;
		if (termFilter != null) {
			term = termFilter.apply(term);
			if (term == TermFilter.SKIP_TERM) {
				// Term Filter decided to skip this entry, just return.
				return;
			}
		}
		char[] termArray = term.toCharArray();
		termArray = applyCharFilter(termArray);

		// Don't count length until after filter applied as it may have changed.
		int termLen = termArray.length;
		if (termLen == 0) {
			// Char Filter removed all characters, just return.
			return;
		}

		// Convert all remaining characters to lower case.
		for (int i = 0; i < termLen; i++) {
			termArray[i] = Character.toLowerCase(termArray[i]);

		}

		int lenUpToTerminus = termLen - 1;
		int termPos = 0;

		/* Position node at last found */
		CharTrieNode node;
		for (; termPos < termLen; termPos++) {
			node = currentNode.getChild(termArray[termPos]);
			// Did not reach the end of the array, will have to add the rest.
			if (node == null) {
				break;
			}
			// Terminus characters will be counted following this loop.
			if (termPos < lenUpToTerminus) {
				listenerList.dispatchCharacterAddedEvent(node);
			}
			currentNode = node;
		}
		// If at end, sequence already existed but may or may not have been a
		// term. If node already a terminus, return, otherwise convert to terminus.
		if (termPos == termLen) {
			if (currentNode.isTerminus()) {
				listenerList.dispatchTerminusCharacterAddedEvent((CharTrieTerminusNode) currentNode);
				return;
			} else {
				currentNode = nodeFactory.convertToTerminus(currentNode, originalTerm);
				listenerList.dispatchTerminusNodeAddedEvent((CharTrieTerminusNode) currentNode);
				wordCount++;
				return;
			}

		}
		for (; termPos < lenUpToTerminus; termPos++) {
			currentNode = nodeFactory.addChild(currentNode, termArray[termPos]);
			listenerList.dispatchNodeAddedEvent(currentNode);
		}
		// Mark the last node as the end of a sequence if the sequence has not
		// previously been added.
		currentNode = nodeFactory.addChildTerminus(currentNode, termArray[termPos],
				originalTerm);
		listenerList.dispatchTerminusNodeAddedEvent((CharTrieTerminusNode) currentNode);
		wordCount++;
	}

	/**
	 * Apply the char filter if applicable on all characters within the
	 * termArray. The results will be returned in a character array. If there is
	 * no applicable filter, the original results will be returned. If the
	 * filter is applied, the length of the results may be less than or equal to
	 * the original length.
	 * 
	 * @param termArray
	 * @return
	 */
	private char[] applyCharFilter(char[] termArray) {
		if (charFilter != null) {
			int currentIndex = 0;
			for (int i = 0; i < termArray.length; i++) {
				char value = charFilter.apply(termArray[i]);
				if (value != CharFilter.SKIP_CHAR) {
					termArray[currentIndex] = charFilter.apply(termArray[i]);
					currentIndex++;
				}
			}
			// If any characters were skipped, then recreate array with new
			// term.
			if (currentIndex != termArray.length) {
				termArray = Arrays.copyOf(termArray, currentIndex);

			}
		}
		return termArray;
	}

	/**
	 * Find and return all terms within the dictionary beginning with the
	 * provided prefix. The query will start at the root of the dictionary.
	 * 
	 * @param parent
	 *            Node at which the query will begin
	 * @param prefix
	 *            Common prefix to all terms to be returned.
	 * @return A set of all terms beginning with the provided prefix. If no
	 *         terms found, an empty set will be returned.
	 */
	public Collection<String> findTerms(String prefix) {
		return findTerms(root, prefix);
	}

	/**
	 * Find and return all terms within the dictionary beginning with the
	 * provided prefix. The query will start with the children of the provided
	 * parent node, but will not include it.
	 * 
	 * @param parent
	 *            Node at which the query will begin
	 * @param prefix
	 *            Common prefix to all terms to be returned.
	 * @return A set of all terms beginning with the provided prefix. If no
	 *         terms found, an empty set will be returned.
	 */
	public Collection<String> findTerms(CharTrieNode parent, String prefix) {
		String lcPrefix = prefix.toLowerCase();
		List<CharTrieNode> prefixNodes = findChildSequence(parent, lcPrefix);
		if (prefixNodes.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> terms = new LinkedList<String>();
		StringBuilder termBuff = new StringBuilder(lcPrefix);
		int prefixLen = lcPrefix.length();

		/*
		 * Starting with the last node in the prefix node as a parent node,
		 * descend the trie looking for nodes with a terminus of true.
		 */
		CharTrieNode current = prefixNodes.get(prefixLen - 1);
		if (current.isTerminus()) {
			terms.add(termBuff.toString());
		}
		findTerms(terms, current, termBuff, prefixLen);

		return terms;
	}

	/**
	 * Returns true if the dictionary contains the provided term.
	 * 
	 * @param term
	 *            Term to query for.
	 * @return true if the term was found, false otherwise.
	 */
	public boolean contains(String term) {
		String lcTerm = term.toLowerCase();
		List<CharTrieNode> prefixNodes = findChildSequence(root, lcTerm);
		if (prefixNodes.isEmpty()) {
			return false;
		} else {
			// If the last node is a terminus, then this is a complete term and
			// was found, otherwise, it is a fragment and the query term was not
			// found.
			return prefixNodes.get(prefixNodes.size() - 1).isTerminus();
		}
	}

	/**
	 * Return all terms within this dictionary.
	 * 
	 * @return Return all terms within this dictionary.
	 */
	public Collection<String> getAllTerms() {
		List<String> terms = new LinkedList<String>();
		int prefixLen = 0;

		/*
		 * Starting with the root node as the parent node, descend the trie
		 * looking for nodes with a terminus of true.
		 */
		for(CharTrieNode child : root) {
			StringBuilder termBuff = new StringBuilder();
			termBuff.append(child.getValue());
			if (child.isTerminus()) {
				terms.add(termBuff.toString());
			}
			findTerms(terms, child, termBuff, prefixLen);
		}

		return terms;
	}

	/**
	 * Internal recursive method to walk the nodes searching for complete words
	 * within the trie. Each word will be added to the provided
	 * {@link Collection}.
	 * 
	 * @param terms
	 * @param current
	 * @param termBuff
	 * @param currentLength
	 */
	private void findTerms(Collection<String> terms, CharTrieNode current,
			StringBuilder termBuff, int currentLength) {
		currentLength++;
		for (CharTrieNode child : current) {
			termBuff.setLength(currentLength);
			termBuff.append(child.getValue());
			if (child.isTerminus()) {
				terms.add(termBuff.toString());
			}
			findTerms(terms, child, termBuff, currentLength);
		}
	}

	/**
	 * Return a {@link List} of {@link CharTrieNode} objects representing the
	 * provided sequence. The search will begin by matching the first character
	 * in the sequence with the first child of the provided node. The sequence
	 * is a match if there is a corresponding child node at the each level
	 * indicated by the position in the sequence.
	 * <p>
	 * Note: Each of the {@link CharTrieNode}s in the returned list is the full
	 * node and may contain other children in addition to the requested node.
	 * 
	 * @param parent
	 *            {@link CharTrieNode} from which the query for children will
	 *            start. This node will <strong>not</strong> be included in the
	 *            returned list.
	 * @param sequence
	 *            String of characters to search for.
	 * @return A {@link List} of {@link CharTrieNode} elements for the sequence.
	 *         If an exact match is not found, then an empty list will be
	 *         returned.
	 */
	protected List<CharTrieNode> findChildSequence(CharTrieNode parent,
			String sequence) {
		List<CharTrieNode> nodes = new ArrayList<CharTrieNode>(
				sequence.length());
		for (char elem : sequence.toCharArray()) {
			CharTrieNode node = parent.getChild(elem);
			if (node == null) {
				return Collections.emptyList(); // Use empty list so nodes can
												// be GC'd
			}
			nodes.add(node);
			parent = node;
		}
		return nodes;
	}

	/**
	 * Return a {@link List} of {@link CharTrieNode} objects representing the
	 * provided sequence. The search will begin by matching the first character
	 * in the sequence beginning at the root of the dictionary. The sequence is
	 * a match if there is a corresponding child node at the each level
	 * indicated by the position in the sequence.
	 * <p>
	 * Note: Each of the {@link CharTrieNode}s in the returned list is the full
	 * node and may contain other children in addition to the requested node.
	 * 
	 * @param sequence
	 *            String of characters to search for.
	 * @return A {@link List} of {@link CharTrieNode} elements for the sequence.
	 *         If an exact match is not found, then an empty list will be
	 *         returned.
	 */
	protected List<CharTrieNode> findSequence(String sequence) {
		return findChildSequence(root, sequence);
	}

	/**
	 * Return the number of unique entry terms.
	 * 
	 * @return the number of unique terms within this dictionary.
	 */
	public int getWordCount() {
		return wordCount;
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
		listenerList.addCharacterAddedListener(listener);
	}

	/**
	 * Remove the specified listener for events fired as each character is
	 * added.
	 */
	public void removeCharacterAddedListener(NodeAddedListener listener) {
		listenerList.removeCharacterAddedListener(listener);
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
	public void addCharacterTerminusAddedListener(NodeAddedListener listener) {
		listenerList.addTerminusCharacterAddedListener(listener);
	}

	/**
	 * Remove the specified listener for events fired when a terminus character
	 * is added.
	 */
	public void removeCharacterTerminusAddedListener(NodeAddedListener listener) {
		listenerList.removeTerminusCharacterAddedListener(listener);
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
		listenerList.addNodeAddedListener(listener);
	}

	/**
	 * Remove the specified listener for events fired when a node is added.
	 */
	public void removeNodeAddedListener(NodeAddedListener listener) {
		listenerList.removeNodeAddedListener(listener);
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
		listenerList.addTerminusNodeAddedListener(listener);
	}

	/**
	 * Remove the specified listener for events fired when a terminus node is
	 * added.
	 */
	public void removeTerminusNodeAddedListener(NodeAddedListener listener) {
		listenerList.removeTerminusNodeAddedListener(listener);
	}

}
