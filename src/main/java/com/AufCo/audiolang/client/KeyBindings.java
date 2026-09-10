package com.AufCo.audiolang.client;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "audiolang", bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBindings {
    
    public static final KeyMapping PLAY_SOUND_KEY = new KeyMapping(
        "key.audiolang.play_sound",
        GLFW.GLFW_KEY_R,
        "key.categories.audiolang"
    );
    
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(PLAY_SOUND_KEY);
    }
}