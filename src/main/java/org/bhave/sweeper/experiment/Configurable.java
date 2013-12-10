package org.bhave.sweeper.experiment;

import java.util.Map;

import org.apache.commons.configuration.Configuration;

/**
 * Interface for something configurable. Any object capable of receiving a
 * configuration.
 * 
 * @author Davide Nunes
 */
public interface Configurable<T> {
	/**
	 * Loads a configuration into this configurable object, if some parameter is
	 * missing this should throw a runtime exception.
	 * 
	 * @param config
	 *            a {@link Configuration} to be applied to this configurable
	 *            object.
	 * @throws RuntimeException
	 *             if the configuration is invalid.
	 */
	void loadConfiguration(Configuration config);

	/**
	 * Checks if a given configuration is valid for the current model, this
	 * method checks if all the necessary parameters are supplied as well as if
	 * the parameter types are appropriate.
	 * 
	 * @param config
	 *            a configuration to be checked
	 * @return
	 */
	boolean validConfiguration(Configuration config);

	/**
	 * Returns a map with the parameter names and the types of the parameters to
	 * be supplied by a {@link Configuration} object.
	 * 
	 * This should be used by {@link #validConfiguration(Configuration)} to
	 * check if the provided configuration supplies types according to the
	 * parameter -> type map.
	 * 
	 * @return parameterMap
	 */
	Map<String, Class<? extends Object>> getConfigurableParameters();
}
