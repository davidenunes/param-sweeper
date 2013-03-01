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

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.impl.DefaultCombinedParameterSweep;
import org.bhave.sweeper.impl.IntegerSequenceSweep;
import org.bhave.sweeper.impl.SingleValueSweep;
import static org.junit.Assert.*;
import org.junit.Test;

public class CombinedParameterSweepTest {

    @Test
    public void test() {
        IntegerSequenceSweep sweep1 = new IntegerSequenceSweep("p1", 0, -1, -1);
        assertEquals(2, sweep1.size());

        IntegerSequenceSweep sweep2 = new IntegerSequenceSweep("p2", 0, 2, 1);
        assertEquals(3, sweep2.size());

        IntegerSequenceSweep sweep3 = new IntegerSequenceSweep("p3", 0, 2, 1);
        assertEquals(3, sweep3.size());

        SingleValueSweep<Integer> sweep4 = new SingleValueSweep<>("p4", 1);

        List<ParameterSweep> params = new LinkedList<>();
        params.add(sweep1);
        params.add(sweep2);
        params.add(sweep3);
        params.add(sweep4);

        CombinedParameterSweep paramSpace = new DefaultCombinedParameterSweep(params,
                1);

        assertEquals(sweep1.size() * sweep2.size() * sweep3.size(),
                paramSpace.size());

        for (Configuration config : paramSpace) {
            System.out.println("[" + config.getInt("p1") + ","
                    + config.getInt("p2") + "," + config.getInt("p3") + "]");
        }


    }

    @Test
    public void testMultipleRuns() {
        SingleValueSweep<Integer> sweep = new SingleValueSweep<>("p1", 1);
        IntegerSequenceSweep sweepSeq = new IntegerSequenceSweep("p2", 0, -1,
                -1);

        List<ParameterSweep> params = new LinkedList<>();
        params.add(sweep);
        params.add(sweepSeq);

        DefaultCombinedParameterSweep paramSpace = new DefaultCombinedParameterSweep(params,
                4);

        for (Configuration config : paramSpace) {
            System.out.println("[" + config.getInt("p1") + ","
                    + config.getInt("p2") + "]");
        }

        assertEquals((sweep.size() * sweepSeq.size()) * 4, paramSpace.size());

    }
}
