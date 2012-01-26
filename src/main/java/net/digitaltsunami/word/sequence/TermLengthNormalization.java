/**
 * 
 */
package net.digitaltsunami.word.sequence;

/**
 * Provides edit distance normalization based on length of terms, with the ratio
 * of edits to length. This results in the same number of edits returning a
 * closer match for longer terms as opposed to shorter terms.
 * 
 * @author dhagberg
 * 
 */
public class TermLengthNormalization implements EditDistanceNormalization {

    /**
     * Normalize edit distance between fromTerm and toTerm and return a value in
     * the range [0,1] with a 0 being a complete mismatch (no characters in
     * common) and 1 being an exact match.
     * <p>
     * Normalization is based on length of terms, with the ratio of edits to
     * length. This results in the same number of edits returning a closer match
     * for longer terms as opposed to shorter terms. See below for an example.
     * 
     * <pre>
     * From     To      Count       Notes
     * -------- ------- -----       ---------------------------------
     * game     g       0.25        Three deletions.  Short word length
     * gamelan  game    0.57        Three deletions.  Longer word length
     * </pre>
     * 
     * @param editCount
     *            number of edits to convert fromTerm to toTerm.
     * @param fromTerm
     *            initial term used as baseline
     * @param toTerm
     *            target term from which the edit count will be calculated.
     *            
     * @return a value returned will be in the closed interval: [0, 1] with an
     *         identical term being 1 and decreasing towards zero as the
     *         difference in the terms increases.
     * @see net.digitaltsunami.word.sequence.EditDistanceNormalization#getNormalizedEditDistance(double,
     *      java.lang.String, java.lang.String)
     */
    @Override
    public double getNormalizedEditDistance(double editCount, String fromTerm, String toTerm) {
        return TermLengthNormalization.normalizeOnTermLength(editCount, fromTerm, toTerm);
    }

    /**
     * Normalize edit distance based on term lengths. The provided edit count is
     * normalized to return a value in the range [0,1] with a 0 being a complete
     * mismatch (no characters in common) and 1 being an exact match.
     * <p>
     * Normalization is based on length of terms, with the ratio of edits to
     * length. This results in the same number of edits returning a closer match
     * for longer terms as opposed to shorter terms. See below for an example.
     * <pre>
     * From     To      Count       Notes
     * -------- ------- -----       ---------------------------------
     * game     g       0.25        Three deletions.  Short word length
     * gamelan  game    0.57        Three deletions.  Longer word length
     * </pre>
     * 
     * 
     * @param editCount
     *            number of edits required to convert fromTerm to toTerm.
     * @param fromTerm
     *            initial term used as baseline
     * @param toTerm
     *            target term from which the edit count will be calculated.
     * @return a value returned will be in the closed interval: [0, 1] with an
     *         identical term being 1 and decreasing towards zero as the
     *         difference in the terms increases.
     */
    protected static double normalizeOnTermLength(double editCount, String fromTerm, String toTerm) {
        int minLen = Math.min(fromTerm.length(), toTerm.length());
        int maxLen = Math.max(fromTerm.length(), toTerm.length());
        return ((maxLen - editCount) * minLen) / (maxLen * minLen);
    }
}
