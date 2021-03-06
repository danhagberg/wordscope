/**
 * 
 */
package net.digitaltsunami.word.trie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.digitaltsunami.word.trie.CharTrie;
import net.digitaltsunami.word.trie.CharTrieNode;
import net.digitaltsunami.word.trie.event.TestNodeEventListener;
import net.digitaltsunami.word.trie.filter.CharFilter;
import net.digitaltsunami.word.trie.filter.TermFilter;

import org.junit.Test;

/**
 * Exercise the various capabilities of the {@link CharTrie}.
 * @author dhagberg
 * 
 */
public class CharTrieTest {

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#addTerm(java.lang.String)}.
     */
    @Test
    public void testAddTerm() {
        CharTrie dict = new CharTrie();
        dict.addTerm("test");
        Collection<String> terms = dict.findTerms("test");
        assertEquals(1, terms.size());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#getWordCount()}.
     */
    @Test
    public void testGetWordCount() {
        CharTrie dict = new CharTrie();
        dict.addTerm("testy");
        assertEquals(1, dict.getWordCount());
        dict.addTerm("rest");
        assertEquals(2, dict.getWordCount());
        // Duplicates should not be counted
        dict.addTerm("rest");
        assertEquals(2, dict.getWordCount());
        dict.addTerm("test");
        assertEquals(3, dict.getWordCount());
        dict.addTerm("tEst");
        assertEquals(3, dict.getWordCount());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findTerms(java.lang.String)}
     * .
     */
    @Test
    public void testFindTerms() {
        CharTrie dict = new CharTrie();
        dict.addTerm("test");
        dict.addTerm("testy");
        Collection<String> terms = dict.findTerms("test");
        assertEquals(2, terms.size());
    }

    /**
     * Ensure that the terms returned are the terms without any other characters
     * within them. Checks for both an exact term returned as well as a term
     * returned for a prefix.
     */
    @Test
    public void testReturnedTerm() {
        CharTrie dict = new CharTrie();
        dict.addTerm("test");
        Collection<String> terms = dict.findTerms("test");
        assertEquals(1, terms.size());
        String[] termArr = terms.toArray(new String[] {});
        terms = dict.findTerms("te");
        assertEquals(1, terms.size());
        termArr = terms.toArray(new String[] {});
        assertEquals("test", termArr[0]);

    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findTerms(java.lang.String)}
     * .
     */
    @Test
    public void testFindTermsEmpty() {
        CharTrie dict = new CharTrie();
        dict.addTerm("test");
        dict.addTerm("testy");
        Collection<String> terms = dict.findTerms("x");
        assertEquals(0, terms.size());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findPattern(java.lang.String)}
     * Test with initial pattern character as a wildcard. Uses default
     * {@link PatternSearchStrategy}.
     */
    @Test
    public void testFindPattern() {
        CharTrie dict = new CharTrie();
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
     * Test with wildcard different than the default value.
     */
    @Test
    public void testFindPatternOverrideWildcard() {
        CharTrie dict = new CharTrie();
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
        assertEquals('+', dict.getWildcardChar());
        
        Collection<String> terms = dict.findPattern("f+a+");
        assertEquals(4, terms.size());
    
        assertTrue(terms.contains("flag"));
        assertTrue(terms.contains("flan"));
        assertTrue(terms.contains("flat"));
        assertTrue(terms.contains("frag"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findPattern(java.lang.String)}
     * Test with initial pattern character as a wildcard. Test to ensure that
     * change to strategy implementation is recognized.
     */
    @Test
    public void testFindPatternChangeStrategy() {
        CharTrie dict = new CharTrie();
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("fast");
        dict.addTerm("pang");
        dict.addTerm("plan");

        // Test with default strategy
        Collection<String> terms = dict.findPattern("~ang");
        assertEquals(2, terms.size());

        assertTrue(terms.contains("fang"));
        assertTrue(terms.contains("pang"));
        dict.setPatternSearchStrategy(new PatternSearchStrategy() {
            
            @Override
            public Collection<String> findPattern(String pattern, CharTrieNode root, char wildcardChar) {
                List<String> results = new ArrayList<String>();
                results.add("fang");
                return results;
            }
        });
        // Test with override strategy
        terms = dict.findPattern("~ang");
        assertEquals(1, terms.size());

        assertTrue(terms.contains("fang"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findTerms(java.lang.String)}
     * .
     */
    @Test
    public void testAddTermsMixedCase() {
        CharTrie dict = new CharTrie();
        dict.addTerm("Test");
        dict.addTerm("Absolute");
        dict.addTerm("tesTer");
        Collection<String> terms = dict.findTerms("test");
        assertEquals(2, terms.size());
        assertTrue(terms.contains("test"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findTerms(java.lang.String)}
     * .
     */
    @Test
    public void testFindTermsMixedCase() {
        CharTrie dict = new CharTrie();
        dict.addTerm("test");
        dict.addTerm("tbsolute");
        dict.addTerm("tester");
        Collection<String> terms = dict.findTerms("Test");
        assertEquals(2, terms.size());
        terms = dict.findTerms("tesT");
        assertEquals(2, terms.size());
        assertTrue(terms.contains("test"));
    }

    /**
     * Find terms where they diverge. Ensure that irrelevant terms are not
     * returned.
     */
    @Test
    public void testFindRelevantTerms() {
        CharTrie dict = new CharTrie();
        dict.addTerm("act");
        dict.addTerm("acid");
        dict.addTerm("ace");
        dict.addTerm("fit");
        dict.addTerm("fight");
        dict.addTerm("aft");
        dict.addTerm("raft");

        Collection<String> terms = dict.findTerms("af");
        assertEquals(1, terms.size());

        terms = dict.findTerms("a");
        assertEquals(4, terms.size());

        terms = dict.findTerms("fi");
        assertEquals(2, terms.size());
    }

    /**
     * Test using a larger set of words in various orders. Ensure that all words
     * were added by checking term count for each starting letter.
     */
    @Test
    public void testAddTermsOutOfOrder() {
        CharTrie dict = new CharTrie();
        dict.addTerm("entity");
        dict.addTerm("physical_entity");
        dict.addTerm("abstraction");
        dict.addTerm("thing");
        dict.addTerm("object");
        dict.addTerm("whole");
        dict.addTerm("congener");
        dict.addTerm("living_thing");
        dict.addTerm("organism");
        dict.addTerm("benthos");
        dict.addTerm("dwarf");
        dict.addTerm("heterotroph");
        dict.addTerm("parent");
        dict.addTerm("life");
        dict.addTerm("biont");
        dict.addTerm("cell");
        dict.addTerm("causal_agent");
        dict.addTerm("person");
        dict.addTerm("animal");
        dict.addTerm("plant");
        dict.addTerm("native");
        dict.addTerm("natural_object");
        dict.addTerm("substance");
        dict.addTerm("substance");
        dict.addTerm("matter");
        dict.addTerm("food");
        dict.addTerm("nutrient");
        dict.addTerm("artifact");
        dict.addTerm("article");
        dict.addTerm("psychological_feature");
        dict.addTerm("cognition");
        dict.addTerm("motivation");
        dict.addTerm("attribute");
        dict.addTerm("state");
        dict.addTerm("feeling");
        dict.addTerm("location");
        dict.addTerm("shape");
        dict.addTerm("time");
        dict.addTerm("space");
        dict.addTerm("absolute_space");
        dict.addTerm("phase_space");
        dict.addTerm("event");
        dict.addTerm("process");
        dict.addTerm("act");
        dict.addTerm("group");
        dict.addTerm("relation");
        dict.addTerm("possession");
        dict.addTerm("social_relation");
        dict.addTerm("communication");
        dict.addTerm("measure");

        Collection<String> terms = dict.findTerms("a");
        assertEquals(7, terms.size());
        terms = dict.findTerms("b");
        assertEquals(2, terms.size());
        terms = dict.findTerms("c");
        assertEquals(5, terms.size());
        terms = dict.findTerms("c");
        assertEquals(5, terms.size());
        terms = dict.findTerms("d");
        assertEquals(1, terms.size());
        terms = dict.findTerms("e");
        assertEquals(2, terms.size());
        terms = dict.findTerms("e");
        assertEquals(2, terms.size());
        terms = dict.findTerms("f");
        assertEquals(2, terms.size());
        terms = dict.findTerms("h");
        assertEquals(1, terms.size());
        terms = dict.findTerms("l");
        assertEquals(3, terms.size());
        terms = dict.findTerms("m");
        assertEquals(3, terms.size());
        terms = dict.findTerms("n");
        assertEquals(3, terms.size());
        terms = dict.findTerms("o");
        assertEquals(2, terms.size());
        terms = dict.findTerms("p");
        assertEquals(8, terms.size());
        terms = dict.findTerms("r");
        assertEquals(1, terms.size());
        terms = dict.findTerms("s");
        assertEquals(5, terms.size());
        terms = dict.findTerms("t");
        assertEquals(2, terms.size());
        terms = dict.findTerms("w");
        assertEquals(1, terms.size());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findChildSequence(net.digitaltsunami.word.trie.CharTrieNode, java.lang.String)}
     * .
     */
    @Test
    public void testFindChildSequence() {
        CharTrie dict = new CharTrie();
        dict.addTerm("test");
        dict.addTerm("testy");
        List<CharTrieNode> seq = dict.findSequence("te");
        CharTrieNode lastNode = seq.get(seq.size() - 1);
        assertEquals('e', lastNode.getValue());
        seq = dict.findChildSequence(lastNode, "st");
        lastNode = seq.get(seq.size() - 1);
        assertEquals('t', lastNode.getValue());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findChildSequence(net.digitaltsunami.word.trie.CharTrieNode, java.lang.String)}
     * .
     */
    @Test
    public void testFindChildSequenceEmpty() {
        CharTrie dict = new CharTrie();
        dict.addTerm("test");
        dict.addTerm("testy");
        List<CharTrieNode> seq = dict.findSequence("te");
        CharTrieNode lastNode = seq.get(seq.size() - 1);
        assertEquals('e', lastNode.getValue());
        seq = dict.findChildSequence(lastNode, "x");
        assertEquals(0, seq.size());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findSequence(java.lang.String)}
     * .
     */
    @Test
    public void testFindSequence() {
        CharTrie dict = new CharTrie();
        dict.addTerm("test");
        dict.addTerm("testy");
        List<CharTrieNode> seq = dict.findSequence("te");
        CharTrieNode lastNode = seq.get(seq.size() - 1);
        assertEquals('e', lastNode.getValue());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findSequence(java.lang.String)}
     * .
     */
    @Test
    public void testFindSequenceEmpty() {
        CharTrie dict = new CharTrie();
        dict.addTerm("test");
        dict.addTerm("testy");
        List<CharTrieNode> seq = dict.findSequence("x");
        assertEquals(0, seq.size());
    }

    /**
     * Test that the character filter exclusion is applied.
     */
    @Test
    public void testCharFilterExclude() {
        CharFilter charFilter = new CharFilter() {
            @Override
            public char apply(char input) {
                return input == 'e' ? CharFilter.SKIP_CHAR : input;
            }
        };
        CharTrie dict = new CharTrie(charFilter);
        dict.addTerm("test");
        dict.addTerm("testy");
        Collection<String> terms = dict.findTerms("tst");
        assertEquals(2, terms.size());
    }

    /**
     * Test that the character filter exclusion is applied and if all characters
     * are removed, then no term is added.
     */
    @Test
    public void testCharFilterExcludeAllChars() {
        CharFilter charFilter = new CharFilter() {
            @Override
            public char apply(char input) {
                return CharFilter.SKIP_CHAR;
            }
        };
        CharTrie dict = new CharTrie(charFilter);
        dict.addTerm("test");
        dict.addTerm("testy");
        assertEquals(0, dict.getWordCount());
    }

    /**
     * Test that the character filter modification is applied.
     */
    @Test
    public void testCharFilterModify() {
        CharFilter charFilter = new CharFilter() {
            @Override
            public char apply(char input) {
                return input == 'e' ? 'x' : input;
            }
        };
        CharTrie dict = new CharTrie(charFilter);
        dict.addTerm("test");
        dict.addTerm("testy");
        Collection<String> terms = dict.findTerms("txst");
        assertEquals(2, terms.size());
    }

    /**
     * Test that the term filter exclusion is applied.
     */
    @Test
    public void testTermFilterExclude() {
        TermFilter termFilter = new TermFilter() {
            @Override
            public String apply(String input) {
                return input.endsWith("y") ? TermFilter.SKIP_TERM : input;
            }
        };
        CharTrie dict = new CharTrie(termFilter);
        dict.addTerm("test");
        dict.addTerm("testy");
        Collection<String> terms = dict.findTerms("test");
        assertEquals(1, terms.size());
    }

    /**
     * Test that the term filter modifications are applied.
     */
    @Test
    public void testTermFilterModify() {
        TermFilter termFilter = new TermFilter() {
            @Override
            public String apply(String input) {
                return input.replaceFirst("t", "z");

            }
        };
        CharTrie dict = new CharTrie(termFilter);
        dict.addTerm("test");
        dict.addTerm("testy");
        Collection<String> terms = dict.findTerms("zest");
        assertEquals(2, terms.size());
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#contains(String)}.
     */
    @Test
    public void testContains() {
        CharTrie dict = new CharTrie();
        dict.addTerm("establish");
        dict.addTerm("rest");
        dict.addTerm("test");
        dict.addTerm("testy");
        dict.addTerm("testimony");
        assertTrue(dict.contains("test"));
        assertTrue(dict.contains("rest"));
        assertTrue(dict.contains("testy"));
        // Not a terminus
        assertFalse(dict.contains("tes"));
        // Sequence not found
        assertFalse(dict.contains("xyz"));
    }

    @Test
    public void testGetAllTerms() {
        CharTrie dict = new CharTrie();
        List<String> terms = new ArrayList<String>();
        terms.add("a");
        terms.add("establish");
        terms.add("rest");
        terms.add("i");
        terms.add("ate");
        terms.add("test");
        terms.add("testy");
        terms.add("testimony");

        // Add a set of terms to the dictionary.
        for (String term : terms) {
            dict.addTerm(term);
        }

        // Remove all terms from original set that are returned by getAllTerms.
        Collection<String> allTerms = dict.getAllTerms();
        terms.removeAll(allTerms);

        assertEquals(0, terms.size());
    }

    /**
     * Test adding of a character listener.
     */
    @Test
    public void testAddCharacterListener() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addCharacterAddedListener(listener);
        // Test that listener was invoked for all 4 characters
        dict.addTerm("tale");
        assertEquals(4, listener.eventCount);
        // Test that listener was invoked for all 4 characters even though they
        // already existed.
        dict.addTerm("tale");
        assertEquals(8, listener.eventCount);
        // Test that listener was invoked for all 4 characters even though the
        // only the last is different.
        dict.addTerm("talk");
        assertEquals(12, listener.eventCount);
    }

    /**
     * Test adding of a terminus character listener.
     */
    @Test
    public void testAddTerminusCharacterListener() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addCharacterTerminusAddedListener(listener);
        // Test that the listener was invoked for the terminus.
        dict.addTerm("tale");
        assertEquals(1, listener.eventCount);
        // Test that the listener was invoked for the terminus even thought it
        // already existed.
        dict.addTerm("tale");
        assertEquals(2, listener.eventCount);
        // Test that the listener was invoked for the terminus which only the
        // last is different.
        dict.addTerm("talk");
        assertEquals(3, listener.eventCount);
        // Test that the listener was invoked for the terminus which extended
        // current entry.
        dict.addTerm("talked");
        assertEquals(4, listener.eventCount);
    }

    /**
     * Test adding of a single character term with a terminus character
     * listener.
     */
    @Test
    public void testAddTerminusCharacterListenerTestSingleChar() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addCharacterTerminusAddedListener(listener);
        // Test that the listener was invoked for the terminus.
        dict.addTerm("t");
        assertEquals(1, listener.eventCount);
    }

    /**
     * Test adding of a terminus char listener when existing nodes are
     * converted.
     */
    @Test
    public void testAddTerminusCharacterListenerConversions() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addCharacterTerminusAddedListener(listener);
        // Test that listener was invoked for the last node added.
        dict.addTerm("talked");
        assertEquals(1, listener.eventCount);
        // Test that listener was invoked as the new k node is now a terminus
        // node
        dict.addTerm("talk");
        assertEquals(2, listener.eventCount);
        // Test that listener was invoked even though the entry already existed.
        dict.addTerm("talk");
        assertEquals(3, listener.eventCount);
    }

    /**
     * Test adding of a node listener.
     */
    @Test
    public void testAddNodeListener() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addNodeAddedListener(listener);
        // Test that listener was invoked for each of the 4 nodes added.
        dict.addTerm("test");
        assertEquals(4, listener.eventCount);
        // Test that listener was not invoked as no new nodes were added.
        dict.addTerm("test");
        assertEquals(4, listener.eventCount);
        // Test that listener was invoked for each of the 2 new nodes (x and t)
        // added.
        dict.addTerm("text");
        assertEquals(6, listener.eventCount);
    }

    /**
     * Test adding of a terminus node listener.
     */
    @Test
    public void testAddTerminusNodeListener() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addTerminusNodeAddedListener(listener);
        // Test that listener was invoked for the last node added.
        dict.addTerm("test");
        assertEquals(1, listener.eventCount);
        // Test that listener was not invoked as no new terminus nodes were
        // added.
        dict.addTerm("test");
        assertEquals(1, listener.eventCount);
        // Test that listener was invoked for the new terminus added ('t').
        dict.addTerm("text");
        assertEquals(2, listener.eventCount);
    }

    /**
     * Test adding of a terminus node listener when entering a single character
     * term.
     */
    @Test
    public void testAddTerminusNodeListenerWithSingleChar() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addTerminusNodeAddedListener(listener);
        // Test that listener was invoked for the last node added.
        dict.addTerm("t");
        assertEquals(1, listener.eventCount);
        // Test that listener was not invoked as no new terminus nodes were
        // added.
        dict.addTerm("t");
        assertEquals(1, listener.eventCount);
    }

    /**
     * Test adding of a terminus node listener when existing nodes are
     * converted.
     */
    @Test
    public void testAddTerminusNodeListenerConversions() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addTerminusNodeAddedListener(listener);
        // Test that listener was invoked for the last node added.
        dict.addTerm("talked");
        assertEquals(1, listener.eventCount);
        // Test that listener was invoked as the new k node is now a terminus
        // node
        dict.addTerm("talk");
        assertEquals(2, listener.eventCount);
        // Test that listener was not invoked as no new terminus nodes were
        // added.
        dict.addTerm("talk");
        assertEquals(2, listener.eventCount);
    }

    /**
     * Test removing of a character listener.
     */
    @Test
    public void testRemoveCharacterListener() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addCharacterAddedListener(listener);
        // Test that listener was invoked for all 4 characters
        dict.addTerm("tale");
        assertEquals(4, listener.eventCount);
        dict.removeCharacterAddedListener(listener);
        // Test that listener was not invoked for any of the 4 characters
        dict.addTerm("lake");
        assertEquals(4, listener.eventCount);
    }

    /**
     * Test removing of a terminus character listener.
     */
    @Test
    public void testRemoveTerminusCharacterListener() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addCharacterTerminusAddedListener(listener);
        // Test that the listener was invoked for the terminus.
        dict.addTerm("tale");
        assertEquals(1, listener.eventCount);
        dict.removeCharacterTerminusAddedListener(listener);
        // Test that listener was not invoked for any of the 4 characters
        dict.addTerm("lake");
        assertEquals(1, listener.eventCount);
    }

    /**
     * Test removal of a node listener.
     */
    @Test
    public void testRemoveNodeListener() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addNodeAddedListener(listener);
        // Test that listener was invoked for each of the 4 nodes added.
        dict.addTerm("test");
        assertEquals(4, listener.eventCount);
        dict.removeNodeAddedListener(listener);
        // Test that listener was not invoked.
        dict.addTerm("lake");
        assertEquals(4, listener.eventCount);
    }

    /**
     * Test removal of a terminus node listener.
     */
    @Test
    public void testRemoveTerminusNodeListener() {
        TestNodeEventListener listener = new TestNodeEventListener();
        CharTrie dict = new CharTrie();
        dict.addTerminusNodeAddedListener(listener);
        // Test that listener was invoked for the last node added.
        dict.addTerm("test");
        assertEquals(1, listener.eventCount);
        dict.removeTerminusNodeAddedListener(listener);
        // Test that listener was not invoked as no new terminus nodes were
        // added.
        dict.addTerm("lake");
        assertEquals(1, listener.eventCount);
    }
    
    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findAllTermsOfLength(int)}
     * Test with multiple words within dictionary of tested length.
     */
    @Test
    public void testAllTermsOfLength() {
        CharTrie dict = new CharTrie();
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("plan");
        dict.addTerm("epsilon");
        dict.addTerm("trachea");
        dict.addTerm("fa");
        dict.addTerm("ace");
        dict.addTerm("acid");
        dict.addTerm("accent");

        Collection<String> terms = dict.findAllTermsOfLength(4);
        assertEquals(5, terms.size());

        assertTrue(terms.contains("flag"));
        assertTrue(terms.contains("frag"));
        assertTrue(terms.contains("fang"));
        assertTrue(terms.contains("plan"));
        assertTrue(terms.contains("acid"));
    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findAllTermsOfLength(int)}
     * Test with no words within dictionary of tested length.
     */
    @Test
    public void testAllTermsOfLengthNoMatches() {
        CharTrie dict = new CharTrie();
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("plan");
        dict.addTerm("epsilon");
        dict.addTerm("trachea");
        dict.addTerm("fa");
        dict.addTerm("ace");
        dict.addTerm("acid");
        dict.addTerm("accent");

        Collection<String> terms = dict.findAllTermsOfLength(1);
        assertEquals(0, terms.size());
    }
    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findAllTermsOfLength(int, int)}
     * Test with multiple words within dictionary of tested length.
     */
    @Test
    public void testAllTermsOfLengthRange() {
        CharTrie dict = new CharTrie();
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("plan");
        dict.addTerm("epsilon");
        dict.addTerm("trachea");
        dict.addTerm("a");
        dict.addTerm("fa");
        dict.addTerm("ace");
        dict.addTerm("acid");
        dict.addTerm("accent");

        // Test tight range
        Collection<String> terms = dict.findAllTermsOfLength(3,4);
        assertEquals(6, terms.size());

        assertTrue(terms.contains("flag"));
        assertTrue(terms.contains("frag"));
        assertTrue(terms.contains("fang"));
        assertTrue(terms.contains("plan"));
        assertTrue(terms.contains("ace"));
        assertTrue(terms.contains("acid"));
        
        // Test range of all words up to 4 in length inclusive
        terms = dict.findAllTermsOfLength(1,4);
        assertEquals(8, terms.size());

        assertTrue(terms.contains("flag"));
        assertTrue(terms.contains("frag"));
        assertTrue(terms.contains("fang"));
        assertTrue(terms.contains("plan"));
        assertTrue(terms.contains("a"));
        assertTrue(terms.contains("fa"));
        assertTrue(terms.contains("ace"));
        assertTrue(terms.contains("acid"));
        
        // Test range of words at the max length
        terms = dict.findAllTermsOfLength(7,8);
        assertEquals(3, terms.size());
        assertTrue(terms.contains("flagrant"));
        assertTrue(terms.contains("epsilon"));
        assertTrue(terms.contains("trachea"));
        
        // Test range enclosing all word lengths
        terms = dict.findAllTermsOfLength(1,100);
        assertEquals(12, terms.size());

    }

    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findAllTermsOfLength(int,int)}
     * Test with no words within dictionary of tested length.
     */
    @Test
    public void testAllTermsOfLengthRangeNoMatches() {
        CharTrie dict = new CharTrie();
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("plan");
        dict.addTerm("epsilon");
        dict.addTerm("trachea");
        dict.addTerm("ace");
        dict.addTerm("acid");
        dict.addTerm("accent");

        // Test range smaller than min word length.
        Collection<String> terms = dict.findAllTermsOfLength(1,2);
        assertEquals(0, terms.size());
        
        // Test range greater than max word length.
        terms = dict.findAllTermsOfLength(9,12);
        assertEquals(0, terms.size());
    }
    /**
     * Test method for
     * {@link net.digitaltsunami.word.trie.CharTrie#findAllTermsOfLength(int,int)}
     * Test with range values too small or out of order.
     */
    @Test
    public void testAllTermsOfLengthRangeTooSmall() {
        CharTrie dict = new CharTrie();
        dict.addTerm("a");
        dict.addTerm("flag");
        dict.addTerm("flagrant");
        dict.addTerm("frag");
        dict.addTerm("fang");
        dict.addTerm("plan");
        dict.addTerm("epsilon");
        dict.addTerm("trachea");
        dict.addTerm("ace");
        dict.addTerm("acid");
        dict.addTerm("accent");

        // Test range smaller than min word length.
        Collection<String> terms = dict.findAllTermsOfLength(0,0);
        assertEquals(0, terms.size());
        
        // Test max lenght smaller than min lenght.  Should result in lenght of 3,3
        terms = dict.findAllTermsOfLength(3,2);
        assertEquals(1, terms.size());
    }
}