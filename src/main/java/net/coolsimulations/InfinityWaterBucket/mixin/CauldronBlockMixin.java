package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@Mixin(CauldronBlock.class)
public abstract class CauldronBlockMixin {

	@Shadow
	public abstract void setWaterLevel(Level level, BlockPos blockPos, BlockState blockState, int i);

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "use", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> info) {
		ItemStack stack = player.getItemInHand(interactionHand);

		if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0 && !stack.isEmpty() && !level.isClientSide) {
			if (stack.getItem() == Items.BUCKET) {
				player.awardStat(Stats.USE_CAULDRON);
				this.setWaterLevel(level, blockPos, blockState, 0);
				level.playSound((Player)null, (BlockPos)blockPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
			}
			if (stack.getItem() == Items.WATER_BUCKET) {
				player.awardStat(Stats.FILL_CAULDRON);
				this.setWaterLevel(level, blockPos, blockState, 3);
				level.playSound((Player)null, (BlockPos)blockPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
			}
		}
	}

}
