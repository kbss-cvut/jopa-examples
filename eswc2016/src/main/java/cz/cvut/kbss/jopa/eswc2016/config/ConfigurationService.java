package cz.cvut.kbss.jopa.eswc2016.config;

import cz.cvut.kbss.jopa.eswc2016.util.ConfigParam;
import cz.cvut.kbss.jopa.eswc2016.util.RepositoryType;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationService {

    private final Map<ConfigParam, String> configuration = new ConcurrentHashMap<>();

    public void set(ConfigParam param, String value) {
        configuration.put(param, value);
    }

    public String get(ConfigParam param) {
        return configuration.get(param);
    }

    @PostConstruct
    private void init() {
        configuration.put(ConfigParam.REPOSITORY_TYPE, RepositoryType.SESAME.toString());
    }
}
