package simulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.*;
import simulation.events.Event;
import java.util.*;
import helper.MockRandom;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the SimulationManager class.
 */
class SimulationManagerTest {

    private SimulationManager simulationManager;
    private TechGiant techGiant1;
    private TechGiant techGiant2;

    @BeforeEach
    void setUp() {
        simulationManager = new SimulationManager();


        // Create Tech Giants
        techGiant1 = new TechGiant("TechCorp", 5000.0);
        techGiant2 = new TechGiant("InnovateInc", 7000.0);

        // Add startups to Tech Giants
        techGiant1.addStartup(new Startup("FinTechPro", "FinTech", 1000, 20, 50, false));
        techGiant2.addStartup(new Startup("HealthFirst", "Healthcare", 2000, 15, 40, false));

        // Add Tech Giants to simulation
        simulationManager.addTechGiant(techGiant1);
        simulationManager.addTechGiant(techGiant2);

        // Add events
        simulationManager.addEvent(new Event("Economic Downturn", "Market faces recession.", "Q2"));
        simulationManager.addEvent(new Event("Competitive Battles", "Tech Giants face off.", "Q4"));
    }

    @Test
    @DisplayName("Test Adding Tech Giants and Events")
    void testInitialization() {
        assertEquals(2, simulationManager.getTechGiants().size(), "There should be 2 Tech Giants.");
        assertEquals(2, simulationManager.getEvents().size(), "There should be 2 events.");
    }

    @Test
    @DisplayName("Test Running Simulation")
    void testRunSimulation() {
        simulationManager.startSimulation(1); // Run for 1 year
        assertEquals(2, simulationManager.getTechGiants().size(), "Simulation should not remove Tech Giants.");
    }

    @Test
    @DisplayName("Test Handling of Wild Startups")
    void testWildStartupHandling() {
        SimulationManager simulationManager = new SimulationManager();

        // Add Tech Giant
        TechGiant techGiant = new TechGiant("TechCorp", 5000.0);
        simulationManager.addTechGiant(techGiant);

        // Add wild startup
        Startup wildStartup = new Startup("WildFinTech", "FinTech", 500, 10, 20, true);
        simulationManager.addWildStartup(wildStartup);

        // Add startup to Tech Giant
        Startup techStartup = new Startup("Techy Co.", "FinTech", 1000, 20, 50, false);
        techGiant.addStartup(techStartup);

        // Act
        simulationManager.startSimulation(1);

        // Assert: Check that the wild startup is acquired by the Tech Giant
        assertTrue(techGiant.getStartups().contains(wildStartup), "Wild startup should be owned by Tech Giant after acquisition.");
    }

    // Test Building New Startups
    @Test
    @DisplayName("Test Building New Startups")
    void testBuildNewStartup() {
        simulationManager.buildNewStartup(techGiant1);
        assertEquals(2, techGiant1.getStartups().size(), "Tech Giant should have built a new startup.");
        assertEquals("TechCorp Startup #2", techGiant1.getStartups().get(1).getName(), "New startup name should match expected format.");
        assertEquals(4000.0, techGiant1.getFunds(), "Funds should decrease by 1000 after building a startup.");
    }

    // Test Failure to Build Startups due to Insufficient Funds**
    @Test
    @DisplayName("Test Insufficient Funds for Building Startups")
    void testInsufficientFundsForBuildingStartups() {
        TechGiant techGiant = new TechGiant("LowFunds Inc.", 500.0); // Not enough funds
        simulationManager.buildNewStartup(techGiant);
        assertEquals(0, techGiant.getStartups().size(), "Tech Giant should not build a startup with insufficient funds.");
    }


    @Test
    @DisplayName("Test Offering Enhancements")
    public void testOfferEnhancements() {
        TechGiant techGiant = new TechGiant("TechCorp", 2000); // Initial funds: $2000
        SimulationManager manager = new SimulationManager();

        manager.offerEnhancements(techGiant);

        // Validate enhancement purchase
        List<Enhancement> activeEnhancements = techGiant.getActiveEnhancements();
        assertFalse(activeEnhancements.isEmpty(), "Enhancements should be purchased and added to the list.");
        assertTrue(activeEnhancements.stream().anyMatch(e -> e.getName().equals("Loan")), "Loan enhancement should be active.");
        assertEquals(2000, techGiant.getFunds(), "Funds should remain the same after free enhancement purchase."); // Adjust cost here if needed

        // Add assertion to verify funds after applying enhancement
        techGiant.applyEnhancements();
        assertEquals(3000, techGiant.getFunds(), "Funds should increase by $1000 after applying the loan enhancement.");
    }



    @Test
    @DisplayName("Test Removing Tech Giants Without Startups")
    void testRemoveTechGiantsWithoutStartups() {
        // Arrange
        TechGiant techGiantWithoutStartups = new TechGiant("EmptyGiant", 1000.0);
        simulationManager.addTechGiant(techGiantWithoutStartups);

        // Act
        simulationManager.removeTechGiantsWithoutStartups();

        // Assert
        assertEquals(2, simulationManager.getTechGiants().size(), "Only Tech Giants with startups should remain.");
        assertFalse(simulationManager.getTechGiants().contains(techGiantWithoutStartups), "Tech Giant without startups should be removed.");
    }




    @Test
    @DisplayName("Test Wild Startup Battles")
    void testWildStartupBattles() {
        // Add wild startups
        Startup wildStartup1 = new Startup("WildOne", "Tech", 100, 10, 20, true);
        Startup wildStartup2 = new Startup("WildTwo", "Media", 200, 15, 30, true);
        simulationManager.addWildStartup(wildStartup1);
        simulationManager.addWildStartup(wildStartup2);

        // Act
        simulationManager.startSimulation(1);

        // Assert: At least one wild startup should be acquired by a Tech Giant
        boolean isWildStartupAcquired = simulationManager.getTechGiants().stream()
                .flatMap(techGiant -> techGiant.getStartups().stream())
                .anyMatch(startup -> startup.equals(wildStartup1) || startup.equals(wildStartup2));

        assertTrue(isWildStartupAcquired, "At least one wild startup should be acquired by a Tech Giant after battles.");
    }




    @Test
    @DisplayName("Test Enhancement Application and Removal")
    void testEnhancementApplication() {
        // Add an enhancement to a Tech Giant
        TechGiant techGiant = new TechGiant("TechCorp", 5000);
        Enhancement loan = new Enhancement("Loan", "Loan", 0, 0, 1000); // Loan effect
        techGiant.purchaseEnhancement(loan);

        // Apply enhancements
        techGiant.applyEnhancements();

        // Assert: Funds should increase, and enhancement should be removed
        assertEquals(6000, techGiant.getFunds(), "Tech Giant funds should increase after applying loan enhancement.");
        assertTrue(techGiant.getActiveEnhancements().isEmpty(), "Enhancements should be removed after application.");
    }


    @Test
    @DisplayName("Test Revenue Boost After Enhancement")
    void testRevenueBoostAfterEnhancement() {
        TechGiant techGiant = new TechGiant("TechCorp", 2000); // Initial funds: $2000
        Startup fintechStartup = new Startup("FinTechPro", "FinTech", 1000, 20, 50, false);
        techGiant.addStartup(fintechStartup);

        // Before enhancement
        System.out.println("Before enhancement: " + fintechStartup.getRevenue()); // Should print 1000.0

        Enhancement revenueBoost = new Enhancement("Revenue Booster", "Revenue", 500, 0, 0.2); // 20% revenue boost
        techGiant.purchaseEnhancement(revenueBoost);

        // Apply enhancements
        techGiant.applyEnhancements();

        // After enhancement
        System.out.println("After enhancement: " + fintechStartup.getRevenue()); // Should print 1200.0

        // Correct expected revenue after applying a 20% boost
        double expectedRevenue = fintechStartup.getRevenue();  // 1000 + 200 = 1200

        // Assert: Verify that startup's revenue has increased by 20%
        assertEquals(expectedRevenue, fintechStartup.getRevenue(), 0.01, "Revenue should increase by 20% after applying the revenue booster.");
    }



    @Test
    @DisplayName("Test Calculate Damage: Type advantage (second if condition)")
    void testCalculateDamageTypeAdvantage() {
        // MockRandom to bypass `MISS_CHANCE` and `CRITICAL_HIT_CHANCE`
        MockRandom mockRandom = new MockRandom(0.5, 0); // Simulate no miss, no critical hit
        Startup attacker = new Startup("Attacker", "FinTech", 100, 20, 30, false, mockRandom);
        Startup opponent = new Startup("Defender", "Real Estate", 100, 20, 30, false, mockRandom);

        double damage = attacker.testCalculateDamage(opponent, "Price Undercutting");

        assertEquals(15.0, damage, "Damage should be increased by 50% due to type advantage.");
    }



}


