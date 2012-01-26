package net.digitaltsunami.word.sequence;

public interface EditDistanceStrategy {

    /**
     * Calculate and return the number of edits required to convert fromTerm
     * into toTerm. As this method provides only a count of the required edits,
     * no significance will be applied to the length of the two terms or any
     * edit weights.
     * <p>
     * Operations that are considered as an edit are specific to the
     * implementations. For example, using a Levenshtein edit distance
     * calculator, the following edit counts would be returned:
     * 
     * <pre>
     * From     To      Count       Notes
     * -------- ------- -----       ---------------------------------
     * game     gnome   2           One insertion, one replacement
     * gnome    game    2           One delete, one replacement
     * calm     clam    2           One delete, one insertion
     * test     t       3           Three deletions
     * test     test    0           Same value, no edits.
     * </pre>
     * 
     * @param fromTerm
     *            initial term used as baseline
     * @param toTerm
     *            target term from which the edit count will be calculated.
     * @return a count of applicable edits required to convert the fromTerm to
     *         toTerm. Count will be an integer value in the range: 0 >= count
     *         >= max_length(fromTerm, toTerm)
     */
    public int getEditCount(String fromTerm, String toTerm);

    /**
     * Calculate and return the edit distance between fromTerm and toTerm. The
     * distance is calculated based on features specific to the implementation.
     * As an example, the implementation may, but is not required to, take into
     * account such factors as: common mistakes, keyboard location, sounds,
     * etc..
     * <p>
     * Operations that are considered as an edit are specific to the
     * implementations. For example, using a Levenshtein edit distance
     * calculator that weighted g/j replacements at 50%, the following edit
     * distances would be returned:
     * 
     * <pre>
     * From     To      Count       Notes
     * -------- ------- -----       ---------------------------------
     * game     gnome   2.0         One insertion, one replacement
     * gym      jym     0.5         One replace at 50% cost.
     * calm     clam    2           One delete, one insertion
     * test     t       3           Three deletions
     * test     test    0           Same value, no edits.
     * </pre>
     * 
     * @param fromTerm
     *            initial term used as baseline
     * @param toTerm
     *            target term from which the edit count will be calculated.
     * @return a value returned will be in the range: 0 >= distance >=
     *         max_length(fromTerm, toTerm) with an identical term being 0 and
     *         increasing in size as the difference in the terms increases.
     */
    public double getEditDistance(String fromTerm, String toTerm);

}
