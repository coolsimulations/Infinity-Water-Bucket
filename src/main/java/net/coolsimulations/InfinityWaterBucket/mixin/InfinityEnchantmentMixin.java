package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(InfinityEnchantment.class)
public class InfinityEnchantmentMixin extends EnchantmentMixin {

	@Override
	protected void iwb$infinityStub(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
		if (stack.getItem() == Item.WATER_BUCKET || stack.getItem() == Item.BUCKET) {
			info.setReturnValue(true);
		}
	}
}

@Mixin(Enchantment.class)
class EnchantmentMixin {
	
	/**
	 * Using Mixin inheritance so we can keep compatibility with mods that override this.
	 */
	@Inject(at = @At("HEAD"), method = "isAcceptableItem", cancellable = true)
	protected void iwb$infinityStub(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
	}
}
