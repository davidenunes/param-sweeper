package org.bhave.sweeper.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.ParameterSweep;

/**
 * A list sweep is a parameter sweep that Iterates over pre-existing lists of
 * values. These are not necessarily ordered nor of a specific type. As these
 * are used to create {@link Configuration} objects the user is advised to use
 * primitive type wrappers or consult the
 * {@link Configuration#addProperty(java.lang.String, java.lang.Object)}
 * documentation.
 *
 * @author Davide Nunes
 * @param <T> the type of the values in the list sweep
 */
public class ListSweep<T> implements ParameterSweep {

    private List<T> values;
    private String param;

    public ListSweep(String param, List<T> values) {
        this.values = new ArrayList<>(values);
        this.param = param;
    }

    /**
     * Returns a list with all the values of the given List sweep
     *
     * @return a list with different parameter values
     */
    List<T> getValueList() {
        return values;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Configuration> iterator() {
        TransformIterator tIt = new TransformIterator(values.iterator(),
                new ConfigurationTranformer(param));
        return tIt;
    }

    @Override
    public String getParameterName() {
        return param;
    }

    @Override
    public int size() {
        return values.size();
    }
}
