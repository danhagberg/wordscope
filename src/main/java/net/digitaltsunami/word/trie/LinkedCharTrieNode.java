package net.digitaltsunami.word.trie;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Container for a character providing links to siblings, parents, and children.
 * 
 * @author dhagberg
 * 
 */
public abstract class LinkedCharTrieNode implements CharTrieTerminusNode {
    private final char value;
    private LinkedCharTrieNode parent;
    private LinkedCharTrieNode firstChild;
    private LinkedCharTrieNode priorSibling;
    private LinkedCharTrieNode nextSibling;
    private boolean terminus;
    private final boolean root;

    /**
     * Create new Node with the provided value.
     * 
     * @param parent
     * @param value
     */
    protected LinkedCharTrieNode(CharTrieNode parent, char value) {
        this.parent = (LinkedCharTrieNode) parent;
        this.value = value;
        this.root = false;
    }

    /**
     * Create new Node with the provided value.
     * 
     * @param value
     */
    protected LinkedCharTrieNode(char value) {
        this.value = value;
        this.root = false;
    }

    /**
     * Create new root node. This node will not contain a value.
     * 
     * @param value
     */
    protected LinkedCharTrieNode(boolean rootNode) {
        this.value = '\0';
        this.root = rootNode;
    }

    /**
     * Marks this node as the terminus in a sequence indicating a complete word
     * from root to this node.
     * 
     * @param terminus
     *            true if this node completes a word from root to here.
     */
    protected void setTerminus(boolean terminus) {
        this.terminus = terminus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.util.CharTrieNode#isTerminus()
     */
    @Override
    public boolean isTerminus() {
        return terminus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.util.CharTrieNode#isRoot()
     */
    @Override
    public boolean isRoot() {
        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.util.CharTrieNode#getValue()
     */
    @Override
    public char getValue() {
        return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.util.CharTrieNode#getParent()
     */
    @Override
    public CharTrieNode getParent() {
        return this.parent;
    }

    /**
     * Return the left sibling for this node or null if first node.
     * 
     * @return the left sibling for this node or null if first node.
     */
    protected LinkedCharTrieNode getPriorSibling() {
        return this.priorSibling;
    }

    /**
     * Set the sibling that precedes this node.
     * 
     * @param sibling
     */
    protected void setPriorSibling(LinkedCharTrieNode sibling) {
        this.priorSibling = sibling;
    }

    /**
     * Return the next sibling for this node or null if first node.
     * 
     * @return the next sibling for this node or null if first node.
     */
    protected LinkedCharTrieNode getNextSibling() {
        return this.nextSibling;
    }

    /**
     * Set the sibling that follows this node.
     * 
     * @param sibling
     */
    protected void setNextSibling(LinkedCharTrieNode sibling) {
        this.nextSibling = sibling;
    }

    /**
     * Return the first child node for this node or null if node has no
     * children.
     * 
     * @return the first child node for this node or null if node has no
     *         children.
     */
    protected LinkedCharTrieNode getFirstChild() {
        return this.firstChild;
    }

    /**
     * Set the first child node for this node.
     * 
     * @param child
     *            the first child node for this node.
     */
    protected void setFirstChild(LinkedCharTrieNode child) {
        this.firstChild = child;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.util.CharTrieTerminusNode#getTerm()
     */
    @Override
    public String getTerm() {
        int buffSize = 128;
        char[] buff = new char[buffSize];
        int buffPos = buffSize;

        LinkedCharTrieNode currNode = this;
        while (currNode.parent != null) {
            // This entry will exceed buffer limits: double size and copy
            if (buffPos == 0) {
                // Save off original values
                int origBuffSize = buffSize;
                char[] origBuff = buff;
                // Double size of buffer
                buffSize = buffSize * 2;
                buff = new char[buffSize];
                // Copy original to new buffer
                buffPos = buffSize - 1;
                for (int origIdx = origBuffSize - 1; origIdx > 0; buffPos--, origIdx--) {
                    buff[buffPos] = origBuff[origIdx];
                }
            }

            buffPos--;
            buff[buffPos] = currNode.value;
            currNode = currNode.parent;
        }

        char[] termBuff = Arrays.copyOfRange(buff, buffPos, buffSize);
        return new String(termBuff);
    }

    /**
     * Return the child of this node that matches the provided value or null if
     * not found.
     * <p>
     * This implmentation will traverse the entire sibling list if the element is
     * not found. Concrete classes should override this method if the traversal
     * can be short circuited.
     * 
     * @param value
     *            child element to search for.
     * @return Child node if found or null if the value is not a child of this
     *         node.
     * 
     */
    @Override
    public CharTrieNode getChild(char value) {
        LinkedCharTrieNode sibling = getFirstChild();
        while (sibling != null) {
            if (value == sibling.getValue()) {
                // Value == child, return
                return sibling;
            } else {
                sibling = sibling.getNextSibling();
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.digitaltsunami.util.CharTrieNode#iterator()
     */
    @Override
    public Iterator<CharTrieNode> iterator() {
        return new ChildIterator(this);
    }

    @Override
    public String toString() {
        return "CharTrieNodeImpl [value=" + value + ", p="
                + (parent == null ? "null" : parent.value) + ", fc="
                + (firstChild == null ? "null" : firstChild.value) + ", l="
                + (priorSibling == null ? "null" : priorSibling.value) + ", r="
                + (nextSibling == null ? "null" : nextSibling.value) + ", terminus=" + terminus
                + "]";
    }

    /**
     * Insert the provided node prior to this node. Fix all pointers of siblings
     * and parent as applicable.
     * 
     * @param node
     *            to insert prior to the node.
     */
    protected void prependNode(LinkedCharTrieNode node) {
        node.setPriorSibling(this.priorSibling);
        node.setNextSibling(this);

        if (priorSibling == null) {
            this.parent.setFirstChild(node);
        } else {
            priorSibling.setNextSibling(node);
        }

        this.priorSibling = node;
    }

    /**
     * Append the provided node prior to this node. Fix all pointers of siblings
     * and parent as applicable.
     * 
     * @param node
     *            to insert prior to the node.
     */
    protected void appendNode(LinkedCharTrieNode node) {
        if (this.nextSibling != null) {
            nextSibling.setPriorSibling(node);
        }
        node.setNextSibling(this.nextSibling);

        this.nextSibling = node;
        node.setPriorSibling(this);
    }

    /**
     * Iterates over the children for this node.
     * 
     * @author dhagberg
     * 
     */
    private class ChildIterator implements Iterator<CharTrieNode> {

        private LinkedCharTrieNode parent;
        private LinkedCharTrieNode currentNode;

        protected ChildIterator(LinkedCharTrieNode parent) {
            this.parent = parent;
        }

        @Override
        public boolean hasNext() {
            if (currentNode == null) {
                return parent.firstChild != null;
            } else {
                return currentNode.nextSibling != null;
            }
        }

        @Override
        public CharTrieNode next() {
            if (currentNode == null) {
                currentNode = parent.firstChild;
            } else {
                currentNode = currentNode.nextSibling;
            }
            return currentNode;
        }

        /**
         * Remove is not supported.
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }

    }

}