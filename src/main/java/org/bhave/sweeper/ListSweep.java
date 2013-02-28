package org.bhave.sweeper;

import java.util.List;

public interface ListSweep<T> extends ParameterSweep {
	/**
	 * Returns a list with all the values of the given List sweep
	 * 
	 * @return a list with different parameter values
	 */
	List<T> getValueList();

}
