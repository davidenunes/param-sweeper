/**
 * Copyright 2013 Davide Nunes Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * This file is part of the b-have sweeper library.
 *
 * The b-have sweeper library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The b-have sweeper library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * the b-have network library. If not, see
 * <http://www.gnu.org/licenses/gpl.html>.
 */
package org.bhave.sweeper;

import java.util.List;
import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.impl.DefaultCombinedParameterSweep;
import org.bhave.sweeper.impl.DoubleSequenceSweep;
import org.bhave.sweeper.impl.IntegerSequenceSweep;
import org.bhave.sweeper.impl.ListSweep;
import org.bhave.sweeper.impl.SingleValueSweep;

/**
 * <p> Utility class with static methods to create different
 * {@link ParameterSweep} objects and {@link CombinedParameterSweep} objects.
 * </p>
 *
 * With this you can create {@link SingleValueSweep single value parameter
 * sweeps}, {@link SequenceSweep sequence parameter sweeps} and
 * {@link ListSweep list parameter sweeps}.
 *
 * @see ParameterSweep
 * @see CombinedParameterSweep
 *
 * @author Davide Nunes
 *
 */
public class ParameterSweepUtil {

    /**
     * Creates a {@link ParameterSweep} object with a given name and Integer
     * value.
     *
     * @param param the parameter name.
     * @param value the parameter Integer value.
     * @return a {@link SingleValueSweep single value sweep} with an Integer
     * value
     */
    public static ParameterSweep createSweep(String param, int value) {
        return new SingleValueSweep<>(param, value);
    }

    /**
     * Creates a {@link ParameterSweep} object with a given name and Double
     * value.
     *
     * @param param the parameter name.
     * @param value the parameter Double value.
     * @return a {@link SingleValueSweep single value sweep} with a Double value
     */
    public static ParameterSweep createSweep(String param, double value) {
        return new SingleValueSweep<>(param, value);
    }

    /**
     * Creates a {@link ParameterSweep} object with a given name and String
     * value.
     *
     * @param param the parameter name.
     * @param value the parameter Double value.
     * @return a {@link SingleValueSweep single value sweep} with an String
     * value
     */
    public static ParameterSweep createSweep(String param, String value) {
        return new SingleValueSweep<>(param, value);
    }

    /**
     * A generic method to create {@link ListSweep} objects. These are basically
     * parameter sweeps with a given list of values.
     *
     * @param param the name of the parameters
     * @param values the given values for the parameter
     * @return a {@link ListSweep list parameter sweep} with multiple values for
     * the given parameter
     */
    public static <T> ParameterSweep createSweep(String param, List<T> values) {
        return new ListSweep<>(param, values);
    }

    /**
     * Creates a {@link SequenceSweep} object. This generates a sequence of
     * Integer values for a given parameters according that range from an
     * initial to a final value using a step to generate the sequence.
     *
     * @param param the name of the parameter.
     * @param from the initial sequence value.
     * @param to the final sequence value.
     * @param step the value used to build the sequence.
     * @return a {@link SequenceSweep sequence parameter sweep} that generates a
     * sequence of values in a lazy way according to the given parameters.
     */
    public static ParameterSweep createSweep(String param, int from, int to,
            int step) {
        return new IntegerSequenceSweep(param, from, to, step);
    }

    /**
     * Creates a {@link SequenceSweep} object. This generates a sequence of
     * Double values for a given parameters according that range from an initial
     * to a final value using a step to generate the sequence.
     *
     * @param param the name of the parameter.
     * @param from the initial sequence value.
     * @param to the final sequence value.
     * @param step the value used to build the sequence.
     * @return a {@link SequenceSweep sequence parameter sweep} that generates a
     * sequence of values in a lazy way according to the given parameters.
     */
    public static ParameterSweep createSweep(String param, double from,
            double to, double step) {
        return new DoubleSequenceSweep(param, from, to, step);
    }

    /**
     * <p> Creates a {@link CombinedParameterSweep} which can be used to combine
     * multiple {@link ParameterSweep} objects into one structure which also
     * implements {@link Iterable} of {@link Configuration} objects. </p> <p>
     * The iterator for this class generates {@link Configuration} objects with
     * all the possible parameter combinations according to the parameter sweeps
     * used to create this object. </p> <p> The order of the generated
     * configurations also depende on the order of the parameter sweeps passed
     * down in the list. </p> <p> Example:<br> <br> for two parameters <b>p1 =
     * {0,1} and p2 = {2,3}</b>, the combined parameter sweep will provide an
     * iterator that generates the configurations:<br> <br> c1 = {p1=0,
     * p2=2}<br> c2 = {p1=0, p2=3}<br> c3 = {p1=1, p2=2}<br> c4 = {p1=1,
     * p2=3}<br> <br>
     *
     * also, the number of runs passed will repeat each configuration a number
     * of times equals to the given runs value.<br> <br>
     *
     * For example, for a number of runs = 2, the configuration values would be:
     * <br> <br> c1 = {p1=0, p2=2}<br> c2 = {p1=0, p2=2}<br> c3 = {p1=0,
     * p2=3}<br> c4 = {p1=0, p2=3}<br> c5 = {p1=1, p2=2}<br> c6 = {p1=1,
     * p2=2}<br> c7 = {p1=1, p2=3}<br> c8 = {p1=1, p2=3}<br>
     *
     * </p>
     *
     *
     * @param sweeps the parameter sweeps used to construct this
     * {@link CombinedParameterSweep}
     * @param runs the number of runs for each possible parameter configuration
     * @return a {@link CombinedParameterSweep} which is basically an
     * {@link Iterable} structure with all the possible
     * {@link Configuration configurations} given the supplied parameter sweeps.
     */
    public static CombinedParameterSweep createCombinedSweep(
            List<ParameterSweep> sweeps, int runs) {
        return new DefaultCombinedParameterSweep(sweeps, runs);
    }
}
