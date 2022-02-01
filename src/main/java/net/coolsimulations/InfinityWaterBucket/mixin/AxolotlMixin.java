package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

@Mixin(Axolotl.class)
public abstract class AxolotlMixin extends Animal {
	
	protected AxolotlMixin(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(at = @At("HEAD"), method = "mobInteract", cancellable = true)
	public void iwb$mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> info) {
		ItemStack stack = player.getItemInHand(interactionHand);
		if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0 && stack.is(Items.WATER_BUCKET)) {
            info.setReturnValue(super.mobInteract(player, interactionHand));
        }	
	}
}