package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IWBDispenserItemBehavior {

	public static void init() {

		IBehaviorDispenseItem water_bucket = BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.getObject(Items.WATER_BUCKET);

		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.WATER_BUCKET, new BehaviorDefaultDispenseItem() {
			public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemStack) > 0 && itemStack.getItem() == Items.WATER_BUCKET) {
					ItemBucket bucketItem = (ItemBucket)itemStack.getItem();
					BlockPos blockPos = blockSource.getBlockPos().offset(blockSource.getBlockState().getValue(BlockDispenser.FACING));
					World world = blockSource.getWorld();
					if (bucketItem.tryPlaceContainedLiquid(null, world, blockPos)) {
						return itemStack;
					} else {
						return new BehaviorDefaultDispenseItem().dispense(blockSource, itemStack);
					}
				} else {
					if(water_bucket != null)
						return water_bucket.dispense(blockSource, itemStack);
					else
						return new BehaviorDefaultDispenseItem().dispense(blockSource, itemStack);
				}
			}
		});

		IBehaviorDispenseItem bucket = BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.getObject(Items.BUCKET);

		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.BUCKET, new BehaviorDefaultDispenseItem() {
			public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemStack) > 0 && itemStack.getItem() == Items.BUCKET) {
					World world = blockSource.getWorld();
					BlockPos blockPos = blockSource.getBlockPos().offset(blockSource.getBlockState().getValue(BlockDispenser.FACING));
					IBlockState blockState = world.getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (block instanceof BlockLiquid && blockState.getValue(BlockLiquid.LEVEL).intValue() == 0) {
						world.setBlockToAir(blockPos);
						return itemStack;
					} else {
						return super.dispenseStack(blockSource, itemStack);
					}
				} else {
					if(bucket != null)
						return bucket.dispense(blockSource, itemStack);
					else
						return new BehaviorDefaultDispenseItem().dispense(blockSource, itemStack);
				}
			}
		});
	}

}