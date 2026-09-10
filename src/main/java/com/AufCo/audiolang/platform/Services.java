package com.AufCo.audiolang.platform;

import com.AufCo.audiolang.platform.services.IPlatformHelper;
import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = (IPlatformHelper)load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        T loadedService = (T)ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        return loadedService;
    }
}