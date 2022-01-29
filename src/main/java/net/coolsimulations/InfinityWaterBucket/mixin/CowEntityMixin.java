package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {

	protected CowEntityMixin(World world) {
		super(world);
	}

	@Inject(at = @At("HEAD"), method = "canBeLeashedBy", cancellable = true)
	private void iwb$interactMob(PlayerEntity player, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.inventory.getMainHandStack();
		if(EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, stack) > 0 && stack.getItem() == Item.BUCKET) {
			info.setReturnValue(super.canBeLeashedBy(player));
		}
	}
}
