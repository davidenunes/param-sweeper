package org.bhave.sweeper;

import org.apache.commons.configuration.Configuration;

/**
 * Interface for Parameter Sweeps. These are data structures capable of storing
 * and iterating over single or aggregate parameter sweeps. Each parameter sweep
 * is uniquely identified according to its content so two identical parameter
 * sweeps should return true with equals.
 * 
 * @author Davide Nunes
 */
public interface ParameterSweep extends Iterable<Configuration> {
	String getParameterName();
	int size();
}
