package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item {

	@Shadow
	private Block fluid;

	@Shadow
	public abstract boolean method_6320(World world, int i, int j, int k);

	@Inject(at = @At("RETURN"), method = "onStartUse", cancellable = true)
	private void iwb$getEmptiedStack(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> info) {
		if (EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, stack) > 0 && stack.getItem() == Items.WATER_BUCKET) {
			info.setReturnValue(stack);
		}
	}

	@Inject(at = @At("HEAD"), method = "fill", cancellable = true)
	private void iwb$getFilledStack(ItemStack stack, PlayerEntity player, Item filledBucket, CallbackInfoReturnable<ItemStack> info) {
		if (EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, stack) > 0) {
			if(stack.getItem() == Items.BUCKET) {
				info.setReturnValue(stack);
			}
		}
	}
}
