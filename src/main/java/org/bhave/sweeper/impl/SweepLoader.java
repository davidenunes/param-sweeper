package org.bhave.sweeper.impl;

import java.io.File;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.bhave.sweeper.ParameterSweep;

/**
 * The sweep loader is used to load parameter sweeps from a file using the
 * {@link PropertiesConfiguration} syntax.
 * 
 * <h2>Configuration File Syntax</h2>
 * <p>
 * The parameter sweep configuration file should contain parameters defined as
 * follows:
 * 
 * </ul> <br>
 * <ul>
 * <li>params.0.name = param1 #a name for the parameter, this should be unique
 * for each parameter
 * 
 * <li>params.0.sweep = list # this can be "single", "list" or "sequence"
 * 
 * <li>params.0.type = double # for lists and single value sweeps this can be
 * "int", "double", "string" or "boolean". for sequence parameter sweeps this
 * can be "int" or "double"
 * <li>params.0.value = 1,2,3,4,5 #values depend on the type of the sweep and
 * data type, this is an example for a list <br>
 * <br>
 * <li>params.0.value.from = 0
 * <li>params.0.value.to = 10
 * <li>params.0.value.step = 1 # this is how you define a sequence parameter
 * sweep
 * </ul>
 * </p>
 * 
 * 
 * @author Davide Nunes
 * 
 */
public class SweepLoader {
	public List<ParameterSweep> fromFile(String filename) {
		File sweepFile = new File(filename);
		return fromFile(sweepFile);
	}

	public List<ParameterSweep> fromFile(File file) {
		Configuration configFile = null;

		try {
			configFile = new PropertiesConfiguration(file);
		} catch (ConfigurationException cfge) {
			throw new RuntimeException(
					"Experiment configuration file is not properly formated");
		}

		// list to store the parameter sweeps
		List<ParameterSweep> sweeps = new LinkedList<>();

		int i = 0;
		// run through all the parameters
		while (configFile.containsKey("params." + i + ".name")) {

			String paramNameKey = "params." + i + ".name";
			String paramSweepKey = "params." + i + ".sweep";
			String paramTypeKey = "params." + i + ".type";
			String paramValueKey = "params." + i + ".value";

			String paramName = configFile.getString(paramNameKey);
			String paramSweep = configFile.getString(paramSweepKey);
			String paramType = configFile.getString(paramTypeKey);

			ParameterSweep sweep = readParameter(configFile, paramName,
					paramSweep, paramType, paramValueKey, i);

			sweeps.add(sweep);
			// next parameter
			i++;
		}

		return sweeps;
	}

	private ParameterSweep readParameter(Configuration configFile,
			String paramName, String paramSweep, String paramType,
			String paramValue, int paramNum) {

		ParameterSweep sweep = null;

		if (paramSweep.equalsIgnoreCase("list")) {
			List<Object> values = configFile.getList(paramValue);
			sweep = new ListSweep<>(paramName, values);
		} else if (paramSweep.equalsIgnoreCase("single")) {
			if (paramType.equalsIgnoreCase("int")) {
				int value = configFile.getInt(paramValue);
				sweep = new SingleValueSweep<Integer>(paramName, value);
			} else if (paramType.equalsIgnoreCase("double")) {
				double value = configFile.getDouble(paramValue);
				sweep = new SingleValueSweep<Double>(paramName, value);

			} else if (paramType.equalsIgnoreCase("string")) {
				String value = configFile.getString(paramValue);
				sweep = new SingleValueSweep<String>(paramName, value);
			} else if (paramType.equalsIgnoreCase("boolean")) {
				boolean value = configFile.getBoolean(paramValue);
				sweep = new SingleValueSweep<Boolean>(paramName, value);
			} else {
				// invalid parameter type
				throw new RuntimeException(
						"Invalid parameter type given for parameter "
								+ paramNum
								+ ", you must use \"int\", \"double\", \"string\" or \"boolean\"");
			}
		} else if (paramSweep.equalsIgnoreCase("sequence")) {
			String fromKey = paramValue + ".from";
			String toKey = paramValue + ".to";
			String stepKey = paramValue + ".step";

			if (paramType.equalsIgnoreCase("int")) {
				int from = configFile.getInt(fromKey);
				int to = configFile.getInt(toKey);
				int step = configFile.getInt(stepKey);

				sweep = new IntegerSequenceSweep(paramName, from, to, step);
			} else if (paramType.equalsIgnoreCase("double")) {
				double from = configFile.getDouble(fromKey);
				double to = configFile.getDouble(toKey);
				double step = configFile.getDouble(stepKey);

				sweep = new DoubleSequenceSweep(paramName, from, to, step);
			} else {// invalid parameter type
				throw new RuntimeException(
						"Invalid parameter type given for parameter "
								+ paramNum
								+ ", \"int\" or \"double\" must be used");
			}
		}

		return sweep;
	}

}
