package simulation;

import java.util.List;

/**
 * Represents an event that affects the market.
 * Events can have positive or negative impacts on startups.
 */
public class Event {

    private String name;         // Name of the event, e.g., "Economic Downturn"
    private String description;  // Detailed description of the event
    private String quarter;      // Quarter in which the event occurs (Q1, Q2, Q3, Q4)

    /**
     * Constructs an Event with the given name, description, and quarter.
     *
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
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getQuarter() {
        return quarter;
    }

    /**
     * Applies the event's effects on the list of startups.
     *
     * @param startups the list of startups affected by the event
     */
    public void applyEffects(List<Startup> startups) {
        System.out.println("Applying event: " + name + " (" + quarter + ")");
        for (Startup startup : startups) {

            switch (name) {
                case "Corporate Tax Cuts":
                    handleCorporateTaxCuts(startup);
                    break;

                case "Economic Downturn":
                    handleEconomicDownturn(startup);
                    break;

                case "Regulatory Scrutiny":
                    handleRegulatoryScrutiny(startup);
                    break;

                default:
                    System.out.println("No specific effects implemented for event: " + name);
            }
        }
    }

    /**
     * Handles the effects of the "Corporate Tax Cuts" event (neutral effect).
     *
     * @param startup the affected startup
     */
    private void handleCorporateTaxCuts(Startup startup) {
        System.out.println(startup.getName() + " benefits from Corporate Tax Cuts (no direct effect).");
    }

    /**
     * Handles the effects of the "Economic Downturn" event.
     *
     * @param startup the affected startup
     */
    private void handleEconomicDownturn(Startup startup) {
        System.out.println("Before Event: " + startup.getName() + " Revenue: " + startup.getRevenue());
        switch (startup.getType().toLowerCase()) {

            case "healthcare":
                double revenueBoost = startup.getRevenue() * 0.2; // Boost revenue by 20%
                startup.setRevenue(startup.getRevenue() + revenueBoost); //  Update revenue
                System.out.println(startup.getName() + " boosted by 20% revenue due to " + name + ".");
                break;

            case "fintech":
                double revenueLoss = startup.getRevenue() * 0.2; //  Reduce revenue by 20%
                startup.setRevenue(startup.getRevenue() - revenueLoss); //  Update revenue
                System.out.println(startup.getName() + " reduced revenue by 20% due to " + name + ".");
                break;

            default:
                System.out.println(startup.getName() + " is not affected by " + name + ".");
        }
    }

    /**
     * Handles the effects of the "Regulatory Scrutiny" event.
     *
     * @param startup the affected startup
     */
    private void handleRegulatoryScrutiny(Startup startup) {
        if (startup.getMarketShare() > 25) {
            double marketShareLoss = startup.getMarketShare() * 0.1; // Reduce market share by 10%
            startup.setMarketShare(startup.getMarketShare() - marketShareLoss); // Update market share
            System.out.println(startup.getName() + " was impacted by Regulatory Scrutiny due to high market share.");
        } else {
            double marketShareBoost = startup.getMarketShare() * 0.1; //  Increase market share by 10%
            startup.setMarketShare(startup.getMarketShare() + marketShareBoost); //  Update market share
            System.out.println(startup.getName() + " benefited from Regulatory Scrutiny with increased market share.");
        }
    }
}
