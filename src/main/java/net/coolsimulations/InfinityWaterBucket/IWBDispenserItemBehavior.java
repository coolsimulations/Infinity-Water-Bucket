package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.class_2704;
import net.minecraft.block.AbstractFluidBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.Material;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IWBDispenserItemBehavior {
	
	public static void init() {

		DispenserBlock.SPECIAL_ITEMS.put(Items.WATER_BUCKET, new ItemDispenserBehavior() {
			
			private final ItemDispenserBehavior baseDispense = new ItemDispenserBehavior();
			
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack itemStack) {

				BucketItem bucketItem = (BucketItem)itemStack.getItem();
				BlockPos blockPos = pointer.getBlockPos().offset(pointer.method_4271().get(DispenserBlock.FACING));
				World world = pointer.getWorld();
				if (bucketItem.method_11365(null, world, blockPos)) {
					if (EnchantmentHelper.method_11452(class_2704.field_12420, itemStack) > 0 && itemStack.getItem() == Items.WATER_BUCKET) {
						return itemStack;
					} else {
						return new ItemStack(Items.BUCKET);
					}
				} else {
					return baseDispense.dispense(pointer, itemStack);
				}
			}
		});

		DispenserBlock.SPECIAL_ITEMS.put(Items.BUCKET, new ItemDispenserBehavior() {
			
			private final ItemDispenserBehavior baseDispense = new ItemDispenserBehavior();
			
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack itemStack) {
				Item item;
				World iWorld = pointer.getWorld();
				BlockPos blockPos = pointer.getBlockPos().offset(pointer.method_4271().get(DispenserBlock.FACING));
				BlockState blockState = iWorld.getBlockState(blockPos);
				Block block = blockState.getBlock();
				Material material = blockState.method_11708();

				if (block instanceof AbstractFluidBlock && (blockState.get(AbstractFluidBlock.LEVEL)).intValue() == 0) {
					iWorld.setAir(blockPos);
					if (EnchantmentHelper.method_11452(class_2704.field_12420, itemStack) > 0 && itemStack.getItem() == Items.BUCKET) {
						return itemStack;
					}
					if(material == Material.WATER) {
						item = Items.WATER_BUCKET;
					} else if (material == Material.LAVA) {
						item = Items.LAVA_BUCKET;
					} else {
						return super.dispenseSilently(pointer, itemStack);
					}
				} else {
					return super.dispenseSilently(pointer, itemStack);
				}
				itemStack.method_13662(1);
				if (itemStack.method_13654())
					return new ItemStack(item); 
				if (((DispenserBlockEntity)pointer.getBlockEntity()).method_514(new ItemStack(item)) < 0)
					baseDispense.dispense(pointer, new ItemStack(item)); 
				return itemStack;
			}
		});
	}

}