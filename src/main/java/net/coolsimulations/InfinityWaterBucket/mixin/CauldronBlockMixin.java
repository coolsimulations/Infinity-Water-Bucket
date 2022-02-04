package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockCauldron.class)
public abstract class CauldronBlockMixin {

	@Shadow
	public abstract void setWaterLevel(World world, BlockPos blockPos, IBlockState blockState, int i);

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "onBlockActivated", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.getHeldItem(hand);

		if(EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0 && !stack.isEmpty() && !world.isRemote) {
			if (stack.getItem() == Items.BUCKET) {
				player.addStat(StatList.CAULDRON_USED);
				this.setWaterLevel(world, blockPos, blockState, 0);
				world.playSound(null, (BlockPos)blockPos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(true);
			}
			if (stack.getItem() == Items.WATER_BUCKET) {
				player.addStat(StatList.CAULDRON_FILLED);
				this.setWaterLevel(world, blockPos, blockState, 3);
				world.playSound(null, (BlockPos)blockPos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(true);
			}
		}
	}

}