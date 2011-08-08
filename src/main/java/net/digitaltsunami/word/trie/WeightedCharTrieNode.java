package net.digitaltsunami.word.trie;

public class WeightedCharTrieNode extends LinkedCharTrieNode {

    private final float weight;
    public static final float MIN_WEIGHT = 0.0F;

    public WeightedCharTrieNode(CharTrieNode parent, char value, float weight) {
        super(parent, value);
        this.weight = weight;
    }

    public WeightedCharTrieNode(CharTrieNode parent, char value) {
        this(parent, value, MIN_WEIGHT);
    }

    public WeightedCharTrieNode(char value, float weight) {
        super(value);
        this.weight = weight;
    }

    public WeightedCharTrieNode(char value) {
        this(value, MIN_WEIGHT);
    }

    public WeightedCharTrieNode(boolean rootNode) {
        super(rootNode);
        this.weight = MIN_WEIGHT;
    }

    @Override
    public CharTrieNode addChild(char value) {
        return addChild(value, MIN_WEIGHT);
    }

    /**
     * Document placement of nodes Equal value (previously added) Lesser weight
     * Greater weight Same weight as existing entry.
     * 
     * @param value
     * @param valueWeight
     * @return
     */
    protected CharTrieNode addChild(char value, float valueWeight) {
        WeightedCharTrieNode sibling = (WeightedCharTrieNode) getFirstChild();
        while (sibling != null) {
            if (valueWeight <= sibling.getWeight()) {
                /*
                 * Sibling is still greater than new value's weight. Must go
                 * until end of list sibling with lesser weight. Equal weights
                 * must be searched as the value may already exist.
                 */
                if (value == sibling.getValue()) {
                    // Value == sibling, already exists, just return
                    return sibling;
                } else if (sibling.getNextSibling() != null) {
                    // Not yet at end of list. Keep searching.
                    sibling = (WeightedCharTrieNode) sibling.getNextSibling();
                } else {
                    // Reached the end of the sibling list. Add new node and
                    // append to the last node in the list.
                    WeightedCharTrieNode newNode = new WeightedCharTrieNode(this, value,
                            valueWeight);
                    sibling.appendNode(newNode);
                    return newNode;
                }
            } else {
                /*
                 * Expected value weight > sibling's weight, list is in
                 * descending order of weight, so it won't be found. Create new,
                 * insert it, and return the new node.
                 */
                WeightedCharTrieNode newNode = new WeightedCharTrieNode(this, value, valueWeight);
                sibling.prependNode(newNode);
                return newNode;
            }
        }

        // First child node to be added, add and return.
        WeightedCharTrieNode newNode = new WeightedCharTrieNode(this, value, valueWeight);
        setFirstChild(newNode);
        return newNode;
    }

    protected float getWeight() {
        return weight;
    }
}
