package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(FurnaceBlockEntity.class)
public abstract class FurnaceBlockEntityMixin {
	
	@Shadow
	private ItemStack[] stacks = new ItemStack[3];;

	@Shadow
	public boolean method_525() {
		throw new AssertionError();
	}

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "method_524")
	private void iwb$modifyWaterBucketBehavior(CallbackInfo info) {
		if (method_525()) {
			ItemStack itemStack = this.stacks[0];
			if(EnchantmentHelper.getLevel(Enchantments.INFINITY, this.stacks[1]) > 0 && ((ItemStack) this.stacks[1]).getItem() == Items.BUCKET) {
				if (itemStack.getItem() == Item.fromBlock(Blocks.SPONGE) && itemStack.getMeta() == 1 && !((ItemStack) this.stacks[1]).isEmpty()) {
					ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
					iwb.addEnchantment(Enchantments.INFINITY, 1);
					this.stacks[1] = iwb;
				}
			}
		}
	}
}
