package patterns.factory;

import java.util.Locale;

/**
 * Factory class to create different types of buildings dynamically.
 * Implements the Factory Pattern.
 */
public class BuildingFactory {

    /**
     * Creates a Building object based on the provided type.
     * @param type the type of building to create ("office", "factory", "store")
     * @return the corresponding Building object
     * @throws IllegalArgumentException if the building type is unknown
     */
    public static Building createBuilding(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Building type cannot be null or empty");
        }

        // Convert the input type to uppercase using Locale.ROOT to avoid locale-specific issues
        String normalizedType = type.toUpperCase(Locale.ROOT);

        switch (normalizedType) {
            case "OFFICE":
                return new Office();
            case "FACTORY":
                return new Factory();
            case "STORE":
                return new Store();
            default:
                throw new IllegalArgumentException("Unknown building type: " + type);
        }
    }
}
