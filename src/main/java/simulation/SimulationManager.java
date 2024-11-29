package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import simulation.events.Event;



/**
 * Manages the entire simulation.
 * Handles yearly cycles, events, and interactions between Tech Giants and startups.
 */
public class SimulationManager {

    private List<TechGiant> techGiants; // List of participating Tech Giants
    private List<Event> events;        // List of events for the simulation
    private List<Startup> wildStartups; // List of wild startups in the market
    private boolean verbose = false;  // Flag to control verbosity of logs


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
     *
     * @param techGiant The Tech Giant to add.
     */
    public void addTechGiant(TechGiant techGiant) {
        techGiants.add(techGiant);
    }

    /**
     * Adds an event to the simulation.
     *
     * @param event The event to add.
     */
    public void addEvent(Event event) {
        events.add(event);
    }


    /**
     * Get the list of Tech Giants.
     *
     * @return list of Tech Giants
     */
    public List<TechGiant> getTechGiants() {
        return Collections.unmodifiableList(techGiants);
    }

    /**
     * Get the list of events.
     *
     * @return list of events
     */
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }


    /**
     * Adds a wild startup to the simulation.
     *
     * @param startup The wild startup to add.
     */
    public void addWildStartup(Startup startup) {
        wildStartups.add(startup);
    }

    /**
     * Retrieves the list of wild startups.
     *
     * @return List of wild startups.
     */
    public List<Startup> getWildStartups() {
        return Collections.unmodifiableList(wildStartups);

    }


    // Set verbose flag
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * Logs a simulation-related message to the console.
     *
     * @param message The message to log.
     */
    private void log(String message) {

        if (verbose) {
            System.out.println("[SIMULATION LOG] " + message);
        }
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
                    double recoveryAmount = startup.getMarketShare() * 2.5; //Example recovery logic
                    startup.setRevenue(recoveryAmount);
                    recoveredCount++;
                }
            }
        }
        if (recoveredCount > 0) {
            log("Recovered " + recoveredCount + " defeated startups.");
        }
    }



    /**
     * Starts the simulation for a specified number of years.
     * @param years The number of years to run the simulation.
     */
    public void startSimulation(int years) {
        for (int year = 1; year <= years; year++) {
            log("\n--- Year " + year + " ---");

            for (int quarter = 1; quarter <= 4; quarter++) {
                processQuarter(quarter);
            }
        }

        log("\nSimulation Completed!");
    }


    /**
     * Processes a specific quarter in the simulation,
     * applying events and handling actions like battles and acquisitions.
     * @param quarter The quarter to process (1 through 4).
     */
    private void processQuarter(int quarter) {
        log("\n--- Quarter " + quarter + " ---");

        // Trigger events for the quarter
        String currentQuarter = "Q" + quarter;
        applyEvents(currentQuarter);

        // Perform odd-quarter actions (Q1 & Q3)
        if (quarter == 1 || quarter == 3) {
            for (TechGiant techGiant : techGiants) {
                processOddQuarterActions(techGiant);
            }
            processWildStartupBattles();
        }

        // Perform quarterly actions (Q4 is special for Tech Giant battles)
        if (quarter == 4) {
            handleTechGiantBattles();
        }

        removeTechGiantsWithoutStartups();
        recoverDefeatedStartups();
    }

    /**
     * Applies events to both wild startups and Tech Giants based on the current quarter.
     * @param currentQuarter The quarter to check for events.
     */
    private void applyEvents(String currentQuarter) {
        for (Event event : events) {
            if (event.getQuarter().equalsIgnoreCase(currentQuarter)) {
                event.applyEffects(wildStartups);
                for (TechGiant techGiant : techGiants) {
                    event.applyEffects(techGiant.getStartups());
                }
            }
        }
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
                    log(winner.getName()
                            + " from "
                            + (winner.isWild() ? "wild startups" : "Tech Giant")
                            + " won the battle!");
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
     * Processes odd-quarter actions for a Tech Giant,
     * such as building startups and making investments.
     * @param techGiant The Tech Giant performing actions.
     */
    private void processOddQuarterActions(TechGiant techGiant) {
        offerEnhancements(techGiant); // Offer enhancements
        buildNewStartup(techGiant); // **Try to build a new startup**
        investInFirstStartup(techGiant);

    }

    /**
     * Invests in the first startup of a Tech Giant to boost market share.
     *
     * @param techGiant The Tech Giant making the investment.
     */
    private void investInFirstStartup(TechGiant techGiant) {
        if (!techGiant.getStartups().isEmpty()) {
            Startup startup = techGiant.getStartups().get(0); // Invest in the first startup
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
                        Startup winner =
                            BattleManager.startBattle(techGiant.getStartups().get(0),
                                    wildStartup, techGiant);

                        if (winner != wildStartup) {
                            // Wild startup is acquired
                            iterator.remove(); // Safely remove wild startup
                            techGiant.addStartup(wildStartup);
                            deductAcquisitionCost(techGiant, wildStartup);
                        }
                        break; // Handle one wild startup per Tech Giant
                    }
                }
            }
        }
    }

    /**
     * Deducts the acquisition cost from a Tech Giant when it acquires a wild startup.
     *
     * @param techGiant The Tech Giant acquiring the startup.
     * @param wildStartup The wild startup being acquired.
     */
    private void deductAcquisitionCost(TechGiant techGiant, Startup wildStartup) {
        double acquisitionCost = 500; // Example cost
        if (techGiant.getFunds() >= acquisitionCost) {
            techGiant.setFunds(techGiant.getFunds() - acquisitionCost);
            log(techGiant.getName()
                    + " paid $" + acquisitionCost
                    + " to acquire " + wildStartup.getName());
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
        }
    }

    /**
     * Offers enhancements to a Tech Giant.
     * @param techGiant The Tech Giant receiving enhancement offers.
     */
    public void offerEnhancements(TechGiant techGiant) {
        Enhancement loan = new Enhancement("Loan", "Loan", 0, 0, 1000); // $1000 loan
        techGiant.purchaseEnhancement(loan);
    }


}
