package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mixin(targets = "hardcorshik31.getinthebucket.util.MobInteractHandler", remap = false)
public class MobInteractHandlerMixin {
	
	@Inject(at = @At("HEAD"), method = "onInteract", cancellable = true, require = 0)
	private static void iwb$preventMilking(PlayerInteractEvent.EntityInteractSpecific event, CallbackInfo info) {
		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, event.getItemStack()) > 0 && event.getItemStack().getItem() == Items.BUCKET) {
			event.setCanceled(true);
			info.cancel();
		}
	}
}
