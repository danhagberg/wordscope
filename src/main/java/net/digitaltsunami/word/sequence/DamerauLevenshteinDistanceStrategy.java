/**
 * 
 */
package net.digitaltsunami.word.sequence;

/**
 * Computes edit distance using the Damerau-Levenshtein distance metric.
 * Damerau-Levenshtein differs from Levenshtein in that the transposition of two
 * values count as a single edit operation; whereas, in Levenshtein this would
 * count as two. Each edit (insertion, deletion, substitution, transposition)
 * counts as one edit.
 * <p>
 * This implementation provides a basic count of edits without weights.
 * 
 * @author dhagberg
 * 
 */
public class DamerauLevenshteinDistanceStrategy implements EditDistanceStrategy {
    /*
     * (non-Javadoc)
     * 
     * @see
     * net.digitaltsunami.word.sequence.EditDistanceStrategy#getEditCount(
     * java.lang.String, java.lang.String)
     */
    @Override
    public int getEditCount(String fromTerm, String toTerm) {
        return calculateEditCount(fromTerm, toTerm);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.digitaltsunami.word.sequence.EditDistanceStrategy#getEditDistance
     * (java.lang.String, java.lang.String)
     */
    @Override
    public double getEditDistance(String fromTerm, String toTerm) {
        return calculateEditCount(fromTerm, toTerm);
    }

    /**
     * Compute and return the Damerau-Levenshtein edit distance between the two
     * provided sequences. Damerau-Levenshtein differs from Levenshtein in that
     * the transposition of two values count as a single edit operation;
     * whereas, in Levenshtein this would count as two. Each edit (insertion,
     * deletion, substitution, transposition) counts as one edit. The total
     * number of these edit operations required to convert string 1 to string 2
     * is calculated and returned.
     * 
     * @param fromTerm
     * @param toTerm
     * @return total number of edits required to convert string 1 to string 2.
     */
    public static int calculateEditCount(String fromTerm, String toTerm) {
        int nRows = fromTerm.length() + 1;
        int nCols = toTerm.length() + 1;
        /*
         * Create matrix to store edit operations. Each dimension is length + 1
         * to allow for full edit distance values.
         */
        int editMatrix[][] = new int[nRows][nCols];
        /*
         * Initialize first row and column to contain edit distances as if the
         * other string were empty.
         */
        for (int r = 0; r < nRows; r++) {
            editMatrix[r][0] = r;
        }
        for (int c = 0; c < nCols; c++) {
            editMatrix[0][c] = c;
        }
        /*
         * Compare each character of the string against one another and
         * calculate the number of edits needed at each stage. The total number
         * of edits will be in editMatrix[fromTerm.len][toTerm.len]
         */
        for (int r = 1; r < nRows; r++) {
            for (int c = 1; c < nCols; c++) {
                // Cost if values are different. Will be used below
                int cost = (fromTerm.charAt(r - 1) == toTerm.charAt(c - 1)) ? 0 : 1;
                // Minimum value of deletion, insertion, or substitution.
                editMatrix[r][c] =
                        Math.min(
                                Math.min(editMatrix[r][c - 1] + 1, editMatrix[r - 1][c] + 1),
                                 editMatrix[r - 1][c - 1] + cost);
                if ((r > 1 && c > 1)
                        && (fromTerm.charAt(r - 1) == toTerm.charAt(c - 2))
                        && (fromTerm.charAt(r - 2) == toTerm.charAt(c - 1))) {
                    editMatrix[r][c] = Math.min(editMatrix[r][c], editMatrix[r - 2][c - 2] + cost);
                }
            }
        }

        return editMatrix[nRows - 1][nCols - 1];
    }

}
