package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(CauldronBlock.class)
public abstract class CauldronBlockMixin {

	@Shadow
	public abstract void setLevel(World world, BlockPos pos, BlockState state, int level);

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "onUse", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(World world, BlockPos pos, BlockState state, PlayerEntity player, Direction direction, float f, float g, float h, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.inventory.getMainHandStack();

		if(EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, stack) > 0 && !stack.isEmpty() && !world.isClient) {
			if (stack.getItem() == Items.WATER_BUCKET) {
				player.incrementStat(Stats.CAULDRONS_FILLED);
				this.setLevel(world, pos, state, 3);
				info.setReturnValue(true);
			}
		}
	}

}
