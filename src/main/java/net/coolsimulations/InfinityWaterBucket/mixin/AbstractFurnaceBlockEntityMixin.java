package net.coolsimulations.InfinityWaterBucket.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.NonNullList;

@Mixin(AbstractFurnaceTileEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {
	
	@Shadow
	protected NonNullList<ItemStack> items;

	@Shadow
	public boolean canSmelt(@Nullable IRecipe<?> recipe) {
		throw new AssertionError();
	}

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "smelt")
	private void iwb$modifyWaterBucketBehavior(@Nullable IRecipe<?> recipe, CallbackInfo info) {
		if (recipe != null && canSmelt(recipe)) {
			ItemStack itemStack = (ItemStack) items.get(0);
			if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, items.get(1)) > 0 && ((ItemStack) items.get(1)).getItem() == Items.BUCKET) {
				if (itemStack.getItem() == Blocks.WET_SPONGE.asItem() && !((ItemStack) items.get(1)).isEmpty()) {
					ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
					iwb.addEnchantment(Enchantments.INFINITY, 1);
					items.set(1, iwb);
				}
			}
		}
	}
}
