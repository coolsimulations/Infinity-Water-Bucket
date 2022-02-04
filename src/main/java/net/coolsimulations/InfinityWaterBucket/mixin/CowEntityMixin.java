package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(EntityCow.class)
public abstract class CowEntityMixin extends EntityAnimal {

	public CowEntityMixin(World worldIn) {
		super(worldIn);
	}

	@Inject(at = @At("HEAD"), method = "interact", cancellable = true)
	private void iwb$interactMob(EntityPlayer player, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.inventory.getCurrentItem();
		if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 && stack.getItem() == Items.bucket && !this.isChild()) {
			info.setReturnValue(super.interact(player));
		}
	}
}