package com.weather.detaiils.model;

public class FeatureFlag {

    private final String name;
    private final boolean enabled;

    public FeatureFlag(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public String name() { return name; }
    public boolean enabled() { return enabled; }
}
