package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.class_3460;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {

	protected CowEntityMixin(class_3460<? extends AnimalEntity> type, World world) {
		super(type, world);
	}

	@Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
	private void iwb$interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.getStackInHand(hand);
		if(EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.BUCKET && !this.isBaby()) {
			info.setReturnValue(super.interactMob(player, hand));
		}
	}
}
