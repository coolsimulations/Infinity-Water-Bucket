package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Redirect(method = "burn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 1))
    private static boolean injected(ItemStack stack, Item item) {
        if (InfinityWaterBucketCommon.hasInfinity(stack))
            return false;
        else
            return stack.is(item);
    }

    /**
     * Unfortunately, no infinite lava fuel source on Fabric as there is no way to get the original {@link ItemStack} since the method is static
     */
    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private static void iwb$modifyLavaBucketBehavior(ItemStack stack, int amount) {
        if (InfinityWaterBucketCommon.hasInfinity(stack) && stack.getItem() == Items.LAVA_BUCKET && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket())
            return;
        stack.shrink(1);
    }
}
