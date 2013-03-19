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

		CombinedParameterSweep paramSpace = new DefaultCombinedParameterSweep(
				params, 1);

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

		DefaultCombinedParameterSweep paramSpace = new DefaultCombinedParameterSweep(
				params, 5);

		for (Configuration config : paramSpace) {
			System.out.println("RUN: "
					+ config.getInt(DefaultCombinedParameterSweep.RUN_PARAM));
			System.out.println("[" + config.getInt("p1") + ","
					+ config.getInt("p2") + "]");
		}

		assertEquals((sweep.size() * sweepSeq.size()) * 5, paramSpace.size());

	}

	@Test
	public void testSize() {
		CombinedParameterSweep params = null;
		ParameterSweep influence = ParameterSweepUtil.createSweep("influence",
				0.5);
		ParameterSweep numAgents = ParameterSweepUtil.createSweep("numAgents",
				300);
		ParameterSweep maxCycles = ParameterSweepUtil.createSweep("cycles",
				3000);
		ParameterSweep numContexts = ParameterSweepUtil.createSweep(
				"numContexts", 2);
		ParameterSweep initialUncertainty = ParameterSweepUtil.createSweep(
				"uncertainty", 1.4);

		// network models
		ParameterSweep model0 = ParameterSweepUtil.createSweep("model0", "reg");
		ParameterSweep model1 = ParameterSweepUtil.createSweep("model1", "reg");
		// network model parameters

		ParameterSweep model0P = ParameterSweepUtil.createSweep("k", "k1");
		ParameterSweep model1P = ParameterSweepUtil.createSweep("k", "k2");

		// context switching values
		ParameterSweep cs0 = ParameterSweepUtil.createSweep("cs0", 0.0, 0.05,
				0.05);
		ParameterSweep cs1 = ParameterSweepUtil.createSweep("cs1", 1);

		List<ParameterSweep> sweeps = new LinkedList<>();
		sweeps.add(influence);
		sweeps.add(numAgents);
		sweeps.add(maxCycles);
		sweeps.add(numContexts);
		sweeps.add(model0);
		sweeps.add(model0P);
		sweeps.add(model1);
		sweeps.add(model1P);
		sweeps.add(cs0);
		sweeps.add(cs1);
		sweeps.add(initialUncertainty);

		params = ParameterSweepUtil.createCombinedSweep(sweeps, 30);
		assertEquals(30 * 2, params.size());

	}
}
