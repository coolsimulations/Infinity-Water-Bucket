package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.core.dispenser.BlockSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mixin(targets = "net/minecraft/core/dispenser/DispenseItemBehavior$6")
public class DispenseItemBehavior6Mixin {

    @Unique
    private ItemStack iwb$bucket;

    @Inject(method = "execute", at = @At("HEAD"))
    public void getBucketItem(BlockSource source, ItemStack stack, CallbackInfoReturnable<ItemStack> info) {
        iwb$bucket = stack.copy();
    }

    @Inject(at = @At(value = "RETURN", ordinal = 0), method = "execute", cancellable = true)
    private void iwb$modifyWaterBucketBehavior(BlockSource source, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (InfinityWaterBucketCommon.hasInfinity(iwb$bucket) && (iwb$bucket.is(Items.WATER_BUCKET) ||
                (iwb$bucket.is(Items.LAVA_BUCKET) && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket()) ||
                InfinityWaterBucketCommon.isSolidBucket(iwb$bucket.getItem())))
            cir.setReturnValue(iwb$bucket);
    }
}
