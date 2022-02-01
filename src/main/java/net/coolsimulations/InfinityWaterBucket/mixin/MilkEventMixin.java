package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

@Mixin(targets = "com.natamus.milkallthemobs.events.MilkEvent", remap = false)
public class MilkEventMixin {
	
	@Inject(at = @At("HEAD"), method = "onEntityInteract", cancellable = true, require = 0)
	private static void iwb$preventMilking(Player player, Level world, InteractionHand hand, Entity target, EntityHitResult hitResult, CallbackInfoReturnable<InteractionResult> info) {
		ItemStack stack = player.getItemInHand(hand);
		if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0 && stack.is(Items.BUCKET) && !world.isClientSide) {
			info.setReturnValue(InteractionResult.PASS);
		}
	}
}
