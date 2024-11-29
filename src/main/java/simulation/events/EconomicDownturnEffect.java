package simulation.events;

import simulation.Startup;

/**
 * Effect for the "Economic Downturn" event.
 */
public class EconomicDownturnEffect implements EventEffect {
    @Override
    public void applyEffect(Startup startup) {
        switch (startup.getType().toLowerCase()) {
            case "healthcare":
                double revenueBoost = startup.getRevenue() * 0.2;
                startup.setRevenue(startup.getRevenue() + revenueBoost);
                System.out.println(startup.getName()
                        + " boosted by 20% revenue due to Economic Downturn.");
                break;

            case "fintech":
                double revenueDecrease = startup.getRevenue() * 0.2;
                startup.setRevenue(startup.getRevenue() - revenueDecrease);
                System.out.println(startup.getName()
                        + " experienced a revenue decrease due to Economic Downturn.");
                break;

            default:
                System.out.println(startup.getName()
                        + " is not affected by Economic Downturn.");
        }
    }
}
