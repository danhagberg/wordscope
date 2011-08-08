/**
 * 
 */
package net.digitaltsunami.word.trie;

/**
 * @author dhagberg
 * 
 */
public class WeightedCharTrieNodeFactory implements CharTrieNodeFactory {
    private ExpectedValueWeightTable charFreqTable;

    public WeightedCharTrieNodeFactory(ExpectedValueWeightTable expectedWeights) {
        this.charFreqTable = expectedWeights;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.word.trie.CharTrieNodeFactory#createNode(char)
     */
    @Override
    public CharTrieNode createNode(char c) {
        return new WeightedCharTrieNode(c);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.word.trie.CharTrieNodeFactory#createRootNode()
     */
    @Override
    public CharTrieNode createRootNode() {
        return new WeightedCharTrieNode(true);
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
        float weight = charFreqTable.getExpectedValue(parentNode.getValue(), c);
        return ((WeightedCharTrieNode) parentNode).addChild(c, weight);
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
        float weight = charFreqTable.getExpectedValue(parentNode.getValue(), c);
        LinkedCharTrieNode newNode = (LinkedCharTrieNode) ((WeightedCharTrieNode) parentNode)
                .addChild(c, weight);
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
        float weight = charFreqTable.getExpectedValue(parentNode.getValue(), c);
        LinkedCharTrieNode newNode = (LinkedCharTrieNode) ((WeightedCharTrieNode) parentNode)
                .addChild(c, weight);
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
