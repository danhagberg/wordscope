package net.digitaltsunami.word.util;

import static org.junit.Assert.*;

import net.digitaltsunami.word.util.CharFilter;
import net.digitaltsunami.word.util.LetterCharFilter;

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

}
