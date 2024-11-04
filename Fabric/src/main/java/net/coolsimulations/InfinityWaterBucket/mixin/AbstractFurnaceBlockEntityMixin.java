package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Shadow
    private static boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipeHolder, NonNullList<ItemStack> nonNullList, int i) {
        throw new AssertionError();
    }

    @Inject(at = @At(value = "HEAD", ordinal = 0), method = "burn", cancellable = true)
    private static void iwb$modifyWaterBucketBehavior(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> nonNullList, int i, CallbackInfoReturnable<Boolean> cir) {
        if (recipe != null && canBurn(registryAccess, recipe, nonNullList, i)) {
            ItemStack itemStack = nonNullList.get(0);
            if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY, nonNullList.get(1)) > 0 && nonNullList.get(1).is(Items.BUCKET)) {
                if (itemStack.is(Blocks.WET_SPONGE.asItem()) && !nonNullList.get(1).isEmpty()) {
                    ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
                    iwb.enchant(Enchantments.INFINITY, 1);
                    nonNullList.set(1, iwb);
                }
            }
        }
    }

    /**
     * Unfortunately, no infinite lava fuel source on Fabric as there is no way to get the original {@link ItemStack} since the method is static
     */
    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getCraftingRemainingItem()Lnet/minecraft/world/item/Item;"))
    private static Item iwb$modifyLavaBucketBehavior(Item item) {
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY, new ItemStack(item)) > 0 && item == Items.LAVA_BUCKET && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket())
            return item;
        return item.getCraftingRemainingItem();
    }
}
