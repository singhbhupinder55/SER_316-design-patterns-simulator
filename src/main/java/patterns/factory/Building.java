package patterns.factory;

/**
 * Represents the blueprint for all types of buildings in the simulation.
 * This is the base interface for the Factory Pattern.
 */
public interface Building {
    /**
     * Method to construct a building.
     * Each implementation will provide its specific construction logic.
     */
    void construct();
}
