package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(FishEntity.class)
public abstract class FishEntityMixin extends WaterCreatureEntity {
	
	protected FishEntityMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
	public void iwb$mobInteract(PlayerEntity player, Hand interactionHand, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.getStackInHand(interactionHand);
		if (EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.WATER_BUCKET) {
            info.setReturnValue(super.interactMob(player, interactionHand));
        }	
	}
}
