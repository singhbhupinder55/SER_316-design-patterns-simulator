package simulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.*;

import java.util.*;


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
    }


}
