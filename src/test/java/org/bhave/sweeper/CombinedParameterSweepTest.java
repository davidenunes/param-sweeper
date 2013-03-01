package org.bhave.sweeper;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.impl.CombinedParameterSweep;
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

		CombinedParameterSweep paramSpace = new CombinedParameterSweep(params,
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

		CombinedParameterSweep paramSpace = new CombinedParameterSweep(params,
				4);

		for (Configuration config : paramSpace) {
			System.out.println("[" + config.getInt("p1") + ","
					+ config.getInt("p2") + "]");
		}

		assertEquals((sweep.size() * sweepSeq.size())*4, paramSpace.size());
                
	}
}
