package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(ItemBucket.class)
public abstract class BucketItemMixin {

	@Inject(at = @At("RETURN"), method = "onItemRightClick", cancellable = true)
	private void iwb$getEmptiedStack(ItemStack stack, World world, EntityPlayer player, CallbackInfoReturnable<ItemStack> info) {
		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 && stack.getItem() == Items.water_bucket) {
			info.setReturnValue(stack);
		}
	}
    
    @Inject(at = @At("HEAD"), method = "func_150910_a", cancellable = true)
    private void iwb$getFilledStack(ItemStack stack, EntityPlayer player, Item filledBucket, CallbackInfoReturnable<ItemStack> info) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 && stack.getItem() == Items.bucket) {
            info.setReturnValue(stack);
        }
    }
}