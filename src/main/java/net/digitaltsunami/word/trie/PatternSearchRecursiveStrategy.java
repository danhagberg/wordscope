package net.digitaltsunami.word.trie;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Concrete implementation of {@link PatternSearchStrategy} using recursion to
 * query the tree.
 * <p>
 * This strategy performs faster than the {@link PatternSearchQueueStrategy},
 * but may not be usable for tries with very deep entries. Normal terms or even
 * phrases should not be an issue, but if this were to be used for character
 * sequence matching, it is possible that a {@link StackOverflowError} could occur.
 * 
 * @author dhagberg
 * 
 */
public class PatternSearchRecursiveStrategy implements PatternSearchStrategy {

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang
     * .String, net.digitaltsunami.word.trie.CharTrieNode, char)
     */
    @Override
    public Collection<String> findPattern(String pattern, CharTrieNode root, char wildcardChar) {
        char[] lcPattern = pattern.toLowerCase().toCharArray();
        CharTrieNode currentNode = root;
        Collection<String> matchingTerms = new ArrayList<String>();
        findPattern(matchingTerms, lcPattern, 0, currentNode, wildcardChar);
        return matchingTerms;
    }

    /**
     * Recursive method to find all terms matching the pattern provided starting
     * at the pattern position and node provided. Results will be stored in the
     * provided list.
     * 
     * @param list
     *            Location to store all matching results.
     * @param pattern
     *            Mix of fixed and/or {@link #WILDCARD_CHAR} characters to
     *            match.
     * @param pos
     *            current character position with pattern.
     * @param node
     *            current node from which the children will be compared against
     *            the current character in the pattern.
     * @param wildcardChar
     *            Character value used as wildcard in query.
     */
    private void findPattern(Collection<String> list, char[] pattern, int pos, CharTrieNode node,
            char wildcardChar) {
        if (node == null) {
            return;
        }
        if (pos == pattern.length) {
            if (node.isTerminus()) {
                list.add(((CharTrieTerminusNode) node).getTerm());
            }
            return;
        }
        if (pattern[pos] == wildcardChar) {
            for (CharTrieNode child : node) {
                findPattern(list, pattern, pos + 1, child, wildcardChar);
            }
        } else {
            findPattern(list, pattern, pos + 1, node.getChild(pattern[pos]), wildcardChar);
        }
    }

}
