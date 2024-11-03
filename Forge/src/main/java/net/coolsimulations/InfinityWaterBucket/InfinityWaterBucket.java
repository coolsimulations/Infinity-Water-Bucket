package net.coolsimulations.InfinityWaterBucket;

import net.coolsimulations.InfinityWaterBucket.config.IWBConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod(value = IWBReference.MOD_ID)
@Mod.EventBusSubscriber(modid = IWBReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InfinityWaterBucket {

    public InfinityWaterBucket() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IWBConfig.commonSpec);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event) {
        IWBReference.LOG.debug("Loaded " + IWBReference.MOD_NAME + " config file {}", event.getConfig().getFileName());
        InfinityWaterBucketCommon.CONFIG.updateConfig();
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading event) {
        IWBReference.LOG.debug(IWBReference.MOD_NAME + " config just got changed on the file system!");
        InfinityWaterBucketCommon.CONFIG.updateConfig();
    }
}
