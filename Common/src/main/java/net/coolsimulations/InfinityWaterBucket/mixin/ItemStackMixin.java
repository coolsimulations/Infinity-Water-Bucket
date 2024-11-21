package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.UseRemainder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Unique
    private ItemStack iwb$milk;

    @Inject(method = "applyAfterUseComponentSideEffects", at = @At("HEAD"))
    public void getBucketItem(LivingEntity entity, ItemStack stack, CallbackInfoReturnable<ItemStack> info) {
        iwb$milk = stack.copy();
    }

    @Redirect(method = "applyAfterUseComponentSideEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/UseRemainder;convertIntoRemainder(Lnet/minecraft/world/item/ItemStack;IZLnet/minecraft/world/item/component/UseRemainder$OnExtraCreatedRemainder;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack iwb$finishUsingItem(UseRemainder remainder, ItemStack stack, int amount, boolean reduce, UseRemainder.OnExtraCreatedRemainder extra) {
        if (InfinityWaterBucketCommon.hasInfinity(iwb$milk) && InfinityWaterBucketCommon.CONFIG.getInfiniteMilkBucket())
            return iwb$milk;
        return remainder.convertIntoRemainder(stack, amount, reduce, extra);
    }
}
