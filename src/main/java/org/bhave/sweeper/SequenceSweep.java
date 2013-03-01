package org.bhave.sweeper;

import java.util.Iterator;
import java.util.List;
import org.bhave.sweeper.impl.DoubleSequenceSweep;
import org.bhave.sweeper.impl.IntegerSequenceSweep;

/**
 * Interface for Sequence Sweep. It provides means to create sequences of
 * Numeric values.
 *
 * @see IntegerSequenceSweep
 * @see DoubleSequenceSweep
 *
 * @author Davide Nunes
 *
 */
public interface SequenceSweep<N extends Number> extends ParameterSweep {

    /**
     * Returns the initial value of the sequence
     *
     * @return a initial numeric value for the sequence sweep
     */
    N from();

    /**
     * Returns the final value fo the sequence
     *
     * @return a final numeric value for the sequence sweep
     */
    N to();

    /**
     * Returns the step by which the sequence is updated
     *
     * @return a numeric value that is used to generate the sequence
     */
    N step();

    /**
     * Returns a list with all the values for this sequence in order
     *
     * @return a list with the sweep numerical values
     */
    List<N> getValues();

    /**
     * Lazy iterator, avoids the creation of a whole list to iterate directly
     * through the values of this sweep
     *
     * @return an iterator that returns instances of T
     */
    Iterator<N> valueIterator();
}
