package simulation.events;

import java.util.List;
import simulation.Startup;

/**
 * Represents an event that affects the market.
 * Events can have positive or negative impacts on startups.
 */
public class Event {

    private String name;         // Name of the event, e.g., "Economic Downturn"
    private String description;  // Detailed description of the event
    private String quarter;      // Quarter in which the event occurs
    private EventEffect effect;  // Strategy pattern: different effects for each event type

    /**
     * Constructs an Event with the given name, description, quarter, and effect.
     * @param name        Name of the event
     * @param description Description of the event
     * @param quarter     Quarter in which the event occurs
     */
    public Event(String name, String description, String quarter) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be null or empty.");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Event description cannot be null or empty.");
        }
        if (quarter == null || quarter.isEmpty()) {
            throw new IllegalArgumentException("Event quarter cannot be null or empty.");
        }

        this.name = name;
        this.description = description;
        this.quarter = quarter;
        this.effect = determineEffect(name);  // Determine the effect based on the event name
    }

    /**
     * Retrieves the name of the event.
     * @return The name of the event.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the description of the event.
     * @return The description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the quarter during which the event occurs.
     * @return The quarter (e.g., "Q1").
     */
    public String getQuarter() {
        return quarter;
    }

    /**
     * Determines the appropriate event effect based on the event's name.
     * @param name The name of the event.
     * @return The EventEffect instance corresponding to the event type.
     */
    private EventEffect determineEffect(String name) {
        switch (name) {
            case "Economic Downturn":
                return new EconomicDownturnEffect();
            case "Corporate Tax Cuts":
                return new CorporateTaxCutsEffect();
            case "Regulatory Scrutiny":
                return new RegulatoryScrutinyEffect();
            default:
                return new NeutralEffect();
        }
    }

    /**
     * Applies the event's effects to a list of startups.
     * Logs the changes to startups before and after the event is applied.
     * @param startups The list of startups affected by the event.
     */
    public void applyEffects(List<Startup> startups) {
        System.out.println("-----------------------------------------------------------");
        System.out.println("Applying event: " + name + " (" + quarter + ")");
        for (Startup startup : startups) {
            // Added logging to trace startup effects
            System.out.println("Before Event: " + startup.getName()
                    + " | Revenue: " + startup.getRevenue()
                    + " | Market Share: " + startup.getMarketShare());

            effect.applyEffect(startup); // Delegate to the strategy

            // Added post-event logging
            System.out.println("After Event: " + startup.getName()
                    + " | Revenue: " + startup.getRevenue()
                    + " | Market Share: " + startup.getMarketShare());
            System.out.println("-----------------------------------------------------------\n");
        }
    }
}
