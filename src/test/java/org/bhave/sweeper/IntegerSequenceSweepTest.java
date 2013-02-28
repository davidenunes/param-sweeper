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
