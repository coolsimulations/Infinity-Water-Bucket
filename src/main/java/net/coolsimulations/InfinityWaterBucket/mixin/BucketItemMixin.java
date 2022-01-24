package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {

    @Inject(at = @At("HEAD"), method = "getEmptySuccessItem", cancellable = true)
    private static void iwb$getEmptySuccessItem(ItemStack stack, Player player, CallbackInfoReturnable<ItemStack> info) {
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0 && stack.is(Items.WATER_BUCKET)) {
            info.setReturnValue(stack);
        }
    }
}
