package patterns.singletonTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import patterns.singleton.SimulationController;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test class for the Singleton SimulationController.
 * Verifies that only one instance of SimulationController is created.
 */
public class SimulationControllerTest {

    /**
     * Test to ensure the Singleton instance is the same across calls.
     */
    @Test
    @DisplayName("Verify Singleton Instance Creation")
    public void testSingletonInstance() {
        // Fetch the first instance of the Singleton.
        SimulationController instance1 = SimulationController.getInstance();

        // Fetch the second instance of the Singleton.
        SimulationController instance2 = SimulationController.getInstance();

        // Assert that both references point to the same object.
        assertSame(instance1, instance2, "Both instances should be the same");
    }

    /**
     * Test to verify the functionality of a method in the Singleton class.
     */
    @Test
    @DisplayName("Verify Singleton Method Functionality")
    public void testSingletonMethod() {
        // Fetch the Singleton instance.
        SimulationController instance = SimulationController.getInstance();

        // Call a method on the Singleton instance.
        instance.startSimulation(); // Should print "Starting the Silicon Valley Simulator!"
    }
}
