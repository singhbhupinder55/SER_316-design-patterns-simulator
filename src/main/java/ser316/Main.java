package ser316;

import patterns.singleton.SimulationController;
import patterns.factory.BuildingFactory;
import patterns.factory.Building;
import patterns.strategy.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, Silicon Valley Simulator!");

        // Step 1: Initialize Singleton
        System.out.println("Initializing Simulation...");
        SimulationController controller = SimulationController.getInstance();

        // Step 2: Use Factory to create buildings
        System.out.println("\nUsing Factory Pattern to create startups::");
        try {
        Building office = BuildingFactory.createBuilding("office");
            System.out.println("Office startup created.");
        Building store = BuildingFactory.createBuilding("store");
            System.out.println("Store startup created.");
        Building factory = BuildingFactory.createBuilding("factory");
            System.out.println("Factory startup created.");

        office.construct(); // Output: Constructing an Office Building.
        factory.construct(); // Output: Constructing a Factory Building.
        store.construct();  // Output: Constructing a Store Building.

            // Add startups to Singleton controller
            controller.addStartup(office);
            System.out.println("Office added to the Simulation Controller.");
            controller.addStartup(factory);
            System.out.println("Factory added to the Simulation Controller.");
            controller.addStartup(store);
            System.out.println("Store added to the Simulation Controller.");


        } catch (IllegalArgumentException e) {
        System.err.println("Error creating building: " + e.getMessage());
    }

        // Step 3: Use Strategy Pattern with Context
        System.out.println("\nUsing Strategy Pattern for market dynamics:");

        // Initialize Context with DefensiveStrategy
        Context context = new Context(new DefensiveStrategy());
        System.out.println("Initial strategy set to DefensiveStrategy...");
        System.out.println("Result: " + context.executeStrategy()); // Output: Executing Defensive Strategy.

        // Simulate an event and switch strategy dynamically
        System.out.println("\nSimulating an event: Competitive Battles...");
        System.out.println("Switching to AggressiveStrategy...");
        context.setStrategy(new AggressiveStrategy());
        System.out.println("Result: " + context.executeStrategy());

        // Display startups managed by the Singleton Controller
        System.out.println("\nCurrent startups in the simulation:");
        for (Building startup : controller.getStartups()) {
            System.out.println("- " + startup.getClass().getSimpleName());
        }


        System.out.println("\nSimulation Completed. Thank you for using Silicon Valley Simulator!");
    }
}
