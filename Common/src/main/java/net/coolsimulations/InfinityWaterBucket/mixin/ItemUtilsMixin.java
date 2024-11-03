package net.coolsimulations.InfinityWaterBucket.mixin;

import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

@Mixin(ItemUtils.class)
public class ItemUtilsMixin {

    @Inject(at = @At("HEAD"), method = "createFilledResult", cancellable = true)
    private static  void iwb$createFilledResult(ItemStack stack, Player player, ItemStack stack2, boolean bl, CallbackInfoReturnable<ItemStack> info) {
        if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0) {
            if (stack.is(Items.BUCKET) && (InfinityWaterBucketCommon.isMilkBucket(stack2.getItem()) || InfinityWaterBucketCommon.isSolidBucket(stack2.getItem()))) {
                Map<Enchantment, Integer> infinity = EnchantmentHelper.getEnchantments(stack2);
                infinity.putIfAbsent(Enchantments.INFINITY_ARROWS, 1);
                EnchantmentHelper.setEnchantments(infinity, stack2);
                info.setReturnValue(stack2);
            }
            else if (stack.is(Items.WATER_BUCKET) && stack2.is(Items.BUCKET))
                info.setReturnValue(stack);
            else if (stack.is(Items.LAVA_BUCKET) && stack2.is(Items.BUCKET) && InfinityWaterBucketCommon.CONFIG.getInfiniteLavaBucket())
                info.setReturnValue(stack);
            else if (stack.is(Items.BUCKET))
                info.setReturnValue(stack);
        }
    }
}