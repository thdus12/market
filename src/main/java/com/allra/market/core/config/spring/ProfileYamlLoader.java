package com.allra.market.core.config.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProfileYamlLoader implements EnvironmentPostProcessor, ApplicationListener<ApplicationEvent> {
    private static final DeferredLog deferredLog = new DeferredLog();
    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    Resource[] loadResources(String pattern) throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader()).getResources(pattern);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        deferredLog.info("ProfileYamlLoader is processing");
        try {
            List<Resource> resources = new ArrayList<>(List.of(loadResources("classpath*:*.yaml")));

            List<String> success = new ArrayList<>();
            for (Resource res : resources) {
                if (res.exists()) {
                    List<PropertySource<?>> propertySources = loadYaml(res);
                    propertySources.forEach(x -> {
                        success.add(x.getName());
                        environment.getPropertySources().addLast(x);
                    });
                }
            }
            deferredLog.info("Load " + success.size() + " Yamls : " + success);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            // throw new RuntimeException(e);
        }
    }

    private List<PropertySource<?>> loadYaml(Resource resource) {
        if (!resource.exists()) {
            throw new IllegalArgumentException("Resource " + resource + " does not exist");
        }
        try {
            String configName = FilenameUtils.getBaseName(resource.getFilename());
            return this.loader.load(configName, resource);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load yaml configuration from " + resource, ex);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        deferredLog.replayTo(ProfileYamlLoader.class);
    }
}
