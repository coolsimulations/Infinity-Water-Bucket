package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.coolsimulations.InfinityWaterBucket.IWBUpdateHandler;
import net.minecraft.network.Connection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

	@Inject(at = @At("TAIL"), method = "onPlayerConnect", cancellable = true)
	public void onPlayerConnect(Connection connection, ServerPlayerEntity player, CallbackInfo info) {

		if(IWBUpdateHandler.isOld == true) {
			if(player.server.isDedicated()) {
				if(player.server.getPlayerManager().method_2007(player.method_3334())) {
					messageOutdated(player);
				}
			} else {
				messageOutdated(player);
			}
		}
	}
	
	@Unique
	private static void messageOutdated(ServerPlayerEntity player) {
		player.method_3332(IWBUpdateHandler.updateInfo, new Object[] {IWBUpdateHandler.iwb, IWBUpdateHandler.version});
		if(IWBUpdateHandler.updateVersionInfo != null)
			player.method_3332(IWBUpdateHandler.updateVersionInfo);
	}
}
