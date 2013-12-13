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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.impl.DoubleSequenceSweep;
import org.junit.Test;

public class DoubleSequenceSweepTest {

    @Test
    public void test() {
        DoubleSequenceSweep seq = new DoubleSequenceSweep("p1", 0, 2, 1.2);

        assertEquals(2, seq.size());

        DoubleSequenceSweep seq2 = new DoubleSequenceSweep("p1", 0, 2, 0.7);

        assertEquals(3, seq2.size());

        DoubleSequenceSweep seq3 = new DoubleSequenceSweep("p1", 0, 2, 0.25);

        assertEquals(9, seq3.size());

    }

    @Test
    public void testPrecision() {

        DoubleSequenceSweep seq = new DoubleSequenceSweep("p1", 0, 1, 0.05);

        double[] expectedValues = new double[]{0, 0.05, 0.1, 0.15, 0.2, 0.25,
            0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8,
            0.85, 0.9, 0.95, 1};

        Iterator<Configuration> it = seq.iterator();
        for (double expected : expectedValues) {
            Configuration config = it.next();
            double generated = config.getDouble("p1");
            assertTrue(generated == expected);
        }


    }
    @Test
    public void testReverse() {

        DoubleSequenceSweep seq = new DoubleSequenceSweep("p1", 1, 0.90, -0.05);

        double[] expectedValues = new double[]{1, 0.95, 0.90};

        Iterator<Configuration> it = seq.iterator();
        for (double expected : expectedValues) {
            Configuration config = it.next();
            double generated = config.getDouble("p1");
            System.out.println(generated);
            assertTrue(generated == expected);
        }


    }

    @Test
    public void testAdditionalDecimalPlaces() {
        DoubleSequenceSweep seq = new DoubleSequenceSweep("p1", 0, 1, 0.05);

        for (Configuration config : seq) {
            System.out.println(config.getDouble("p1"));
        }
    }

    @Test
    public void testSweepSize() {
        DoubleSequenceSweep seq = new DoubleSequenceSweep("p1", 0, 0.05, 0.05);
        assertEquals(2, seq.size());
    }
}
