package simulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.*;
import java.util.ArrayList;

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

    @Test
    @DisplayName("Test Select Startup for Battle")
    void testSelectStartupForBattle() {
        Startup startup1 = new Startup("StartupOne", "Operating Systems", 2000, 20, 50, false);
        Startup startup2 = new Startup("StartupTwo", "Social Media", 1500, 25, 40, false);
        Startup startup3 = new Startup("StartupThree", "Tech", 3000, 15, 60, false);

        techGiant.addStartup(startup1);
        techGiant.addStartup(startup2);
        techGiant.addStartup(startup3);

        Startup selectedStartup = techGiant.selectStartupForBattle();

        assertEquals(startup3, selectedStartup, "TechGiant should select the startup with the highest revenue.");
    }

    @Test
    @DisplayName("Test Investing with Exact Funds")
    void testInvestWithExactFunds() {
        Startup startup = new Startup("TechNow", "Tech", 1000, 20, 50, false);
        techGiant.addStartup(startup);

        // Set funds exactly equal to the investment amount
        techGiant.setFunds(1000.0);
        techGiant.investInStartup(startup, 1000.0);

        // Assert that funds are correctly reduced
        assertEquals(0.0, techGiant.getFunds(), "Funds should be reduced to zero after the investment.");
        assertEquals(10, startup.getExperiencePoints(), "Startup should gain experience points based on the investment.");
    }



    @Test
    void testTechGiantConstructorWithName() {
        // Act
        TechGiant techGiant = new TechGiant("TechCorp");

        // Assert
        assertNotNull(techGiant, "TechGiant object should not be null");
        assertEquals("TechCorp", techGiant.getName(), "TechGiant name should be correctly initialized");
        assertEquals(5000.0, techGiant.getFunds(), "TechGiant should have the default funds set to 5000");
    }


    // test cases to test setfunds()
    @Test
    void testSetFundsWithPositiveValue() {
        // Arrange
        TechGiant techGiant = new TechGiant("TechCorp");

        // Act
        techGiant.setFunds(10000);

        // Assert
        assertEquals(10000, techGiant.getFunds(), "Funds should be set to the positive value provided");
    }

    @Test
    void testSetFundsWithZero() {
        // Arrange
        TechGiant techGiant = new TechGiant("TechCorp");

        // Act
        techGiant.setFunds(0);

        // Assert
        assertEquals(0, techGiant.getFunds(), "Funds should be set to 0 when zero is provided");
    }

    @Test
    void testSetFundsWithNegativeValue() {
        // Arrange
        TechGiant techGiant = new TechGiant("TechCorp");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            techGiant.setFunds(-5000);
        });
        assertEquals("Funds cannot be negative.", exception.getMessage(), "Exception message should indicate funds cannot be negative");
    }

    @Test
    void testBattleWithNoStartups() {
        // Arrange
        TechGiant techGiant = new TechGiant("TechCorp");
        techGiant.setStartups(new ArrayList<>());  // Empty list

        TechGiant opponent = new TechGiant("InnovateInc");
        opponent.setStartups(new ArrayList<>());  // Empty list

        // Act
        Startup result = techGiant.battle(opponent);

        // Assert
        assertNull(result, "Battle should return null when both teams have no startups left to battle.");
    }





}
