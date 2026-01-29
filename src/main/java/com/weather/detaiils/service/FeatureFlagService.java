package com.weather.detaiils.service;

import com.weather.detaiils.Repo.FeatureFlagRepository;
import com.weather.detaiils.model.FeatureFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;


@Service
public class FeatureFlagService {

    private static final Logger log = LoggerFactory.getLogger(FeatureFlagService.class);

    private final FeatureFlagRepository repository;
    private final Supplier<Map<String, FeatureFlag>> remoteFetcher; // injected for testability

    public FeatureFlagService(FeatureFlagRepository repository) {
        this(repository, null);
    }

    // Constructor for tests / optional remote fetcher injection
    public FeatureFlagService(FeatureFlagRepository repository, Supplier<Map<String, FeatureFlag>> remoteFetcher) {
        this.repository = repository;
        this.remoteFetcher = remoteFetcher;
    }

    public boolean isEnabled(String flagName) {
        FeatureFlag f = repository.get(flagName);
        return f != null && f.enabled();
    }

    public Collection<FeatureFlag> listAll() {
        return repository.findAll();
    }

    public void setFlag(String name, boolean enabled) {
        repository.save(new FeatureFlag(name, enabled));
    }

    /**
     * Example scheduled refresh: every 60 seconds, optionally fetch remote flags and replace.
     * In production, you may prefer a push/webhook model to avoid polling and to reduce latency.
     */
    @Scheduled(fixedDelayString = "${featureflags.refresh-ms:60000}")
    public void refreshFromRemoteIfConfigured() {
        if (remoteFetcher == null) {
            return; // no remote configured (safe default)
        }
        try {
            Map<String, FeatureFlag> fetched = remoteFetcher.get();
            if (fetched != null) {
                repository.replaceAll(fetched);
                log.info("Feature flags refreshed from remote: {} flags", fetched.size());
            }
        } catch (Exception e) {
            log.error("Failed to refresh feature flags from remote", e);
        }
    }

    // helper for wiring remote fetcher in config class (optional)
    public static Supplier<Map<String, FeatureFlag>> simulateRemoteFetch() {
        return () -> Map.of(
                "betaFeature", new FeatureFlag("betaFeature", true),
                "useNewCheckout", new FeatureFlag("useNewCheckout", false)
        );
    }


}
