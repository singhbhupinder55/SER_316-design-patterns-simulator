package simulation;

import java.util.Random;



/**
 * Manages battles between startups.
 * Facilitates turn-based combat and determines the winner.
 */
public class BattleManager {

    private static final Random random = new Random(); // Reuse Random instance for efficiency

    /**
     * Initiates a battle between two startups.
     * @param startup1 the first startup
     * @param startup2 the second startup
     * @return the winning startup
     */
    public static Startup startBattle(Startup startup1,
                                      Startup startup2, TechGiant winnerTechGiant) {
        System.out.println("Battle Start: " + startup1.getName() + " vs. " + startup2.getName());

        // Handle the case where one of the startups has zero revenue initially
        if (startup1.getRevenue() <= 0) {
            System.out.println(startup1.getName() + " has zero revenue and cannot fight.");
            return startup2;
        }
        if (startup2.getRevenue() <= 0) {
            System.out.println(startup2.getName() + " has zero revenue and cannot fight.");
            return startup1;
        }

        while (startup1.getRevenue() > 0 && startup2.getRevenue() > 0) {
            if (performRound(startup1, startup2, winnerTechGiant)) {
                return startup1;
            }
            if (performRound(startup2, startup1, winnerTechGiant)) {
                return startup2;
            }
        }
        System.out.println("The battle ended with no winner.");
        return null; // Should not reach here
    }

    /**
     * Performs a round of attack between two startups.
     * @param attacker the startup performing the attack
     * @param defender the startup being attacked
     * @param winnerTechGiant the tech giant that may acquire the defeated wild startup
     * @return true if attacker wins, false otherwise
     */
    private static boolean performRound(Startup attacker,
                                        Startup defender, TechGiant winnerTechGiant) {
        System.out.println(performAttack(attacker, defender, winnerTechGiant)); // Attack phase
        if (defender.getRevenue() <= 0) {
            System.out.println(attacker.getName() + " wins the battle!");
            attacker.gainExperience(5); // Gain XP for the winner
            if (winnerTechGiant != null && defender.isWild()) {
                System.out.println(defender.getName() + " is a wild startup and will be acquired.");
                winnerTechGiant.addStartup(defender); // Acquire the wild startup
            }
            return true; // Attacker wins
        }
        return false; // Battle continues
    }

    /**
     * Performs an attack from one startup to another with mechanics like type advantage,
     * critical hits, and misses.
     * @param attacker the startup performing the attack
     * @param defender the startup being attacked
     * @return a summary of the attack
     */
    public static String performAttack(Startup attacker,
                                       Startup defender, TechGiant winnerTechGiant) {

        // Check for miss chance
        if (checkMiss()) {
            return attacker.getName() + " missed the attack!";
        }

        // Check for critical hit
        if (checkCriticalHit()) {
            return attacker.getName() + " landed a critical hit! " + attacker.attack(defender);
        }



        return attacker.attack(defender);
    }


    // Helper methods for checks
    private static boolean checkMiss() {
        return random.nextDouble() < 0.1; // 10% chance to miss
    }

    private static boolean checkCriticalHit() {
        return random.nextDouble() < 0.2; // 20% chance for critical hit
    }
}
