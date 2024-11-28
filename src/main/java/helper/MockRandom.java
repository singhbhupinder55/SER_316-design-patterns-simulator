package helper;

import java.util.Random;

/**
 * A mock implementation of Random to control randomness for testing.
 */
public class MockRandom extends Random {
    private final double fixedDouble;
    private final int fixedInt;

    /**
     * Constructor to set fixed values for randomness.
     *
     * @param fixedDouble A fixed value for double random outputs.
     * @param fixedInt    A fixed value for integer random outputs.
     */
    public MockRandom(double fixedDouble, int fixedInt) {
        this.fixedDouble = fixedDouble;
        this.fixedInt = fixedInt;
    }

    @Override
    public double nextDouble() {
        return fixedDouble;
    }

    @Override
    public int nextInt(int bound) {
        return fixedInt % bound; // Ensure the value is within the bound
    }
}
