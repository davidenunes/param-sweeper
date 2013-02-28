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
