package com.optibest;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.ParticlesMode;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.gui.entries.EnumListEntry;

public class OptiBest implements ClientModInitializer {

    private static boolean modActive = true;

    public enum FPSMode {
        MINIMAL, BALANCED, MAX
    }

    private static FPSMode currentMode = FPSMode.BALANCED;

    @Override
    public void onInitializeClient() {
        System.out.println("OptiBest Ultra 1.21.4 loaded");

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                    if (modActive) applyOptimization();
                } catch (Exception ignored) {}
            }
        }).start();

        ConfigBuilder builder = ConfigBuilder.create().setTitle("OptiBest Settings");
        ConfigCategory general = builder.getOrCreateCategory("General");
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        entryBuilder.startBooleanToggle("Enable OptiBest", modActive)
                .setDefaultValue(true)
                .setSaveConsumer(val -> modActive = val)
                .build();

        EnumListEntry<FPSMode> fpsModeEntry = entryBuilder.startEnumSelector("FPS Mode", FPSMode.class, currentMode)
                .setDefaultValue(FPSMode.BALANCED)
                .setSaveConsumer(val -> currentMode = val)
                .build();

        general.addEntry(fpsModeEntry);
        builder.build();
    }

    private static void applyOptimization() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.options == null || client.player == null) return;

        switch (currentMode) {
            case MINIMAL:
                client.options.getViewDistance().setValue(10);
                client.options.getParticles().setValue(ParticlesMode.ALL);
                client.options.getEntityShadows().setValue(true);
                client.options.getAo().setValue(true);
                break;

            case BALANCED:
                client.options.getViewDistance().setValue(6);
                client.options.getParticles().setValue(ParticlesMode.MINIMAL);
                client.options.getEntityShadows().setValue(false);
                client.options.getAo().setValue(false);
                break;

            case MAX:
                client.options.getViewDistance().setValue(4);
                client.options.getParticles().setValue(ParticlesMode.MINIMAL);
                client.options.getEntityShadows().setValue(false);
                client.options.getAo().setValue(false);
                break;
        }

        client.world.getEntities().forEach(entity -> {
            if (entity.distanceTo(client.player) > 32) entity.setInvisible(true);
        });
    }
}
