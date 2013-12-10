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

	}

}
