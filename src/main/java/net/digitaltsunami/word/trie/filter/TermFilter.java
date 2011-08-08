package net.digitaltsunami.word.trie.filter;

/**
 * Filter to allow altering or exclusion of term prior to processing. If
 * individual character filtering is needed, see {@link CharFilter}.
 * 
 * @author dhagberg
 * 
 */
public interface TermFilter {
    /**
     * Returned if filter is excluding term from processing.
     */
    public static final String SKIP_TERM = "";

    /**
     * Provides filter on term based on specific criteria. May result in no
     * change, modified term, or entire term excluded from processing.
     * 
     * If no change, return original term. If the term should be excluded from
     * processing, return {@link TermFilter#SKIP_TERM} to indicate term should
     * be excluded from entry.
     * 
     * @param term
     *            to filter.
     * @return term to process. If term is to be excluded from processing, an
     *         empty string must be returned. Use {@link TermFilter#SKIP_TERM}
     *         to exclude processing.
     */
    public String apply(String term);
}
