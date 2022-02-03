package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

@Mixin(CauldronBlock.class)
public abstract class CauldronBlockMixin {

	@Shadow
	public abstract void setWaterLevel(World world, BlockPos blockPos, BlockState blockState, int i);

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "use", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockHitResult, CallbackInfoReturnable<ActionResultType> info) {
		ItemStack stack = player.getItemInHand(hand);

		if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0 && !stack.isEmpty() && !world.isClientSide) {
			if (stack.getItem() == Items.BUCKET) {
				player.awardStat(Stats.USE_CAULDRON);
				this.setWaterLevel(world, blockPos, blockState, 0);
				world.playSound(null, (BlockPos)blockPos, SoundEvents.BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(ActionResultType.sidedSuccess(world.isClientSide));
			}
			if (stack.getItem() == Items.WATER_BUCKET) {
				player.awardStat(Stats.FILL_CAULDRON);
				this.setWaterLevel(world, blockPos, blockState, 3);
				world.playSound(null, (BlockPos)blockPos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(ActionResultType.sidedSuccess(world.isClientSide));
			}
		}
	}

}