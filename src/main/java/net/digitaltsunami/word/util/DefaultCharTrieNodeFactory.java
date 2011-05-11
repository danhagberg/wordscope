/**
 * 
 */
package net.digitaltsunami.word.util;


/**
 * @author dhagberg
 *
 */
public class DefaultCharTrieNodeFactory implements CharTrieNodeFactory {
	/* (non-Javadoc)
	 * @see net.digitaltsunami.util.CharTrieNodeFactory#createNode(char)
	 */
	@Override
	public CharTrieNode createNode(char c) {
		return new CharTrieNodeImpl(c);
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.util.CharTrieNodeFactory#createRootNode()
	 */
	@Override
	public CharTrieNode createRootNode() {
		return new CharTrieNodeImpl(true);
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.util.CharTrieNodeFactory#addChild(net.digitaltsunami.util.CharTrieNodeInterface, char)
	 */
	@Override
	public CharTrieNode addChild(CharTrieNode parentNode, char c) {
		return parentNode.addChild(c);
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.util.CharTrieNodeFactory#addChildTerminal(net.digitaltsunami.util.CharTrieNodeInterface, char)
	 */
	@Override
	public CharTrieTerminusNode addChildTerminus(CharTrieNode parentNode,
			char c) {
		CharTrieNodeImpl newNode = (CharTrieNodeImpl) parentNode.addChild(c);
		newNode.setTerminus(true);
		return newNode;
	}

	/* (non-Javadoc)
	 * @see net.digitaltsunami.util.CharTrieNodeFactory#addChildTerminal(net.digitaltsunami.util.CharTrieNodeInterface, char, java.lang.String)
	 */
	@Override
	public CharTrieTerminusNode addChildTerminus(CharTrieNode parentNode,
			char c, String term) {
		CharTrieNodeImpl newNode = (CharTrieNodeImpl) parentNode.addChild(c);
		newNode.setTerminus(true);
		return newNode;
	}

	@Override
	public CharTrieTerminusNode convertToTerminus(CharTrieNode currentNode) {
		((CharTrieNodeImpl)currentNode).setTerminus(true);
		return (CharTrieNodeImpl)currentNode;
	}

	@Override
	public CharTrieTerminusNode convertToTerminus(CharTrieNode currentNode,
			String originalTerm) {
		((CharTrieNodeImpl)currentNode).setTerminus(true);
		return (CharTrieNodeImpl)currentNode;
	}


}
