package net.coolsimulations.InfinityWaterBucket.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
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
	public boolean canBurn(RegistryAccess registryAccess, @Nullable Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i) {
		throw new AssertionError();
	}

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "burn", cancellable = true)
	private void iwb$modifyWaterBucketBehavior(RegistryAccess registryAccess, @Nullable Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i, CallbackInfoReturnable<Boolean> cir) {
		if (recipe != null && canBurn(registryAccess, recipe, nonNullList, i)) {
			ItemStack itemStack = (ItemStack) nonNullList.get(0);
			if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, nonNullList.get(1)) > 0 && ((ItemStack) nonNullList.get(1)).is(Items.BUCKET)) {
				if (itemStack.is(Blocks.WET_SPONGE.asItem()) && !((ItemStack) nonNullList.get(1)).isEmpty()) {
					ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
					iwb.enchant(Enchantments.INFINITY_ARROWS, 1);
					nonNullList.set(1, iwb);
				}
			}
		}
	}
}
