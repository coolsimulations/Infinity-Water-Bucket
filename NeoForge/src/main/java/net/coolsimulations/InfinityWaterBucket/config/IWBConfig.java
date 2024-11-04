package net.coolsimulations.InfinityWaterBucket.config;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class IWBConfig {

    public static final IWBConfig COMMON;
    public static final ModConfigSpec commonSpec;

    protected final ModConfigSpec.BooleanValue infiniteLavaBucket;
    protected final ModConfigSpec.BooleanValue infiniteMilkBucket;
    protected final ModConfigSpec.BooleanValue infiniteSolidBucket;
    protected boolean ilb;
    protected boolean imb;
    protected boolean isb;

    public IWBConfig(final ModConfigSpec.Builder builder) {
        builder.comment(" Common configuration settings").push("common");
        infiniteLavaBucket = builder.comment(InfinityWaterBucketCommon.CONFIG.LAVA_BUCKET_COMMENT).translation(InfinityWaterBucketCommon.CONFIG.getTranslationKey(InfinityWaterBucketCommon.CONFIG.LAVA_BUCKET)).define(InfinityWaterBucketCommon.CONFIG.LAVA_BUCKET, false);
        infiniteMilkBucket = builder.comment(InfinityWaterBucketCommon.CONFIG.MILK_BUCKET_COMMENT).translation(InfinityWaterBucketCommon.CONFIG.getTranslationKey(InfinityWaterBucketCommon.CONFIG.MILK_BUCKET)).define(InfinityWaterBucketCommon.CONFIG.MILK_BUCKET, false);
        infiniteSolidBucket = builder.comment(InfinityWaterBucketCommon.CONFIG.SOLID_BUCKET_COMMENT).translation(InfinityWaterBucketCommon.CONFIG.getTranslationKey(InfinityWaterBucketCommon.CONFIG.SOLID_BUCKET)).define(InfinityWaterBucketCommon.CONFIG.SOLID_BUCKET, false);
        builder.pop();
    }

    static {
        final Pair<IWBConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(IWBConfig::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
