package com.AufCo.audiolang;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("audiolang")
public class AudioLang {
    public AudioLang() {
        MinecraftForge.EVENT_BUS.register(this);
        CommonClass.init();
    }
}