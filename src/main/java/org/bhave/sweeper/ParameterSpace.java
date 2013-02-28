package org.bhave.sweeper;

import org.apache.commons.configuration.Configuration;

/**
 * A parameter space is a Parameter sweep for aggregated {@link ParameterSweep}
 * objects.
 * 
 * @author Davide Nunes
 * 
 */
public interface ParameterSpace extends Iterable<Configuration> {

	/**
	 * Sets the number of runs for each parameter combination. The default
	 * should be 1.
	 * 
	 * @param numRuns
	 *            number of repetitions for each parameter combination
	 */
	void setNumRuns(int numRuns);

	/**
	 * Returns the current number of repetitions for each parameter combination.
	 * 
	 * @return the number of repetitions for each parameter combination
	 */
	int getNumRuns();
}
