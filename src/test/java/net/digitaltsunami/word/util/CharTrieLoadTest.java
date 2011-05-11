package net.digitaltsunami.word.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import net.digitaltsunami.word.util.CharTrie;
import net.digitaltsunami.word.util.LetterCharFilter;

import com.sun.tools.corba.se.idl.InvalidArgument;

public class CharTrieLoadTest {

	private static HashMap<String, String> wordList = new HashMap<String, String>();
	private static CharTrie dict;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			int maxWordCount = Integer.MAX_VALUE;
			BufferedReader stdin;
			stdin = new BufferedReader(
					new InputStreamReader(System.in, "UTF-8"));
			System.out.print("Default is Trie.  Enter m to use map: ");
			String dictChoice = stdin.readLine();
			if (!dictChoice.equalsIgnoreCase("m")) {
				dict = new CharTrie(new LetterCharFilter());
			}

			for (String location : args) {
				loadGoogleWords(location, maxWordCount);
				// loadDictionary(location, maxWordCount);
			}
			String queryPrefix;
			while (true) {
				System.out.print("Enter term -ALL- for all or qq to end: ");
				queryPrefix = stdin.readLine();
				if (queryPrefix.equals("qq")) {
					return;
				}

				query(queryPrefix);
				// contains(queryPrefix);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void query(String queryPrefix) {
		Collection<String> terms = null;
		long start = System.nanoTime();
		if (dict == null) {
			if (queryPrefix.equals("-ALL-")) {
				terms = new ArrayList<String>(wordList.keySet());
				Collections.sort((ArrayList<String>)terms);
			} else {
				terms = new ArrayList<String>();
				String word = wordList.get(queryPrefix);
				if (word != null) {

					terms.add(word);
				}
			}
		} else {
			if (queryPrefix.equals("-ALL-")) {
				terms = dict.getAllTerms();
			} else {
				terms = dict.findTerms(queryPrefix);
			}
		}
		long stop = System.nanoTime();
		for (String term : terms) {
			System.out.println(term);
		}
		System.out.println("Number of words: " + terms.size() + " in "
				+ (stop - start) / 1000000.0 + "ms");

	}

	private static void contains(String term) {
		long start = System.nanoTime();
		boolean contains = false;
		if (dict == null) {
			contains = wordList.containsKey(term);
		} else {
			contains = dict.contains(term);
		}
		long stop = System.nanoTime();
		System.out.println("Contains term: " + contains + " in "
				+ (stop - start) / 1000000.0 + "ms");

	}

	public static void loadDictionary(String fileName, int maxWordCount) {
		int wordCount = 0;
		File wordNetFile = new File(fileName);
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(wordNetFile));
			String line = null;
			long start = System.currentTimeMillis();
			while ((line = in.readLine()) != null && wordCount < maxWordCount) {
				if (line.charAt(0) == ' ') {
					// File comments, skip
					continue;
				}
				// Format up to word is %d %d %c %d WORD
				int delPos = 0;
				for (int i = 0; i < 4; i++) {
					delPos = line.indexOf(' ', delPos) + 1;
				}
				String word = line.substring(delPos, line.indexOf(' ', delPos));
				addWordToDict(word);
				wordCount++;
			}
			long stop = System.currentTimeMillis();
			System.out.println("Number of words: " + wordCount + " in "
					+ (stop - start) + "ms");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected static void addWordToDict(String word) {
		if (word == null) {
			throw new IllegalArgumentException("Word cannot be null");
		}
		if (dict == null) {
			word = word.toLowerCase();
			wordList.put(word, word);
		} else {
			dict.addTerm(word);
		}
	}

	public static void loadGoogleWords(String fileName, int maxWordCount) {
		int wordCount = 0;
		File googleFile = new File(fileName);
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					googleFile), "UTF-8"));
			String line = null;
			long start = System.currentTimeMillis();
			while ((line = in.readLine()) != null && wordCount < maxWordCount) {
				if (line.charAt(0) == ' ') {
					// File comments, skip
					continue;
				}
				if (!Character.isLetter(line.charAt(0))) {
					// Dont want all of the numbers and special chars
					continue;

				}
				// Format up to word is %d %d %c %d WORD
				int delPos = 0;
				String word = line.substring(0, line.indexOf('\t'));
				// wordList.add(word);
				addWordToDict(word);
				wordCount++;
			}
			long stop = System.currentTimeMillis();
			System.out
					.printf("Number of entries: %d\tNumber of unique entries: %d\tTime: %dms\n",
							wordCount,
							(dict == null) ? wordList.size() : dict
									.getWordCount(), (stop - start));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
