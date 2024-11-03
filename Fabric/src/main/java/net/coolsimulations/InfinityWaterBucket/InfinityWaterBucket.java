package net.coolsimulations.InfinityWaterBucket;

import net.coolsimulations.InfinityWaterBucket.config.IWBConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class InfinityWaterBucket implements ModInitializer {

    @Override
    public void onInitialize() {
        IWBConfig.init(new File(FabricLoader.getInstance().getConfigDir().toFile(), IWBReference.MOD_ID + ".json"));
    }
}
