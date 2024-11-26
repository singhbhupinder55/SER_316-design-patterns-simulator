package simulationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import simulation.Event;
import simulation.Startup;

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

        // Act & Assert
        assertDoesNotThrow(() -> event.applyEffects(startups), "Unrecognized event should not throw exceptions.");
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
}
