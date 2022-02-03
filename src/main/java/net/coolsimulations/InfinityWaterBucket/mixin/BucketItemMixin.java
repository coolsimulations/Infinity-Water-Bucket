package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    @Inject(at = @At("HEAD"), method = "emptyBucket", cancellable = true)
    private void iwb$getEmptySuccessItem(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ItemStack> info) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.WATER_BUCKET) {
            info.setReturnValue(stack);
        }
    }
    
    @Inject(at = @At("HEAD"), method = "fillBucket", cancellable = true)
    private void iwb$getFilledStack(ItemStack stack, PlayerEntity player, Item filledBucket, CallbackInfoReturnable<ItemStack> info) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.BUCKET) {
            info.setReturnValue(stack);
        }
    }
}