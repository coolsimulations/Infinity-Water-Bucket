package net.coolsimulations.InfinityWaterBucket;

import net.coolsimulations.InfinityWaterBucket.config.IWBConfigCommon;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.level.ItemLike;

import java.util.ServiceLoader;

public class InfinityWaterBucketCommon {

    public static final IWBConfigCommon CONFIG = load(IWBConfigCommon.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        IWBReference.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    public static boolean isMilkBucket(ItemLike item) {
        return item.asItem() instanceof MilkBucketItem && item.asItem().hasCraftingRemainingItem() && item.asItem().getCraftingRemainingItem() == Items.BUCKET && InfinityWaterBucketCommon.CONFIG.getInfiniteMilkBucket();
    }

    public static boolean isSolidBucket(ItemLike item) {
        return item.asItem() instanceof SolidBucketItem && InfinityWaterBucketCommon.CONFIG.getInfiniteSolidBucket();
    }
}
