package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.core.dispenser.BlockSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

@Mixin(targets = "net/minecraft/core/dispenser/DispenseItemBehavior$6")
public class DispenseItemBehavior6Mixin {

    @Inject(at = @At(value = "RETURN", ordinal = 0), method = "execute", cancellable = true)
    private void iwb$modifyWaterBucketBehavior(BlockSource source, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && (stack.is(Items.WATER_BUCKET) ||
                (stack.is(Items.LAVA_BUCKET) && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket()) ||
                InfinityWaterBucketCommon.isSolidBucket(stack.getItem())))
            cir.setReturnValue(stack);
    }
}
