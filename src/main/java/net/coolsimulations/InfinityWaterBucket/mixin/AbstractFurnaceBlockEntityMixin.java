package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

@Mixin(TileEntityFurnace.class)
public abstract class AbstractFurnaceBlockEntityMixin {
	
	@Shadow
	protected ItemStack[] furnaceItemStacks;

	@Shadow
	public abstract boolean canSmelt();

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "smeltItem")
	private void iwb$modifyWaterBucketBehavior(CallbackInfo info) {
		if (canSmelt()) {
			ItemStack itemStack = (ItemStack) furnaceItemStacks[0];
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, furnaceItemStacks[1]) > 0 && ((ItemStack) furnaceItemStacks[1]).getItem() == Items.bucket) {
				if (itemStack.getItem() == Item.getItemFromBlock(Blocks.sponge) && itemStack.getMetadata() == 1 && furnaceItemStacks[1] != null) {
					ItemStack iwb = new ItemStack(Items.water_bucket);
					iwb.addEnchantment(Enchantment.infinity, 1);
					furnaceItemStacks[1] = iwb;
				}
			}
		}
	}
}
