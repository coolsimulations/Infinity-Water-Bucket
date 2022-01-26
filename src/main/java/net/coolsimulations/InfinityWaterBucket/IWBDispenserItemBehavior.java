package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.class_3695;
import net.minecraft.class_4022;
import net.minecraft.class_4023;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class IWBDispenserItemBehavior {

	public static void init() {

		DispenserBlock.method_16665(Items.WATER_BUCKET, new ItemDispenserBehavior() {
			
			private final ItemDispenserBehavior baseDispense = new ItemDispenserBehavior();
			
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack itemStack) {

				BucketItem bucketItem = (BucketItem)itemStack.getItem();
				BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().method_16934(DispenserBlock.FACING));
				World world = pointer.getWorld();
				if (bucketItem.method_16028(null, world, blockPos, null)) {
					bucketItem.method_16031(world, itemStack, blockPos);
					if (EnchantmentHelper.getLevel(Enchantments.INFINITY, itemStack) > 0 && itemStack.getItem() == Items.WATER_BUCKET) {
						return itemStack;
					} else {
						return new ItemStack(Items.BUCKET);
					}
				} else {
					return baseDispense.dispense(pointer, itemStack);
				}
			}
		});

		DispenserBlock.method_16665(Items.BUCKET, new ItemDispenserBehavior() {
			
			private final ItemDispenserBehavior baseDispense = new ItemDispenserBehavior();
			
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack itemStack) {
				Item item;
				World iWorld = pointer.getWorld();
				BlockPos blockPos = pointer.getBlockPos().offset((Direction)pointer.getBlockState().method_16934(DispenserBlock.FACING));
				BlockState blockState = iWorld.getBlockState(blockPos);
				Block block = blockState.getBlock();

				if (block instanceof class_3695) {
					class_4023 fluid = ((class_3695)block).method_16631(iWorld, blockPos, blockState);
					if (!(fluid instanceof class_4022)) {
						return super.dispenseSilently(pointer, itemStack);
					} else {
						if (EnchantmentHelper.getLevel(Enchantments.INFINITY, itemStack) > 0 && itemStack.getItem() == Items.BUCKET) {
							return itemStack;
						}
						item = fluid.method_17787();
					}
				} else {
					return super.dispenseSilently(pointer, itemStack);
				}
				itemStack.decrement(1);
				if (itemStack.method_13654())
					return new ItemStack(item); 
				if (((DispenserBlockEntity)pointer.getBlockEntity()).method_514(new ItemStack(item)) < 0)
					baseDispense.dispense(pointer, new ItemStack(item)); 
				return itemStack;
			}
		});
	}

}
