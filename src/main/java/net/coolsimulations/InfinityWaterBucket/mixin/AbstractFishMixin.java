package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin extends WaterAnimal {
	
	protected AbstractFishMixin(EntityType<? extends WaterAnimal> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(at = @At("HEAD"), method = "mobInteract", cancellable = true)
	public void iwb$mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> info) {
		ItemStack stack = player.getItemInHand(interactionHand);
		if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0 && stack.getItem() == Items.WATER_BUCKET) {
            info.setReturnValue(super.mobInteract(player, interactionHand));
        }	
	}
}
