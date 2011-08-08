package net.digitaltsunami.word.trie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Build an expected value weight table.
 * 
 * @author dhagberg
 * 
 */
public class ExpectedValueWeightTable {
    private Map<Character, Map<Character, Float>> weightsByChar;

    public static final Float MIN_WEIGHT = new Float(0.0);
    public static final float MIN_WEIGHT_PRIMITIVE = 0.0f;
    public static final char ROOT_CHAR_VAL = '\0';

    /**
     * TODO comments NO " around character values.
     * 
     * @param weightTableCsvStream
     * @throws IOException
     */
    public ExpectedValueWeightTable(InputStream weightTableCsvStream) throws IOException {
        this(new BufferedReader(new InputStreamReader(weightTableCsvStream)));
    }

    /**
     * TODO comments NO " around character values.
     * 
     * @param weightTableCsvStream
     * @throws IOException
     */
    public ExpectedValueWeightTable(File weightTableCsvFile) throws IOException {
        this(new BufferedReader(new FileReader(weightTableCsvFile)));
    }

    /**
     * TODO comments NO " around character values.
     * 
     * @param weightTableCsvStream
     * @throws IOException
     */
    public ExpectedValueWeightTable(BufferedReader csvReader) throws IOException {
        String line = csvReader.readLine();
        if (line == null) {
            // TODO: Need new exception class for this.
            throw new IOException("File is empty");
        }
        weightsByChar = new HashMap<Character, Map<Character, Float>>();
        // Build the outer map of all observed characters for the corpus.
        String[] header = line.split(",");
        // Skip the first column and the last two columns.
        for (int i = 1; i < header.length - 2; i++) {
            Character key = header[i].equals("FIRST") ? ROOT_CHAR_VAL : header[i].charAt(0);
            weightsByChar.put(key, new HashMap<Character, Float>());
        }
        // Total characters following character is in last column;
        int totalColIdx = header.length - 1;

        // Iterate over rest of file and for each line representing a character,
        // add an entry to the map for that character with the follow on
        // character and the expected value that the character follows the
        // current character.
        while ((line = csvReader.readLine()) != null) {
            String[] data = line.split(",");
            Character key = data[0].equals("FIRST") ? ROOT_CHAR_VAL : data[0].charAt(0);
            Map<Character, Float> weightsForChar = weightsByChar.get(key);
            // first 2 and last 2 columns do not contain follow on data.
            float totalFreq = Float.parseFloat(data[totalColIdx]);
            for (int i = 2; i < data.length - 2; i++) {
                float freq = Float.parseFloat(data[i]);
                Float weight = totalFreq == 0 ? MIN_WEIGHT : freq / totalFreq;
                weightsForChar.put(header[i].charAt(0), weight);
            }
        }
    }

    public float getExpectedValue(Character current, Character next) {
        Map<Character, Float> weightsForChar = weightsByChar.get(current);
        if (weightsForChar != null) {
            Float followOnWeight = weightsForChar.get(next);
            if (followOnWeight != null) {
                return followOnWeight;
            }
        }
        return MIN_WEIGHT_PRIMITIVE;
    }
}
