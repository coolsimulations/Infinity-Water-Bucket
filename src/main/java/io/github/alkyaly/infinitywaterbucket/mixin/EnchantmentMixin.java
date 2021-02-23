package io.github.alkyaly.infinitywaterbucket.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Enchantment.class, priority = 999)
public class EnchantmentMixin {

    @Inject(at = @At("RETURN"), method = "isAcceptableItem", cancellable = true)
    private void isInfinityAcceptableKindaCompatWithOtherMods(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        if ((Enchantment)(Object)this instanceof InfinityEnchantment) {
            info.setReturnValue(info.getReturnValueZ() || stack.getItem() == Items.WATER_BUCKET);
        }
    }

}
