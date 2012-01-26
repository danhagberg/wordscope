package net.digitaltsunami.word.sequence;

/**
 * Utility methods for processing of sequences.  
 * 
 * @author dhagberg
 *
 */
public class Sequence {
    /**
     * Returns one of possibly many longest subsequences of characters common to
     * both input strings. As a subsequence, characters are not required to be
     * contiguous.
     * 
     * @param string1
     * @param string2
     * @return a string from the set of 0 to many strings, the length of each
     *         being the longest subsequence found.
     */
    public static String getALongestCommonSubsequence(String string1, String string2) {
        /*
         * Matrix containing results of iterations looking for common
         * characters. Arrays for results are 1 greater than length of input
         * strings to accommodate 0s in first row and column.
         */
        int nRows = string1.length() + 1;
        int nCols = string2.length() + 1;
        int[][] lcsMatrix = new int[nRows][nCols];
        /*
         * Initialize with -1 to indicate value has not yet been determined. The
         * first row and column will be will be skipped as they contain 0s used
         * for the comparison.
         */
        for (int r = 1; r < nRows; r++) {
            for (int c = 1; c < nCols; c++) {
                lcsMatrix[r][c] = -1; // O(r x c)
            }
        }
        /*
         * Generate the LCS values for each row and column
         */
        for (int r = 1; r < nRows; r++) {
            for (int c = 1; c < nCols; c++) {
                getPrefixLenForPosition(string1, string2, r, c, lcsMatrix); // O(r x c)
            }
        }
        return getLongestSequence(string1, string2, lcsMatrix); // O(r + c)
    }

    /**
     * Calculate the common subsequence length for the current position within
     * the LCS matrix. The value will be returned as well as updated within the
     * results matrix.
     * <p>
     * The indexes into the strings will be one less than the provided row and
     * columns to allow for the zeros row and column.
     * 
     * @param s1
     *            String one. Each character represents a row.
     * @param s2
     *            String two. Each character represents a column.
     * @param row
     *            current row index into the results matrix.
     * @param col
     *            current column index into the results matrix.
     * @param results
     *            current results matrix populated up to [row,col].
     * 
     * @return length of longest common subsequence for the given row and
     *         column.
     */
    private static int getPrefixLenForPosition(String s1, String s2, int row, int col, int[][] results) {
        // All 0th row and column results are 0.
        if (row == 0 && col == 0) {
            return 0;
        }
        if (results[row][col] > -1) {
            // Results have already been calculated, return previously
            // calculated prefix len.
            return results[row][col];
        }

        // Index into strings is one less to account for 0's row and column.
        if (s1.charAt(row - 1) == s2.charAt(col - 1)) {
            // Match found, increase prefix len.
            results[row][col] = results[row - 1][col - 1] + 1;
        } else {
            if (results[row - 1][col] > results[row][col - 1]) {
                results[row][col] = results[row - 1][col];
            } else {
                results[row][col] = Math.max(results[row - 1][col], results[row][col - 1]);
            }
        }
        return results[row][col];
    }

    /**
     * Return one of possibly many longest subsequences using the pre-compiled
     * results contained in the provided matrix (prefixLens).
     * 
     * @param s1
     * @param s2
     * @param prefixLens
     * @return
     */
    private static String getLongestSequence(String s1, String s2, int[][] prefixLens) {
        // Start row and col at last row and col. Matrix is s1.len + 1 x s2.len + 1
        int row = s1.length();
        int col = s2.length();
        StringBuilder sb = new StringBuilder(prefixLens[row][col]);
        while (row > 0 && col > 0) {
            if (prefixLens[row][col] > prefixLens[row - 1][col]) {
                if (prefixLens[row][col] > prefixLens[row][col - 1]) {
                    // Change in prefix length means common char found,
                    // save and move to previous row and column
                    sb.append(s1.charAt(row - 1));
                    row--;
                    col--;
                } else {
                    col--; // Move left
                }
            } else {
                // No change in length up, so move up. There could also have
                // been no change in left, but can go only one way.
                row--;
            }
        }
        sb.reverse();
        return sb.toString();
    }

    private static void printLCSMatrix(String s1, String s2, int[][] prefixLengths) {
        System.out.printf("\n%3s", ""); // Print first Row
        System.out.printf("%2s", "0"); // Print 0 col hdr
        for (int c = 0; c < s2.length(); c++) { // Print String 2
            System.out.printf("%3s", s2.charAt(c));
        }
        System.out.printf("\n");
        System.out.printf("%2s", " 0"); // Print 0 row hdr
        for (int c = 0; c <= s2.length(); c++) { // Print 0 for each column
            System.out.printf("|%2s", "0");
        }
        for (int r = 0; r < s1.length(); r++) {
            System.out.printf("\n%2s", s1.charAt(r));
            System.out.printf("%3s", "0");
            for (int c = 0; c < s2.length(); c++) {
                System.out.printf("|%2d", prefixLengths[r + 1][c + 1]);
            }
        }
        System.out.println("");
    }
}
