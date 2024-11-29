package patterns.strategyTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import patterns.strategy.Strategy;
import patterns.strategy.Context;
import patterns.strategy.AggressiveStrategy;
import patterns.strategy.DefensiveStrategy;

/**
 * Unit tests for the Strategy Pattern implementation.
 * Tests the functionality of various strategies and their dynamic switching within the Context class.
 */
public class StrategyPatternTest {

    /**
     * Displays the results of a specific strategy test.
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


    /**
     * Tests the execute method directly in AggressiveStrategy.
     * Verifies the correct message is returned when executed without a context.
     */
    @Test
    @DisplayName("Test AggressiveStrategy execute method directly")
    void testAggressiveStrategyExecuteMethod() {
        // Arrange
        AggressiveStrategy aggressiveStrategy = new AggressiveStrategy();

        // Act
        String result = aggressiveStrategy.execute();

        // Display
        display("AggressiveStrategy Execute", result);

        // Assert
        assertEquals("Executing aggressive strategy: prioritize growth and expansion.", result,
                "AggressiveStrategy execute method did not return the expected output.");
    }

    /**
     * Tests the setStrategy method in Context.
     * Verifies that switching strategies dynamically works as expected.
     */
    @Test
    @DisplayName("Test Context setStrategy method independently")
    void testSetStrategyMethodInContext() {
        // Arrange
        Context context = new Context(new DefensiveStrategy());

        // Act & Display for Defensive Strategy
        String initialResult = context.executeStrategy();
        display("Initial Strategy (Defensive)", initialResult);

        // Switch to AggressiveStrategy
        context.setStrategy(new AggressiveStrategy());
        String switchedResult = context.executeStrategy();
        display("Switched Strategy (Aggressive)", switchedResult);

        // Assert
        assertEquals("Executing defensive strategy: focus on risk management and consolidation.", initialResult,
                "Initial defensive strategy did not return the expected output.");
        assertEquals("Executing aggressive strategy: prioritize growth and expansion.", switchedResult,
                "Switched aggressive strategy did not return the expected output.");
    }

    /**
     *  Simulates a real-world scenario using the Strategy Pattern.
     * Verifies that strategies respond appropriately to market events.
     */
    @Test
    @DisplayName("Test Real-World Simulation Scenarios")
    void testSimulationScenarios() {
        // Arrange
        Context context = new Context(new DefensiveStrategy());
        String eventQ2 = "Economic Downturn";
        String eventQ4 = "Competitive Battles";

        // Act & Assert for Q2 Event
        System.out.println("Simulating Q2 Event: " + eventQ2);
        String defensiveResult = context.executeStrategy();
        display("Q2 Defensive Strategy", defensiveResult);
        assertEquals("Executing defensive strategy: focus on risk management and consolidation.", defensiveResult,
                "Defensive strategy did not handle Q2 event as expected.");

        // Switch strategy for Q4 Event
        context.setStrategy(new AggressiveStrategy());
        System.out.println("Simulating Q4 Event: " + eventQ4);
        String aggressiveResult = context.executeStrategy();
        display("Q4 Aggressive Strategy", aggressiveResult);
        assertEquals("Executing aggressive strategy: prioritize growth and expansion.", aggressiveResult,
                "Aggressive strategy did not handle Q4 event as expected.");
    }
}
