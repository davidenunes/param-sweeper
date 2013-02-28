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
