package simulation;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Manages the entire simulation.
 * Handles yearly cycles, events, and interactions between Tech Giants and startups.
 */
public class SimulationManager {

    private List<TechGiant> techGiants; // List of participating Tech Giants
    private List<Event> events;        // List of events for the simulation
    private List<Startup> wildStartups; // List of wild startups in the market

    // Constructor
    public SimulationManager() {
        techGiants = new ArrayList<>();
        events = new ArrayList<>();
        wildStartups = new ArrayList<>(); // Initialize wild startups
    }

    // Add a Tech Giant to the simulation
    public void addTechGiant(TechGiant techGiant) {
        techGiants.add(techGiant);
    }

    // Add an event to the simulation
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Get the list of Tech Giants.
     * @return list of Tech Giants
     */
    public List<TechGiant> getTechGiants() {
        return techGiants;
    }

    /**
     * Get the list of events.
     * @return list of events
     */
    public List<Event> getEvents() {
        return events;
    }
    // Add a wild startup to the simulation
    public void addWildStartup(Startup startup) {
        wildStartups.add(startup);
        System.out.println("[DEBUG] Added Wild Startup: " + startup.getName() + " with Revenue: " + startup.getRevenue());
    }

    // Get wild startups
    public List<Startup> getWildStartups() {
        return wildStartups;

    }

    // Logging Method for Debugging**
    private void log(String message) {
        System.out.println("[SIMULATION LOG] " + message);
    }

    // Start the simulation
    public void startSimulation(int years) {
        System.out.println("[DEBUG] Starting simulation for " + years + " year(s).");
        for (int year = 1; year <= years; year++) {
            System.out.println("\n--- Year " + year + " ---");

            for (int quarter = 1; quarter <= 4; quarter++) {
                System.out.println("\n--- Quarter " + quarter + " ---");
                for (TechGiant techGiant : techGiants) {  //remove debug
                    System.out.println("[DEBUG] " + techGiant.getName() + " funds at the start of Q" + quarter + ": $" + techGiant.getFunds());
                }
                String currentQuarter = "Q" + quarter;

                // Trigger events for the quarter
                for (Event event : events) {
                    if (event.getQuarter().equalsIgnoreCase(currentQuarter)) {
                        System.out.println("[DEBUG] Applying event: " + event.getName() + " (" + currentQuarter + ")");


                        // Before applying effects   debug remove these both for loops
                        for (TechGiant techGiant : techGiants) {
                            for (Startup startup : techGiant.getStartups()) {
                                System.out.println("[DEBUG] Before Event: " + startup.getName() + " Revenue: " + startup.getRevenue());
                            }
                        }

                        // Apply effects to tech giant startups
                        for (TechGiant techGiant : techGiants) {
                            event.applyEffects(techGiant.getStartups());
                        }
                        // Apply effects to wild startups
                        event.applyEffects(wildStartups);

                        // After applying effects    debig remove these both for lops
                        for (TechGiant techGiant : techGiants) {
                            for (Startup startup : techGiant.getStartups()) {
                                System.out.println("[DEBUG] After Event: " + startup.getName() + " Revenue: " + startup.getRevenue());
                            }
                        }
                    }
                }

                if (quarter == 1 || quarter == 3) { // **Odd Quarter Actions**
                    for (TechGiant techGiant : techGiants) {
                        processOddQuarterActions(techGiant);
                    }
                    processWildStartupBattles();
                }



                // Quarterly actions
                if (quarter == 4) { // Competitive battles in Q4
                    for (int i = 0; i < techGiants.size() - 1; i++) {
                        for (int j = i + 1; j < techGiants.size(); j++) {
                            TechGiant giant1 = techGiants.get(i);
                            TechGiant giant2 = techGiants.get(j);

                            Startup winner = giant1.battle(giant2);
                            if (winner != null) {
                                System.out.println(winner.getName() + " won the battle!");
                            }
                        }
                    }
                }
            }
        }

        System.out.println("\nSimulation Completed!");
    }

    //Process Odd Quarter Actions
    private void processOddQuarterActions(TechGiant techGiant) {
        System.out.println("[DEBUG] Processing odd quarter actions for: " + techGiant.getName());
        offerEnhancements(techGiant); // Offer enhancements
        buildNewStartup(techGiant); // **Try to build a new startup**

        if (!techGiant.getStartups().isEmpty()) {
            Startup startup = techGiant.getStartups().get(0); // Simple logic: invest in the first startup
            log(techGiant.getName() + " invests in " + startup.getName() + "!");
            System.out.println("[DEBUG] " + techGiant.getName() + " funds before investment: $" + techGiant.getFunds());   //debug remove
            techGiant.investInStartup(startup, 50); // Invest $50 (boost market share)
            System.out.println("[DEBUG] " + techGiant.getName() + " funds before investment: $" + techGiant.getFunds());   //debug remove
        }
    }

    // Handle Wild Startup Battles**
    private void processWildStartupBattles() {
        System.out.println("[DEBUG] Processing wild startup battles.");   //remove debug
        if (!wildStartups.isEmpty()) {
            Iterator<Startup> iterator = wildStartups.iterator();
            while (iterator.hasNext()) {
                Startup wildStartup = iterator.next(); // Get the next wild startup
                for (TechGiant techGiant : techGiants) {
                    if (!techGiant.getStartups().isEmpty()) {
                        log(techGiant.getName() + " battles a wild startup: " + wildStartup.getName());
                        System.out.println("[DEBUG] " + wildStartup.getName() + " Revenue before battle: " + wildStartup.getRevenue());   //debug
                        Startup winner = BattleManager.startBattle(techGiant.getStartups().get(0), wildStartup, techGiant);

                        if (winner != wildStartup) {
                            // Wild startup is acquired by the Tech Giant
                            iterator.remove(); // Safely remove wild startup
                            techGiant.addStartup(wildStartup);
                            log(techGiant.getName() + " acquired wild startup: " + wildStartup.getName());
                        } else {
                            log("Wild startup " + wildStartup.getName() + " survived the battle!");
                        }
                        break; // Handle one wild startup per Tech Giant
                    }
                }
            }
        }
    }


    // Build New Startup with Specific Conditions**
    public void buildNewStartup(TechGiant techGiant) {
        System.out.println("[DEBUG] Attempting to build startup for: " + techGiant.getName());
        System.out.println("[DEBUG] " + techGiant.getName() + " funds before building startup: $" + techGiant.getFunds());
        if (techGiant.getFunds() >= 1000) {
            String startupType = "General";
            if (techGiant.getFunds() > 5000) { // **Condition: Premium startup for higher funds**
                startupType = "Premium";
            }

            Startup newStartup = new Startup(
                    techGiant.getName() + " Startup #" + (techGiant.getStartups().size() + 1),
                    startupType,
                    1000,
                    10,
                    20,
                    false
            );

            techGiant.addStartup(newStartup);
            techGiant.setFunds(techGiant.getFunds() - 1000); // Deduct building cost
            log(techGiant.getName() + " built a new startup: " + newStartup.getName());
            System.out.println("[DEBUG] " + techGiant.getName() + " funds after building: $" + techGiant.getFunds());
        } else {
            log(techGiant.getName() + " does not have enough funds to build a new startup.");
        }
    }


    public void offerEnhancements(TechGiant techGiant) {
        Enhancement loan = new Enhancement("Loan", "Loan", 0, 0, 1000); // $1000 loan
        Enhancement revenueBooster = new Enhancement("Revenue Booster", "Revenue", 500, 0, 0.2); // 20% boost

        System.out.println(techGiant.getName() + " can purchase the following enhancements:");
        System.out.println("1. " + loan.getName() + ": Adds $" + loan.getEffectValue() + ".");
        System.out.println("2. " + revenueBooster.getName() + ": Boosts revenue by 20% for $500.");

        // Simple purchase logic (e.g., randomly choose)
        techGiant.purchaseEnhancement(loan); // Example logic
    }


}
