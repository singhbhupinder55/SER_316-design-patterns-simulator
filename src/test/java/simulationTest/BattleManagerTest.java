package simulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simulation.BattleManager;
import simulation.Startup;
import simulation.TechGiant;
import helper.MockRandom;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BattleManager class.
 */
public class BattleManagerTest {

    private Startup startup1;
    private Startup startup2;
    private TechGiant techGiant;

    @BeforeEach
    void setUp() {
        startup1 = new Startup("TechOne", "Operating Systems", 100.0, 30.0, 20.0, false);
        startup2 = new Startup("SocialGiant", "Social Media", 80.0, 25.0, 15.0, true);
        techGiant = new TechGiant("MegaCorp", 500.0);
    }

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






    @Test
    @DisplayName("Test Start Battle - Wild Startup Acquisition")
    void testStartBattle() {
        // Setup
        Startup startup1 = new Startup("TechOne", "Operating Systems", 100.0, 20.0, 30.0, false); // Regular startup
        Startup startup2 = new Startup("SocialGiant", "Social Media", 100.0, 25.0, 20.0, true); // Wild startup
        TechGiant techGiant = new TechGiant("MegaCorp");

        // Add the wild startup to the simulation
        techGiant.addStartup(startup1); // Adding the first startup to the Tech Giant
        // Note: startup2 is wild and not added to any Tech Giant yet.

        // Run the battle
        Startup winner = BattleManager.startBattle(startup1, startup2, techGiant);

        // Assertions to check the results of the battle
        assertNotNull(winner, "The battle should produce a winner.");
        assertTrue(winner == startup1 || winner == startup2, "Winner should be one of the two startups.");

        // Check if the Tech Giant acquired the wild startup (startup2) if it lost
        if (winner == startup1) {
            // If startup1 wins, the Tech Giant should acquire startup2 (the wild startup).
            assertTrue(techGiant.getStartups().contains(startup2), "TechGiant should acquire the defeated wild startup.");
        } else {
            // If startup2 wins, it means startup2 (wild startup) has won, and the Tech Giant should acquire startup1
            assertTrue(techGiant.getStartups().contains(startup1), "TechGiant should acquire the defeated startup1.");
        }
    }


    @Test
    @DisplayName("Test Battle Between Identical Startups")
    void testBattleIdenticalStartups() {
        Startup identicalStartup1 = new Startup("IdenticalOne", "Operating Systems", 100, 20, 10, false);
        Startup identicalStartup2 = new Startup("IdenticalTwo", "Operating Systems", 100, 20, 10, false);

        Startup winner = BattleManager.startBattle(identicalStartup1, identicalStartup2, null);

        assertNotNull(winner, "There should still be a winner when startups are identical.");
        assertTrue(winner == identicalStartup1 || winner == identicalStartup2,
                "Winner should be one of the identical startups.");
    }


    @Test
    @DisplayName("Test Battle with Zero Revenue")
    void testBattleZeroRevenue() {
        Startup startupWithZeroRevenue = new Startup("ZeroRevenue", "FinTech", 0, 20, 10, false);
        Startup opponent = new Startup("Opponent", "Social Media", 100, 25, 15, false);
        TechGiant techGiant = new TechGiant("TechGiant");

        Startup winner = BattleManager.startBattle(startupWithZeroRevenue, opponent, techGiant);

        assertEquals(opponent, winner, "Startup with zero revenue should lose instantly.");
    }


    @Test
    @DisplayName("Test Battle Without TechGiant")
    void testBattleWithoutTechGiant() {
        Startup startup1 = new Startup("TechOne", "Operating Systems", 100.0, 20.0, 30.0, false);
        Startup startup2 = new Startup("SocialGiant", "Social Media", 100.0, 25.0, 20.0, true); // wild startup

        Startup winner = BattleManager.startBattle(startup1, startup2, null);

        assertNotNull(winner, "The battle should produce a winner.");
        assertTrue(startup2.isWild(), "Wild startup should remain wild when TechGiant is null.");
    }




    @Test
    @DisplayName("Test Battle with Null Startup")
    void testBattleWithNullStartup() {
        Startup nullStartup = null;
        Startup validStartup = new Startup("ValidStartup", "FinTech", 100, 20, 30, false);
        TechGiant techGiant = new TechGiant("MegaCorp");

        // Handle case where a startup is null
        assertThrows(NullPointerException.class, () -> {
            BattleManager.startBattle(nullStartup, validStartup, techGiant);
        }, "Should throw NullPointerException when one startup is null.");
    }







}
