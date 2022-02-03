package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.blackgear.cavesandcliffs.common.blocks.LayeredCauldronBlock;
import com.blackgear.cavesandcliffs.core.registries.CCBBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

@Mixin(targets = "com.blackgear.cavesandcliffs.common.blocks.AbstractCauldronBlock")
public abstract class AbstractCauldronBlockMixin extends CauldronBlock {
	
	public AbstractCauldronBlockMixin(Properties p_i48431_1_) {
		super(p_i48431_1_);
	}

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "onBlockActivated", cancellable = true, require = 0)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockRayTraceResult blockHitResult, CallbackInfoReturnable<ActionResultType> info) {
		ItemStack stack = player.getHeldItem(hand);

		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && !stack.isEmpty() && !world.isRemote) {
			if (stack.getItem() == Items.BUCKET) {
				
				SoundEvent sound = SoundEvents.ITEM_BUCKET_FILL;
				
				if(blockState.getBlock() == CCBBlocks.LAVA_CAULDRON.get())
					sound = SoundEvents.ITEM_BUCKET_FILL_LAVA;
				
				player.addStat(Stats.USE_CAULDRON);
				world.setBlockState(blockPos, Blocks.CAULDRON.getDefaultState());
				world.playSound(null, (BlockPos)blockPos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(ActionResultType.SUCCESS);
			}
			if (stack.getItem() == Items.WATER_BUCKET) {
				player.addStat(Stats.FILL_CAULDRON);
				world.setBlockState(blockPos, CCBBlocks.WATER_CAULDRON.get().getDefaultState().with(LayeredCauldronBlock.FLUID_LEVEL, Integer.valueOf(3)));
				world.playSound(null, (BlockPos)blockPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(ActionResultType.SUCCESS);
			}
		}
	}
}
