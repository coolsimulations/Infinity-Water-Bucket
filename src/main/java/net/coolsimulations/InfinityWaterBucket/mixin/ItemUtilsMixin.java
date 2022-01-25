package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

@Mixin(ItemUtils.class)
public class ItemUtilsMixin {

	@Inject(at = @At("HEAD"), method = "createFilledResult", cancellable = true)
	private static  void iwb$createFilledResult(ItemStack stack, Player player, ItemStack stack2, boolean bl, CallbackInfoReturnable<ItemStack> info) {
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
