package patterns.singletonTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import patterns.factory.Building;
import patterns.factory.Office;
import patterns.factory.Store;
import patterns.singleton.SimulationController;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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
     * Test to verify the functionality of adding startups.
     */
    @Test
    @DisplayName("Verify Adding Startups")
    public void testAddStartups() {
        // Arrange
        SimulationController instance = SimulationController.getInstance();

        // Create startups
        Building office = new Office();
        Building store = new Store();

        // Act
        instance.addStartup(office);
        instance.addStartup(store);

        // Assert
        List<Building> startups = instance.getStartups();
        assertNotNull(startups, "Startups list should not be null");
        assertEquals(2, startups.size(), "Startups list size should be 2");
        assertTrue(startups.contains(office), "Startups list should contain the Office startup");
        assertTrue(startups.contains(store), "Startups list should contain the Store startup");
    }

    /**
     * Test to verify the startup list retrieval.
     */
    @Test
    @DisplayName("Verify Retrieving Startups")
    public void testGetStartups() {
        // Arrange
        SimulationController instance = SimulationController.getInstance();

        // Act
        List<Building> startups = instance.getStartups();

        // Assert
        assertNotNull(startups, "Startups list should not be null");
    }

    @Test
    @DisplayName("Verify Constructor Initialization")
    public void testConstructorInitialization() {
        SimulationController instance = SimulationController.getInstance();
        List<Building> startups = instance.getStartups();
        assertNotNull(startups, "Startups list should be initialized in the constructor");
    }


}
