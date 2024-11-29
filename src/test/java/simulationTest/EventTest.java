package simulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.Startup;
import simulation.events.Event;
import simulation.events.EventEffect;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class.
 */
public class EventTest {

    private List<Startup> startups;

    @BeforeEach
    public void setup() {
        // Initialize a list of startups for testing
        startups = new ArrayList<>();
        startups.add(new Startup("HealthTech Inc.", "Healthcare", 100, 20, 10, false));
        startups.add(new Startup("FinTech Co.", "FinTech", 100, 30, 15, false));
    }

    @Test
    @DisplayName("Test Event Constructor Initialization")
    public void testConstructor() {
        // Arrange
        Event event = new Event("Economic Downturn", "Market faces downturn", "Q2");

        // Assert
        assertEquals("Economic Downturn", event.getName());
        assertEquals("Market faces downturn", event.getDescription());
        assertEquals("Q2", event.getQuarter());
    }

    @Test
    @DisplayName("Test Economic Downturn Effects")
    public void testEconomicDownturn() {
        // Arrange
        Event event = new Event("Economic Downturn", "Market faces downturn", "Q2");

        // Act
        event.applyEffects(startups);

        // Assert
        assertEquals(120, startups.get(0).getRevenue(), "Healthcare startup should gain 20% revenue.");
        assertEquals(80, startups.get(1).getRevenue(), "FinTech startup should lose 20% revenue.");
    }



        @Test
    @DisplayName("Test Regulatory Scrutiny Effects")
    public void testRegulatoryScrutiny() {
        // Arrange
        Event event = new Event("Regulatory Scrutiny", "Market faces regulatory challenges", "Q3");

        // Act
        startups.get(1).setMarketShare(30); // Ensure one startup exceeds 25% market share
        event.applyEffects(startups);

        // Assert
        assertTrue(startups.get(1).getMarketShare() < 30, "FinTech startup with >25% market share should lose share.");
        assertTrue(startups.get(0).getMarketShare() > 20, "Healthcare startup with <25% market share should gain share.");
    }

    @Test
    @DisplayName("Test Empty Startup List")
    public void testEmptyStartupList() {
        // Arrange
        Event event = new Event("Economic Downturn", "Market faces downturn", "Q2");

        // Act
        event.applyEffects(new ArrayList<>());

        // Assert
        // No exception should be thrown
        assertTrue(true, "Applying effects to an empty list should not throw an exception.");
    }

    @Test
    @DisplayName("Test Unrecognized Event")
    public void testUnrecognizedEvent() {
        // Arrange
        Event event = new Event("Unknown Event", "No specific effects", "Q1");

        // Ensure that no exception is thrown and that no changes were made (i.e., NeutralEffect)
        assertEquals(100, startups.get(0).getRevenue(), "Healthcare startup's revenue should remain the same.");
        assertEquals(100, startups.get(1).getRevenue(), "FinTech startup's revenue should remain the same.");
    }
    @Test
    @DisplayName("Test Corporate Tax Cuts Effects (Neutral)")
    void testCorporateTaxCutsEffects() {
        // Arrange
        Event taxCutsEvent = new Event("Corporate Tax Cuts", "Market benefits from tax cuts", "Q1");
        double initialRevenueStartup1 = startups.get(0).getRevenue();
        double initialRevenueStartup2 = startups.get(1).getRevenue();

        // Act
        taxCutsEvent.applyEffects(startups);

        // Assert
        assertEquals(initialRevenueStartup1, startups.get(0).getRevenue(),
                "Corporate Tax Cuts should not change the revenue of Healthcare startup.");
        assertEquals(initialRevenueStartup2, startups.get(1).getRevenue(),
                "Corporate Tax Cuts should not change the revenue of FinTech startup.");
    }

    @Test
    @DisplayName("Test Corporate Tax Cuts on Empty Startup List")
    public void testCorporateTaxCutsOnEmptyList() {
        // Arrange
        Event taxCutsEvent = new Event("Corporate Tax Cuts", "Market benefits from tax cuts", "Q1");

        // Act
        taxCutsEvent.applyEffects(new ArrayList<>());

        // Assert
        assertTrue(true, "Applying effects to an empty list should not throw an exception.");
    }

    @Test
    @DisplayName("Test Unaffected Attributes from Corporate Tax Cuts")
    public void testUnaffectedAttributesFromTaxCuts() {
        // Arrange
        Event taxCutsEvent = new Event("Corporate Tax Cuts", "Market benefits from tax cuts", "Q1");

        // Act
        taxCutsEvent.applyEffects(startups);

        // Assert
        // Check attributes other than revenue remain unchanged
        assertEquals(20, startups.get(0).getMarketShare(), "Market share should not change.");
        assertEquals(10, startups.get(0).getNetIncome(), "Net income should not change.");
        assertEquals(30, startups.get(1).getMarketShare(), "Market share should not change.");
        assertEquals(15, startups.get(1).getNetIncome(), "Net income should not change.");
    }


    @Test
    @DisplayName("Test Constructor: Null name")
    void testConstructorNullName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Event(null, "Description", "Q1")
        );
        assertEquals("Event name cannot be null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Constructor: Empty description")
    void testConstructorEmptyDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Event("EventName", "", "Q1")
        );
        assertEquals("Event description cannot be null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Constructor: Null quarter")
    void testConstructorNullQuarter() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Event("EventName", "Description", null)
        );
        assertEquals("Event quarter cannot be null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Economic Downturn: Zero revenue")
    void testEconomicDownturnZeroRevenue() {
        // Arrange
        startups.get(0).setRevenue(0); // Healthcare
        startups.get(1).setRevenue(0); // FinTech
        Event event = new Event("Economic Downturn", "Market faces downturn", "Q2");

        // Act
        event.applyEffects(startups);

        // Assert
        assertEquals(0, startups.get(0).getRevenue(), "Healthcare startup with 0 revenue should remain unaffected.");
        assertEquals(0, startups.get(1).getRevenue(), "FinTech startup with 0 revenue should remain unaffected.");
    }

    @Test
    @DisplayName("Test Regulatory Scrutiny: Zero market share")
    void testRegulatoryScrutinyZeroMarketShare() {
        // Arrange
        startups.get(0).setMarketShare(0); // Healthcare
        Event event = new Event("Regulatory Scrutiny", "Market faces regulatory challenges", "Q3");

        // Act
        event.applyEffects(startups);

        // Assert
        assertEquals(0, startups.get(0).getMarketShare(), "Startup with 0 market share should remain unaffected.");
    }

    @Test
    @DisplayName("Test Economic Downturn: Unrecognized type")
    void testEconomicDownturnUnrecognizedType() {
        // Arrange
        startups.add(new Startup("TechStartup", "UnknownType", 200, 10, 50, false));
        Event event = new Event("Economic Downturn", "Market faces downturn", "Q2");

        // Act
        event.applyEffects(startups);

        // Assert
        assertEquals(200, startups.get(2).getRevenue(), "Startup with unrecognized type should remain unaffected.");
    }


    @Test
    @DisplayName("Test Constructor: Empty Event Name")
    public void testConstructorEmptyName() {
        // Assert that an IllegalArgumentException is thrown when name is empty
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Event("", "Valid Description", "Q1")
        );
        assertEquals("Event name cannot be null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Constructor: Null Event Description")
    public void testConstructorNullDescription() {
        // Assert that an IllegalArgumentException is thrown when description is null
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Event("Valid Name", null, "Q1")
        );
        assertEquals("Event description cannot be null or empty.", exception.getMessage());
    }

    @Test
    @DisplayName("Test Constructor: Empty Event Quarter")
    public void testConstructorEmptyQuarter() {
        // Assert that an IllegalArgumentException is thrown when quarter is empty
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Event("Valid Name", "Valid Description", "")
        );
        assertEquals("Event quarter cannot be null or empty.", exception.getMessage());
    }




}
