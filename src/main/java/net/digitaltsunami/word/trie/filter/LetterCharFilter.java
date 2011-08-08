/**
 * 
 */
package net.digitaltsunami.word.trie.filter;

/**
 * Basic filter to include only characters determined to be letters. Uses
 * {@link Character#isLetter(char)} to determine validity.
 * 
 * @author dhagberg
 * 
 */
public class LetterCharFilter implements CharFilter {

    /**
     * Returns character if it {@link Character#isLetter(char)} returns true,
     * otherwise it will return {@link CharFilter#SKIP_CHAR}.
     */
    @Override
    public char apply(char input) {
        return Character.isLetter(input) | input == ' ' ? input : CharFilter.SKIP_CHAR;
    }

}
