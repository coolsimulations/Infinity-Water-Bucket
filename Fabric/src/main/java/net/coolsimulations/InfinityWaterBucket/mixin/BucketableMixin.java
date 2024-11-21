package net.coolsimulations.InfinityWaterBucket.mixin;

import java.util.Optional;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mixin(Bucketable.class)
public interface BucketableMixin {

    @Inject(at = @At ("HEAD" ), method = "bucketMobPickup", cancellable = true, require = 0)
    private static <T extends LivingEntity & Bucketable> void iwb$bucketMobPickup(Player player, InteractionHand interactionHand, T livingEntity, CallbackInfoReturnable<Optional<InteractionResult>> info) {
        ItemStack stack = player.getItemInHand (interactionHand);
        if (InfinityWaterBucketCommon.hasInfinity(stack) && stack.is (Items.WATER_BUCKET) && livingEntity.isAlive())
            info.setReturnValue (Optional.empty());
    }
}