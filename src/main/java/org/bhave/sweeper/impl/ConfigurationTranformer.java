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
