package patterns.factory;

/**
 * Represents an Office building.
 * Implements the Building interface.
 */
public class Office implements Building {

    /**
     * Constructs an Office building.
     * Prints a message indicating the construction process.
     */
    @Override
    public void construct() {
        System.out.println("Constructing an Office Building.");
    }
}
