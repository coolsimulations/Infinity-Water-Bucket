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
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(AbstractFishEntity.class)
public abstract class AbstractFishMixin extends WaterMobEntity {
	
	protected AbstractFishMixin(EntityType<? extends WaterMobEntity> entityType, World level) {
		super(entityType, level);
	}

	@Inject(at = @At("HEAD"), method = "processInteract", cancellable = true)
	public void iwb$mobInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.getHeldItem(hand);
		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.WATER_BUCKET) {
            info.setReturnValue(super.processInteract(player, hand));
        }	
	}
}