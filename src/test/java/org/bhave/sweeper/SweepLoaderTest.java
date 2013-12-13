package org.bhave.sweeper;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.impl.SweepLoader;
import org.junit.Test;

public class SweepLoaderTest {

	@Test
	public void test() {
		String filename = "sweep-loader.config";
		File file = new File(Thread.currentThread().getContextClassLoader()
				.getResource(filename).getPath().toString());

		assertTrue(file.exists());

		SweepLoader sweepLoader = new SweepLoader();
		List<ParameterSweep> sweeps = sweepLoader.fromFile(file);

		assertEquals(3, sweeps.size());

		assertEquals(sweeps.get(0).getParameterName(), "p1-single");
		assertEquals(sweeps.get(1).getParameterName(), "p2-list");
		assertEquals(sweeps.get(2).getParameterName(), "p3-sequence");

		Configuration config = sweeps.get(0).iterator().next();
		assertEquals(15, config.getInt("p1-single"));

		int i = 1;
		for (Configuration cfg : sweeps.get(1)) {
			assertEquals(i, cfg.getInt("p2-list"));
			i++;
		}
		assertEquals(11, i);

		i = 0;
		for (Configuration cfg : sweeps.get(2)) {
			assertTrue(cfg.getDouble("p3-sequence") == (i * 0.5));
			i++;
		}

		// test sweep size and loading utility
		ParameterSweep sweep0 = sweeps.get(0);
		assertEquals(1, sweep0.size());
		
		ParameterSweep sweep1 = sweeps.get(1);
		assertEquals(10, sweep1.size());
		
		//0 to 10 -> step 0.5  [0,..,10]
		ParameterSweep sweep2 = sweeps.get(2);
		assertEquals(21, sweep2.size());
		

		int totalRuns = 1;
		for (ParameterSweep sweep : sweeps) {
			totalRuns *= sweep.size();
		}

		int runs = 2;

		CombinedParameterSweep combinedSweep = ParameterSweepUtil
				.loadCombinedSweep(file, runs);

		assertEquals(totalRuns * runs, combinedSweep.size());

		System.out.println(combinedSweep.size());
	}

}
