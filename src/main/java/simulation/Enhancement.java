package simulation;

/**
 * Represents a financial enhancement for Tech Giants.
 */
public class Enhancement {
    private String name;        // Name of the enhancement (e.g., "Loan", "Revenue Booster")
    private String type;        // Type (e.g., "Loan", "Revenue")
    private double cost;        // Cost of the enhancement
    private int duration;       // Duration in quarters (0 for one-time effects)
    private double effectValue; // Value of the effect (e.g., funds added, revenue boost)

    public Enhancement(String name, String type, double cost, int duration, double effectValue) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.duration = duration;
        this.effectValue = effectValue;
    }

    // Getters
    public String getName() { return name; }
    public String getType() { return type; }
    public double getCost() { return cost; }
    public int getDuration() { return duration; }
    public double getEffectValue() { return effectValue; }


}
