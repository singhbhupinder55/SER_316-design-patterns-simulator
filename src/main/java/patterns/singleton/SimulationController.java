package patterns.singleton;

import java.util.Locale;
/**
 * Singleton class for managing the simulation.
 * Ensures only one instance of SimulationController exists.
 */
public class SimulationController {

    // Step 1: Create a private static variable to hold the single instance.
    private static volatile SimulationController instance;


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
            synchronized (SimulationController.class) {
                if (instance == null) {
                    instance = new SimulationController();
                }
            }
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

    /**
     * Example of a localized string conversion to avoid locale-sensitive issues.
     * Converts a given input to uppercase using the default root locale.
     *
     * @param input the input string to convert
     * @return the uppercase version of the input
     */
    public String processInput(String input) {
        if (input == null || input.isEmpty()) {
            return "Input is empty!";
        }
        // Use Locale.ROOT to ensure consistent behavior across locales
        return input.toUpperCase(Locale.ROOT);
    }
}
