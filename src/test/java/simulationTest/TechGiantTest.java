package simulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the TechGiant class.
 */
public class TechGiantTest {

    private TechGiant techGiant;

    @BeforeEach
    void setUp() {
        techGiant = new TechGiant("TechCorp", 5000.0);
    }

    @Test
    @DisplayName("Test TechGiant Initialization")
    void testInitialization() {
        assertEquals("TechCorp", techGiant.getName(), "Tech Giant name should be 'TechCorp'");
        assertEquals(5000.0, techGiant.getFunds(), "Initial funds should be 5000.0");
        assertTrue(techGiant.getStartups().isEmpty(), "Tech Giant should start with no startups.");
    }

    @Test
    @DisplayName("Test Adding Startups")
    void testAddStartup() {
        Startup startup = new Startup("FinTechPro", "FinTech", 1000, 20, 50, false);
        techGiant.addStartup(startup);

        List<Startup> startups = techGiant.getStartups();
        assertEquals(1, startups.size(), "Tech Giant should have one startup.");
        assertEquals("FinTechPro", startups.get(0).getName(), "Startup name should be 'FinTechPro'.");
    }

    @Test
    @DisplayName("Test Investing in Startups")
    void testInvestInStartup() {
        Startup startup = new Startup("HealthFirst", "Healthcare", 1200, 15, 40, false);
        techGiant.addStartup(startup);

        techGiant.investInStartup(startup, 2000);
        assertEquals(3000.0, techGiant.getFunds(), "Funds should be reduced after investment.");
        assertEquals(20, startup.getExperiencePoints(), "Startup should gain experience points.");
    }

    @Test
    @DisplayName("Test Investing with Insufficient Funds")
    void testInvestWithInsufficientFunds() {
        Startup startup = new Startup("HealthFirst", "Healthcare", 1200, 15, 40, false);
        techGiant.addStartup(startup);

        techGiant.investInStartup(startup, 6000); // Attempt to invest more than available funds
        assertEquals(5000.0, techGiant.getFunds(), "Funds should not change when investment exceeds available funds.");
        assertEquals(0, startup.getExperiencePoints(), "Startup should not gain experience points when investment fails.");
    }

    @Test
    @DisplayName("Test Removing Startups")
    void testRemoveStartup() {
        Startup startup = new Startup("HealthFirst", "Healthcare", 1200, 15, 40, false);
        techGiant.addStartup(startup);
        techGiant.removeStartup(startup);

        assertTrue(techGiant.getStartups().isEmpty(), "Startup should be removed.");
    }

    @Test
    @DisplayName("Test Gaining Startup from Opponent")
    void testGainingStartupFromOpponent() {
        TechGiant opponent = new TechGiant("Innovators Inc.", 7000.0);

        Startup startup1 = new Startup("FinTechPro", "FinTech", 1000, 20, 50, false);
        Startup startup2 = new Startup("HealthFirst", "Healthcare", 1200, 15, 40, false);

        techGiant.addStartup(startup1);
        opponent.addStartup(startup2);

        // Act
        Startup winner = techGiant.battle(opponent);

        // Assert
        if (winner == startup1) {
            assertTrue(techGiant.getStartups().contains(startup2), "Tech Giant should gain opponent's startup.");
            assertFalse(opponent.getStartups().contains(startup2), "Opponent should lose the startup.");
        } else {
            assertTrue(opponent.getStartups().contains(startup1), "Opponent should gain Tech Giant's startup.");
            assertFalse(techGiant.getStartups().contains(startup1), "Tech Giant should lose its startup.");
        }
    }

    @Test
    @DisplayName("Test Purchasing Enhancement")
    public void testPurchaseEnhancement() {
        TechGiant techGiant = new TechGiant("InnovateInc", 2000);
        Enhancement loan = new Enhancement("Loan", "Loan", 500, 0, 1000);

        techGiant.purchaseEnhancement(loan);
        assertEquals(1500, techGiant.getFunds());
        assertTrue(techGiant.getActiveEnhancements().contains(loan));
    }

    @Test
    @DisplayName("Test Applying Enhancements")
    public void testApplyEnhancements() {
        TechGiant techGiant = new TechGiant("InnovateInc", 1000);
        Enhancement loan = new Enhancement("Loan", "Loan", 0, 0, 500);

        techGiant.purchaseEnhancement(loan);
        techGiant.applyEnhancements();

        assertEquals(1500, techGiant.getFunds());
        assertTrue(techGiant.getActiveEnhancements().isEmpty());
    }

    @Test
    @DisplayName("Test Apply Enhancements - Revenue Boost")
    public void testApplyEnhancementsRevenueBoost() {
        // Create a TechGiant
        TechGiant techGiant = new TechGiant("TechCorp", 5000);

        // Create a Startup and add it to the TechGiant
        Startup startup = new Startup("HealthTech", "Healthcare", 1000, 10, 500, false);
        techGiant.addStartup(startup);

        // Add a Revenue Booster enhancement to the TechGiant
        Enhancement revenueBooster = new Enhancement("Revenue Booster", "Revenue", 500, 0, 0.2); // 20% boost
        techGiant.purchaseEnhancement(revenueBooster);

        // Apply enhancements
        techGiant.applyEnhancements();

        // Verify revenue boost
        double expectedRevenue = 1000 + (1000 * 0.2); // 20% boost to revenue
        assertEquals(expectedRevenue, startup.getRevenue(), 0.01, "Startup revenue should be boosted by 20%.");

        // Verify enhancement removal
        assertTrue(techGiant.getActiveEnhancements().isEmpty(), "Active enhancements list should be empty after applying one-time enhancements.");
    }


    @Test
    @DisplayName("Test Purchase Enhancement - Insufficient Funds")
    public void testPurchaseEnhancementInsufficientFunds() {
        // Create a TechGiant with low funds
        TechGiant techGiant = new TechGiant("TechCorp", 100);

        // Create an enhancement that costs more than the available funds
        Enhancement expensiveEnhancement = new Enhancement("Premium Loan", "Loan", 500, 0, 1000);

        // Attempt to purchase the enhancement
        techGiant.purchaseEnhancement(expensiveEnhancement);

        // Verify funds remain unchanged
        assertEquals(100, techGiant.getFunds(), 0.01, "Funds should remain unchanged when purchase fails.");

        // Verify the enhancement is not added
        assertTrue(techGiant.getActiveEnhancements().isEmpty(), "No enhancements should be added when funds are insufficient.");
    }

    @Test
    @DisplayName("Test Apply Enhancements - Default Case")
    public void testApplyEnhancementsDefaultCase() {
        // Create a TechGiant
        TechGiant techGiant = new TechGiant("TechCorp", 5000);

        // Create an unknown type of enhancement
        Enhancement unknownEnhancement = new Enhancement("Mystery Boost", "Unknown", 200, 0, 0);

        // Add the unknown enhancement to the TechGiant
        techGiant.purchaseEnhancement(unknownEnhancement);

        // Capture system output (if desired, use a mock logger for real projects)
        techGiant.applyEnhancements();

        // Verify the enhancement is not removed (as its type is unknown)
        assertFalse(techGiant.getActiveEnhancements().isEmpty(), "Unknown enhancements should remain in the list.");

        // Verify funds remain unchanged
        assertEquals(4800, techGiant.getFunds(), 0.01, "Funds should only be reduced by the enhancement's cost, with no further changes.");
    }


}
