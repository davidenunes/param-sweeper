package org.bhave.sweeper;

/**
 * A parameter space is a Parameter sweep for aggregated {@link ParameterSweep}
 * objects.
 * 
 * @author Davide Nunes
 * 
 */
public interface ParameterSpace extends ParameterSweep {

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

	/**
	 * Adds a new {@link ParameterSweep} to this parameter space
	 * 
	 * @param sweep
	 *            a sweep to be added to the parameter space
	 * @return true if the sweep was successfully added
	 */
	boolean add(ParameterSweep sweep);

	/**
	 * Removes a given {@link ParameterSweep} from the parameter space.
	 * 
	 * @param sweep
	 *            the sweep to be removed.
	 * 
	 * @return true of the sweep was removed.
	 */
	boolean remove(ParameterSweep sweep);
}
