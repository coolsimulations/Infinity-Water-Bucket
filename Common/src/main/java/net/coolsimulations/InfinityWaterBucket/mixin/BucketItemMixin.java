package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    @Inject(at = @At("HEAD"), method = "getEmptySuccessItem", cancellable = true)
    private static void iwb$getEmptySuccessItem(ItemStack stack, Player player, CallbackInfoReturnable<ItemStack> info) {
        if (InfinityWaterBucketCommon.hasInfinity(stack) && (stack.is(Items.WATER_BUCKET) ||
                (stack.is(Items.LAVA_BUCKET) && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket()) ||
                InfinityWaterBucketCommon.isMilkBucket(stack.getItem()) ||
                InfinityWaterBucketCommon.isSolidBucket(stack.getItem())))
            info.setReturnValue(stack);
    }
}