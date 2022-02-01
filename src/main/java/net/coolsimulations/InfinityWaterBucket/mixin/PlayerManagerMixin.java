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

	@Inject(at = @At("TAIL"), method = "onPlayerConnect", cancellable = true)
	public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {

		if(IWBUpdateHandler.isOld == true) {
			if(player.getServer().isDedicated()) {
				if(player.allowsPermissionLevel(player.getServer().getOpPermissionLevel())) {
					messageOutdated(player);
				}
			} else {
				messageOutdated(player);
			}
		}
	}
	
	@Unique
	private static void messageOutdated(ServerPlayerEntity player) {
		player.sendMessage(IWBUpdateHandler.updateInfo);
		if(IWBUpdateHandler.updateVersionInfo != null)
			player.sendMessage(IWBUpdateHandler.updateVersionInfo);
	}
}
