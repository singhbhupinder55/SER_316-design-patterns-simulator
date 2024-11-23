package patterns.singleton;

/**
 * Singleton class for managing the simulation.
 * Ensures only one instance of SimulationController exists.
 */
public class SimulationController {

    // Step 1: Create a private static variable to hold the single instance.
    private static SimulationController instance;

    // Step 2: Make the constructor private to prevent instantiation from outside.
    private SimulationController() {
        System.out.println("SimulationController Instance Created!");
    }

    /**
     * Step 3: Provide a public method to return the single instance.
     * If the instance is null, it initializes it.
     *
     * @return the single instance of SimulationController
     */
    public static SimulationController getInstance() {
        if (instance == null) {
            // Lazily initialize the instance when it is first requested.
            instance = new SimulationController();
        }
        return instance;
    }

    /**
     * Example method to demonstrate functionality of the Singleton.
     * This method will simulate starting the simulation.
     */
    public void startSimulation() {
        System.out.println("Starting the Silicon Valley Simulator!");
    }
}
