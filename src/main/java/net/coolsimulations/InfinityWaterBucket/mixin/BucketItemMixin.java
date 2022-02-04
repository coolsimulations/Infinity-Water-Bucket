package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ItemBucket.class)
public abstract class BucketItemMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;triggerAchievement(Lnet/minecraft/stats/StatBase;)V", shift = At.Shift.AFTER), method = "onItemRightClick", slice = @Slice(
			from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemBucket;tryPlaceContainedLiquid(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Z")
			), cancellable = true)
    private void iwb$getEmptiedStack(ItemStack stack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> info) {
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 && stack.getItem() == Items.water_bucket) {
            info.setReturnValue(stack);
        }
    }
    
    @Inject(at = @At("HEAD"), method = "fillBucket", cancellable = true)
    private void iwb$getFilledStack(ItemStack stack, EntityPlayer player, Item filledBucket, CallbackInfoReturnable<ItemStack> info) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 && stack.getItem() == Items.bucket) {
            info.setReturnValue(stack);
        }
    }
}