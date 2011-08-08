/**
 * 
 */
package net.digitaltsunami.word.trie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

/**
 * Test pattern variations return proper matches when using recursive strategy.
 * @author dhagberg
 *
 */
public class PatternSearchRecursiveStrategyTest {
    private final PatternSearchStrategy strategy = new PatternSearchRecursiveStrategy();

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with all wildcard pattern so that all terms of pattern length are
     * returned.
     */
    @Test
    public void testFindPatternAllWildcards() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");
        dict.addTerm("act");
    
        Collection<String> terms = dict.findPattern("~~~~");
        assertEquals(4, terms.size());
    
        assertTrue(terms.contains("flag"));
        assertTrue(terms.contains("fast"));
        assertTrue(terms.contains("pang"));
        assertTrue(terms.contains("plan"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with empty pattern.
     */
    @Test
    public void testFindPatternEmptyPattern() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("plan");
    
        Collection<String> terms = dict.findPattern("");
        assertEquals(0, terms.size());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test simple non-wildcard pattern
     */
    @Test
    public void testFindPatternExact() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");
    
        Collection<String> terms = dict.findPattern("flag");
        assertEquals(1, terms.size());
    
        assertTrue(terms.contains("flag"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test simple non-wildcard pattern for a pattern that does not exist.
     */
    @Test
    public void testFindPatternExactNotFound() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");
    
        Collection<String> terms = dict.findPattern("frat");
        assertEquals(0, terms.size());
    
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with wildcard at end of pattern
     */
    @Test
    public void testFindPatternFinalWildcard() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("flan");
        dict.addTerm("flat");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");
    
        Collection<String> terms = dict.findPattern("fla~");
        assertEquals(3, terms.size());
    
        assertTrue(terms.contains("flag"));
        assertTrue(terms.contains("flan"));
        assertTrue(terms.contains("flat"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with initial pattern character as a wildcard
     */
    @Test
    public void testFindPatternInitialWildcard() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");
    
        Collection<String> terms = dict.findPattern("~ang");
        assertEquals(2, terms.size());
    
        assertTrue(terms.contains("fang"));
        assertTrue(terms.contains("pang"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with wildcard within the pattern
     */
    @Test
    public void testFindPatternInternalWildcard() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");
    
        Collection<String> terms = dict.findPattern("f~ag");
        assertEquals(2, terms.size());
    
        assertTrue(terms.contains("frag"));
        assertTrue(terms.contains("flag"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with wildcard at multiple locations within pattern.
     */
    @Test
    public void testFindPatternMultipleWildcards() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("flan");
        dict.addTerm("flat");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");
    
        Collection<String> terms = dict.findPattern("f~a~");
        assertEquals(4, terms.size());
    
        assertTrue(terms.contains("flag"));
        assertTrue(terms.contains("flan"));
        assertTrue(terms.contains("flat"));
        assertTrue(terms.contains("frag"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with single character pattern with no wildcard.
     */
    @Test
    public void testFindPatternSingleChar() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("plan");
        dict.addTerm("a");
        dict.addTerm("i");
    
        Collection<String> terms = dict.findPattern("a");
        assertEquals(1, terms.size());
    
        assertTrue(terms.contains("a"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with single character pattern with no wildcard where the pattern is
     * not found.
     */
    @Test
    public void testFindPatternSingleCharNotFound() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("plan");
        dict.addTerm("a");
        dict.addTerm("i");
    
        Collection<String> terms = dict.findPattern("e");
        assertEquals(0, terms.size());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with single character pattern with wildcard.
     */
    @Test
    public void testFindPatternSingleCharWildcard() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("plan");
        dict.addTerm("a");
        dict.addTerm("i");
    
        Collection<String> terms = dict.findPattern("~");
        assertEquals(2, terms.size());
    
        assertTrue(terms.contains("a"));
        assertTrue(terms.contains("i"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with single character pattern withno wildcard where the pattern is
     * not found.
     */
    @Test
    public void testFindPatternSingleCharWildcardNotFound() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("plan");
    
        Collection<String> terms = dict.findPattern("~");
        assertEquals(0, terms.size());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test simple non-wildcard pattern where no matches for the pattern are
     * found.
     */
    @Test
    public void testFindPatternWildcardNotFound() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");
    
        Collection<String> terms = dict.findPattern("f~it");
        assertEquals(0, terms.size());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.PatternSearchStrategy#findPattern(java.lang.String)}
     * Test with wildcard different than the default value.
     */
    @Test
    public void testFindPatternOverrideWildcard() {
        CharTrie dict = new CharTrie();
        dict.setPatternSearchStrategy(strategy);
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("flan");
        dict.addTerm("flat");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");
    
        dict.setWildcardChar('+');
        Collection<String> terms = dict.findPattern("f+a+");
        assertEquals(4, terms.size());
    
        assertTrue(terms.contains("flag"));
        assertTrue(terms.contains("flan"));
        assertTrue(terms.contains("flat"));
        assertTrue(terms.contains("frag"));
    }

}
