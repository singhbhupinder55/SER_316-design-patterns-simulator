package patterns.strategy;

/**
 * Concrete implementation of the Strategy interface.
 * Represents an aggressive business strategy focusing on growth and expansion.
 */
public class AggressiveStrategy implements Strategy {

    /**
     * Executes the aggressive strategy.
     * @return a message describing the aggressive strategy.
     */
    @Override
    public String execute() {
        return "Executing aggressive strategy: prioritize growth and expansion.";
    }
}
