package com.AufCo.audiolang.platform;

import com.AufCo.audiolang.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {
    public String getPlatformName() {
        return "Forge";
    }

    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
}