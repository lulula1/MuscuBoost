package uqac.dim.muscuboost.core.training;

import uqac.dim.muscuboost.core.Identifiable;

/**
 * An exercise statistics.
 */
public class Statistics implements Identifiable {

    private final Long ID;
    private double weight;
    private int repCount;

    /**
     * Creates statistics.
     *
     * @param id Id of the statistics
     * @param weight Weight statistic
     * @param repCount Repetition count statistic
     */
    public Statistics(Long id, double weight, int repCount) {
        ID = id;
        this.weight = weight;
        this.repCount = repCount;
    }

    /**
     * Creates statistics without id.
     *
     * @param weight Weight statistic
     * @param repCount Repetition count statistic
     */
    public Statistics(double weight, int repCount) {
        this(null, weight, repCount);
    }

    /**
     * Creates empty statistics.
     */
    public Statistics() {
        this(null, -1, 1);
    }

    /**
     * Returns the statistics' id.
     *
     * @return Id of the statistics
     */
    @Override
    public long getId() {
        return ID;
    }

    /**
     * Defines the weight statistic.
     *
     * @param weight New weight statistic
     */
    public void setWeight(double weight) {
        this.weight = Math.max(.0, weight);
    }

    /**
     * Returns the weight statistic.
     *
     * @return Weight statistic
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Defines the repetition count statistic.
     *
     * @param repCount New repetition count statistic
     */
    public void setRepCount(int repCount) {
        this.repCount = Math.max(0, repCount);
    }

    /**
     * Returns the repetition count statistic.
     *
     * @return Repetition count statistic
     */
    public int getRepCount() {
        return repCount;
    }

}
