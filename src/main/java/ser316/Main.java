package ser316;

import patterns.singleton.SimulationController;
import patterns.factory.BuildingFactory;
import patterns.factory.Building;
import patterns.strategy.*;
import simulation.*;

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

        // Step 3: Use Strategy Pattern to manage market strategies
        System.out.println("\nApplying Strategies using Strategy Pattern:");

        // Initialize Context with DefensiveStrategy
        Context context = new Context(new DefensiveStrategy());
        System.out.println("Initial Strategy: Defensive Strategy");
        System.out.println("Result: " + context.executeStrategy()); // Output: Executing Defensive Strategy.

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

        // Add events to the simulation
        Event corporateTaxCuts = new Event("Corporate Tax Cuts", "Market benefits from tax cuts", "Q1");
        Event economicDownturn = new Event("Economic Downturn", "Market faces a recession", "Q2");
        Event regulatoryScrutiny = new Event("Regulatory Scrutiny", "Large companies face strict regulations", "Q3");
        manager.addEvent(corporateTaxCuts);
        manager.addEvent(economicDownturn);
        manager.addEvent(regulatoryScrutiny);

        // Add startups to tech giants
        // HealthTech Inc.: Revenue $100K, Market Share 10%, Net Income $20K
        techGiant1.addStartup(new Startup("HealthTech Inc.", "Healthcare", 100, 10, 20, false));

        // FinTech Co.: Revenue $90K, Market Share 30%, Net Income $15K
        techGiant2.addStartup(new Startup("FinTech Co.", "FinTech", 90, 30, 15, false));

        // Add wild startups to the simulation
        manager.addWildStartup(new Startup("SocialWild Inc.", "Social Media", 50, 15, 10, true));
        manager.addWildStartup(new Startup("RealWild Co.", "Real Estate", 70, 10, 5, true));

        // Start simulation for 1 year
        System.out.println("\nStarting Simulation...");
        manager.startSimulation(1);

        // Display startups managed by the Singleton Controller
        System.out.println("\nFinal startups in the Simulation Controller:");
        for (Building startup : controller.getStartups()) {
            System.out.println("- " + startup.getClass().getSimpleName());
        }

        System.out.println("\nSimulation Completed. Thank you for using Silicon Valley Simulator!");
    }
}
