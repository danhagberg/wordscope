package net.digitaltsunami.word.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * Container for a character providing links to siblings, parents, and children.
 * 
 * @author dhagberg
 * 
 */
public class CharTrieNodeImpl implements CharTrieTerminusNode {
	private final char value;
	private CharTrieNodeImpl parent;
	private CharTrieNodeImpl firstChild;
	private CharTrieNodeImpl left;
	private CharTrieNodeImpl right;
	private boolean terminus;
	private final boolean root;

	/**
	 * Create new Node with the provided value.
	 * 
	 * @param parent
	 * @param value
	 */
	private CharTrieNodeImpl(CharTrieNode parent, char value) {
		this.parent = (CharTrieNodeImpl) parent;
		this.value = value;
		this.root = false;
	}

	/**
	 * Create new Node with the provided value.
	 * 
	 * @param value
	 */
	protected CharTrieNodeImpl(char value) {
		this.value = value;
		this.root = false;
	}
	
	/**
	 * Create new root node.  This node will not contain a value.
	 * 
	 * @param value
	 */
	protected CharTrieNodeImpl(boolean rootNode) {
		this.value = '\0';
		this.root = rootNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.digitaltsunami.util.CharTrieNode#getChild(char)
	 */
	@Override
	public CharTrieNode getChild(char value) {
		CharTrieNodeImpl sibling = firstChild;
		while (sibling != null) {
			if (value > sibling.value) {
				// Value > child, still could be found
				sibling = sibling.right;
			} else if (value == sibling.value) {
				// Value == child, return
				return sibling;
			} else {
				// Value < child, can't be found as they are in order
				return null;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.digitaltsunami.util.CharTrieNode#addChild(char)
	 */
	@Override
	public CharTrieNode addChild(char value) {
		CharTrieNodeImpl sibling = firstChild;
		while (sibling != null) {
			// if value > sibling
			if (value > sibling.value) {
				if (sibling.right != null) {
					// New value still less than remaining siblings,
					// continue searching.
					sibling = sibling.right;
				} else {
					// Reached the end of the sibling list. Add new node and
					// append to the last node in the list.
					CharTrieNodeImpl newNode = new CharTrieNodeImpl(this, value);
					sibling.right = newNode;
					newNode.left = sibling;
					return newNode;
				}
			} else if (value == sibling.value) {
				// Value == sibling, already exists, just return
				return sibling;
			} else {
				// Value < sibling, list is in order, so it won't be found.
				// Create new, insert it, and return the new node.
				CharTrieNodeImpl newNode = new CharTrieNodeImpl(this, value);
				newNode.left = sibling.left;
				newNode.right = sibling;
				sibling.left = newNode;
				if (newNode.left == null) {
					// New head of sibling list
					firstChild = newNode;
				} else {
					// Inserting between two nodes
					newNode.left.right = newNode;
				}
				return newNode;
			}
		}

		// First child node to be added, add and return.
		CharTrieNodeImpl newNode = new CharTrieNodeImpl(this, value);
		firstChild = newNode;
		return newNode;
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
	protected CharTrieNode getPriorSibling() {
		return this.left;
	}

	/**
	 * Return the right sibling for this node or null if first node.
	 * 
	 * @return the right sibling for this node or null if first node.
	 */
	protected CharTrieNode getNextSibling() {
		return this.right;
	}

	/**
	 * Return the first child node for this node or null if node has no
	 * children.
	 * 
	 * @return the first child node for this node or null if node has no
	 *         children.
	 */
	protected CharTrieNode getFirstChild() {
		return this.firstChild;
	}

	/*
	 * (non-Javadoc)
	 * @see net.digitaltsunami.util.CharTrieTerminusNode#getTerm()
	 */
	@Override
	public String getTerm() {
		int buffSize = 128;
		char[] buff = new char[buffSize];
		int buffPos = buffSize;
		
		CharTrieNodeImpl currNode = this;
		while (currNode.parent != null) {
			buffPos--;
			buff[buffPos] = currNode.value;
			currNode = currNode.parent;
		}
		
		char[] termBuff = Arrays.copyOfRange(buff, buffPos, buffSize);
		return new String(termBuff);
		
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
				+ (left == null ? "null" : left.value) + ", r="
				+ (right == null ? "null" : right.value) + ", terminus="
				+ terminus + "]";
	}

	/**
	 * Iterates over the children for this node.
	 * 
	 * @author dhagberg
	 * 
	 */
	private class ChildIterator implements Iterator<CharTrieNode> {

		private CharTrieNodeImpl parent;
		private CharTrieNodeImpl currentNode;

		protected ChildIterator(CharTrieNodeImpl parent) {
			this.parent = parent;
		}

		@Override
		public boolean hasNext() {
			if (currentNode == null) {
				return parent.firstChild != null;
			} else {
				return currentNode.right != null;
			}
		}

		@Override
		public CharTrieNode next() {
			if (currentNode == null) {
				currentNode = parent.firstChild;
			} else {
				currentNode = currentNode.right;
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