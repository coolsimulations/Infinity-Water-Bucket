package net.coolsimulations.InfinityWaterBucket.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.DefaultedList;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {
	
	@Shadow
	protected DefaultedList<ItemStack> inventory;

	@Shadow
	public boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe) {
		throw new AssertionError();
	}

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "craftRecipe")
	private void iwb$modifyWaterBucketBehavior(@Nullable Recipe<?> recipe, CallbackInfo info) {
		if (recipe != null && canAcceptRecipeOutput(recipe)) {
			ItemStack itemStack = inventory.get(0);
			if(EnchantmentHelper.getLevel(Enchantments.INFINITY, inventory.get(1)) > 0 && ((ItemStack) inventory.get(1)).getItem() == Items.BUCKET) {
				if (itemStack.getItem() == Blocks.WET_SPONGE.asItem() && !((ItemStack) inventory.get(1)).isEmpty()) {
					ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
					iwb.addEnchantment(Enchantments.INFINITY, 1);
					inventory.set(1, iwb);
				}
			}
		}
	}
}
