package simulationTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.Enhancement;

import simulation.TechGiant;
import simulation.SimulationManager;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Enhancement Class Tests")
public class EnhancementTest {

    @Test
    @DisplayName("Test Enhancement Constructor and Getters")
    public void testConstructorAndGetters() {
        System.out.println("=== Test: Enhancement Constructor and Getters ===");

        // Create an enhancement object
        Enhancement enhancement = new Enhancement("Loan", "Financial", 1000, 0, 1000);

        // Validate constructor initialization
        assertEquals("Loan", enhancement.getName(), "Name should be initialized correctly.");
        assertEquals("Financial", enhancement.getType(), "Type should be initialized correctly.");
        assertEquals(1000, enhancement.getCost(), "Cost should be initialized correctly.");
        assertEquals(0, enhancement.getDuration(), "Duration should be initialized correctly.");
        assertEquals(1000, enhancement.getEffectValue(), "Effect value should be initialized correctly.");
    }

    @Test
    @DisplayName("Test Negative or Invalid Input Handling")
    public void testInvalidInputs() {
        System.out.println("=== Test: Negative or Invalid Inputs ===");

        // Negative cost
        Enhancement enhancementNegativeCost = new Enhancement("Revenue Booster", "Revenue", -500, 2, 20);
        assertEquals(-500, enhancementNegativeCost.getCost(), "Cost should match the provided value, even if negative.");

        // Zero duration
        Enhancement enhancementZeroDuration = new Enhancement("One-Time Loan", "Financial", 1000, 0, 500);
        assertEquals(0, enhancementZeroDuration.getDuration(), "Duration should allow zero for one-time effects.");
    }

    @Test
    @DisplayName("Test Different Enhancement Types")
    public void testEnhancementTypes() {
        System.out.println("=== Test: Different Enhancement Types ===");

        Enhancement loan = new Enhancement("Loan", "Financial", 1000, 0, 1000);
        Enhancement revenueBooster = new Enhancement("Revenue Booster", "Revenue", 500, 3, 20);

        // Validate type-specific properties
        assertEquals("Financial", loan.getType(), "Loan should have a type of Financial.");
        assertEquals("Revenue", revenueBooster.getType(), "Revenue Booster should have a type of Revenue.");
        assertEquals(3, revenueBooster.getDuration(), "Revenue Booster should have a duration of 3 quarters.");
    }

    @Test
    @DisplayName("Test Large Values for Enhancements")
    public void testLargeValues() {
        System.out.println("=== Test: Large Values for Enhancements ===");

        // Create an enhancement with large values
        Enhancement largeEnhancement = new Enhancement("Mega Loan", "Financial", 1_000_000, 10, 50_000);

        // Validate properties
        assertEquals(1_000_000, largeEnhancement.getCost(), "Cost should handle large values correctly.");
        assertEquals(10, largeEnhancement.getDuration(), "Duration should handle large values correctly.");
        assertEquals(50_000, largeEnhancement.getEffectValue(), "Effect value should handle large values correctly.");
    }

    @Test
    @DisplayName("Test Zero and Edge Cases for Values")
    public void testEdgeCases() {
        System.out.println("=== Test: Edge Cases for Values ===");

        // Zero values
        Enhancement zeroEnhancement = new Enhancement("Basic Revenue", "Revenue", 0, 0, 0);

        assertEquals(0, zeroEnhancement.getCost(), "Cost should allow zero.");
        assertEquals(0, zeroEnhancement.getDuration(), "Duration should allow zero.");
        assertEquals(0, zeroEnhancement.getEffectValue(), "Effect value should allow zero.");
    }


    @Test
    @DisplayName("Test Enhancement Creation")
    public void testEnhancementCreation() {
        Enhancement loan = new Enhancement("Loan", "Loan", 0, 0, 1000);
        assertEquals("Loan", loan.getName());
        assertEquals("Loan", loan.getType());
        assertEquals(1000, loan.getEffectValue());
    }


}
