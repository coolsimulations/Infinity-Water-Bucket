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

		IDispenseItemBehavior water_bucket = DispenserBlock.DISPENSER_REGISTRY.get(Items.WATER_BUCKET);

		DispenserBlock.registerBehavior(Items.WATER_BUCKET, new DefaultDispenseItemBehavior() {
			public ItemStack execute(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0 && itemStack.getItem() == Items.WATER_BUCKET) {
					BucketItem bucketItem = (BucketItem)itemStack.getItem();
					BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
					World world = blockSource.getLevel().getLevel();
					if (bucketItem.emptyBucket(null, world, blockPos, null)) {
						bucketItem.checkExtraContent(world, itemStack, blockPos);
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

		IDispenseItemBehavior bucket = DispenserBlock.DISPENSER_REGISTRY.get(Items.BUCKET);

		DispenserBlock.registerBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
			public ItemStack execute(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0 && itemStack.getItem() == Items.BUCKET) {
					IWorld iworld = blockSource.getLevel();
					BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
					BlockState blockState = iworld.getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (block instanceof IBucketPickupHandler) {
						Fluid fluid = ((IBucketPickupHandler)block).takeLiquid(iworld, blockPos, blockState);
						if (!(fluid instanceof FlowingFluid)) {
							return super.execute(blockSource, itemStack);
						} else {
							return itemStack;
						}
					} else {
						return super.execute(blockSource, itemStack);
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