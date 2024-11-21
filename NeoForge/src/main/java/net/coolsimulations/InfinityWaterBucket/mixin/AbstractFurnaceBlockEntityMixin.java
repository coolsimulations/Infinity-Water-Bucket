package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
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
    private static boolean canBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> nonNullList, int i, AbstractFurnaceBlockEntity furnace) {
        throw new AssertionError();
    }

    @Redirect(method = "burn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z", ordinal = 1))
    private static boolean injected(ItemStack stack, Item item) {
        if (InfinityWaterBucketCommon.hasInfinity(stack))
            return false;
        else
            return stack.is(item);
    }

    @Redirect(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getCraftingRemainingItem()Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack iwb$modifyLavaBucketBehavior(ItemStack item) {
        if (InfinityWaterBucketCommon.hasInfinity(item) && item.is(Items.LAVA_BUCKET) && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket())
            return item;
        return item.getCraftingRemainingItem();
    }
}
