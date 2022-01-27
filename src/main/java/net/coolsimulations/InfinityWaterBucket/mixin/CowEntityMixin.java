package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.class_2704;
import net.minecraft.class_2961;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {

	protected CowEntityMixin(World world) {
		super(world);
	}

	@Inject(at = @At("HEAD"), method = "method_13079", cancellable = true)
	private void iwb$interactMob(PlayerEntity player, class_2961 hand, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.method_13047(hand);
		if(EnchantmentHelper.method_11452(class_2704.field_12420, stack) > 0 && stack.getItem() == Items.BUCKET && !this.isBaby()) {
			info.setReturnValue(super.method_13079(player, hand));
		}
	}
}
