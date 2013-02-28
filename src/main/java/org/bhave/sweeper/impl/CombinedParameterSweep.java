package org.bhave.sweeper.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.bhave.sweeper.ParameterSpace;
import org.bhave.sweeper.ParameterSweep;

public class CombinedParameterSweep implements ParameterSpace {
	private List<ParameterSweep> params;
	private int runs;

	public CombinedParameterSweep(List<ParameterSweep> params, int numRuns) {
		this.params = new ArrayList<ParameterSweep>(params);
		this.runs = numRuns;
	}

	public Iterator<Configuration> iterator() {
		return new CombinedIterator();
	}

	public void setNumRuns(int numRuns) {
		this.runs = numRuns;
	}

	public int getNumRuns() {
		return runs;
	}

	private class CombinedIterator implements Iterator<Configuration> {
		List<Iterator<Configuration>> iterators;

		// index for the current iterator that needs a next
		int currentIt;
		Configuration config;
		int numConfigs;
		int currentNumRuns;

		public CombinedIterator() {
			iterators = new ArrayList<>(params.size());
			// add iterators
			for (ParameterSweep param : params) {
				iterators.add(param.iterator());
			}
			currentIt = 0;
			config = new PropertiesConfiguration();
			numConfigs = 0;
			currentNumRuns = 0;
		}

		/**
		 * Only has next if the current iterator is not the first one and
		 * hasNext returns true
		 */
		@Override
		public boolean hasNext() {
			return numConfigs < size();
		}

		private void createConfiguration(Configuration config) {

			if (hasNext()) {
				if (iterators.get(currentIt).hasNext()) {
					iteratorToConfiguration(config);
					if (currentIt + 1 < params.size()) {
						currentIt++;
						createConfiguration(config);
					}
				} else {
					// reset the current iterator and step back to the previous
					iterators.set(currentIt, params.get(currentIt).iterator());
					currentIt--;
					createConfiguration(config);
				}
			}
		}

		private void iteratorToConfiguration(Configuration config) {
			String param = params.get(currentIt).getParameterName();
			Object value = iterators.get(currentIt).next().getProperty(param);
			config.setProperty(param, value);
		}

		@Override
		public Configuration next() {
			if (currentNumRuns == 0) {
				createConfiguration(config);

			}
			currentNumRuns = (currentNumRuns + 1) % runs;
			numConfigs++;
			return config;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(
					"Can't remove values from a parameter sweep iterator");

		}

	}

	@Override
	public int size() {
		if (params.size() > 0) {
			int size = 1;
			for (ParameterSweep param : params) {
				size *= param.size();
			}
			return size * runs;
		}
		return 0;
	}
}
