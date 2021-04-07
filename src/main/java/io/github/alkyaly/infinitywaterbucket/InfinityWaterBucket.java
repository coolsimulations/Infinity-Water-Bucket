package io.github.alkyaly.infinitywaterbucket;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfinityWaterBucket implements ModInitializer {
    @Override
    public void onInitialize() {
        DispenserBehavior dispenserBehavior = new DispenserBehavior() {
            private final ItemDispenserBehavior itemDispenserBehavior = new ItemDispenserBehavior();
            @Override
            public ItemStack dispense(BlockPointer pointer, ItemStack stack) {
                BucketItem bucket = (BucketItem) stack.getItem();
                BlockPos pos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                World world = pointer.getWorld();
                if (bucket.placeFluid(null, world, pos, null)) {
                    bucket.onEmptied(world, stack, pos);
                    return EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0 ? stack : new ItemStack(Items.BUCKET);
                } else {
                    return itemDispenserBehavior.dispense(pointer, stack);
                }

            }
        };
        DispenserBlock.registerBehavior(Items.WATER_BUCKET, dispenserBehavior);
    }
}
