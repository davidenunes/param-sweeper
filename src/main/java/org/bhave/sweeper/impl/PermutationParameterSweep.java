package org.bhave.sweeper.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.ParameterSpace;
import org.bhave.sweeper.ParameterSweep;

public class PermutationParameterSweep implements ParameterSpace {
	private List<ParameterSweep> params;
	private int runs;

	private Object[] currentConfigValues;

	public PermutationParameterSweep(List<ParameterSweep> params, int numRuns) {
		this.params = new ArrayList<ParameterSweep>(params);
		this.runs = numRuns;
		currentConfigValues = new Object[params.size()];
	}

	public Iterator<Configuration> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNumRuns(int numRuns) {
		// TODO Auto-generated method stub

	}

	public int getNumRuns() {
		// TODO Auto-generated method stub
		return 0;
	}
}
