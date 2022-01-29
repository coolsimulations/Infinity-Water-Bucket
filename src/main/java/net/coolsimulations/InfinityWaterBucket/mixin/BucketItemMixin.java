package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item {

	protected BucketItemMixin(int id) {
		super(id);
	}

	@Unique
	private int iwb$startStack;
	
	@Inject(at = @At("HEAD"), method = "onStartUse", cancellable = true)
	private void iwb$getStartStack(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> info) {
		iwb$startStack = stack.count;
	}

	@Inject(at = @At("RETURN"), method = "onStartUse", cancellable = true)
	private void iwb$getEmptiedStack(ItemStack stack, World world, PlayerEntity player, CallbackInfoReturnable<ItemStack> info) {
		if (EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, stack) > 0) {
			if(stack.getItem() == Item.WATER_BUCKET) {
				info.setReturnValue(stack);
			}
			if(stack.getItem() == Item.BUCKET) {
				
				stack.count = iwb$startStack;
				info.setReturnValue(stack);
			}
		}
	}
}
