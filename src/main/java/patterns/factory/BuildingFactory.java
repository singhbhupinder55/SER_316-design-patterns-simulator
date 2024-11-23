package patterns.factory;

/**
 * Factory class to create different types of buildings dynamically.
 * Implements the Factory Pattern.
 */
public class BuildingFactory {

    /**
     * Creates a Building object based on the provided type.
     *
     * @param type the type of building to create ("office", "factory", "store")
     * @return the corresponding Building object
     * @throws IllegalArgumentException if the building type is unknown
     */
    public static Building createBuilding(String type) {
        return switch (type.toLowerCase()) {
            case "office" -> new Office();
            case "factory" -> new Factory();
            case "store" -> new Store();
            default -> throw new IllegalArgumentException("Unknown building type: " + type);
        };
    }
}
