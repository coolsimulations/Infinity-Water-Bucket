package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(targets = "com.blackgear.cavesandcliffs.common.item.ItemUsage", remap = false)
public class ItemUsageMixin {

	@Inject(at = @At("HEAD"), method = "exchangeStack", cancellable = true)
	private static  void iwb$createFilledResult(ItemStack stack, PlayerEntity player, ItemStack stack2, boolean bl, CallbackInfoReturnable<ItemStack> info) {
		if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0) {
			if (stack.getItem() == Items.BUCKET) {
				info.setReturnValue(stack);
			}
			if (stack.getItem() == Items.WATER_BUCKET && stack2.getItem() == Items.BUCKET) {
				info.setReturnValue(stack);
			}
		}
	}
}