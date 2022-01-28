package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.CauldronBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

@Mixin(CauldronBlock.class)
public abstract class CauldronBlockMixin {

	@Shadow
	public abstract void method_6465(World world, int i, int j, int k, int l);

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "method_421", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(World world, int i, int j, int k, PlayerEntity player, int l, float f, float g, float h, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.inventory.getMainHandStack();

		if(EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, stack) > 0 && !stack.isEmpty() && !world.isClient) {
			if (stack.getItem() == Items.WATER_BUCKET) {
				this.method_6465(world, i, j, k, 3);
				info.setReturnValue(true);
			}
		}
	}

}
