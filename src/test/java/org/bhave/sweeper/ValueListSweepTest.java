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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.impl.CombinedParameterSweep;
import org.bhave.sweeper.impl.ListSweep;
import org.bhave.sweeper.impl.SingleValueSweep;
import org.junit.Test;

public class ValueListSweepTest {

	@Test
	public void testListSweep() {
		List<Integer> values = new ArrayList<>();
		values.add(1);
		values.add(5);
		values.add(10);

		ListSweep<Integer> sweep = new ListSweep<>("p1", values);
		SingleValueSweep<Integer> sweep2 = new SingleValueSweep<Integer>("p2",
				1);

		assertEquals(values.size(), sweep.size());

		List<ParameterSweep> params = new ArrayList<>();
		params.add(sweep);
		params.add(sweep2);

		CombinedParameterSweep space = new CombinedParameterSweep(params, 3);

		for (Configuration config : space) {
			System.out.println("[" + config.getInt("p1") + ","
					+ config.getInt("p2") + "]");
		}

	}

}
