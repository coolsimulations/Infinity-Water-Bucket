package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.class_2704;
import net.minecraft.class_3114;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(FurnaceBlockEntity.class)
public abstract class FurnaceBlockEntityMixin {
	
	@Shadow
	protected class_3114<ItemStack> field_15154;

	@Shadow
	public boolean method_525() {
		throw new AssertionError();
	}

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "method_524")
	private void iwb$modifyWaterBucketBehavior(CallbackInfo info) {
		if (method_525()) {
			ItemStack itemStack = field_15154.get(0);
			if(EnchantmentHelper.method_11452(class_2704.field_12420, field_15154.get(1)) > 0 && ((ItemStack) field_15154.get(1)).getItem() == Items.BUCKET) {
				if (itemStack.getItem() == Item.fromBlock(Blocks.SPONGE) && itemStack.getMeta() == 1 && !((ItemStack) field_15154.get(1)).isEmpty()) {
					ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
					iwb.addEnchantment(class_2704.field_12420, 1);
					field_15154.set(1, iwb);
				}
			}
		}
	}
}
