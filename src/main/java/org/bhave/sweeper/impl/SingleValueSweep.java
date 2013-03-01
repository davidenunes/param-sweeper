package org.bhave.sweeper.impl;

import java.util.Iterator;
import org.apache.commons.collections.iterators.SingletonIterator;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.commons.configuration.Configuration;
import org.bhave.sweeper.ParameterSweep;

/**
 * An Iterator for a singleton object. The iterator returns a single value and
 * then, the {@link Iterator#hasNext()} returns false;
 *
 * @author Davide Nunes
 *
 * @param <T> the type of the single value
 */
public class SingleValueSweep<T> implements ParameterSweep {

    private T value;
    private String param;

    public SingleValueSweep(String param, T value) {
        this.value = value;
        this.param = param;
    }

    T getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<Configuration> iterator() {
        SingletonIterator it = new SingletonIterator(value, false);
        TransformIterator itConfig = new TransformIterator(it,
                new ConfigurationTranformer(param));

        return itConfig;
    }

    @Override
    public String getParameterName() {
        return param;
    }

    @Override
    public int size() {
        return 1;
    }
}
