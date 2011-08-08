/**
 * 
 */
package net.digitaltsunami.word.trie;

/**
 * Implementation of the abstract factory {@link CharTrieNodeFactory} that
 * creates instances of {@link LinkedCharTrieNode} for both the
 * {@link CharTrieNode} and {@link CharTrieTerminusNode} interfaces.
 * 
 * @author dhagberg
 * 
 */
public class LexCharTrieNodeFactory implements CharTrieNodeFactory {
    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.word.trie.CharTrieNodeFactory#createNode(char)
     */
    @Override
    public CharTrieNode createNode(char c) {
        return new LexLinkedCharTrieNode(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.word.trie.CharTrieNodeFactory#createRootNode()
     */
    @Override
    public CharTrieNode createRootNode() {
        return new LexLinkedCharTrieNode(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.digitaltsunami.word.trie.CharTrieNodeFactory#addChild(net.digitaltsunami
     * .word.trie.CharTrieNode, char)
     */
    @Override
    public CharTrieNode addChild(CharTrieNode parentNode, char c) {
        return parentNode.addChild(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.digitaltsunami.word.trie.CharTrieNodeFactory#addChildTerminus(net
     * .digitaltsunami.word.trie.CharTrieNode, char)
     */
    @Override
    public CharTrieTerminusNode addChildTerminus(CharTrieNode parentNode, char c) {
        LinkedCharTrieNode newNode = (LinkedCharTrieNode) parentNode.addChild(c);
        newNode.setTerminus(true);
        return newNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.digitaltsunami.word.trie.CharTrieNodeFactory#addChildTerminus(net
     * .digitaltsunami.word.trie.CharTrieNode, char, java.lang.String)
     */
    @Override
    public CharTrieTerminusNode addChildTerminus(CharTrieNode parentNode, char c, String term) {
        LinkedCharTrieNode newNode = (LinkedCharTrieNode) parentNode.addChild(c);
        newNode.setTerminus(true);
        return newNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.digitaltsunami.word.trie.CharTrieNodeFactory#convertToTerminus(net
     * .digitaltsunami.word.trie.CharTrieNode)
     */
    @Override
    public CharTrieTerminusNode convertToTerminus(CharTrieNode currentNode) {
        ((LinkedCharTrieNode) currentNode).setTerminus(true);
        return (LinkedCharTrieNode) currentNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.digitaltsunami.word.trie.CharTrieNodeFactory#convertToTerminus(net
     * .digitaltsunami.word.trie.CharTrieNode, java.lang.String)
     */
    @Override
    public CharTrieTerminusNode convertToTerminus(CharTrieNode currentNode, String originalTerm) {
        ((LinkedCharTrieNode) currentNode).setTerminus(true);
        return (LinkedCharTrieNode) currentNode;
    }

}
