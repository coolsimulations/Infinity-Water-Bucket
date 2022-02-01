package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
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
		if(IWBUpdateHandler.isOld == true && event.getPlayer() instanceof ServerPlayer) {
			if(event.getPlayer().getServer().isDedicatedServer()) {
				if(event.getPlayer().hasPermissions(event.getPlayer().getServer().getOperatorUserPermissionLevel())) {
					messageOutdated((ServerPlayer) event.getPlayer());
				}
			} else {
				messageOutdated((ServerPlayer) event.getPlayer());
			}
		}
	}

	private static void messageOutdated(ServerPlayer player) {
		player.sendMessage(IWBUpdateHandler.updateInfo.withStyle((style) -> {return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("iwb.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));}), ChatType.SYSTEM, Util.NIL_UUID);
		if(IWBUpdateHandler.updateVersionInfo != null)
			player.sendMessage(IWBUpdateHandler.updateVersionInfo.withStyle((style) -> {return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableComponent("iwb.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));}), ChatType.SYSTEM, Util.NIL_UUID);
	}
}