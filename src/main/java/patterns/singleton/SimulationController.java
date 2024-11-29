package patterns.singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import patterns.factory.Building;

/**
 * Singleton class for managing the simulation.
 * Ensures only one instance of SimulationController exists.
 */
public class SimulationController {

    // Create a private static variable to hold the single instance.
    private static volatile SimulationController instance;



    // Stores a list of startups for the simulation
    private final List<Building> startups;

    // Step 2: Make the constructor private to prevent instantiation from outside.
    private SimulationController() {
        this.startups = new ArrayList<>();
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
     * Adds a startup to the simulation.
     * @param startup the Building object representing the startup
     */
    public void addStartup(Building startup) {
        startups.add(startup);
    }

    /**
     * Returns the list of startups.
     *
     * @return the list of Building objects
     */
    public List<Building> getStartups() {
        return Collections.unmodifiableList(startups);
    }

}
