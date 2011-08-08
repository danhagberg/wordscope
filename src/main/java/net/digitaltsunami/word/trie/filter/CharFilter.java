package net.digitaltsunami.word.trie.filter;

public interface CharFilter {

    /**
     * Returned if filter is excluding term from processing.
     */
    public static final char SKIP_CHAR = 0;

    /**
     * Provides filter on input char based on specific criteria. May result in
     * no change, modified char, or char excluded from processing.
     * 
     * If no change, return original char. If the char should be excluded from
     * processing, return {@link TermFilter#SKIP_CHAR} to indicate that the
     * character should not be included.
     * 
     * @param term
     *            to filter.
     * @return term to process. If term is to be excluded from processing, an
     *         empty string must be returned. Use {@link TermFilter#SKIP_TERM}
     *         to exclude processing.
     */
    public char apply(char input);

}
