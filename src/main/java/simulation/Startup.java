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


    private final Random randomGenerator; // Instance of Random for controlled randomness
    private static final double CRITICAL_HIT_CHANCE = 0.2;
    private static final double MISS_CHANCE = 0.1;
    private static final String[] ATTACK_TYPES = {"Talent Drain", "Trade Secret Theft", "Price Undercutting"};


    /**
     * Constructs a new Startup instance.
     * @param name        Name of the startup.
     * @param type        Type of the startup (e.g., FinTech, Social Media).
     * @param revenue     Initial revenue of the startup.
     * @param marketShare Initial market share of the startup.
     * @param netIncome   Initial net income of the startup.
     * @param isWild      Indicates whether the startup is wild or owned by a Tech Giant.
     * @throws IllegalArgumentException if the name or type is null or empty.
     */
    public Startup(String name, String type, double revenue, double marketShare, double netIncome, boolean isWild) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty.");
        }

        this.name = name;
        this.type = type;
        this.revenue = Math.max(0, revenue);   // Monetary value representing financial health
        this.marketShare = Math.max(0, marketShare); // Percentage value
        this.netIncome = Math.max(0, netIncome);  // Monetary value representing profitability
        this.experiencePoints = 0; // Initial XP
        this.stage = "Garage Startup"; // Initial stage
        this.isWild = isWild; // Initialize wild status
        this.randomGenerator = new Random();
    }

    // Overloaded Constructor (With Random Parameter)
    public Startup(String name, String type, double revenue, double marketShare, double netIncome,
                   boolean isWild, Random randomGenerator) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty.");
        }

        this.name = name;
        this.type = type;
        this.revenue = Math.max(0, revenue);
        this.marketShare = Math.max(0, marketShare);
        this.netIncome = Math.max(0, netIncome);
        this.experiencePoints = 0;
        this.stage = "Garage Startup";
        this.isWild = isWild;
        this.randomGenerator = randomGenerator;
    }

    /**
     * Retrieves the name of the startup.
     * @return The name of the startup.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the type of the startup.
     * @return The type of the startup.
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the current revenue of the startup.
     * @return The revenue of the startup.
     */
    public double getRevenue() {
        return revenue;
    }

    /**
     * Retrieves the market share of the startup.
     * @return The market share of the startup.
     */
    public double getMarketShare() {
        return marketShare;
    }

    /**
     * Retrieves the net income of the startup.
     * @return The net income of the startup.
     */
    public double getNetIncome() {
        return netIncome;
    }


    /**
     * Retrieves the experience points of the startup.
     * @return The experience points of the startup.
     */
    public int getExperiencePoints() {
        return experiencePoints;
    }

    /**
     * Retrieves the current stage of the startup.
     * @return The stage of the startup.
     */
    public String getStage() {
        return stage;
    }

    /**
     * Checks whether the startup is wild or owned by a Tech Giant.
     * @return {@code true} if the startup is wild, {@code false} otherwise.
     */
    public boolean isWild() {
        return isWild;
    }

    // Setters
    /**
     * Sets the revenue of the startup.
     * @param revenue The new revenue value.
     * @throws IllegalArgumentException if the revenue is negative.
     */
    public void setRevenue(double revenue) {
        if (revenue < 0) {
            throw new IllegalArgumentException("Revenue cannot be negative.");
        }
        this.revenue = revenue;
    }

    /**
     * Sets the market share of the startup.
     * @param marketShare The new market share value.
     * @throws IllegalArgumentException if the market share is negative.
     */
    public void setMarketShare(double marketShare) {
        if (marketShare < 0) {
            throw new IllegalArgumentException("Market share cannot be negative.");
        }
        this.marketShare = marketShare;
    }

    /**
     * Sets the net income of the startup.
     * @param netIncome The new net income value.
     * @throws IllegalArgumentException if the net income is negative.
     */

    public void setNetIncome(double netIncome) {
        if (netIncome < 0) {
            throw new IllegalArgumentException("Net income cannot be negative.");
        }
        this.netIncome = netIncome;
    }

    /**
     * Applies damage to a specific attribute of the startup based on the attack type.
     * @param damage     The amount of damage to apply.
     * @param attackType The type of attack (e.g., "Talent Drain", "Trade Secret Theft").
     *  @throws IllegalArgumentException if the damage is negative.
     */
    public void takeDamage(double damage, String attackType) {

        if (damage < 0) {
            throw new IllegalArgumentException("Damage must be non-negative.");
        }

        switch (attackType) {
            case "Talent Drain":
                this.marketShare = Math.max(0, this.marketShare - damage);
                System.out.println(name + " lost " + damage + " Market Share due to Talent Drain.");
                break;

            case "Trade Secret Theft":
                this.netIncome = Math.max(0, this.netIncome - damage);
                System.out.println(name + " lost " + damage + " Net Income due to Trade Secret Theft.");
                break;

            case "Price Undercutting":
                this.revenue = Math.max(0, this.revenue - damage);
                System.out.println(name + " lost " + damage + " Revenue due to Price Undercutting. Current Revenue:" + this.revenue);
                break;

            default:
                System.out.println("Invalid attack type: " + attackType);
        }
    }


    /**
     * Performs an attack on another startup.
     * The attack type and damage are determined based on the attacker's attributes and type advantages.
     * @param opponent The opponent startup being attacked.
     * @return A summary of the attack, including the damage dealt.
     * @throws IllegalArgumentException if the opponent is null.
     */
    public String attack(Startup opponent) {
        if (opponent == null) {
            throw new IllegalArgumentException("Opponent cannot be null.");
        }
        System.out.println(name + " is attacking " + opponent.getName() + "!");

        // Determine attack type
        String attackType = determineAttackType();
        double damage = calculateDamage(opponent, attackType);
        opponent.takeDamage(damage, attackType);

        return String.format("%s used %s on %s. Damage: %.2f",
                this.name, attackType, opponent.getName(), damage);
    }


    /**
     * Adds experience points to the startup and handles evolution if milestones are reached.
     * @param points The number of experience points gained.
     * @throws IllegalArgumentException if the points are negative.
     */
    public void gainExperience(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Experience points must be non-negative.");
        }
        experiencePoints += points;
        System.out.println(name + " gained " + points + " XP!");

        // Check for evolution
        evolve();
    }


    /**
     * Handles evolution based on experience points.
     */
    private void evolve() {
        if (experiencePoints >= 10) {
            if (!stage.equals("Unicorn")) {
                stage = "Unicorn";
                System.out.println(name + " has evolved into a Unicorn!");
            }
        } else if (experiencePoints >= 5) {
            if (!stage.equals("Tech Star")) {
                stage = "Tech Star";
                System.out.println(name + " has evolved into a Tech Star!");
            }
        }
    }




    /**
     * Determines the type of attack randomly.
     * @return The selected attack type.
     */
    private String determineAttackType() {
        return ATTACK_TYPES[randomGenerator.nextInt(ATTACK_TYPES.length)];
    }


    /**
     * Calculates the damage dealt to an opponent, considering type advantages and critical hits.
     * @param opponent   The opponent startup being attacked.
     * @param attackType The type of attack being performed.
     * @return The calculated damage value.
     */
    private double calculateDamage(Startup opponent, String attackType) {
        double baseDamage = 10.0; // Default damage value
        if (randomGenerator.nextDouble() <  MISS_CHANCE) { // 10% chance to miss
            System.out.println(name + " missed the attack!");
            return 0;
        }

        // Type advantage logic
        if ((type.equalsIgnoreCase("Operating Systems") && opponent.getType().equalsIgnoreCase("Social Media")) ||
                (type.equalsIgnoreCase("FinTech") && opponent.getType().equalsIgnoreCase("Real Estate"))) {
            baseDamage *= 1.5;
            System.out.println("Type advantage! Damage boosted by 50%.");
        }

        if (randomGenerator.nextDouble() < CRITICAL_HIT_CHANCE) { // 20% chance for critical hit
            baseDamage *= 2;
            System.out.println("Critical hit! Damage doubled.");
        }

        return baseDamage;
    }

    public double testCalculateDamage(Startup opponent, String attackType) {
        return calculateDamage(opponent, attackType);
    }


}
