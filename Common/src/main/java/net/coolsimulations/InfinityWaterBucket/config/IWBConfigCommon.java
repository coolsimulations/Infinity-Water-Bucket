package net.coolsimulations.InfinityWaterBucket.config;

import net.coolsimulations.InfinityWaterBucket.IWBReference;

public interface IWBConfigCommon {

    String TRANSLATION_KEY = ".config.gui.";
    String TOOLTIP = ".tooltip";
    String LAVA_BUCKET = "infinite_lava_bucket";
    String LAVA_BUCKET_COMMENT = " If true, lava buckets can be enchanted too ";
    String MILK_BUCKET = "infinite_milk_bucket";
    String MILK_BUCKET_COMMENT = " If true, milk buckets can be enchanted too ";
    String SOLID_BUCKET = "infinite_solid_bucket";
    String SOLID_BUCKET_COMMENT = " If true, powdered snow buckets can be enchanted too ";

    default String getTranslationKey(String value) {
        return IWBReference.MOD_ID + TRANSLATION_KEY + value;
    }

    default String getTooltipTranslationKey(String value) {
        return getTranslationKey(value) + TOOLTIP;
    }

    void updateConfig();

    boolean getInfiniteLavaBucket();

    boolean getInfiniteMilkBucket();

    boolean getInfiniteSolidBucket();
}
