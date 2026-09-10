package com.AufCo.audiolang.platform.services;

public interface IPlatformHelper {
    String getPlatformName();

    boolean isModLoaded(String var1);

    boolean isDevelopmentEnvironment();

    default String getEnvironmentName() {
        return this.isDevelopmentEnvironment() ? "development" : "production";
    }
}