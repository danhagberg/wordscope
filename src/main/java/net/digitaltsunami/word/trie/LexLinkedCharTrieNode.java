/**
 * 
 */
package net.digitaltsunami.word.trie;

/**
 * @author dhagberg
 * 
 */
public class LexLinkedCharTrieNode extends LinkedCharTrieNode {

    protected LexLinkedCharTrieNode(CharTrieNode parent, char value) {
        super(parent, value);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param value
     */
    public LexLinkedCharTrieNode(char value) {
        super(value);
    }

    /**
     * @param rootNode
     */
    public LexLinkedCharTrieNode(boolean rootNode) {
        super(rootNode);
    }

    @Override
    public CharTrieNode getChild(char value) {
        LinkedCharTrieNode sibling = getFirstChild();
        while (sibling != null) {
            if (value > sibling.getValue()) {
                // Value > child, still could be found
                sibling = sibling.getNextSibling();
            } else if (value == sibling.getValue()) {
                // Value == child, return
                return sibling;
            } else {
                // Value < child, can't be found as they are in order
                return null;
            }
        }
        return null;
    }

    @Override
    public CharTrieNode addChild(char value) {
        LinkedCharTrieNode sibling = getFirstChild();
        while (sibling != null) {
            // if value > sibling
            if (value > sibling.getValue()) {
                if (sibling.getNextSibling() != null) {
                    // New value still less than remaining siblings,
                    // continue searching.
                    sibling = sibling.getNextSibling();
                } else {
                    // Reached the end of the sibling list. Add new node and
                    // append to the last node in the list.
                    LinkedCharTrieNode newNode = new LexLinkedCharTrieNode(this, value);
                    sibling.appendNode(newNode);
                    /*
                     * sibling.setNextSibling(newNode);
                     * newNode.setPriorSibling(sibling);
                     */
                    return newNode;
                }
            } else if (value == sibling.getValue()) {
                // Value == sibling, already exists, just return
                return sibling;
            } else {
                // Value < sibling, list is in order, so it won't be found.
                // Create new, insert it, and return the new node.
                LinkedCharTrieNode newNode = new LexLinkedCharTrieNode(this, value);
                sibling.prependNode(newNode);
                return newNode;
            }
        }

        // First child node to be added, add and return.
        LinkedCharTrieNode newNode = new LexLinkedCharTrieNode(this, value);
        setFirstChild(newNode);
        return newNode;
    }

}
