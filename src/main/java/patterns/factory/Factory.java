package patterns.factory;

/**
 * Represents a Factory building.
 * Implements the Building interface.
 */
public class Factory implements Building {

    /**
     * Constructs a Factory building.
     * Prints a message indicating the construction process.
     */
    @Override
    public void construct() {
        System.out.println("Constructing a Factory Building.");
    }
}
