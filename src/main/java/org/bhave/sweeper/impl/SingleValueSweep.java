/**
 * Copyright 2013 Davide Nunes
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://davidenunes.com 
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * This file is part of the b-have sweeper library.
 * 
 * The b-have sweeper library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The b-have sweeper library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the b-have network library.  
 * If not, see <http://www.gnu.org/licenses/gpl.html>.
 */
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
