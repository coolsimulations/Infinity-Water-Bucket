package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(AbstractFishEntity.class)
public abstract class AbstractFishMixin extends WaterMobEntity {
	
	protected AbstractFishMixin(EntityType<? extends WaterMobEntity> entityType, World level) {
		super(entityType, level);
	}

	@Inject(at = @At("HEAD"), method = "mobInteract", cancellable = true)
	public void iwb$mobInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResultType> info) {
		ItemStack stack = player.getItemInHand(hand);
		if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0 && stack.getItem() == Items.WATER_BUCKET) {
            info.setReturnValue(super.mobInteract(player, hand));
        }	
	}
}