package patterns.strategy;


/**
 * Strategy interface defining the contract for all concrete strategies.
 * Classes implementing this interface must provide their own implementation of execute().
 */
public interface Strategy {

    /**
     * Execute the strategy.
     * @return a message describing the strategy being executed.
     */
    String execute();
}
