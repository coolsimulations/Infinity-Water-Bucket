package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Shadow public abstract Component description();

    /**
     * I hate this method so much as it relies on {@link #description()} as a way of identifying infinity
     */
    @Inject(at = @At("HEAD"), method = "canEnchant", cancellable = true)
    protected void iwb$infinityStub(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if (this.description().getString().equals(Component.translatable("enchantment.minecraft.infinity").getString()) && (stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.BUCKET ||
                (stack.getItem() == Items.LAVA_BUCKET && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket()) ||
                InfinityWaterBucketCommon.isMilkBucket(stack.getItem()) ||
                InfinityWaterBucketCommon.isSolidBucket(stack.getItem())))
            info.setReturnValue(true);
    }
}
