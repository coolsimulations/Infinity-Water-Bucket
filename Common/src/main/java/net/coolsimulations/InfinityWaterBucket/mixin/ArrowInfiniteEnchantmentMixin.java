package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ArrowInfiniteEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;

@Mixin(ArrowInfiniteEnchantment.class)
public class ArrowInfiniteEnchantmentMixin extends EnchantmentMixin {

    @Override
    protected void iwb$infinityStub(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if (stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.BUCKET ||
                (stack.getItem() == Items.LAVA_BUCKET && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket()) ||
                InfinityWaterBucketCommon.isMilkBucket(stack.getItem()) ||
                InfinityWaterBucketCommon.isSolidBucket(stack.getItem()))
            info.setReturnValue(true);
    }
}

@Mixin(Enchantment.class)
class EnchantmentMixin {

    /**
     * Using Mixin inheritance, so we can keep compatibility with mods that override this.
     */
    @Inject(at = @At("HEAD"), method = "canEnchant", cancellable = true)
    protected void iwb$infinityStub(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
    }
}
