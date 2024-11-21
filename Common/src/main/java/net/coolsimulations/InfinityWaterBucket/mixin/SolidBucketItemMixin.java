package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SolidBucketItem.class)
public abstract class SolidBucketItemMixin {

    @Unique
    private ItemStack iwb$solid;

    @Inject(method = "useOn", at = @At("HEAD"))
    public void getSolidItem(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
        iwb$solid = context.getItemInHand().copy();
    }

    @ModifyArg(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setItemInHand(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;)V"), index = 1)
    private ItemStack injected(ItemStack stack) {
        if (InfinityWaterBucketCommon.hasInfinity(iwb$solid) && InfinityWaterBucketCommon.CONFIG.getInfiniteSolidBucket())
            return iwb$solid;
        return stack;
    }
}
