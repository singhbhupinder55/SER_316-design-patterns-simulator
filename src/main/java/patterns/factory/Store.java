package patterns.factory;

/**
 * Represents a Store building.
 * Implements the Building interface.
 */
public class Store implements Building {

    /**
     * Constructs a Store building.
     * Prints a message indicating the construction process.
     */
    @Override
    public void construct() {
        System.out.println("Constructing a Store Building.");
    }
}
