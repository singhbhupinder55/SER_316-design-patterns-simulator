package simulation.events;

import simulation.Startup;

/**
 * A neutral effect for events that do not affect the startups.
 */
public class NeutralEffect implements EventEffect {
    @Override
    public void applyEffect(Startup startup) {
        // No effect on the startup, just log that no change happened.
        System.out.println(startup.getName() + " remains unaffected by the event.");
    }
}
