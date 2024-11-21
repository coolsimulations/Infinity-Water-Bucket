package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.core.dispenser.BlockSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

@Mixin(targets = "net/minecraft/core/dispenser/DispenseItemBehavior$6")
public abstract class DispenseItemBehavior6Mixin extends DefaultDispenseItemBehavior {

    @Inject(at = @At(value = "HEAD", ordinal = 0), method = "execute", cancellable = true)
    private void iwb$getEmptyBucket(BlockSource blockSource, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {
        if (InfinityWaterBucketCommon.hasInfinity(itemStack) && itemStack.is(Items.BUCKET)) {
            ServerLevel serverLevel = blockSource.level();
            BlockPos blockPos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
            BlockState blockState = serverLevel.getBlockState(blockPos);
            Block block = blockState.getBlock();

            if (block instanceof BucketPickup) {
                ItemStack itemStack2 = ((BucketPickup)block).pickupBlock(null, serverLevel, blockPos, blockState);
                if (itemStack2.isEmpty())
                    cir.setReturnValue(super.execute(blockSource, itemStack));
                serverLevel.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            } else {
                cir.setReturnValue(super.execute(blockSource, itemStack));
            }
            cir.setReturnValue(itemStack);
        }
    }
}
