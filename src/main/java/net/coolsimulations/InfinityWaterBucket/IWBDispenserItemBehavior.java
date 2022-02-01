package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class IWBDispenserItemBehavior {

	public static void init() {

		DispenseItemBehavior water_bucket = DispenserBlock.DISPENSER_REGISTRY.get(Items.WATER_BUCKET);

		DispenserBlock.registerBehavior(Items.WATER_BUCKET, new DefaultDispenseItemBehavior() {
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0 && itemStack.getItem() == Items.WATER_BUCKET) {
					DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)itemStack.getItem();
					BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
					Level level = blockSource.getLevel();
					if (dispensiblecontaineritem.emptyContents(null, level, blockPos, null)) {
						dispensiblecontaineritem.checkExtraContent(null, level, itemStack, blockPos);
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

		DispenseItemBehavior bucket = DispenserBlock.DISPENSER_REGISTRY.get(Items.BUCKET);

		DispenserBlock.registerBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0 && itemStack.getItem() == Items.BUCKET) {
					LevelAccessor levelAccessor = blockSource.getLevel();
					BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
					Level level = blockSource.getLevel();
					BlockState blockState = levelAccessor.getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (block instanceof BucketPickup) {
						ItemStack itemstack = ((BucketPickup)block).pickupBlock(level, blockPos, blockState);
						if (itemstack.isEmpty()) {
							return super.execute(blockSource, itemStack);
						} else {
							level.gameEvent((Entity)null, GameEvent.FLUID_PICKUP, blockPos);
						}
						return itemStack;
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