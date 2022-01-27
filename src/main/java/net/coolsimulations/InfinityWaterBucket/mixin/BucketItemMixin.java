package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.class_2704;
import net.minecraft.class_2961;
import net.minecraft.class_2962;
import net.minecraft.class_2963;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V", shift = At.Shift.AFTER), method = "method_13649", slice = @Slice(
			from = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;method_8638(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z")
			), cancellable = true)
    private void iwb$getEmptiedStack(World world, PlayerEntity playerEntity, class_2961 hand, CallbackInfoReturnable<class_2963<ItemStack>> info) {
		ItemStack stack = playerEntity.method_13047(hand);
        if (EnchantmentHelper.method_11452(class_2704.field_12420, stack) > 0 && stack.getItem() == Items.WATER_BUCKET) {
            info.setReturnValue(new class_2963(class_2962.field_14439, stack));
        }
    }

	@Inject(at = @At("HEAD"), method = "fill", cancellable = true)
	private void iwb$getFilledStack(ItemStack stack, PlayerEntity player, Item filledBucket, CallbackInfoReturnable<ItemStack> info) {
		if (EnchantmentHelper.method_11452(class_2704.field_12420, stack) > 0) {
			if(stack.getItem() == Items.BUCKET) {
				info.setReturnValue(stack);
			}
		}
	}
}
