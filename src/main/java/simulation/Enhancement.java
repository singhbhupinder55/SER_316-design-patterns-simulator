package simulation;

/**
 * Represents a financial enhancement for Tech Giants.
 * Enhancements can affect the financial standing or growth of a Tech Giant in various ways,
 * such as boosting revenue or providing loans. Each enhancement has a cost, duration, and
 * effect value that defines its impact.
 */
public class Enhancement {
    private String name;        // Name of the enhancement (e.g., "Loan", "Revenue Booster")
    private String type;        // Type (e.g., "Loan", "Revenue")
    private double cost;        // Cost of the enhancement
    private int duration;       // Duration in quarters (0 for one-time effects)
    private double effectValue; // Value of the effect (e.g., funds added, revenue boost)

    /**
     * Constructor to create a new enhancement.
     * @param name        The name of the enhancement (e.g., "Loan", "Revenue Booster").
     * @param type        The type of the enhancement (e.g., "Loan", "Revenue").
     * @param cost        The cost of the enhancement (e.g., $1000 for a loan).
     * @param duration    The duration in quarters. A value of 0 means a one-time effect.
     * @param effectValue The effect value of the enhancement,
     *                    which could represent a loan amount or percentage increase in revenue.
     */
    public Enhancement(String name, String type, double cost, int duration, double effectValue) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.duration = duration;
        this.effectValue = effectValue;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getCost() {
        return cost;
    }

    public int getDuration() {
        return duration;
    }

    public double getEffectValue() {
        return effectValue;
    }



}
