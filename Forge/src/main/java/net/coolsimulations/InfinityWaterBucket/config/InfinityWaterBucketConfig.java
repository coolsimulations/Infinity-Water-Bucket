package net.coolsimulations.InfinityWaterBucket.config;

public class InfinityWaterBucketConfig implements IWBConfigCommon {

    @Override
    public void updateConfig() {
        IWBConfig.COMMON.ilb = IWBConfig.COMMON.infiniteLavaBucket.get();
        IWBConfig.COMMON.imb = IWBConfig.COMMON.infiniteMilkBucket.get();
        IWBConfig.COMMON.isb = IWBConfig.COMMON.infiniteSolidBucket.get();
    }

    @Override
    public boolean getInfiniteLavaBucket() {
        return IWBConfig.COMMON.ilb;
    }

    @Override
    public boolean getInfiniteMilkBucket() {
        return IWBConfig.COMMON.imb;
    }

    @Override
    public boolean getInfiniteSolidBucket() {
        return IWBConfig.COMMON.isb;
    }
}
