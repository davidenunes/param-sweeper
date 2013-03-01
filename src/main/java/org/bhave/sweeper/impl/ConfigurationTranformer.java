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

import org.apache.commons.collections.Transformer;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * A Transformer to transform iterators if necessary to produce a simple
 * Configuration object if necessary
 *
 * @author Davide Nunes
 *
 */
public class ConfigurationTranformer implements Transformer {

    private String paramName;

    public ConfigurationTranformer(String param) {
        paramName = param;
    }

    @Override
    public Configuration transform(Object value) {
        Configuration config = new PropertiesConfiguration();
        config.addProperty(paramName, value);

        return config;
    }
}
