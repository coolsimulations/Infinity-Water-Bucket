package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;

@Mixin(TileEntityFurnace.class)
public abstract class AbstractFurnaceBlockEntityMixin {
	
	@Shadow
	protected NonNullList<ItemStack> furnaceItemStacks;

	@Shadow
	public abstract boolean canSmelt();

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "smeltItem")
	private void iwb$modifyWaterBucketBehavior(CallbackInfo info) {
		if (canSmelt()) {
			ItemStack itemStack = (ItemStack) furnaceItemStacks.get(0);
			if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, furnaceItemStacks.get(1)) > 0 && ((ItemStack) furnaceItemStacks.get(1)).getItem() == Items.BUCKET) {
				if (itemStack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemStack.getMetadata() == 1 && !((ItemStack) furnaceItemStacks.get(1)).isEmpty()) {
					ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
					iwb.addEnchantment(Enchantments.INFINITY, 1);
					furnaceItemStacks.set(1, iwb);
				}
			}
		}
	}
}
