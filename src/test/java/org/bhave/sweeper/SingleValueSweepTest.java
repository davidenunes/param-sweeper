package org.bhave.sweeper;

import static org.junit.Assert.*;

import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.impl.SingleValueSweep;
import org.junit.Test;

public class SingleValueSweepTest {

	@Test
	public void test() {
		SingleValueSweep<Integer> sweep = new SingleValueSweep<Integer>("p1", 1);

		for (Configuration config : sweep) {
			System.out.println(config.getProperty("p1"));
		}

		assertEquals(1, sweep.size());
	}

}
