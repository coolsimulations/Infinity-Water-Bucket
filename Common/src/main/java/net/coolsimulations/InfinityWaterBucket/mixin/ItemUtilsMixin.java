package net.coolsimulations.InfinityWaterBucket.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.coolsimulations.InfinityWaterBucket.InfinityWaterBucketCommon;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
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
import java.util.Set;

@Mixin(ItemUtils.class)
public class ItemUtilsMixin {

    @Inject(at = @At("HEAD"), method = "createFilledResult", cancellable = true)
    private static  void iwb$createFilledResult(ItemStack stack, Player player, ItemStack stack2, boolean bl, CallbackInfoReturnable<ItemStack> info) {
        if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY, stack) > 0) {
            if (stack.is(Items.BUCKET) && (InfinityWaterBucketCommon.isMilkBucket(stack2.getItem()) || InfinityWaterBucketCommon.isSolidBucket(stack2.getItem()))) {
                stack2.enchant(Enchantments.INFINITY, 1);
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