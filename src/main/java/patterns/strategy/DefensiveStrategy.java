package patterns.strategy;

/**
 * Concrete implementation of the Strategy interface.
 * Represents a defensive business strategy focusing on risk management and consolidation.
 */
public class DefensiveStrategy implements Strategy {

    /**
     * Executes the defensive strategy.
     * @return a message describing the defensive strategy.
     */
    @Override
    public String execute() {
        return "Executing defensive strategy: focus on risk management and consolidation.";
    }
}


