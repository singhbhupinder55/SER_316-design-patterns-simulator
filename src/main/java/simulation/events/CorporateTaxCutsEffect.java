package simulation.events;

import simulation.Startup;

/**
 * Effect for the "Corporate Tax Cuts" event.
 */
public class CorporateTaxCutsEffect implements EventEffect {
    @Override
    public void applyEffect(Startup startup) {
        System.out.println(startup.getName()
                + " benefits from Corporate Tax Cuts (no direct effect).");
    }
}
