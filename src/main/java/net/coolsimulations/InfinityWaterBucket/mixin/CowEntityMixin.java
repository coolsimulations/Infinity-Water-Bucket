package net.coolsimulations.InfinityWaterBucket.mixin;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

@Mixin(EntityCow.class)
public abstract class CowEntityMixin extends EntityAnimal {

	public CowEntityMixin(World worldIn) {
		super(worldIn);
	}

	@Inject(at = @At("HEAD"), method = "processInteract", cancellable = true)
	private void iwb$interactMob(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack, CallbackInfoReturnable<Boolean> info) {
		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && stack.getItem() == Items.BUCKET && !this.isChild()) {
			info.setReturnValue(super.processInteract(player, hand, stack));
		}
	}
}