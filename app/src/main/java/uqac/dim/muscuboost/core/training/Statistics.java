package uqac.dim.muscuboost.core.training;

/**
 * TODO
 */
public class Statistics {

    private final Long ID;
    private double weight;
    private int repCount;

    /**
     * TODO
     *
     * @param id TODO
     * @param weight TODO
     * @param repCount TODO
     */
    public Statistics(Long id, double weight, int repCount) {
        ID = id;
        this.weight = weight;
        this.repCount = repCount;
    }

    /**
     * TODO
     *
     * @param weight TODO
     * @param repCount TODO
     */
    public Statistics(double weight, int repCount) {
        this(null, weight, repCount);
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public long getId() {
        return ID;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public double getWeight() {
        return weight;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public int getRepCount() {
        return repCount;
    }

}
