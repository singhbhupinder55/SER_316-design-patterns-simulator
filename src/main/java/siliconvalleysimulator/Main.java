package siliconvalleysimulator;

import patterns.factory.Building;
import patterns.factory.BuildingFactory;
import patterns.singleton.SimulationController;
import patterns.strategy.AggressiveStrategy;
import patterns.strategy.Context;
import patterns.strategy.DefensiveStrategy;
import simulation.SimulationManager;
import simulation.Startup;
import simulation.TechGiant;
import simulation.events.Event;

/**
 * Main class that initiates the Silicon Valley Simulator.
 * This class demonstrates the use of various design patterns such as Singleton,
 * Factory, and Strategy in the simulation of startups and tech giants.
 * The main method initializes the simulation, creates and manages startups,
 * applies strategies, adds events, and runs the simulation for a specified duration.
 */
public class Main {

    /**
     * The entry point of the Silicon Valley Simulator application.
     * It demonstrates the use of the following design patterns:
     * <ul>
     *   <li>Singleton Pattern: Ensures that only one instance of the SimulationController exists.
     *   <li>Factory Pattern: Used to create different types of buildings in the simulation.
     *   <li>Strategy Pattern: Allows dynamic switching between market strategies for startups.
     * </ul>
     * The method initializes the simulation, creates buildings, applies strategies,
     * sets up tech giants and events, and starts the simulation for one year.
     * It also adds wild startups to the simulation and displays final results.
     *
     * @param args command-line arguments (not used in this method)
     */
    public static void main(String[] args) {
        System.out.println("\n==================== Silicon Valley Simulation "
                + "====================\n");

        // Step 1: Initialize Singleton
        System.out.println("Initializing Simulation...");
        SimulationController controller = SimulationController.getInstance();
        // Highlighting Singleton Pattern**
        System.out.println("Singleton controller initialized.");

        // Step 2: Use Factory to create buildings
        System.out.println("\nUsing Factory Pattern to create buildings (startups):");
        try {
            final Building office = BuildingFactory.createBuilding("office");
            System.out.println("Office startup created.");
            final Building store = BuildingFactory.createBuilding("store");
            System.out.println("Store startup created.");
            final Building factory = BuildingFactory.createBuilding("factory");
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

        // Step 3: Use Strategy Pattern to manage market strategies
        System.out.println("\nApplying Strategies using Strategy Pattern:");

        // Initialize Context with DefensiveStrategy
        Context context = new Context(new DefensiveStrategy());
        System.out.println("Initial Strategy: Defensive Strategy");
        System.out.println("Result: "
                + context.executeStrategy()); // Output: Executing Defensive Strategy.

        // Simulate an event and switch strategy dynamically
        System.out.println("\nEvent: Competitive Battles - Switching to Aggressive Strategy...");
        context.setStrategy(new AggressiveStrategy());
        System.out.println("Result: " + context.executeStrategy());

        // Step 4: Setup simulation package
        System.out.println("\nSetting up Simulation...");
        SimulationManager manager = new SimulationManager();

        // Add tech giants to the simulation
        TechGiant techGiant1 = new TechGiant("Techy Co.", 500);
        TechGiant techGiant2 = new TechGiant("Innovators Inc.", 300);
        manager.addTechGiant(techGiant1);
        manager.addTechGiant(techGiant2);
        System.out.println("Tech Giants added to simulation: "
                + techGiant1.getName() + ", "
                + techGiant2.getName());

        // Add events to the simulation
        Event corporateTaxCuts = new Event("Corporate Tax Cuts",
                "Market benefits from tax cuts", "Q1");
        Event economicDownturn = new Event("Economic Downturn",
                "Market faces a recession", "Q2");
        Event regulatoryScrutiny = new Event("Regulatory Scrutiny",
                "Large companies face strict regulations", "Q3");
        manager.addEvent(corporateTaxCuts);
        manager.addEvent(economicDownturn);
        manager.addEvent(regulatoryScrutiny);
        System.out.println("Events added to simulation: "
                + corporateTaxCuts.getName() + ", "
                + economicDownturn.getName() + ", "
                + regulatoryScrutiny.getName());

        // Add startups to tech giants
        // HealthTech Inc.: Revenue $100K, Market Share 10%, Net Income $20K
        techGiant1.addStartup(new Startup("HealthTech Inc.", "Healthcare", 100, 10, 20, false));

        // FinTech Co.: Revenue $90K, Market Share 30%, Net Income $15K
        techGiant2.addStartup(new Startup("FinTech Co.", "FinTech", 90, 30, 15, false));

        // Add wild startups to the simulation
        manager.addWildStartup(new Startup("SocialWild Inc.", "Social Media", 50, 15, 10, true));
        manager.addWildStartup(new Startup("RealWild Co.", "Real Estate", 70, 10, 5, true));
        System.out.println("Wild Startups added to simulation.");

        // Start simulation for 1 year
        System.out.println("\n==================== Simulation Start ====================\n");
        manager.startSimulation(1);

        // Display startups managed by the Singleton Controller
        System.out.println("\n==================== Final Results ====================\n");
        System.out.println("\nFinal startups in the Simulation Controller:");
        for (Building startup : controller.getStartups()) {
            System.out.println("- " + startup.getClass().getSimpleName());
        }

        System.out.println("\n==================== Simulation Completed ====================\n");
    }
}
