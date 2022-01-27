package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.class_2963;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V", shift = At.Shift.AFTER), method = "method_11373", slice = @Slice(
			from = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;method_8638(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z")
			), cancellable = true)
    private void iwb$getEmptiedStack(ItemStack stack, World world, PlayerEntity playerEntity, Hand hand, CallbackInfoReturnable<class_2963<ItemStack>> info) {
        if (EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.WATER_BUCKET) {
            info.setReturnValue(new class_2963(ActionResult.SUCCESS, stack));
        }
    }

	@Inject(at = @At("HEAD"), method = "fill", cancellable = true)
	private void iwb$getFilledStack(ItemStack stack, PlayerEntity player, Item filledBucket, CallbackInfoReturnable<ItemStack> info) {
		if (EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0) {
			if(stack.getItem() == Items.BUCKET) {
				info.setReturnValue(stack);
			}
		}
	}
}
