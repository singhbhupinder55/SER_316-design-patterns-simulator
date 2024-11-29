package simulation.events;

import simulation.Startup;

/**
 * Effect for the "Economic Downturn" event.
 */
public class EconomicDownturnEffect implements EventEffect {
    @Override
    public void applyEffect(Startup startup) {
        double revenueChange;

        if (startup.getRevenue() == 0) {
            System.out.println(startup.getName() + " has zero revenue, applying default effect.");

        }

        switch (startup.getType().toLowerCase()) {
            case "healthcare":
                revenueChange = startup.getRevenue() * 0.2;
                startup.setRevenue(startup.getRevenue() + revenueChange);
                System.out.println(startup.getName()
                        + " boosted by 20% revenue due to Economic Downturn.");
                break;

            case "fintech":
                revenueChange = startup.getRevenue() * 0.2;
                startup.setRevenue(startup.getRevenue() - revenueChange);
                System.out.println(startup.getName()
                        + " experienced a revenue decrease due to Economic Downturn.");
                break;
            case "real estate":
                // Apply a 10% decrease to Real Estate startups (example)
                revenueChange = startup.getRevenue() * 0.1;
                startup.setRevenue(startup.getRevenue() - revenueChange);
                System.out.println(startup.getName() + " experienced a 10% revenue decrease due to Economic Downturn.");
                break;

            case "social media":
                // Apply a 10% revenue increase to Social Media startups (example)
                revenueChange = startup.getRevenue() * 0.1;
                startup.setRevenue(startup.getRevenue() + revenueChange);
                System.out.println(startup.getName() + " boosted by 10% revenue due to Economic Downturn.");
                break;

            default:
                // Apply a general case for all other startups
                revenueChange = startup.getRevenue() * 0.15; // Default 15% change for others
                startup.setRevenue(startup.getRevenue() + revenueChange);
                System.out.println(startup.getName() + " experienced a general revenue change of 15% due to Economic Downturn.");
                break;
        }

        // Display after event
        System.out.println("After Event: " + startup.getName() + " | Revenue: " + startup.getRevenue());
    }
}
