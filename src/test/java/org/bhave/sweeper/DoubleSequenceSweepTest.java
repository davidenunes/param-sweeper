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

import static org.junit.Assert.assertEquals;

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

}
