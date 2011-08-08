package net.digitaltsunami.word.trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Concrete implementation of {@link PatternSearchStrategy} using a queue to
 * query the trie.
 * <p>
 * This strategy performs slower than the {@link PatternSearchQueueStrategy} and
 * should be used only for very deep tries that may cause a
 * {@link StackOverflowError} if using recursion.
 * 
 * @author dhagberg
 * 
 */
public class PatternSearchQueueStrategy implements PatternSearchStrategy {

    private static final CharTrieNode SENTINEL = new CharTrieNodeSentinel('\0');

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang
     * .String, net.digitaltsunami.word.trie.CharTrieNode, char)
     */
    @Override
    public Collection<String> findPattern(String pattern, CharTrieNode root, char wildcardChar) {
        int patLen = pattern.length();
        if (patLen < 1) {
            return Collections.emptyList();
        }
        char[] lcPattern = pattern.toLowerCase().toCharArray();
        Collection<String> matchingTerms = new ArrayList<String>();

        Queue<CharTrieNode> candidates = new LinkedList<CharTrieNode>();
        CharTrieNode currentNode = root;
        candidates.add(root);

        // Sentinel is used to delimit level changes.
        candidates.add(SENTINEL);

        int patPos = 0;
        int lastPatPos = patLen - 1;
        while (!candidates.isEmpty()) {
            currentNode = candidates.poll();
            if (currentNode == SENTINEL) {
                /*
                 * All nodes at the current level have been processed. Advance
                 * to next position and add a sentinel for the next level if not
                 * at the end.
                 */
                patPos++;
                if (patPos < patLen) {
                    candidates.add(SENTINEL);
                }
                continue;
            }

            if (patPos == lastPatPos) {
                /*
                 * At the end of the pattern. Check all children of current node
                 * for match of pattern. If a match and the node indicates the
                 * last character in a term, add to list.
                 */
                if (lcPattern[patPos] == wildcardChar) {
                    for (CharTrieNode child : currentNode) {
                        if (child.isTerminus()) {
                            matchingTerms.add(((CharTrieTerminusNode) child).getTerm());
                        }
                    }
                } else {
                    CharTrieNode child = currentNode.getChild(lcPattern[patPos]);
                    if (child != null) {
                        if (child.isTerminus()) {
                            matchingTerms.add(((CharTrieTerminusNode) child).getTerm());
                        }
                    }
                }
            } else {
                /*
                 * Examine current nodes children and add all nodes matching the
                 * pattern to the candidates queue for subsequent processing.
                 */
                if (lcPattern[patPos] == wildcardChar) {
                    for (CharTrieNode child : currentNode) {
                        candidates.add(child);
                    }
                } else {
                    CharTrieNode child = currentNode.getChild(lcPattern[patPos]);
                    if (child != null) {
                        candidates.add(child);
                    }
                }
            }
        }
        return matchingTerms;
    }

    /**
     * Empty implementation of {@link CharTrieNode} used to delimit levels
     * within the trie while adding nodes to the queue. All methods return
     * default value of null or false and should not be used.
     * 
     * @author dhagberg
     * 
     */
    private static class CharTrieNodeSentinel implements CharTrieNode {

        private char value;

        public CharTrieNodeSentinel(char value) {
            this.value = value;
        }

        @Override
        public CharTrieNode getChild(char value) {
            return null;
        }

        @Override
        public CharTrieNode addChild(char value) {
            return null;
        }

        @Override
        public boolean isTerminus() {
            return false;
        }

        @Override
        public boolean isRoot() {
            return false;
        }

        @Override
        public char getValue() {
            return value;
        }

        @Override
        public CharTrieNode getParent() {
            return null;
        }

        @Override
        public Iterator<CharTrieNode> iterator() {
            return null;
        }

    }

}
