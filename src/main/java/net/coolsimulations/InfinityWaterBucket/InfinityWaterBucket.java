package net.coolsimulations.InfinityWaterBucket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class InfinityWaterBucket implements ModInitializer {
	
	@Override
	public void onInitialize() {
		IWBUpdateHandler.init();
	}
	
	public static String langTranslations(String key) {

		if(FabricLoader.getInstance().isModLoaded("fabric")) {
			return key;
		} else {
			if(key.equals("iwb.update.display1"))
				return "This is an old version of %s! Version %s is now available!";
			else if(key.equals("iwb.update.display2"))
				return "Please click to download!";
			else if(key.equals("iwb.update.display3"))
				return "%s no longer supports Minecraft Version %s! Please update to a newer Minecraft Version for more features!";
			else
				return "iwb.missing.translation";
		}
	}

}
