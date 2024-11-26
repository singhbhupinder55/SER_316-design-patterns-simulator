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

        Random random = new Random();
        while (startup1.getRevenue() > 0 && startup2.getRevenue() > 0) {
            // Startup 1 attacks Startup 2
            startup1.attack(startup2);
            if (startup2.getRevenue() <= 0) {
                System.out.println(startup1.getName() + " wins the battle!");
                startup1.gainExperience(5);

                // Add reward for defeating wild startups
                if (winnerTechGiant != null && startup2.isWild()) {
                    winnerTechGiant.addStartup(startup2); // Acquire wild startup
                    winnerTechGiant.investInStartup(startup1, 10); // Optional reward
                }
                return startup1;
            }

            // Startup 2 attacks Startup 1
            startup2.attack(startup1);
            if (startup1.getRevenue() <= 0) {
                System.out.println(startup2.getName() + " wins the battle!");
                startup2.gainExperience(5);
                return startup2;
            }
        }

        return null; // Should not reach here
    }
}
