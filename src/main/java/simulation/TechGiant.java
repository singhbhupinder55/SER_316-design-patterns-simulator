package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Represents a Tech Giant that manages startups.
 * Handles interactions like battles, acquisitions, and investments.
 */
public class TechGiant {
    private String name; // Name of the Tech Giant (e.g., Google, Amazon)
    private List<Startup> startups; // List of startups owned by the Tech Giant
    private double funds; // Available funds for investments and acquisitions
    private List<Enhancement> activeEnhancements; // Track ongoing enhancements


    /**
     * Constructor with only the name (defaults funds to $5000).
     * @param name Name of the Tech Giant
     */
    public TechGiant(String name) {
        this(name, 5000); // Call the main constructor with default funds
    }

    /**
     * Constructor with specified name and initial funds.
     * @param name         Name of the Tech Giant
     * @param initialFunds Initial amount of funds
     */
    public TechGiant(String name, double initialFunds) {
        this.name = name;
        this.startups = new ArrayList<>();
        this.funds = initialFunds;
        this.activeEnhancements = new ArrayList<>(); // Initialize the list
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public List<Startup> getStartups() {
        return startups;
    }


    public double getFunds() {
        return funds;
    }

    /**
     * Sets the list of startups for the Tech Giant.
     * @param startups New list of startups
     */
    public void setStartups(List<Startup> startups) {
        this.startups = startups;
    }


    /**
     * Sets the funds for the Tech Giant.
     * @param funds New funds amount (non-negative)
     * @throws IllegalArgumentException if funds are negative
     */
    public void setFunds(double funds) {
        if (funds < 0) {
            throw new IllegalArgumentException("Funds cannot be negative.");
        }
        this.funds = funds >= 0 ? funds : 0; // Ensure funds cannot go negative
    }

    /**
     * Adds a startup to the Tech Giant.
     * @param startup Startup to be added
     */
    public void addStartup(Startup startup) {
        startups.add(startup);
        System.out.println(name + " acquired " + startup.getName() + "!");
    }

    /**
     * Removes a startup from the Tech Giant.
     * @param startup Startup to be removed
     */
    public void removeStartup(Startup startup) {
        startups.remove(startup);
        System.out.println(name + " lost " + startup.getName() + "!");
    }


    /**
     * Invests in a startup.
     * Deducts funds and grants experience points to the startup.
     *
     * @param startup Startup to invest in
     * @param amount  Investment amount
     */
    public void investInStartup(Startup startup, double amount) {
        System.out.println("[DEBUG] " + name + " funds before investment: $" + funds);
        if (funds >= amount) {
            funds -= amount;
            startup.gainExperience((int) amount / 100); // Convert funds to XP
            System.out.println(name + " invested $" + amount + " in " + startup.getName() + ".");
        } else {
            System.out.println(name + " does not have enough funds to invest!");
        }
    }

    /**
     * Engages in a battle with another Tech Giant.
     * Selects startups dynamically and handles battle results.
     *
     * @param opponent Opposing Tech Giant
     * @return Winning startup, or null in case of a draw
     */
    public Startup battle(TechGiant opponent) {
        if (startups.isEmpty()) {
            System.out.println(name + " has no startups left to battle!");
            return null;
        }

        if (opponent.getStartups().isEmpty()) {
            System.out.println(opponent.getName() + " has no startups left to battle!");
            return null;
        }

        // Select random startups for battle
        Startup myStartup = selectStartupForBattle(); // Simple selection logic
        Startup opponentStartup = opponent.selectStartupForBattle();

        if (myStartup == null || opponentStartup == null) {
            System.out.println("No valid startups for battle.");
            return null;
        }
        System.out.println(name + " is battling " + opponent.getName() + "!");
        // Determine the winner using the BattleManager
        Startup winner = BattleManager.startBattle(myStartup, opponentStartup, this);

        // Handle acquisition based on the winner
        if (winner == myStartup) {
            // Acquire opponent's startup
            Startup acquired = opponent.getStartups().remove(0);
            addStartup(acquired);
            System.out.println(name + " acquired " + acquired.getName() + " from " + opponent.getName() + "!");
        } else if (winner == opponentStartup) {
            // Opponent acquires my startup
            Startup lost = startups.remove(0);
            opponent.addStartup(lost);
            System.out.println(opponent.getName() + " acquired " + lost.getName() + " from " + name + "!");
        }

        // After the battle, check if the losing TechGiant has no startups left
        if (startups.isEmpty()) {
            System.out.println(name + " has no startups left!");
        }

        return winner;
    }

    /**
     * Selects the best startup for battle based on revenue.
     * @return Startup with the highest revenue
     */
    public Startup selectStartupForBattle() {
        return startups.stream()
                .max((s1, s2) -> Double.compare(s1.getRevenue(), s2.getRevenue()))
                .orElse(null); // Select the startup with the highest revenue
    }


    /**
     * Purchases an enhancement for the Tech Giant.
     * @param enhancement Enhancement to purchase
     */
    public void purchaseEnhancement(Enhancement enhancement) {
        if (funds >= enhancement.getCost()) {
            funds -= enhancement.getCost();
            activeEnhancements.add(enhancement);
            System.out.println(name + " purchased " + enhancement.getName() + ".");
        } else {
            System.out.println(name + " does not have enough funds to purchase " + enhancement.getName() + "!");
        }
    }

    /**
     * Applies all active enhancements.
     * Enhancements like loans and revenue boosters are processed.
     */
    public void applyEnhancements() {
        Iterator<Enhancement> iterator = activeEnhancements.iterator();
        while (iterator.hasNext()) {
            Enhancement enhancement = iterator.next();
            switch (enhancement.getType()) {
                case "Loan":
                    funds += enhancement.getEffectValue(); // Apply loan effect
                    System.out.println(name + " received a loan of $" + enhancement.getEffectValue() + ".");
                    iterator.remove(); // Remove one-time effect
                    break;

                case "Revenue":
                    for (Startup startup : startups) {
                        double initialRevenue = startup.getRevenue();
                        double revenueBoost = initialRevenue * enhancement.getEffectValue();
                        double newRevenue = initialRevenue + revenueBoost;
                        startup.setRevenue(newRevenue);
                        // Print out the details of the revenue boost
                        System.out.println(startup.getName() + " revenue boosted by " +
                                (enhancement.getEffectValue() * 100) + "%: " +
                                "From $" + initialRevenue + " to $" + newRevenue + ".");
                    }
                    iterator.remove(); // Remove one-time effect
                    break;

                default:
                    System.out.println("Unknown enhancement type: " + enhancement.getType());
            }
        }
    }


    /**
     * Gets the list of active enhancements.
     *
     * @return List of active enhancements
     */
    public List<Enhancement> getActiveEnhancements() {
        return activeEnhancements;
    }

}
