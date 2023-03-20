package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mixin(targets = "com.natamus.milkallthemobs.events.MilkEvent", remap = false)
public class MilkEventMixin {
	
	@Inject(at = @At("HEAD"), method = "onEntityInteract", cancellable = true, require = 0)
	public void iwb$preventMilking(PlayerInteractEvent.EntityInteract event, CallbackInfo info) {
		if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, event.getItemStack()) > 0 && event.getItemStack().is(Items.BUCKET) && !event.getLevel().isClientSide) {
			event.setCanceled(true);
			info.cancel();
		}
	}
}
