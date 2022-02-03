package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
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
				if(event.getPlayer().hasPermissions(event.getPlayer().getServer().getOperatorUserPermissionLevel())) {
					messageOutdated((ServerPlayerEntity) event.getPlayer());
				}
			} else {
				messageOutdated((ServerPlayerEntity) event.getPlayer());
			}
		}
	}

	private static void messageOutdated(ServerPlayerEntity player) {
		player.sendMessage(IWBUpdateHandler.updateInfo.withStyle((style) -> {return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("iwb.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));}), ChatType.SYSTEM, Util.NIL_UUID);
		if(IWBUpdateHandler.updateVersionInfo != null)
			player.sendMessage(IWBUpdateHandler.updateVersionInfo.withStyle((style) -> {return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("iwb.update.display2"))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));}), ChatType.SYSTEM, Util.NIL_UUID);
	}
}