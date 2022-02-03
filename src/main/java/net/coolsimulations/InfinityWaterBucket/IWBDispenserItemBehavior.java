package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class IWBDispenserItemBehavior {

	public static void init() {

		IDispenseItemBehavior water_bucket = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(Items.WATER_BUCKET);

		DispenserBlock.registerDispenseBehavior(Items.WATER_BUCKET, new DefaultDispenseItemBehavior() {
			public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemStack) > 0 && itemStack.getItem() == Items.WATER_BUCKET) {
					BucketItem bucketItem = (BucketItem)itemStack.getItem();
					BlockPos blockPos = blockSource.getBlockPos().offset(blockSource.getBlockState().get(DispenserBlock.FACING));
					World world = blockSource.getWorld();
					if (bucketItem.tryPlaceContainedLiquid(null, world, blockPos, null)) {
						bucketItem.onLiquidPlaced(world, itemStack, blockPos);
						return itemStack;
					} else {
						return new DefaultDispenseItemBehavior().dispense(blockSource, itemStack);
					}
				} else {
					if(water_bucket != null)
						return water_bucket.dispense(blockSource, itemStack);
					else
						return new DefaultDispenseItemBehavior().dispense(blockSource, itemStack);
				}
			}
		});

		IDispenseItemBehavior bucket = DispenserBlock.DISPENSE_BEHAVIOR_REGISTRY.get(Items.BUCKET);

		DispenserBlock.registerDispenseBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
			public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemStack) > 0 && itemStack.getItem() == Items.BUCKET) {
					IWorld iworld = blockSource.getWorld();
					BlockPos blockPos = blockSource.getBlockPos().offset(blockSource.getBlockState().get(DispenserBlock.FACING));
					BlockState blockState = iworld.getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (block instanceof IBucketPickupHandler) {
						Fluid fluid = ((IBucketPickupHandler)block).pickupFluid(iworld, blockPos, blockState);
						if (!(fluid instanceof FlowingFluid)) {
							return super.dispenseStack(blockSource, itemStack);
						} else {
							return itemStack;
						}
					} else {
						return super.dispenseStack(blockSource, itemStack);
					}
				} else {
					if(bucket != null)
						return bucket.dispense(blockSource, itemStack);
					else
						return new DefaultDispenseItemBehavior().dispense(blockSource, itemStack);
				}
			}
		});
	}

}