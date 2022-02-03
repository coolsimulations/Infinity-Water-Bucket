package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(value = IWBReference.MOD_ID)
@Mod.EventBusSubscriber(modid = IWBReference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InfinityWaterBucket {

	public InfinityWaterBucket() {
		IWBUpdateHandler.init();
		IWBDispenserItemBehavior.init();
	}

	@SubscribeEvent
	public static void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if(IWBUpdateHandler.isOld == true && event.getPlayer() instanceof ServerPlayerEntity) {
			if(event.getPlayer().getServer().isDedicatedServer()) {
				if(event.getPlayer().hasPermissionLevel(event.getPlayer().getServer().getOpPermissionLevel())) {
					messageOutdated((ServerPlayerEntity) event.getPlayer());
				}
			} else {
				messageOutdated((ServerPlayerEntity) event.getPlayer());
			}
		}
	}

	private static void messageOutdated(ServerPlayerEntity player) {
		player.sendMessage(IWBUpdateHandler.updateInfo);
		if(IWBUpdateHandler.updateVersionInfo != null)
			player.sendMessage(IWBUpdateHandler.updateVersionInfo);
	}
}