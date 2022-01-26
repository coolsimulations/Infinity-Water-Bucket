package net.coolsimulations.InfinityWaterBucket;

import net.coolsimulations.InfinityWaterBucket.mixin.DispenserBlockMixin;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class IWBDispenserItemBehavior {

	public static void init() {

		DispenserBehavior water_bucket = DispenserBlockMixin.getRegistry().get(Items.WATER_BUCKET);
		
		DispenserBlock.registerBehavior(Items.WATER_BUCKET, new ItemDispenserBehavior() {
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack itemStack) {

				if (EnchantmentHelper.getLevel(Enchantments.INFINITY, itemStack) > 0 && itemStack.getItem() == Items.WATER_BUCKET) {
					BucketItem bucketItem = (BucketItem)itemStack.getItem();
					BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
					World world = pointer.getWorld();
					if (bucketItem.placeFluid(null, world, blockPos, null)) {
						bucketItem.onEmptied(world, itemStack, blockPos);
						return itemStack;
					} else {
						return itemStack;
					}
				} else {
					return water_bucket.dispense(pointer, itemStack);
				}
			}
		});
		
		DispenserBehavior bucket = DispenserBlockMixin.getRegistry().get(Items.BUCKET);

		DispenserBlock.registerBehavior(Items.BUCKET, new ItemDispenserBehavior() {
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack itemStack) {

				if (EnchantmentHelper.getLevel(Enchantments.INFINITY, itemStack) > 0 && itemStack.getItem() == Items.BUCKET) {
					IWorld iWorld = pointer.getWorld();
					BlockPos blockPos = pointer.getBlockPos().offset((Direction)pointer.getBlockState().get(DispenserBlock.FACING));
					BlockState blockState = iWorld.getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (block instanceof FluidDrainable) {
						Fluid fluid = ((FluidDrainable)block).tryDrainFluid(iWorld, blockPos, blockState);
						if (!(fluid instanceof BaseFluid)) {
							return super.dispenseSilently(pointer, itemStack);
						} else {
							return itemStack;
						}
					} else {
						return super.dispenseSilently(pointer, itemStack);
					}
				} else {
					return bucket.dispense(pointer, itemStack);
				}
			}
		});
	}

}
