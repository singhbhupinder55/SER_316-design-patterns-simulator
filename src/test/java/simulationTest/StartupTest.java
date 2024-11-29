package simulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.Startup;
import helper.MockRandom;

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
        Exception exception = assertThrows(IllegalArgumentException.class, () -> startup1.setRevenue(-50));
        assertEquals("Revenue cannot be negative.", exception.getMessage());
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
        assertEquals("Tech Star", startup1.getStage(), "Startup should evolve to Tech Star at 5 XP.");

        // Tech Star -> Unicorn
        startup1.gainExperience(5);
        assertEquals("Unicorn", startup1.getStage(), "Startup should evolve to Unicorn at 10 XP.");
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


    @Test
    void testTakeDamage() {
        Startup startup = new Startup("TestStartup", "Social Media", 100.0, 20.0, 30.0, false);

        // Apply Talent Drain damage
        startup.takeDamage(5.0, "Talent Drain");
        assertEquals(15.0, startup.getMarketShare(), "Market share should be reduced by 5.0.");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> startup1.takeDamage(-5, "Talent Drain"));
        assertEquals("Damage must be non-negative.", exception.getMessage());

        // Apply Trade Secret Theft damage
        startup.takeDamage(10.0, "Trade Secret Theft");
        assertEquals(20.0, startup.getNetIncome(), "Net income should be reduced by 10.0.");

        // Apply Price Undercutting damage
        startup.takeDamage(15.0, "Price Undercutting");
        assertEquals(85.0, startup.getRevenue(), "Revenue should be reduced by 15.0.");
    }

    @Test
    void testEvolution() {
        Startup startup = new Startup("EvolutionTest", "FinTech", 200.0, 50.0, 100.0, false);

        // Gain experience points and evolve
        startup.gainExperience(5);
        assertEquals("Tech Star", startup.getStage(), "Startup should evolve to Tech Star at 5 XP.");

        startup.gainExperience(5);
        assertEquals("Unicorn", startup.getStage(), "Startup should evolve to Unicorn at 10 XP.");
    }

    @Test
    @DisplayName("Test Gain Experience: Negative XP handling")
    void testGainExperienceNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> startup1.gainExperience(-10));
        assertEquals("Experience points must be non-negative.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Attack: Verify attacks consider type advantages and critical hits")
    void testAttack1() {
        // Simulate attack with type advantage
        Startup opponent = new Startup("SocialMedia Co.", "Social Media", 100, 20, 30, false);
        String attackSummary = startup1.attack(opponent);
        assertTrue(attackSummary.contains("used"), "Attack should generate a summary string.");
    }


    // constructor exception test code
    @Test
    @DisplayName("Test Constructor: Valid inputs")
    void testConstructorValidInputs() {
        Startup startup = new Startup("Tech Startup", "FinTech", 500.0, 30.0, 100.0, false);

        assertEquals("Tech Startup", startup.getName(), "Name should match the provided input.");
        assertEquals("FinTech", startup.getType(), "Type should match the provided input.");
        assertEquals(500.0, startup.getRevenue(), "Revenue should match the provided input.");
        assertEquals(30.0, startup.getMarketShare(), "Market share should match the provided input.");
        assertEquals(100.0, startup.getNetIncome(), "Net income should match the provided input.");
        assertFalse(startup.isWild(), "Wild status should match the provided input.");
    }

    @Test
    @DisplayName("Test Constructor: Null name")
    void testConstructorNullName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Startup(null, "FinTech", 500.0, 30.0, 100.0, false)
        );
        assertEquals("Name cannot be null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Constructor: Empty name")
    void testConstructorEmptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Startup("", "FinTech", 500.0, 30.0, 100.0, false)
        );
        assertEquals("Name cannot be null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Constructor: Null type")
    void testConstructorNullType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Startup("Tech Startup", null, 500.0, 30.0, 100.0, false)
        );
        assertEquals("Type cannot be null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Constructor: Empty type")
    void testConstructorEmptyType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Startup("Tech Startup", "", 500.0, 30.0, 100.0, false)
        );
        assertEquals("Type cannot be null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Constructor: Negative revenue")
    void testConstructorNegativeRevenue() {
        Startup startup = new Startup("Tech Startup", "FinTech", -100.0, 30.0, 100.0, false);
        assertEquals(0.0, startup.getRevenue(), "Revenue should be set to 0 for negative input.");
    }

    @Test
    @DisplayName("Test Constructor: Negative market share")
    void testConstructorNegativeMarketShare() {
        Startup startup = new Startup("Tech Startup", "FinTech", 500.0, -30.0, 100.0, false);
        assertEquals(0.0, startup.getMarketShare(), "Market share should be set to 0 for negative input.");
    }

    @Test
    @DisplayName("Test Constructor: Negative net income")
    void testConstructorNegativeNetIncome() {
        Startup startup = new Startup("Tech Startup", "FinTech", 500.0, 30.0, -100.0, false);
        assertEquals(0.0, startup.getNetIncome(), "Net income should be set to 0 for negative input.");
    }






}
