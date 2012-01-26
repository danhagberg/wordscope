package net.digitaltsunami.word.sequence;

public interface EditDistanceNormalization {

    /**
     * Normalize a given edit distance to return a value in the range [0,1] with
     * a 0 being a complete mismatch (no characters in common) and 1 being an
     * exact match.
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
     */
    public double getNormalizedEditDistance(double editCount, String fromTerm, String toTerm);

}
