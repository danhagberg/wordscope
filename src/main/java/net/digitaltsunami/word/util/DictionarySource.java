package net.digitaltsunami.word.util;

import java.util.Collection;

/**
 * Contract for a dictionary source providing a list of terms.
 * @author dhagberg
 *
 */
public interface DictionarySource {

    /**
     * Perform source specific actions necessary to ready the dictionary source
     * for returning terms.
     * @throws WordscopeException
     */
    public void open() throws WordscopeException;

    /**
     * Perform source specific actions necessary to close the dictionary source.
     * @throws WordscopeException
     */
    public void close() throws WordscopeException;

    /**
     * Returns the next term from the dictionary source or null if all terms
     * exhausted.
     * 
     * @return next term or null indicating end of terms.
     * @throws WordscopeException
     */
    public String getNextTerm() throws WordscopeException;

    /**
     * Returns the next N number of terms as a collection where 0 <= N <=
     * maxNumberOfTerms. A returned collection of size less than
     * maxNumberOfTerms indicates that the term source has been exhausted and no
     * more terms will be returned.
     * 
     * @param maxNumberOfTerms
     * @return a collection of terms not to exceed a size of maxNumberOfTerms.
     * @throws WordscopeException
     */
    public Collection<String> getNextTerms(int maxNumberOfTerms) throws WordscopeException;
}
