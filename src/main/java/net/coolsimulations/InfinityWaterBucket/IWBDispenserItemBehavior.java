package net.coolsimulations.InfinityWaterBucket;

import net.coolsimulations.InfinityWaterBucket.mixin.DispenserBlockMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.BucketItem;
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
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public class IWBDispenserItemBehavior {

	public static void init() {

		DispenseItemBehavior water_bucket = DispenserBlockMixin.getRegistry().get(Items.WATER_BUCKET);
		
		DispenserBlock.registerBehavior(Items.WATER_BUCKET, new DefaultDispenseItemBehavior() {
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0 && itemStack.getItem() == Items.WATER_BUCKET) {
					BucketItem bucketItem = (BucketItem)itemStack.getItem();
					BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
					Level level = blockSource.getLevel();
					if (bucketItem.emptyBucket(null, level, blockPos, null)) {
						bucketItem.checkExtraContent(level, itemStack, blockPos);
						return itemStack;
					} else {
						return itemStack;
					}
				} else {
					return water_bucket.dispense(blockSource, itemStack);
				}
			}
		});
		
		DispenseItemBehavior bucket = DispenserBlockMixin.getRegistry().get(Items.BUCKET);

		DispenserBlock.registerBehavior(Items.BUCKET, new DefaultDispenseItemBehavior() {
			public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0 && itemStack.getItem() == Items.BUCKET) {
					LevelAccessor levelAccessor = blockSource.getLevel();
					BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
					BlockState blockState = levelAccessor.getBlockState(blockPos);
					Block block = blockState.getBlock();

					if (block instanceof BucketPickup) {
						Fluid fluid = ((BucketPickup)block).takeLiquid(levelAccessor, blockPos, blockState);
						if (!(fluid instanceof FlowingFluid)) {
							return super.execute(blockSource, itemStack);
						} else {
							return itemStack;
						}
					} else {
						return super.execute(blockSource, itemStack);
					}
				} else {
					return bucket.dispense(blockSource, itemStack);
				}
			}
		});
	}

}
