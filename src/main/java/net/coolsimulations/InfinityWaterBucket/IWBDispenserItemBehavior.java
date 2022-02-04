package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class IWBDispenserItemBehavior {

	public static void init() {

		IBehaviorDispenseItem water_bucket = BlockDispenser.dispenseBehaviorRegistry.getObject(Items.water_bucket);

		BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, new BehaviorDefaultDispenseItem() {
			public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0 && itemStack.getItem() == Items.water_bucket) {
					ItemBucket bucketItem = (ItemBucket)itemStack.getItem();
					BlockPos blockPos = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
					World world = blockSource.getWorld();
					if (bucketItem.tryPlaceContainedLiquid(world, blockPos)) {
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

		IBehaviorDispenseItem bucket = BlockDispenser.dispenseBehaviorRegistry.getObject(Items.bucket);

		BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem() {
			public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0 && itemStack.getItem() == Items.bucket) {
					World world = blockSource.getWorld();
					BlockPos blockPos = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
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