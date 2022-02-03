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

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "onBlockActivated", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockHitResult, CallbackInfoReturnable<ActionResultType> info) {
		ItemStack stack = player.getHeldItem(hand);

		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && !stack.isEmpty() && !world.isRemote) {
			if (stack.getItem() == Items.BUCKET) {
				player.addStat(Stats.USE_CAULDRON);
				this.setWaterLevel(world, blockPos, blockState, 0);
				world.playSound(null, (BlockPos)blockPos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(ActionResultType.SUCCESS);
			}
			if (stack.getItem() == Items.WATER_BUCKET) {
				player.addStat(Stats.FILL_CAULDRON);
				this.setWaterLevel(world, blockPos, blockState, 3);
				world.playSound(null, (BlockPos)blockPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(ActionResultType.SUCCESS);
			}
		}
	}

}