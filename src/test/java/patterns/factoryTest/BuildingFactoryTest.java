package patterns.factoryTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import patterns.factory.Building;
import patterns.factory.BuildingFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the BuildingFactory.
 * Verifies the correct behavior of the Factory Pattern.
 */
public class BuildingFactoryTest {

    /**
     * Test to ensure an Office building is created correctly.
     */
    @Test
    @DisplayName("Test Office Building Creation")
    public void testCreateOfficeBuilding() {
        // Create an Office building using the factory.
        Building office = BuildingFactory.createBuilding("office");

        // Assert the created object is not null.
        assertNotNull(office, "Office building should not be null");

        // Call the construct method and ensure it runs correctly.
        office.construct(); // Should print "Constructing an Office Building."
    }

    /**
     * Test to ensure a Factory building is created correctly.
     */
    @Test
    @DisplayName("Test Factory Building Creation")
    public void testCreateFactoryBuilding() {
        Building factory = BuildingFactory.createBuilding("factory");
        assertNotNull(factory, "Factory building should not be null");
        factory.construct(); // Should print "Constructing a Factory Building."
    }

    /**
     * Test to ensure a Store building is created correctly.
     */
    @Test
    @DisplayName("Test Store Building Creation")
    public void testCreateStoreBuilding() {
        Building store = BuildingFactory.createBuilding("store");
        assertNotNull(store, "Store building should not be null");
        store.construct(); // Should print "Constructing a Store Building."
    }

    /**
     * Test to ensure an exception is thrown for unknown building types.
     */
    @Test
    @DisplayName("Test Invalid Building Type")
    public void testInvalidBuildingType() {
        // Expect an exception for an unknown building type.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            BuildingFactory.createBuilding("unknown");
        });

        // Assert the exception message is as expected.
        assertEquals("Unknown building type: unknown", exception.getMessage());
    }
}
