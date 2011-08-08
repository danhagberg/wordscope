package net.digitaltsunami.word.trie;

import java.util.Collection;

/**
 * Strategy to query a {@link CharTrie} and return all matches for a pattern
 * consisting of fixed and wildcard characters.
 * 
 * @author dhagberg
 * 
 */
public interface PatternSearchStrategy {

    /**
     * Find and return all terms within the dictionary matching the provided
     * pattern. The pattern provided is currently limited to fixed and wildcard
     * characters for a specific position. For example, with the wildcard char
     * of ~ (tilde) provided via the argument, a~~m will return all terms that
     * begin with a, end with m and are exactly four characters in length.
     * <p>
     * The query will start at the provided root of the dictionary.
     * 
     * @param pattern
     *            Mix of fixed and/or wildcard characters to match.
     * @param root
     *            Root of trie being searched for pattern.
     * @param wildcardChar
     *            Character value used as wildcard in query.
     * @return A set of all terms matching the provided pattern. If no terms
     *         found, an empty set will be returned.
     */
    public Collection<String> findPattern(String pattern, CharTrieNode root, char wildcardChar);

}