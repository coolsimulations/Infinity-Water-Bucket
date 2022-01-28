package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.Material;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class IWBDispenserItemBehavior {
	
	public static void init() {

		DispenserBlock.field_5000.put(Items.WATER_BUCKET, new ItemDispenserBehavior() {
			
			private final ItemDispenserBehavior baseDispense = new ItemDispenserBehavior();
			
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack itemStack) {

				BucketItem bucketItem = (BucketItem)itemStack.getItem();
				int i = pointer.getBlockX();
			    int j = pointer.getBlockY();
			    int k = pointer.getBlockZ();
				Direction direction = DispenserBlock.getDirection(pointer.getBlockStateData());
				if (bucketItem.method_6320(pointer.getWorld(), i + direction.getOffsetX(), j + direction.getOffsetY(), k + direction.getOffsetZ())) {
					if (EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, itemStack) > 0 && itemStack.getItem() == Items.WATER_BUCKET) {
						return itemStack;
					} else {
						return new ItemStack(Items.BUCKET);
					}
				} else {
					return baseDispense.dispense(pointer, itemStack);
				}
			}
		});

		DispenserBlock.field_5000.put(Items.BUCKET, new ItemDispenserBehavior() {
			
			private final ItemDispenserBehavior baseDispense = new ItemDispenserBehavior();
			
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack itemStack) {
				Item item;
				World iWorld = pointer.getWorld();
				Direction direction = DispenserBlock.getDirection(pointer.getBlockStateData());
				int i = pointer.getBlockX() + direction.getOffsetX();
			    int j = pointer.getBlockY() + direction.getOffsetY();
			    int k = pointer.getBlockZ() + direction.getOffsetZ();
			    Material material = iWorld.method_3774(i, j, k).getMaterial();
			    int m = iWorld.getBlockData(i, j, k);

				if ((Material.WATER.equals(material) || Material.LAVA.equals(material)) && m == 0) {
					iWorld.method_4722(i, j, k);
					if (EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, itemStack) > 0 && itemStack.getItem() == Items.BUCKET) {
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
				--itemStack.count;
				if (itemStack.count == 0)
					return new ItemStack(item); 
				if (((DispenserBlockEntity)pointer.getBlockEntity()).method_514(new ItemStack(item)) < 0)
					baseDispense.dispense(pointer, new ItemStack(item)); 
				return itemStack;
			}
		});
	}

}