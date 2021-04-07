package io.github.alkyaly.infinitywaterbucket.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InfinityEnchantment.class)
public class InfinityEnchantmentMixin extends EnchantmentMixin {

    @Override
    protected void stub(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if (stack.getItem() == Items.WATER_BUCKET) {
            info.setReturnValue(true);
        }
    }
}
@Mixin(Enchantment.class)
class EnchantmentMixin {

    @Inject(at = @At("HEAD"), method = "isAcceptableItem", cancellable = true)
    protected void stub(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
    }

}
