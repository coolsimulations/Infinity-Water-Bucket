package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;

@Mixin(targets = "net/minecraft/class_1373")
public abstract class DispenseItemBehavior16Mixin extends ItemDispenserBehavior {

	@Shadow
	private ItemDispenserBehavior field_5282;

	@Inject(at = @At("HEAD"), method = "dispenseSilently", cancellable = true)
	private void iwb$modifyWaterBucketBehavior(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> info) {
		BucketItem bucketItem = (BucketItem)stack.getItem();
		int i = pointer.getBlockX();
		int j = pointer.getBlockY();
		int k = pointer.getBlockZ();
		Direction direction = Direction.getById(pointer.getBlockStateData());
		if (EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, stack) > 0 && stack.getItem() == Item.WATER_BUCKET) {
			if (bucketItem.method_3312(pointer.getWorld(), i, j, k, i + direction.getOffsetX(), j, k + direction.getOffsetZ())) {
				info.setReturnValue(stack);
			}
		}
	}

}
