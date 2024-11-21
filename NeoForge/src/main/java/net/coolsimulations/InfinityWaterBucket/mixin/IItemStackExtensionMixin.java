package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IItemStackExtension.class)
public interface IItemStackExtensionMixin {

    @Shadow abstract ItemStack self();

    /**
     * And, of course, NeoForge has to make this more difficult by overwriting the bad method
     */
    @Inject(at = @At("HEAD"), method = "supportsEnchantment", cancellable = true)
    private void iwb$infinityStub(Holder<Enchantment> enchantment, CallbackInfoReturnable<Boolean> info) {
        if (enchantment.value().description().getString().equals(Component.translatable("enchantment.minecraft.infinity").getString()) && (this.self().getItem() == Items.WATER_BUCKET || this.self().getItem() == Items.BUCKET ||
                (this.self().getItem() == Items.LAVA_BUCKET && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket()) ||
                InfinityWaterBucketCommon.isMilkBucket(this.self().getItem()) ||
                InfinityWaterBucketCommon.isSolidBucket(this.self().getItem())))
            info.setReturnValue(true);
    }
}
