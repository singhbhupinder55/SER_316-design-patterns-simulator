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


    // Constructor with only the name (defaults funds to 0)
    public TechGiant(String name) {
        this(name, 5000); // Call the main constructor with default funds
    }

    // Constructor
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

    public void setFunds(double funds) {
        if (funds < 0) {
            throw new IllegalArgumentException("Funds cannot be negative.");
        }
        this.funds = funds;
    }

    // Add a startup to the Tech Giant
    public void addStartup(Startup startup) {
        startups.add(startup);
        System.out.println(name + " acquired " + startup.getName() + "!");
    }

    // Remove a startup from the Tech Giant
    public void removeStartup(Startup startup) {
        startups.remove(startup);
        System.out.println(name + " lost " + startup.getName() + "!");
    }



    // Invest in a startup
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

    // Engage in a battle with another Tech Giant
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
        Startup myStartup = startups.get(0); // Simple selection logic
        Startup opponentStartup = opponent.getStartups().get(0);

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

        return winner;
    }


    public void purchaseEnhancement(Enhancement enhancement) {
        if (funds >= enhancement.getCost()) {
            funds -= enhancement.getCost();
            activeEnhancements.add(enhancement);
            System.out.println(name + " purchased " + enhancement.getName() + ".");
        } else {
            System.out.println(name + " does not have enough funds to purchase " + enhancement.getName() + "!");
        }
    }

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
                        double revenueBoost = startup.getRevenue() * enhancement.getEffectValue();
                        startup.setRevenue(startup.getRevenue() + revenueBoost);
                        System.out.println(startup.getName() + " revenue boosted by " + (enhancement.getEffectValue() * 100) + "%.");
                    }
                    iterator.remove(); // Remove one-time effect
                    break;

                default:
                    System.out.println("Unknown enhancement type: " + enhancement.getType());
            }
        }
    }

    public List<Enhancement> getActiveEnhancements() {
        return activeEnhancements;
    }

}
