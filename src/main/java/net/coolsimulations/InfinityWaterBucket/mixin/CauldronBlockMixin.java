package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

@Mixin(BlockCauldron.class)
public abstract class CauldronBlockMixin {

	@Shadow
	public abstract void setWaterLevel(World world, BlockPos blockPos, IBlockState blockState, int i);

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "onBlockActivated", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.inventory.getCurrentItem();

		if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 && stack != null && !world.isRemote) {
			if (stack.getItem() == Items.water_bucket) {
				player.triggerAchievement(StatList.field_181725_I);
				this.setWaterLevel(world, blockPos, blockState, 3);
				info.setReturnValue(true);
			}
		}
	}

}