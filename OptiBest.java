package com.optibest;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;

public class OptiBest implements ClientModInitializer {

    private static boolean isEnabled = true; // mod aktif mi

    @Override
    public void onInitializeClient() {
        System.out.println("OptiBest 1.21.4 mod loaded");

        // FPS optimize thread
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                    if (isEnabled) {
                        applyOptimization();
                    }
                } catch (Exception ignored) {}
            }
        }).start();

        // Mod Menu Config GUI
        ConfigBuilder builder = ConfigBuilder.create()
                .setTitle("OptiBest Settings");

        ConfigCategory general = builder.getOrCreateCategory("General");
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        BooleanListEntry toggleEntry = entryBuilder.startBooleanToggle("Enable OptiBest", isEnabled)
                .setDefaultValue(true)
                .setSaveConsumer(val -> isEnabled = val)
                .build();

        general.addEntry(toggleEntry);
        builder.build(); // Mod Menu otomatik algılar
    }

    private static void applyOptimization() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client != null && client.options != null) {
            client.options.getViewDistance().setValue(6);
            client.options.getParticles().setValue(ParticlesMode.MINIMAL);
            client.options.getEntityShadows().setValue(false);
            client.options.getAo().setValue(false);
        }

        if (client != null && client.world != null && client.player != null) {
            client.world.getEntities().forEach(entity -> {
                if (entity.distanceTo(client.player) > 32) {
                    entity.setInvisible(true);
                }
            });
        }
    }
}
