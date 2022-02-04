package net.coolsimulations.InfinityWaterBucket;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class IWBDispenserItemBehavior {

	public static void init() {

		IBehaviorDispenseItem water_bucket = (IBehaviorDispenseItem) BlockDispenser.dispenseBehaviorRegistry.getObject(Items.water_bucket);

		BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, new BehaviorDefaultDispenseItem() {
			public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0 && itemStack.getItem() == Items.water_bucket) {
					ItemBucket bucketItem = (ItemBucket)itemStack.getItem();
					int i = blockSource.getXInt();
	                int j = blockSource.getYInt();
	                int k = blockSource.getZInt();
	                EnumFacing enumfacing = BlockDispenser.getFacingDirection(blockSource.getBlockMetadata());
					World world = blockSource.getWorld();
					if (bucketItem.tryPlaceContainedLiquid(blockSource.getWorld(), i + enumfacing.getFrontOffsetX(), j + enumfacing.getFrontOffsetY(), k + enumfacing.getFrontOffsetZ())) {
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

		IBehaviorDispenseItem bucket = (IBehaviorDispenseItem) BlockDispenser.dispenseBehaviorRegistry.getObject(Items.bucket);

		BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem() {
			public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {

				if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0 && itemStack.getItem() == Items.bucket) {
					World world = blockSource.getWorld();
					EnumFacing enumfacing = BlockDispenser.getFacingDirection(blockSource.getBlockMetadata());
					int i = blockSource.getXInt() + enumfacing.getFrontOffsetX();
	                int j = blockSource.getYInt() + enumfacing.getFrontOffsetY();
	                int k = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
	                Material material = world.getBlock(i, j, k).getMaterial();
					int l = world.getBlockMetadata(i, j, k);

					if ((Material.water.equals(material) || Material.lava.equals(material)) && l == 0) {
						world.setBlockToAir(i, j, k);
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