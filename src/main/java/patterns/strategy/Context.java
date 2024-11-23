package patterns.strategy;



/**
 * Context class that uses a Strategy object.
 * Allows dynamic changes to the strategy being executed.
 */
public class Context {

    // Holds the current strategy
    private Strategy strategy;

    /**
     * Constructor to initialize the context with a specific strategy.
     * @param strategy the strategy to be used.
     */
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Changes the strategy dynamically at runtime.
     * @param strategy the new strategy to be used.
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Executes the current strategy.
     * @return the result of the strategy's execution.
     */
    public String executeStrategy() {
        return strategy.execute();
    }
}
