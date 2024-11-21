package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public abstract class MilkBucketItemMixin {

    @Unique
    private ItemStack iwb$milk;

    @Inject(method = "finishUsingItem", at = @At("HEAD"))
    public void getMilkItem(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> info) {
        iwb$milk = stack.copy();
    }

    @Inject(at = @At("RETURN"), method = "finishUsingItem", cancellable = true)
    private void iwb$finishUsingItem(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> info) {
        if (InfinityWaterBucketCommon.hasInfinity(iwb$milk) && InfinityWaterBucketCommon.CONFIG.getInfiniteMilkBucket())
            info.setReturnValue(iwb$milk);
    }
}
