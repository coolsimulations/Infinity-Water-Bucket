package io.github.alkyaly.infinitywaterbucket.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/block/dispenser/DispenserBehavior$8")
public class DispenserBehaviorMixin {

    @Inject(at = @At(value = "RETURN", ordinal = 0), method = "dispenseSilently", cancellable = true)
    private void iwb$modifyWaterBucketBehavior(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0 && stack.isOf(Items.WATER_BUCKET)) {
            cir.setReturnValue(stack);
        }
    }
}
