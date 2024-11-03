package net.coolsimulations.InfinityWaterBucket.config;

public class InfinityWaterBucketConfig implements IWBConfigCommon {

    @Override
    public void updateConfig() {
        IWBConfig.load(IWBConfig.getFile());
    }

    @Override
    public boolean getInfiniteLavaBucket() {
        return IWBConfig.ilb;
    }

    @Override
    public boolean getInfiniteMilkBucket() {
        return IWBConfig.imb;
    }

    @Override
    public boolean getInfiniteSolidBucket() {
        return IWBConfig.isb;
    }
}
