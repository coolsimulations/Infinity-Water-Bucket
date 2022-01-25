package net.coolsimulations.InfinityWaterBucket.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {
	
	@Shadow
	protected NonNullList<ItemStack> items;

	@Shadow
	public boolean canBurn(@Nullable Recipe<?> recipe) {
		throw new AssertionError();
	}

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "burn")
	private void iwb$modifyWaterBucketBehavior(@Nullable Recipe<?> recipe, CallbackInfo info) {
		if (recipe != null && canBurn(recipe)) {
			ItemStack itemStack = items.get(0);
			if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, items.get(1)) > 0 && ((ItemStack) items.get(1)).getItem() == Items.BUCKET) {
				if (itemStack.getItem() == Blocks.WET_SPONGE.asItem() && !((ItemStack) items.get(1)).isEmpty()) {
					ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
					iwb.enchant(Enchantments.INFINITY_ARROWS, 1);
					items.set(1, iwb);
				}
			}
		}
	}
}
