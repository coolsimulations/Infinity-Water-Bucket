package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.coolsimulations.InfinityWaterBucket.IWBUpdateHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

	@Inject(at = @At("TAIL"), method = "method_12827", cancellable = true)
	public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {

		if(IWBUpdateHandler.isOld == true) {
			if(player.server.isDedicated()) {
				if(player.method_15592(player.server.getOpPermissionLevel())) {
					messageOutdated(player);
				}
			} else {
				messageOutdated(player);
			}
		}
	}
	
	@Unique
	private static void messageOutdated(ServerPlayerEntity player) {
		player.method_5505(IWBUpdateHandler.updateInfo);
		player.method_5505(IWBUpdateHandler.updateVersionInfo);
	}
}
