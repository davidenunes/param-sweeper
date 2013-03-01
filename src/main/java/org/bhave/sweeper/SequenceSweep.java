/**
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * This file is part of the b-have sweeper library.
 * 
 * The b-have sweeper library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The b-have sweeper library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the b-have network library.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
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
