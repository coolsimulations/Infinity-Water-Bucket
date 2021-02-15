package io.github.alkyaly.infinitywaterbucket.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin extends Item {

    public BucketItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "getEmptiedStack", cancellable = true)
    private void getEmptiedStack(ItemStack stack, PlayerEntity player, CallbackInfoReturnable<ItemStack> info) {
        if(player.abilities.creativeMode || (EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.WATER_BUCKET)) {
            info.setReturnValue(stack);
        } else info.setReturnValue(new ItemStack(Items.BUCKET));
    }
}

