package net.digitaltsunami.word.sequence;

/**
 * Combination of edit distance and normalization strategies used to provide a
 * configuration for calculating edit distances.
 * <p>
 * By allowing the the setting of the distance strategy separate from the
 * normalization strategy, distance calculations can be tailored without the
 * need to create additional subclasses.
 * 
 * @author dhagberg
 * 
 */
public class EditDistanceCalculator {
    private final EditDistanceStrategy distanceStrategy;
    private final EditDistanceNormalization distanceNormalizer;

    /**
     * Instantiate an EditDistanceCalculator using the default edit distance
     * strategy and default normalization strategy.
     */
    public EditDistanceCalculator() {
        this(new LevenshteinDistanceStrategy(), new TermLengthNormalization());
    }

    /**
     * Instantiate an EditDistanceCalculator using the provided
     * {@link EditDistanceStrategy} and default normalization strategy.
     * 
     * @param strategy
     *            used to calculate edit distance.
     */
    public EditDistanceCalculator(EditDistanceStrategy strategy) {
        this(strategy, new TermLengthNormalization());
    }

    /**
     * Instantiate an EditDistanceCalculator using the default edit distance
     * strategy and the provided {@link EditDistanceNormalization}.
     * 
     * @param normalizer
     *            used to normalize edit distances.
     */
    public EditDistanceCalculator(EditDistanceNormalization normalizer) {
        this(new LevenshteinDistanceStrategy(), normalizer);
    }

    /**
     * Instantiate an EditDistanceCalculator using the provided
     * {@link EditDistanceStrategy} and {@link EditDistanceNormalization}.
     * 
     * @param strategy
     *            used to calculate edit distance.
     * @param normalizer
     *            used to normalize edit distances.
     */
    public EditDistanceCalculator(EditDistanceStrategy strategy, EditDistanceNormalization normalizer) {
        this.distanceStrategy = strategy;
        this.distanceNormalizer = normalizer;
    }

    /**
     * Calculate and return the number of edits required to convert fromTerm
     * into toTerm. As this method provides only a count of the required edits,
     * no significance will be applied to the length of the two terms or any
     * edit weights.
     * 
     * @param fromTerm
     *            initial term used as baseline
     * @param toTerm
     *            target term from which the edit count will be calculated.
     * @return a count of applicable edits required to convert the fromTerm to
     *         toTerm. Count will be an integer value in the range: 0 >= count
     *         >= max_length(fromTerm, toTerm)
     */
    public int getEditCount(String fromTerm, String toTerm) {
        return distanceStrategy.getEditCount(fromTerm, toTerm);
    }

    /**
     * Calculate and return the edit distance between fromTerm and toTerm. The
     * distance is calculated based on features specific to the current edit
     * distance strategy. As an example, the strategy may, but is not required
     * to, take into account such factors as: common mistakes, keyboard
     * location, sounds, etc..
     * 
     * @param fromTerm
     *            initial term used as baseline
     * @param toTerm
     *            target term from which the edit count will be calculated.
     * @return a value returned will be in the range: 0 >= distance >=
     *         max_length(fromTerm, toTerm) with an identical term being 0 and
     *         increasing in size as the difference in the terms increases.
     */
    public double getEditDistance(String fromTerm, String toTerm) {
        return distanceStrategy.getEditDistance(fromTerm, toTerm);
    }

    /**
     * Calculate and return the <strong>normalized</strong> edit distance
     * between fromTerm and toTerm. The distance is calculated based on features
     * specific to the current edit distance strategy. As an example, the
     * strategy may, but is not required to, take into account such factors as:
     * common mistakes, keyboard location, sounds, etc..
     * <p>
     * After the edit distance is calculated, it will be normalized using the
     * current normalization strategy to provide a value in the range [0,1] with
     * a 0 being a complete mismatch (no characters in common) and 1 being an
     * exact match.
     * 
     * @param fromTerm
     *            initial term used as baseline
     * @param toTerm
     *            target term from which the edit count will be calculated.
     * 
     * @return a value returned will be in the closed interval: [0, 1] with an
     *         identical term being 1 and decreasing towards zero as the
     *         difference in the terms increases.
     */
    public double getNormalizedEditDistance(String fromTerm, String toTerm) {
        double distance = distanceStrategy.getEditDistance(fromTerm, toTerm);
        return distanceNormalizer.getNormalizedEditDistance(distance, fromTerm, toTerm);
    }
}
