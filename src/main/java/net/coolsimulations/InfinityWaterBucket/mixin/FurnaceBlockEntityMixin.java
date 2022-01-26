package net.coolsimulations.InfinityWaterBucket.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.DefaultedList;

@Mixin(FurnaceBlockEntity.class)
public abstract class FurnaceBlockEntityMixin {
	
	@Shadow
	protected DefaultedList<ItemStack> field_15154;

	@Shadow
	public boolean method_16815(@Nullable RecipeType recipe) {
		throw new AssertionError();
	}

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "method_16816")
	private void iwb$modifyWaterBucketBehavior(@Nullable RecipeType recipe, CallbackInfo info) {
		if (recipe != null && method_16815(recipe)) {
			ItemStack itemStack = field_15154.get(0);
			if(EnchantmentHelper.getLevel(Enchantments.INFINITY, field_15154.get(1)) > 0 && ((ItemStack) field_15154.get(1)).getItem() == Items.BUCKET) {
				if (itemStack.getItem() == Blocks.WET_SPONGE.method_16312() && !((ItemStack) field_15154.get(1)).isEmpty()) {
					ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
					iwb.addEnchantment(Enchantments.INFINITY, 1);
					field_15154.set(1, iwb);
				}
			}
		}
	}
}
