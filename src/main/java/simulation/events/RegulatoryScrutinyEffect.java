package simulation.events;

import simulation.Startup;

/**
 * Effect for the "Regulatory Scrutiny" event.
 */
public class RegulatoryScrutinyEffect implements EventEffect {
    @Override
    public void applyEffect(Startup startup) {
        if (startup.getMarketShare() > 25) {
            double marketShareLoss = startup.getMarketShare() * 0.1;
            startup.setMarketShare(startup.getMarketShare() - marketShareLoss);
            System.out.println(startup.getName()
                    + " lost 10% market share due to Regulatory Scrutiny.");
        } else {
            double marketShareBoost = startup.getMarketShare() * 0.1;
            startup.setMarketShare(startup.getMarketShare() + marketShareBoost);
            System.out.println(startup.getName()
                    + " gained 10% market share due to Regulatory Scrutiny.");
        }
    }
}
