package com.weather.detaiils.Repo;

import com.weather.detaiils.model.FeatureFlag;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FeatureFlagRepository {


    // Keyed by flag name; ConcurrentHashMap for thread-safe reads and updates.
    private final Map<String, FeatureFlag> flags = new ConcurrentHashMap<>();

    public FeatureFlagRepository() {
        // seed defaults (could come from config)
        flags.put("betaFeature", new FeatureFlag("betaFeature", false));
        flags.put("useNewCheckout", new FeatureFlag("useNewCheckout", false));
    }

    public FeatureFlag get(String name) {
        return flags.get(name);
    }

    public Collection<FeatureFlag> findAll() {
        return flags.values();
    }

    public void save(FeatureFlag flag) {
        flags.put(flag.name(), flag);
    }

    public void replaceAll(Map<String, FeatureFlag> newFlags) {
        flags.clear();
        flags.putAll(newFlags);
    }
}
