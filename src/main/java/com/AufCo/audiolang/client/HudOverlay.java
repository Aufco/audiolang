package com.AufCo.audiolang.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "audiolang", value = {Dist.CLIENT})
public class HudOverlay {
    
    private static final Map<String, String> englishNames = new HashMap<>();
    private static final Map<String, String> spanishNames = new HashMap<>();
    private static boolean namesLoaded = false;
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onCustomizeGuiOverlay(CustomizeGuiOverlayEvent event) {
        if (!namesLoaded) {
            loadDisplayNames();
        }
        
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        Level level = mc.level;
        
        if (player == null || level == null) {
            return;
        }
        
        Font font = mc.font;
        
        // Get player position
        BlockPos playerPos = player.blockPosition();
        String positionText = playerPos.getX() + " " + playerPos.getY() + " " + playerPos.getZ();
        
        // Get translation key using same logic as SoundHandler
        String translationKey = getTranslationKey(mc, player, level);
        
        // Get display names
        String englishName = englishNames.getOrDefault(translationKey, translationKey);
        String spanishName = spanishNames.getOrDefault(translationKey, translationKey);
        
        // Render text in top-left corner without background (transparent)
        int x = 4;
        int y = 4;
        int lineHeight = font.lineHeight + 1;
        
        // Position
        event.getGuiGraphics().drawString(font, positionText, x, y, 0xFFFFFF, false);
        y += lineHeight;
        
        // English name
        event.getGuiGraphics().drawString(font, "en: " + englishName, x, y, 0xFFFFFF, false);
        y += lineHeight;
        
        // Spanish name
        event.getGuiGraphics().drawString(font, "es: " + spanishName, x, y, 0xFFFFFF, false);
    }
    
    private static String getTranslationKey(Minecraft mc, LocalPlayer player, Level level) {
        // Get the player's raycast result
        HitResult hitResult = mc.hitResult;
        
        if (hitResult != null) {
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                // Player is looking at a block
                BlockHitResult blockHit = (BlockHitResult) hitResult;
                BlockPos pos = blockHit.getBlockPos();
                BlockState blockState = level.getBlockState(pos);
                Block block = blockState.getBlock();
                
                return block.getDescriptionId();
            } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                // Player is looking at an entity
                EntityHitResult entityHit = (EntityHitResult) hitResult;
                Entity entity = entityHit.getEntity();
                
                return entity.getType().getDescriptionId();
            }
        }
        
        // Not looking at block or entity, check main hand item
        ItemStack mainHandItem = player.getMainHandItem();
        if (!mainHandItem.isEmpty()) {
            return mainHandItem.getItem().getDescriptionId();
        }
        
        // No item in main hand, get biome
        BlockPos playerPos = player.blockPosition();
        
        // Try to get biome information
        try {
            var biomeHolder = level.getBiome(playerPos);
            // Get the registry manager from the client
            var registryAccess = mc.getConnection().registryAccess();
            var biomeRegistry = registryAccess.lookupOrThrow(net.minecraft.core.registries.Registries.BIOME);
            ResourceLocation biomeLocation = biomeRegistry.getKey(biomeHolder.value());
            
            if (biomeLocation != null) {
                return "biome." + biomeLocation.getNamespace() + "." + biomeLocation.getPath();
            } else {
                return "biome.minecraft.plains";
            }
        } catch (Exception e) {
            return "biome.minecraft.plains";
        }
    }
    
    private static void loadDisplayNames() {
        if (namesLoaded) {
            return;
        }
        
        try {
            // Load English names
            InputStream enStream = HudOverlay.class.getResourceAsStream("/assets/audiolang/lang/en_us_display.json");
            if (enStream != null) {
                JsonObject enJson = new Gson().fromJson(new InputStreamReader(enStream, "UTF-8"), JsonObject.class);
                for (Map.Entry<String, com.google.gson.JsonElement> entry : enJson.entrySet()) {
                    englishNames.put(entry.getKey(), entry.getValue().getAsString());
                }
                enStream.close();
            }
            
            // Load Spanish names
            InputStream esStream = HudOverlay.class.getResourceAsStream("/assets/audiolang/lang/es_mx_display.json");
            if (esStream != null) {
                JsonObject esJson = new Gson().fromJson(new InputStreamReader(esStream, "UTF-8"), JsonObject.class);
                for (Map.Entry<String, com.google.gson.JsonElement> entry : esJson.entrySet()) {
                    spanishNames.put(entry.getKey(), entry.getValue().getAsString());
                }
                esStream.close();
            }
            
            namesLoaded = true;
            System.out.println("DEBUG: Loaded " + englishNames.size() + " English names and " + spanishNames.size() + " Spanish names");
            
        } catch (Exception e) {
            System.out.println("ERROR: Failed to load display names: " + e.getMessage());
            e.printStackTrace();
        }
    }
}