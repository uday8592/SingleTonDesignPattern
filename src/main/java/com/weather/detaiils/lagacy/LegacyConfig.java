package com.weather.detaiils.lagacy;

public enum LegacyConfig {

    INSTANCE;

    // mutable internal state - guard carefully
    private volatile String value = "default";

    public String getValue() { return value; }

    public synchronized void setValue(String v) { this.value = v; }
}
