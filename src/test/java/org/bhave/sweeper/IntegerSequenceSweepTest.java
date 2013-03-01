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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.impl.IntegerSequenceSweep;
import org.junit.Test;

public class IntegerSequenceSweepTest {

	@Test
	public void test() {
		IntegerSequenceSweep seq = new IntegerSequenceSweep("p1", -10, 10, 1);
		System.out.println(seq);
		List<Integer> values = seq.getValues();
		System.out.println(values);

		Iterator<Integer> valuesIt = values.iterator();
		for (Configuration config : seq) {
			assertEquals(valuesIt.next().intValue(), config.getInt("p1"));
		}

		assertEquals(20, seq.size());

		IntegerSequenceSweep seq2 = new IntegerSequenceSweep("p1", 0, 1, 1);
		assertEquals(2, seq2.size());
	}

}
