package simulation.events;

import simulation.Startup;

/**
 * Interface that defines the effect of an event on a startup.
 */
public interface EventEffect {
    void applyEffect(Startup startup);
}