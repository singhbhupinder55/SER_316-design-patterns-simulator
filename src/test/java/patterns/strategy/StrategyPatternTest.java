package patterns.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Strategy Pattern implementation.
 * Tests the functionality of various strategies and their dynamic switching within the Context class.
 */
class StrategyPatternTest {

    /**
     * Displays the results of a specific strategy test.
     *
     * @param strategyType the type of strategy being tested
     * @param result       the result from executing the strategy
     */
    private void display(String strategyType, String result) {
        System.out.println("Testing " + strategyType + ": " + result);
    }

    /**
     * Tests the aggressive strategy implementation.
     * Verifies the correct message is returned when using the AggressiveStrategy.
     */
    @Test
    @DisplayName("Test Aggressive Strategy: Verifies correct execution message.")
    void testAggressiveStrategy() {
        // Arrange
        Strategy aggressiveStrategy = new AggressiveStrategy();
        Context context = new Context(aggressiveStrategy);

        // Act
        String result = context.executeStrategy();

        // Display
        display("Aggressive Strategy", result);

        // Assert
        assertEquals("Executing aggressive strategy: prioritize growth and expansion.", result,
                "Aggressive strategy did not return the expected output.");
    }

    /**
     * Tests the defensive strategy implementation.
     * Verifies the correct message is returned when using the DefensiveStrategy.
     */
    @Test
    @DisplayName("Test Defensive Strategy: Verifies correct execution message.")
    void testDefensiveStrategy() {
        // Arrange
        Strategy defensiveStrategy = new DefensiveStrategy();
        Context context = new Context(defensiveStrategy);

        // Act
        String result = context.executeStrategy();

        // Display
        display("Defensive Strategy", result);

        // Assert
        assertEquals("Executing defensive strategy: focus on risk management and consolidation.", result,
                "Defensive strategy did not return the expected output.");
    }

    /**
     * Tests dynamic switching of strategies within the Context class.
     * Ensures the output changes appropriately when the strategy is switched.
     */
    @Test
    @DisplayName("Test Strategy Switching: Verifies dynamic switching between strategies.")
    void testStrategySwitching() {
        // Arrange
        Strategy aggressiveStrategy = new AggressiveStrategy();
        Strategy defensiveStrategy = new DefensiveStrategy();
        Context context = new Context(aggressiveStrategy);

        // Act & Display for Aggressive Strategy
        String initialResult = context.executeStrategy();
        display("Initial Strategy (Aggressive)", initialResult);
        assertEquals("Executing aggressive strategy: prioritize growth and expansion.", initialResult,
                "Initial aggressive strategy did not return the expected output.");

        // Switch to Defensive Strategy
        context.setStrategy(defensiveStrategy);
        String switchedResult = context.executeStrategy();
        display("Switched Strategy (Defensive)", switchedResult);
        assertEquals("Executing defensive strategy: focus on risk management and consolidation.", switchedResult,
                "Switched defensive strategy did not return the expected output.");
    }
}
