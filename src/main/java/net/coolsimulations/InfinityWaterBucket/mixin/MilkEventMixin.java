package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mixin(targets = "com.natamus.milkallthemobs.events.MilkEvent", remap = false)
public class MilkEventMixin {
	
	@Inject(at = @At("HEAD"), method = "onEntityInteract", cancellable = true, require = 0)
	public void iwb$preventMilking(PlayerInteractEvent.EntityInteract event, CallbackInfo info) {
		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, event.getItemStack()) > 0 && event.getItemStack().getItem() == Items.BUCKET && !event.getWorld().isRemote) {
			event.setCanceled(true);
			info.cancel();
		}
	}
}
