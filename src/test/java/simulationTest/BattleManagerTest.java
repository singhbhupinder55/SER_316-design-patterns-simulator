package simulationTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BattleManager class.
 */
public class BattleManagerTest {

    @Test
    @DisplayName("Test Battle with Revenue Reduction")
    void testBattle() {
        // Create TechGiant instance
        TechGiant techGiant1 = new TechGiant("Techy Co.", 1000.0);

        Startup startup1 = new Startup("StartupOne", "Operating Systems", 100, 20, 50, false);
        Startup startup2 = new Startup("StartupTwo", "Social Media", 100, 25, 40, false);

        // Add the first startup to the TechGiant
        techGiant1.addStartup(startup1);

        Startup winner = BattleManager.startBattle(startup1, startup2, techGiant1);

        assertNotNull(winner, "There should be a winner.");
        assertTrue(winner.getRevenue() > 0, "Winner's revenue should not be zero.");
        assertEquals(5, winner.getExperiencePoints(), "Winner should gain 5 experience points.");
    }

    @Test
    @DisplayName("Test Battling Wild Startups")
    void testBattleWildStartup() {
        Startup wildStartup = new Startup("SocialWild", "Social Media", 50, 15, 10, true);
        Startup techGiantStartup = new Startup("TechGiantStartup", "FinTech", 100, 20, 30, false);

        TechGiant techGiant = new TechGiant("Techy Co.");
        techGiant.addStartup(techGiantStartup);

        Startup winner = BattleManager.startBattle(techGiantStartup, wildStartup, techGiant);

        assertNotNull(winner, "A winner should be determined in the battle.");
        assertTrue(techGiant.getStartups().contains(wildStartup), "Wild startup should be acquired by Tech Giant.");
    }

}
