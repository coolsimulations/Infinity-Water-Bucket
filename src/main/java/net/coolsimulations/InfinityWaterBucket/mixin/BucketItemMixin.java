package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

@Mixin(ItemBucket.class)
public abstract class BucketItemMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addStat(Lnet/minecraft/stats/StatBase;)V", shift = At.Shift.AFTER), method = "onItemRightClick", slice = @Slice(
			from = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;isReplaceable(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Z")
			), cancellable = true)
    private void iwb$getEmptiedStack(World world, EntityPlayer playerEntity, EnumHand hand, CallbackInfoReturnable<ActionResult<ItemStack>> info) {
		ItemStack stack = playerEntity.getHeldItem(hand);
		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.WATER_BUCKET) {
            info.setReturnValue(new ActionResult(EnumActionResult.SUCCESS, stack));
        }
    }
    
    @Inject(at = @At("HEAD"), method = "fillBucket", cancellable = true)
    private void iwb$getFilledStack(ItemStack stack, EntityPlayer player, Item filledBucket, CallbackInfoReturnable<ItemStack> info) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.BUCKET) {
            info.setReturnValue(stack);
        }
    }
}