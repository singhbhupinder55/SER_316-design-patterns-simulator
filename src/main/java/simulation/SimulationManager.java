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

    /**
     * Constructs a SimulationManager to manage Tech Giants, events, and startups.
     */
    public SimulationManager() {
        techGiants = new ArrayList<>();
        events = new ArrayList<>();
        wildStartups = new ArrayList<>(); // Initialize wild startups
    }

    /**
     * Adds a Tech Giant to the simulation.
     * @param techGiant The Tech Giant to add.
     */
    public void addTechGiant(TechGiant techGiant) {
        techGiants.add(techGiant);
        log(techGiant.getName() + " added to the simulation.");
    }

    /**
     * Adds an event to the simulation.
     * @param event The event to add.
     */
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


    /**
     * Adds a wild startup to the simulation.
     * @param startup The wild startup to add.
     */
    public void addWildStartup(Startup startup) {
        wildStartups.add(startup);
        log("Added Wild Startup: " + startup.getName() + " with Revenue: " + startup.getRevenue());
    }

    /**
     * Retrieves the list of wild startups.
     * @return List of wild startups.
     */
    public List<Startup> getWildStartups() {
        return wildStartups;

    }

    /**
     * Logs a simulation-related message to the console.
     * @param message The message to log.
     */
    private void log(String message) {
        System.out.println("[SIMULATION LOG] " + message);
    }

    /**
     * Recovers defeated startups in the next cycle.
     * Restores their revenue to 50% of the original starting revenue.
     */
    private void recoverDefeatedStartups() {
        int recoveredCount = 0;
        for (TechGiant techGiant : techGiants) {
            for (Startup startup : techGiant.getStartups()) {
                if (startup.getRevenue() <= 0) {
                    // Recover revenue based on market share
                    double recoveryAmount = startup.getMarketShare() * 2.5; // Example recovery logic
                    startup.setRevenue(recoveryAmount);
                    recoveredCount++;
                }
            }
        }
        if (recoveredCount > 0){
            log("Recovered " + recoveredCount + " defeated startups.");
        }
    }


    /**
     * Starts the simulation for a specified number of years.
     * @param years The number of years to run the simulation.
     */
    public void startSimulation(int years) {
        for (int year = 1; year <= years; year++) {
            System.out.println("\n--- Year " + year + " ---");

            for (int quarter = 1; quarter <= 4; quarter++) {
                System.out.println("\n--- Quarter " + quarter + " ---");
                String currentQuarter = "Q" + quarter;

                // Trigger events for the quarter
                for (Event event : events) {
                    if (event.getQuarter().equalsIgnoreCase(currentQuarter)) {
                        event.applyEffects(wildStartups);
                        // Apply effects to tech giant startups
                        for (TechGiant techGiant : techGiants) {
                            event.applyEffects(techGiant.getStartups());
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
                    handleTechGiantBattles();
                }
                // **Remove Tech Giants with No Startups**
                removeTechGiantsWithoutStartups();

                // Recover defeated startups
                recoverDefeatedStartups(); // Call recovery logic
            }
        }

        System.out.println("\nSimulation Completed!");
    }


    /**
     * Handles Tech Giant vs. Tech Giant battles in Q4.
     */
    private void handleTechGiantBattles() {
        for (int i = 0; i < techGiants.size() - 1; i++) {
            for (int j = i + 1; j < techGiants.size(); j++) {
                TechGiant giant1 = techGiants.get(i);
                TechGiant giant2 = techGiants.get(j);

                Startup winner = giant1.battle(giant2);
                if (winner != null) {
                    log(winner.getName() + " from " + (winner.isWild() ? "wild startups" : "Tech Giant") + " won the battle!");
                }
            }
        }
    }

    /**
     * Removes Tech Giants with no startups from the simulation.
     */
    public void removeTechGiantsWithoutStartups() {
        Iterator<TechGiant> iterator = techGiants.iterator();
        while (iterator.hasNext()) {
            TechGiant techGiant = iterator.next();
            if (techGiant.getStartups().isEmpty()) {
                iterator.remove();
            }
        }
    }

    /**
     * Processes odd-quarter actions for a Tech Giant, such as building startups and making investments.
     * @param techGiant The Tech Giant performing actions.
     */
    private void processOddQuarterActions(TechGiant techGiant) {
        offerEnhancements(techGiant); // Offer enhancements
        buildNewStartup(techGiant); // **Try to build a new startup**

        if (!techGiant.getStartups().isEmpty()) {
            Startup startup = techGiant.getStartups().get(0); // Simple logic: invest in the first startup
            techGiant.investInStartup(startup, 50); // Invest $50 (boost market share)
        }
    }

    /**
     * Handles battles between Tech Giants and wild startups.
     */
    private void processWildStartupBattles() {
        if (!wildStartups.isEmpty()) {
            Iterator<Startup> iterator = wildStartups.iterator();
            while (iterator.hasNext()) {
                Startup wildStartup = iterator.next();
                for (TechGiant techGiant : techGiants) {
                    if (!techGiant.getStartups().isEmpty()) {
                        log(techGiant.getName() + " battles a wild startup: " + wildStartup.getName());
                        Startup winner = BattleManager.startBattle(techGiant.getStartups().get(0), wildStartup, techGiant);

                        if (winner != wildStartup) {
                            // Wild startup is acquired
                            iterator.remove(); // Safely remove wild startup
                            techGiant.addStartup(wildStartup);

                            // Deduct financial cost
                            double acquisitionCost = 500; // Example cost
                            if (techGiant.getFunds() >= acquisitionCost) {
                                techGiant.setFunds(techGiant.getFunds() - acquisitionCost);
                                log(techGiant.getName() + " paid $" + acquisitionCost + " to acquire " + wildStartup.getName());
                            } else {
                                log(techGiant.getName() + " could not afford to acquire " + wildStartup.getName() + ".");
                            }
                        } else {
                            log("Wild startup " + wildStartup.getName() + " survived the battle!");
                        }
                        break; // Handle one wild startup per Tech Giant
                    }
                }
            }
        }
    }



    /**
     * Builds a new startup for a Tech Giant if sufficient funds are available.
     * @param techGiant The Tech Giant attempting to build a startup.
     */
    public void buildNewStartup(TechGiant techGiant) {
        if (techGiant.getFunds() >= 1000) {
            String startupType = techGiant.getFunds() > 5000 ? "Premium" : "General";

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
        } else {
            log(techGiant.getName() + " does not have enough funds to build a new startup.");
        }
    }

    /**
     * Offers enhancements to a Tech Giant.
     * @param techGiant The Tech Giant receiving enhancement offers.
     */
    public void offerEnhancements(TechGiant techGiant) {
        Enhancement loan = new Enhancement("Loan", "Loan", 0, 0, 1000); // $1000 loan
        Enhancement revenueBooster = new Enhancement("Revenue Booster", "Revenue", 500, 0, 0.2); // 20% boost

        log(techGiant.getName() + " can purchase the following enhancements:");
        log("1. " + loan.getName() + ": Adds $" + loan.getEffectValue() + ".");
        log("2. " + revenueBooster.getName() + ": Boosts revenue by 20% for $500.");
        techGiant.purchaseEnhancement(loan);
    }


}
