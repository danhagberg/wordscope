/**
 * 
 */
package net.digitaltsunami.word.util;

/**
 * Implementation of the abstract factory {@link CharTrieNodeFactory} that
 * creates instances of {@link CharTrieNodeImpl} for both the
 * {@link CharTrieNode} and {@link CharTrieTerminusNode} interfaces.
 * 
 * @author dhagberg
 * 
 */
public class DefaultCharTrieNodeFactory implements CharTrieNodeFactory {
	/* (non-Javadoc)
	 * @see net.digitaltsunami.word.util.CharTrieNodeFactory#createNode(char)
	 */
	@Override
	public CharTrieNode createNode(char c) {
		return new CharTrieNodeImpl(c);
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.word.util.CharTrieNodeFactory#createRootNode()
	 */
	@Override
	public CharTrieNode createRootNode() {
		return new CharTrieNodeImpl(true);
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.word.util.CharTrieNodeFactory#addChild(net.digitaltsunami.word.util.CharTrieNode, char)
	 */
	@Override
	public CharTrieNode addChild(CharTrieNode parentNode, char c) {
		return parentNode.addChild(c);
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.word.util.CharTrieNodeFactory#addChildTerminus(net.digitaltsunami.word.util.CharTrieNode, char)
	 */
	@Override
	public CharTrieTerminusNode addChildTerminus(CharTrieNode parentNode, char c) {
		CharTrieNodeImpl newNode = (CharTrieNodeImpl) parentNode.addChild(c);
		newNode.setTerminus(true);
		return newNode;
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.word.util.CharTrieNodeFactory#addChildTerminus(net.digitaltsunami.word.util.CharTrieNode, char, java.lang.String)
	 */
	@Override
	public CharTrieTerminusNode addChildTerminus(CharTrieNode parentNode,
			char c, String term) {
		CharTrieNodeImpl newNode = (CharTrieNodeImpl) parentNode.addChild(c);
		newNode.setTerminus(true);
		return newNode;
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.word.util.CharTrieNodeFactory#convertToTerminus(net.digitaltsunami.word.util.CharTrieNode)
	 */
	@Override
	public CharTrieTerminusNode convertToTerminus(CharTrieNode currentNode) {
		((CharTrieNodeImpl) currentNode).setTerminus(true);
		return (CharTrieNodeImpl) currentNode;
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.word.util.CharTrieNodeFactory#convertToTerminus(net.digitaltsunami.word.util.CharTrieNode, java.lang.String)
	 */
	@Override
	public CharTrieTerminusNode convertToTerminus(CharTrieNode currentNode,
			String originalTerm) {
		((CharTrieNodeImpl) currentNode).setTerminus(true);
		return (CharTrieNodeImpl) currentNode;
	}

}
