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

import org.apache.commons.configuration.Configuration;

/**
 * A parameter space is a Parameter sweep for aggregated {@link ParameterSweep}
 * objects. It provides an {@link Iterable} structure that can be used to
 * retrieve all the possible configurations given a list of parameter sweeps.
 *
 * <p> The order of the generated configurations also depends on the order of
 * the parameter sweeps passed down in the list. </p> <p> Example:<br> <br> for
 * two parameters <b>p1 = {0,1} and p2 = {2,3}</b>, the combined parameter sweep
 * will provide an iterator that generates the configurations:<br> <br> c1 =
 * {p1=0, p2=2}<br> c2 = {p1=0, p2=3}<br> c3 = {p1=1, p2=2}<br> c4 = {p1=1,
 * p2=3}<br> <br>
 *
 * also, the number of runs passed will repeat each configuration a number of
 * times equals to the given runs value.<br> <br>
 *
 * For example, for a number of runs = 2, the configuration values would be:
 * <br> <br> c1 = {p1=0, p2=2}<br> c2 = {p1=0, p2=2}<br> c3 = {p1=0, p2=3}<br>
 * c4 = {p1=0, p2=3}<br> c5 = {p1=1, p2=2}<br> c6 = {p1=1, p2=2}<br> c7 = {p1=1,
 * p2=3}<br> c8 = {p1=1, p2=3}<br>
 *
 * </p>
 *
 * @author Davide Nunes
 *
 */
public interface CombinedParameterSweep extends Iterable<Configuration> {

    /**
     * Sets the number of runs for each parameter combination. The default
     * should be 1.
     *
     * @param numRuns number of repetitions for each parameter combination
     */
    void setNumRuns(int numRuns);

    /**
     * Returns the current number of repetitions for each parameter combination.
     *
     * @return the number of repetitions for each parameter combination
     */
    int getNumRuns();

    /**
     * Just like the {@link ParameterSweep} objects. The size method returns the
     * number of possible configuration values returned by this ParameterSpace.
     *
     * @return the number of possible configurations (the size of the parameter
     * space)
     */
    int size();
}
