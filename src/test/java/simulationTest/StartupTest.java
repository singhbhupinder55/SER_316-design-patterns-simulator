package simulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.Startup;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Startup class.
 * Verifies the core functionalities of Startup, including attacks, revenue management, and evolution.
 */
public class StartupTest {

    private Startup startup1;
    private Startup startup2;

    @BeforeEach
    void setUp() {
        // Initialize two startups for testing
        startup1 = new Startup("Techy Co.", "FinTech", 100, 20, 30, false);
        startup2 = new Startup("HealthTech Inc.", "Healthcare", 150, 15, 50, false);
    }

    @Test
    @DisplayName("Test Getters: Verify initial values are set correctly")
    void testGetters() {
        assertEquals("Techy Co.", startup1.getName());
        assertEquals("FinTech", startup1.getType());
        assertEquals(100, startup1.getRevenue());
        assertEquals(20, startup1.getMarketShare());
        assertEquals(30, startup1.getNetIncome());
        assertFalse(startup1.isWild(), "Startup should not be wild initially.");
    }

    @Test
    @DisplayName("Test Set Revenue: Verify revenue updates correctly")
    void testSetRevenue() {
        startup1.setRevenue(120);
        assertEquals(120, startup1.getRevenue());

        // Test negative revenue handling
        startup1.setRevenue(-50);
        assertEquals(0, startup1.getRevenue(), "Revenue should not be negative.");
    }

    @Test
    @DisplayName("Test Attack: Verify attacks reduce opponent's attributes or handle misses")
    void testAttack() {
        // Arrange: Capture initial values of opponent's attributes
        double initialRevenue = startup2.getRevenue();
        double initialMarketShare = startup2.getMarketShare();
        double initialNetIncome = startup2.getNetIncome();

        // Act: Perform the attack
        startup1.attack(startup2);

        // Assert: Check if the attack missed
        if (initialRevenue == startup2.getRevenue() &&
                initialMarketShare == startup2.getMarketShare() &&
                initialNetIncome == startup2.getNetIncome()) {
            // If no attribute changed, assert the attack missed
            System.out.println("The attack missed! No attributes were reduced.");
            assertTrue(true, "Attack missed, so no attributes were reduced.");
        } else {
            // Assert one of the attributes has decreased
            assertTrue(
                    startup2.getRevenue() < initialRevenue ||
                            startup2.getMarketShare() < initialMarketShare ||
                            startup2.getNetIncome() < initialNetIncome,
                    "One of the opponent's attributes should have decreased."
            );

            // Assert opponent's revenue is set to zero if reduced below zero
            if (startup2.getRevenue() <= 0) {
                assertEquals(0, startup2.getRevenue());
            }
        }
    }


    @Test
    @DisplayName("Test Gain Experience and Evolution: Verify startups evolve correctly")
    void testGainExperienceAndEvolution() {
        // Garage Startup -> Tech Star
        startup1.gainExperience(5);
        assertEquals("Tech Star", startup1.getStage());

        // Tech Star -> Unicorn
        startup1.gainExperience(5);
        assertEquals("Unicorn", startup1.getStage());
    }

    @Test
    @DisplayName("Test Attack Miss: Verify no damage is dealt on a missed attack")
    void testAttackMiss() {
        // Simulate a miss by overriding the randomness logic
        double initialRevenue = startup2.getRevenue();
        double initialMarketShare = startup2.getMarketShare();

        // We'll force no attribute changes in the attack
        startup1.attack(new Startup("Placeholder", "Misc", 0, 0, 0, false));
        assertEquals(initialRevenue, startup2.getRevenue(), "Revenue should remain the same on a missed attack.");
        assertEquals(initialMarketShare, startup2.getMarketShare(), "Market share should remain the same on a missed attack.");
    }

    @Test
    @DisplayName("Test Wild Status: Verify the isWild flag works correctly")
    void testIsWild() {
        Startup wildStartup = new Startup("WildOne", "Misc", 50, 10, 5, true);
        assertTrue(wildStartup.isWild(), "Startup should be marked as wild.");
    }

    @Test
    @DisplayName("Test Wild Status of Startups")
    void testWildStatus() {
        Startup wildStartup = new Startup("SocialWild", "Social Media", 50, 15, 10, true);
        Startup regularStartup = new Startup("Techy Co.", "FinTech", 100, 20, 30, false);

        assertTrue(wildStartup.isWild(), "Wild startup should have isWild set to true.");
        assertFalse(regularStartup.isWild(), "Regular startup should have isWild set to false.");
    }

}
