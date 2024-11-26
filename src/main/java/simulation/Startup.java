package simulation;

import java.util.Random;

/**
 * Represents a startup in the simulation.
 * Each startup has attributes like revenue, market share, and experience points.
 * Attribute Details:
 * - Revenue: Represents the financial health of the startup in monetary units (e.g., $100K, $1M).
 * - Market Share: Represents the startup's influence in the market as a percentage (e.g., 10 means 10%).
 * - Net Income: Represents profitability in monetary units (e.g., $20K, $500K).
 */
public class Startup {

    private String name;        // Name of the startup
    private String type;        // Type of the startup (e.g., FinTech, Social Media)
    private double revenue;     // Financial health (monetary units)
    private double marketShare; // Market influence (percentage)
    private double netIncome;   // Profitability (monetary units)
    private int experiencePoints; // Experience points for evolution
    private String stage;       // Current stage of the startup (e.g., Garage Startup, Unicorn)
    private boolean isWild; // Indicates if the startup is wild or owned by a Tech Giant


    // Constructor
    public Startup(String name, String type, double revenue, double marketShare, double netIncome, boolean isWild) {
        this.name = name;
        this.type = type;
        this.revenue = revenue;    // Monetary value representing financial health
        this.marketShare = marketShare;  // Percentage value
        this.netIncome = netIncome;  // Monetary value representing profitability
        this.experiencePoints = 0; // Initial XP
        this.stage = "Garage Startup"; // Initial stage
        this.isWild = isWild; // Initialize wild status
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getRevenue() {
        return revenue;
    }

    public double getMarketShare() {
        return marketShare;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public String getStage() {
        return stage;
    }

    public boolean isWild() {
        return isWild;
    }

    // Setter for market share
    public void setMarketShare(double marketShare) {
        this.marketShare = marketShare;
    }

    // Setter for revenue
    public void setRevenue(double revenue) {
        if (revenue < 0) {
            this.revenue = 0; // Revenue cannot be negative
        } else {
            this.revenue = revenue;
        }
    }

    // Attack another startup
    public void attack(Startup opponent) {
        System.out.println(name + " is attacking " + opponent.getName() + "!");

        // Determine attack type
        String attackType = determineAttackType();
        double damage = calculateDamage(opponent, attackType);

        // Apply damage based on attack type
        switch (attackType) {
            case "Talent Drain":
                opponent.marketShare -= damage;
                System.out.println(opponent.getName() + "'s market share reduced by " + damage + "!");
                break;

            case "Trade Secret Theft":
                opponent.netIncome -= damage;
                System.out.println(opponent.getName() + "'s net income reduced by " + damage + "!");
                break;

            case "Price Undercutting":
                opponent.revenue -= damage;
                System.out.println(opponent.getName() + "'s revenue reduced by " + damage + "!");
                break;
        }

        // Check if opponent is defeated
        if (opponent.revenue <= 0) {
            opponent.revenue = 0;
            System.out.println(opponent.getName() + " has been defeated!");
        }
    }

    // Gain experience points
    public void gainExperience(int points) {
        experiencePoints += points;
        System.out.println(name + " gained " + points + " XP!");

        // Check for evolution
        evolve();
    }

    // Evolution logic
    private void evolve() {
        if (experiencePoints >= 10 && stage.equals("Tech Star")) {
            stage = "Unicorn";
            System.out.println(name + " has evolved into a Unicorn!");
        } else if (experiencePoints >= 5 && stage.equals("Garage Startup")) {
            stage = "Tech Star";
            System.out.println(name + " has evolved into a Tech Star!");
        }
    }

    // Determines the type of attack to perform
    private String determineAttackType() {
        String[] attackTypes = {"Talent Drain", "Trade Secret Theft", "Price Undercutting"};
        Random random = new Random();
        return attackTypes[random.nextInt(attackTypes.length)];
    }

    // Calculates damage based on type advantage
    private double calculateDamage(Startup opponent, String attackType) {
        double baseDamage = 10.0; // Default damage value
        Random random = new Random();
        boolean isCritical = random.nextDouble() < 0.2; // 20% chance for critical damage
        boolean isMiss = random.nextDouble() < 0.1; // 10% chance to miss

        if (isMiss) {
            System.out.println(name + " missed the attack!");
            return 0;
        }

        // Type advantage logic
        if (type.equalsIgnoreCase("Operating Systems") && opponent.getType().equalsIgnoreCase("Social Media")) {
            baseDamage *= 1.5; // Bonus damage
        } else if (type.equalsIgnoreCase("FinTech") && opponent.getType().equalsIgnoreCase("Real Estate")) {
            baseDamage *= 1.5;
        }

        // Apply critical damage
        if (isCritical) {
            baseDamage *= 2;
            System.out.println("Critical hit!");
        }

        return baseDamage;
    }
}
