package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Shadow
    private boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> nonNullList, int i) {
        throw new AssertionError();
    }

    @Inject(at = @At(value = "HEAD", ordinal = 0), method = "burn", cancellable = true)
    private void iwb$modifyWaterBucketBehavior(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> nonNullList, int i, CallbackInfoReturnable<Boolean> cir) {
        if (recipe != null && canBurn(registryAccess, recipe, nonNullList, i)) {
            ItemStack itemStack = nonNullList.get(0);
            if(nonNullList.get(1).getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0 && nonNullList.get(1).is(Items.BUCKET)) {
                if (itemStack.is(Blocks.WET_SPONGE.asItem()) && !nonNullList.get(1).isEmpty()) {
                    ItemStack iwb = new ItemStack(Items.WATER_BUCKET);
                    iwb.enchant(Enchantments.INFINITY_ARROWS, 1);
                    nonNullList.set(1, iwb);
                }
            }
        }
    }

    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getCraftingRemainingItem()Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack iwb$modifyLavaBucketBehavior(ItemStack item) {
        if (item.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0 && item.is(Items.LAVA_BUCKET) && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket())
            return item;
        return item.getCraftingRemainingItem();
    }
}
