package io.github.alkyaly.infinitywaterbucket;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfinityWaterBucket implements ModInitializer {
    @Override
    public void onInitialize() {
        //Override the bucket fluid behavior, not really compatible with other mods that do the same thing
        DispenserBehavior dispenserBehavior = new DispenserBehavior() {
            private final ItemDispenserBehavior itemDispenserBehavior = new ItemDispenserBehavior();
            @Override
            public ItemStack dispense(BlockPointer pointer, ItemStack stack) {
                FluidModificationItem fluidModItem = (FluidModificationItem) stack.getItem();
                BlockPos pos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                World world = pointer.getWorld();
                if (fluidModItem.placeFluid(null, world, pos, null)) {
                    fluidModItem.onEmptied(null, world, stack, pos);
                    return EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0 ? stack : new ItemStack(Items.BUCKET);
                } else {
                    return itemDispenserBehavior.dispense(pointer, stack);
                }

            }
        };
        DispenserBlock.registerBehavior(Items.WATER_BUCKET, dispenserBehavior);
    }
}
