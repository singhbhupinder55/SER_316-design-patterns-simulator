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
        assertEquals("Office", office.getClass().getSimpleName(), "The created building should be of type Office");
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
        assertEquals("Factory", factory.getClass().getSimpleName(), "The created building should be of type Factory");
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
        assertEquals("Store", store.getClass().getSimpleName(), "The created building should be of type Store");
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

    /**
     * Test to ensure invalid inputs are handled correctly.
     */
    @Test
    @DisplayName("Test Null and Empty Building Type")
    public void testNullAndEmptyBuildingType() {

        // Test null input.
        Exception nullException = assertThrows(IllegalArgumentException.class, () -> {
            BuildingFactory.createBuilding(null);
        });
        assertEquals("Building type cannot be null or empty", nullException.getMessage());

        // Test empty string input.
        Exception emptyException = assertThrows(IllegalArgumentException.class, () -> {
            BuildingFactory.createBuilding("");
        });
        assertEquals("Building type cannot be null or empty", emptyException.getMessage());
    }

    /**
     * Test to ensure input type is case-insensitive.
     */
    @Test
    @DisplayName("Test Case Insensitivity")
    public void testCaseInsensitivity() {
        // Act
        Building office = BuildingFactory.createBuilding("OfFiCe");
        Building factory = BuildingFactory.createBuilding("FaCtOrY");
        Building store = BuildingFactory.createBuilding("SToRe");

        // Assert
        assertNotNull(office, "Office building should not be null");
        assertEquals("Office", office.getClass().getSimpleName(), "The created building should be of type Office");

        assertNotNull(factory, "Factory building should not be null");
        assertEquals("Factory", factory.getClass().getSimpleName(), "The created building should be of type Factory");

        assertNotNull(store, "Store building should not be null");
        assertEquals("Store", store.getClass().getSimpleName(), "The created building should be of type Store");
    }
}
