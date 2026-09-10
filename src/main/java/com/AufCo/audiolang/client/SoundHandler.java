package com.AufCo.audiolang.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "audiolang", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SoundHandler {
    
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.PLAY_SOUND_KEY.consumeClick()) {
            playSoundBasedOnContext();
        }
    }
    
    private static void playSoundBasedOnContext() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        Level level = mc.level;
        
        if (player == null || level == null) {
            return;
        }
        
        // Get the player's raycast result
        HitResult hitResult = mc.hitResult;
        
        if (hitResult != null) {
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                // Player is looking at a block
                BlockHitResult blockHit = (BlockHitResult) hitResult;
                BlockPos pos = blockHit.getBlockPos();
                BlockState blockState = level.getBlockState(pos);
                Block block = blockState.getBlock();
                
                String translationKey = block.getDescriptionId();
                playSound(translationKey);
                return;
            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                // Player is looking at an entity
                EntityHitResult entityHit = (EntityHitResult) hitResult;
                Entity entity = entityHit.getEntity();
                
                String translationKey = entity.getType().getDescriptionId();
                playSound(translationKey);
                return;
            }
        }
        
        // Not looking at block or entity, check main hand item
        ItemStack mainHandItem = player.getMainHandItem();
        if (!mainHandItem.isEmpty()) {
            String translationKey = mainHandItem.getItem().getDescriptionId();
            playSound(translationKey);
            return;
        }
        
        // No item in main hand, play biome sound
        BlockPos playerPos = player.blockPosition();
        
        // Try to get biome information
        try {
            var biomeHolder = level.getBiome(playerPos);
            // Get the registry manager from the client
            var registryAccess = mc.getConnection().registryAccess();
            var biomeRegistry = registryAccess.lookupOrThrow(net.minecraft.core.registries.Registries.BIOME);
            ResourceLocation biomeLocation = biomeRegistry.getKey(biomeHolder.value());
            
            if (biomeLocation != null) {
                String translationKey = "biome." + biomeLocation.getNamespace() + "." + biomeLocation.getPath();
                playSound(translationKey);
            } else {
                // Fallback to plains biome sound
                playSound("biome.minecraft.plains");
            }
        } catch (Exception e) {
            // Fallback to plains biome sound
            playSound("biome.minecraft.plains");
        }
    }
    
    private static void playSound(String translationKey) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        Level level = mc.level;
        
        if (player == null || level == null) {
            return;
        }
        
        // Get current language
        String language = mc.getLanguageManager().getSelected();
        
        // Create sound ID based on translation key and language
        String soundId = translationKey + "_" + language + "_sound";
        
        // Debug output to console
        System.out.println("DEBUG: Translation key: " + translationKey);
        System.out.println("DEBUG: Language: " + language);
        System.out.println("DEBUG: Sound ID: " + soundId);
        
        try {
            ResourceLocation soundLocation = ResourceLocation.fromNamespaceAndPath("audiolang", soundId);
            SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(soundLocation);
            
            System.out.println("DEBUG: Sound location: " + soundLocation);
            
            // Play the sound
            level.playLocalSound(
                player.getX(), 
                player.getY(), 
                player.getZ(), 
                soundEvent, 
                SoundSource.VOICE, 
                1.0f, 
                1.0f, 
                false
            );
            
            System.out.println("DEBUG: Sound playback attempted successfully");
            
        } catch (Exception e) {
            System.out.println("DEBUG: Error - " + e.getMessage());
            e.printStackTrace();
        }
    }
}