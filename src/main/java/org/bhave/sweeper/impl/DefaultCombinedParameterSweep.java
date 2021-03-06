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
package org.bhave.sweeper.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.bhave.sweeper.CombinedParameterSweep;
import static org.bhave.sweeper.CombinedParameterSweep.CFG_ID_PARAM;
import org.bhave.sweeper.ParameterSweep;

/**
 * <p>
 * This class allows the creation of {@link CombinedParameterSweep} objects
 * which can be used to combine multiple {@link ParameterSweep} objects into one
 * structure that implements {@link Iterable} of {@link Configuration} objects.
 * </p>
 * <p>
 * The iterator for this class generates {@link Configuration} objects with all
 * the possible parameter combinations according to the parameter sweeps used to
 * create this object.
 * </p>
 * <p>
 * The order of the generated configurations also depends on the order of the
 * parameter sweeps passed down in the list.
 * </p>
 * <p>
 * Example:<br>
 * <br>
 * for two parameters <b>p1 = {0,1} and p2 = {2,3}</b>, the combined parameter
 * sweep will provide an iterator that generates the configurations:<br>
 * <br>
 * c1 = {p1=0, p2=2}<br>
 * c2 = {p1=0, p2=3}<br>
 * c3 = {p1=1, p2=2}<br>
 * c4 = {p1=1, p2=3}<br>
 * <br>
 *
 * also, the number of runs passed will repeat each configuration a number of
 * times equals to the given runs value.<br>
 * <br>
 *
 * For example, for a number of runs = 2, the configuration values would be:
 * <br>
 * <br>
 * c1 = {p1=0, p2=2}<br>
 * c2 = {p1=0, p2=2}<br>
 * c3 = {p1=0, p2=3}<br>
 * c4 = {p1=0, p2=3}<br>
 * c5 = {p1=1, p2=2}<br>
 * c6 = {p1=1, p2=2}<br>
 * c7 = {p1=1, p2=3}<br>
 * c8 = {p1=1, p2=3}<br>
 *
 * </p>
 *
 * Note: all the configurations generated by the iterator have an additional
 * property named "cfg.id" this can be useful to track down configurations
 * within a single experiment. This id is not unique across multiple instances
 * of the iterator. Each time an iterator is created the id is reseted to 0.
 */
public class DefaultCombinedParameterSweep implements CombinedParameterSweep {

    private List<ParameterSweep> params;
    private int runs;

    public DefaultCombinedParameterSweep(List<ParameterSweep> params,
            int numRuns) {
        this.params = new ArrayList<>(params);
        this.runs = numRuns;
    }

    @Override
    public Iterator<Configuration> iterator() {
        return new CombinedIterator();
    }

    @Override
    public void setNumRuns(int numRuns) {
        this.runs = numRuns;
    }

    @Override
    public int getNumRuns() {
        return runs;
    }

    /**
     * This creates a file to store the configuration ids and the respective
     * parameter values for each configuration
     *
     * the file also has a header with the parameter names
     *
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    @Override
    public File writeSweepFile(String filename) throws IOException {
        File file = new File(filename);
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter(file));
        Iterator<Configuration> paramIT = this.iterator();

        StringBuilder sb = new StringBuilder(CombinedParameterSweep.CFG_ID_PARAM);
        sb.append(';');

        List<ParameterSweep> parameters = this.getParameterSweeps();
        for (ParameterSweep sweep : parameters) {
            sb.append(sweep.getParameterName()).append(';');
        }
        writer.write(sb.toString());
        writer.newLine();

        int i = 0;
        while (paramIT.hasNext()) {
            Configuration cfg = paramIT.next();
            if (i == 0) {
                sb = new StringBuilder();
                sb.append(cfg.getInt(CombinedParameterSweep.CFG_ID_PARAM));
                sb.append(';');
                for (ParameterSweep sweep : parameters) {
                    String param = sweep.getParameterName();
                    sb.append(cfg.getProperty(param).toString()).append(';');
                }
                writer.write(sb.toString());
                writer.newLine();
            }

            i = (i + 1) % runs;
        }
        writer.close();
        return file;
    }

    /**
     * An iterator that combines the existing parameter sweeps and provides a
     * new {@link Configuration} iterator. Each Configuration object has all the
     * parameters for the parameter sweeps contained in the Combined Parameter
     * Sweep.
     */
    private class CombinedIterator implements Iterator<Configuration> {

        List<Iterator<Configuration>> iterators;
        // index for the current iterator that needs a next
        int currentIt;
        Configuration config;
        int numConfigs;
        int currentNumRuns;

        private int currentCfgID;

        public CombinedIterator() {
            iterators = new ArrayList<>(params.size());
            // add iterators
            for (ParameterSweep param : params) {
                iterators.add(param.iterator());
            }
            currentIt = 0;
            config = new PropertiesConfiguration();
            numConfigs = 0;
            currentNumRuns = 0;
            currentCfgID = 0;
        }

        /**
         * <p>
         * Has more values if the Configuration objects generator did not reach
         * the total size of this parameter sweep.
         * </p>
         *
         * <p>
         * Note that the size of a parameter sweep is the number of possible
         * values it provides, in this case, all the possible parameter value
         * combinations.
         * </p>
         *
         * return true if it can generate more configurations
         */
        @Override
        public boolean hasNext() {
            return numConfigs < size();
        }

        /**
         * Private recursive method that generates the configuration values it
         * jumps from Iterator to iterator to supply the possible configuration
         * instances.
         */
        private void createConfiguration() {

            if (hasNext()) {
                if (iterators.get(currentIt).hasNext()) {
                    iteratorToConfiguration();
                    if (currentIt + 1 < params.size()) {
                        currentIt++;
                        createConfiguration();
                    }
                } else {
                    // reset the current iterator and step back to the previous
                    iterators.set(currentIt, params.get(currentIt).iterator());
                    currentIt--;
                    createConfiguration();
                }
            }
        }

        /**
         * Utility method that extracts the value for the currentIterator and
         * updates this value for the current configuration instance.
         */
        private void iteratorToConfiguration() {
            String param = params.get(currentIt).getParameterName();
            Object value = iterators.get(currentIt).next().getProperty(param);
            config.setProperty(param, value);
        }

        /**
         * Returns the next {@link Configuration} object
         *
         * @return a new configuration
         */
        @Override
        public Configuration next() {

            if (currentNumRuns == 0) {
                createConfiguration();

            }
            if (((currentNumRuns) % runs) == 0) {
                currentCfgID++;
            }
            // create a new configuration copy
            PropertiesConfiguration result = new PropertiesConfiguration();
            result.copy(config);
            result.setProperty(RUN_PARAM, (currentNumRuns + 1));

            currentNumRuns = (currentNumRuns + 1) % runs;
            numConfigs++;

            result.setProperty(CFG_ID_PARAM, currentCfgID);

            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "Can't remove values from a parameter sweep iterator");

        }
    }

    @Override
    public int size() {
        if (params.size() > 0) {
            int size = 1;
            for (ParameterSweep param : params) {
                size *= param.size();
            }
            return size * runs;
        }
        return 0;
    }

    /**
     * Returns all the parameter sweeps used to create this combined parameter
     * sweep
     */
    @Override
    public List<ParameterSweep> getParameterSweeps() {
        return new LinkedList<>(this.params);
    }
}
