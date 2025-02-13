package com.allra.market.core.config.spring;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class YamlLoadFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(encodedResource.getResource());

        Properties properties = Objects.requireNonNull(factory.getObject(), "Failed to load yaml properties");
        String fileName = Objects.requireNonNullElse(encodedResource.getResource().getFilename(), "yaml-properties");
        return new PropertiesPropertySource(fileName, properties);
    }
}
