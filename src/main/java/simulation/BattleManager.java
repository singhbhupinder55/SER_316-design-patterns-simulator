package simulation;

import java.util.Random;



/**
 * Manages battles between startups.
 * Facilitates turn-based combat and determines the winner.
 */
public class BattleManager {

    /**
     * Initiates a battle between two startups.
     * @param startup1 the first startup
     * @param startup2 the second startup
     * @return the winning startup
     */
    public static Startup startBattle(Startup startup1, Startup startup2, TechGiant winnerTechGiant) {
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

        Random random = new Random();
        while (startup1.getRevenue() > 0 && startup2.getRevenue() > 0) {
            System.out.println(performAttack(startup1, startup2, random));
            if (startup2.getRevenue() <= 0) {
                System.out.println(startup1.getName() + " wins the battle!");
                startup1.gainExperience(5);

                // Add reward for defeating wild startups
                if (winnerTechGiant != null && startup2.isWild()) {
                    System.out.println(startup2.getName() + " is a wild startup and will be acquired.");
                    winnerTechGiant.addStartup(startup2); // Acquire wild startup
                }
                return startup1;
            }

            // Startup 2 attacks Startup 1
            System.out.println(performAttack(startup2, startup1, random));
            if (startup1.getRevenue() <= 0) {
                System.out.println(startup2.getName() + " wins the battle!");
                startup2.gainExperience(5);
                // Check and add wild startup to the winning Tech Giant
                if (winnerTechGiant != null && startup1.isWild()) {
                    System.out.println(startup1.getName() + " is a wild startup and will be acquired.");
                    winnerTechGiant.addStartup(startup1); // Acquire the wild startup
                }
                return startup2;
            }
        }
        System.out.println("The battle ended with no winner.");
        return null; // Should not reach here
    }
    /**
     * Performs an attack from one startup to another with mechanics like type advantage,
     * critical hits, and misses.
     * @param attacker the startup performing the attack
     * @param defender the startup being attacked
     * @return a summary of the attack
     */
    public static String performAttack(Startup attacker, Startup defender, Random random) {

        // 10% chance to miss
        if (random.nextDouble() < 0.1) {
            return attacker.getName() + " missed the attack!";
        }
        // 20% chance for critical hit (or you can adjust this as needed)
        if (random.nextDouble() < 0.2) {
            return attacker.getName() + " landed a critical hit! " + attacker.attack(defender);
        }


        // Use Startup's attack method
        return attacker.attack(defender);
    }
}
