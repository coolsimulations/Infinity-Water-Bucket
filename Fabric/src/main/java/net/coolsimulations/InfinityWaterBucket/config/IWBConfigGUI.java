package net.coolsimulations.InfinityWaterBucket.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class IWBConfigGUI {

    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable(InfinityWaterBucketCommon.CONFIG.getTranslationKey("title")));

        builder.setSavingRunnable(() -> {
            IWBConfig.save(IWBConfig.getFile(), IWBConfig.setValues());
            IWBConfig.load(IWBConfig.getFile());
        });

        builder.setDefaultBackgroundTexture(ResourceLocation.withDefaultNamespace("textures/block/tube_coral_block.png"));

        ConfigCategory common = builder.getOrCreateCategory(Component.translatable(InfinityWaterBucketCommon.CONFIG.getTranslationKey("common")));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        common.addEntry(entryBuilder.startBooleanToggle(Component.translatable(InfinityWaterBucketCommon.CONFIG.getTranslationKey(InfinityWaterBucketCommon.CONFIG.LAVA_BUCKET)), IWBConfig.ilb).setTooltip(Component.translatable(InfinityWaterBucketCommon.CONFIG.getTooltipTranslationKey(InfinityWaterBucketCommon.CONFIG.LAVA_BUCKET))).setDefaultValue(false).setSaveConsumer(newValue->IWBConfig.ilb = newValue).build());
        common.addEntry(entryBuilder.startBooleanToggle(Component.translatable(InfinityWaterBucketCommon.CONFIG.getTranslationKey(InfinityWaterBucketCommon.CONFIG.MILK_BUCKET)), IWBConfig.imb).setTooltip(Component.translatable(InfinityWaterBucketCommon.CONFIG.getTooltipTranslationKey(InfinityWaterBucketCommon.CONFIG.MILK_BUCKET))).setDefaultValue(false).setSaveConsumer(newValue->IWBConfig.imb = newValue).build());
        common.addEntry(entryBuilder.startBooleanToggle(Component.translatable(InfinityWaterBucketCommon.CONFIG.getTranslationKey(InfinityWaterBucketCommon.CONFIG.SOLID_BUCKET)), IWBConfig.isb).setTooltip(Component.translatable(InfinityWaterBucketCommon.CONFIG.getTooltipTranslationKey(InfinityWaterBucketCommon.CONFIG.SOLID_BUCKET))).setDefaultValue(false).setSaveConsumer(newValue->IWBConfig.isb = newValue).build());

        return builder.build();
    }
}
