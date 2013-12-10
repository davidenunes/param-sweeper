package org.bhave.sweeper.dummy;

import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.experiment.Model;

public class DummyModel implements Model{

	@Override
	public void loadConfiguration(Configuration config) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validConfiguration(Configuration config) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Class<? extends Object>> getConfigurableParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

}
