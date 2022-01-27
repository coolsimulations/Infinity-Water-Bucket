package net.coolsimulations.InfinityWaterBucket.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.client.sound.SoundCategory;
import net.minecraft.client.sound.SoundEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(CauldronBlock.class)
public abstract class CauldronBlockMixin {

	@Shadow
	public abstract void setLevel(World world, BlockPos pos, BlockState state, int level);

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "method_421", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, @Nullable ItemStack stack, Direction direction, float f, float g, float h, CallbackInfoReturnable<Boolean> info) {

		if(EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0 && !stack.isEmpty() && !world.isClient) {
			if (stack.getItem() == Items.BUCKET) {
				player.incrementStat(Stats.CAULDRONS_USED);
				this.setLevel(world, pos, state, 0);
				world.method_11486(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(true);
			}
			if (stack.getItem() == Items.WATER_BUCKET) {
				player.incrementStat(Stats.CAULDRONS_FILLED);
				this.setLevel(world, pos, state, 3);
				world.method_11486(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(true);
			}
		}
	}

}
