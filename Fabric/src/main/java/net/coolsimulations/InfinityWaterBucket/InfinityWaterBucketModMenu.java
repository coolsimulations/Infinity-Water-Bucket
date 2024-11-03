package net.coolsimulations.InfinityWaterBucket;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.coolsimulations.InfinityWaterBucket.config.IWBConfigGUI;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class InfinityWaterBucketModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return IWBConfigGUI::getConfigScreen;
    }
}
