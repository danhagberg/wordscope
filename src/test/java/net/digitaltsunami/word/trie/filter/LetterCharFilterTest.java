package net.digitaltsunami.word.trie.filter;

import static org.junit.Assert.*;

import net.digitaltsunami.word.trie.filter.CharFilter;
import net.digitaltsunami.word.trie.filter.LetterCharFilter;

import org.junit.Test;

public class LetterCharFilterTest {
    private LetterCharFilter filter = new LetterCharFilter();

    @Test
    public void testApplyWithLetter() {
        assertEquals('a', filter.apply('a'));
    }

    @Test
    public void testApplyWithNumber() {
        assertEquals(CharFilter.SKIP_CHAR, filter.apply('1'));
    }

    @Test
    public void testApplySpace() {
        assertEquals(' ', filter.apply(' '));
    }
}
