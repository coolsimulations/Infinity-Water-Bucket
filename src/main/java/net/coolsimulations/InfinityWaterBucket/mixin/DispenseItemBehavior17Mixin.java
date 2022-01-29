package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Material;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(targets = "net/minecraft/class_1374")
public abstract class DispenseItemBehavior17Mixin extends ItemDispenserBehavior {

	@Shadow
	private ItemDispenserBehavior field_5284;

	@Inject(at = @At("HEAD"), method = "dispenseSilently", cancellable = true)
	private void iwb$modifyWaterBucketBehavior(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> info) {
		World iWorld = pointer.getWorld();
		Direction direction = Direction.getById(pointer.getBlockStateData());
		int i = pointer.getBlockX() + direction.getOffsetX();
		int j = pointer.getBlockY();
		int k = pointer.getBlockZ() + direction.getOffsetZ();
		Material material = iWorld.getMaterial(i, j, k);
		int m = iWorld.getBlockData(i, j, k);
		if (EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, stack) > 0 && stack.getItem() == Item.BUCKET) {
			if ((Material.WATER.equals(material) || Material.LAVA.equals(material)) && m == 0) {
				iWorld.method_3690(i, j, k, 0);
				info.setReturnValue(stack);
			}
		}
	}

}
