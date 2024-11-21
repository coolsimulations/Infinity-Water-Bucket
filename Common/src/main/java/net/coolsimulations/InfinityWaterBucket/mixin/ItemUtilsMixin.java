package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;

@Mixin(ItemUtils.class)
public class ItemUtilsMixin {

    @Inject(at = @At("HEAD"), method = "createFilledResult", cancellable = true)
    private static  void iwb$createFilledResult(ItemStack stack, Player player, ItemStack stack2, boolean bl, CallbackInfoReturnable<ItemStack> info) {
        if(InfinityWaterBucketCommon.hasInfinity(stack)) {
            if (stack.is(Items.BUCKET) && (InfinityWaterBucketCommon.isMilkBucket(stack2.getItem()) || InfinityWaterBucketCommon.isSolidBucket(stack2.getItem())))
                info.setReturnValue(stack);
            else if (stack.is(Items.WATER_BUCKET))
                info.setReturnValue(stack);
            else if (stack.is(Items.LAVA_BUCKET) && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket())
                info.setReturnValue(stack);
            else if (InfinityWaterBucketCommon.isMilkBucket(stack.getItem()))
                info.setReturnValue(stack);
            else if (InfinityWaterBucketCommon.isSolidBucket(stack.getItem()))
                info.setReturnValue(stack);
            else if (stack.is(Items.BUCKET))
                info.setReturnValue(stack);
        }
    }
}