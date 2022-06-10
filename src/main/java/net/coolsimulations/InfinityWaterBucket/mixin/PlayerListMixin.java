package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.coolsimulations.InfinityWaterBucket.IWBUpdateHandler;
import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucket;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;

@Mixin(PlayerList.class)
public class PlayerListMixin {

	@Inject(at = @At("TAIL"), method = "placeNewPlayer", cancellable = true)
	public void placeNewPlayer(Connection connection, ServerPlayer player, CallbackInfo info) {

		if(IWBUpdateHandler.isOld == true) {
			if(player.getServer().isDedicatedServer()) {
				if(player.hasPermissions(player.getServer().getOperatorUserPermissionLevel())) {
					messageOutdated(player);
				}
			} else {
				messageOutdated(player);
			}
		}
	}
	
	@Unique
	private static void messageOutdated(ServerPlayer player) {
		player.sendSystemMessage(IWBUpdateHandler.updateInfo.withStyle((style) -> {return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable(InfinityWaterBucket.langTranslations("iwb.update.display2")))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));}));
		if(IWBUpdateHandler.updateVersionInfo != null)
			player.sendSystemMessage(IWBUpdateHandler.updateVersionInfo.withStyle((style) -> {return style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable(InfinityWaterBucket.langTranslations("iwb.update.display2")))).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://curseforge.com/minecraft/mc-mods/infinity-water-bucket"));}));
	}
}
