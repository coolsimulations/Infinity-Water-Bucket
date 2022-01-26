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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(CauldronBlock.class)
public abstract class CauldronBlockMixin {

	@Shadow
	public abstract void setLevel(World world, BlockPos pos, BlockState state, int level);

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "onUse", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> info) {
		ItemStack stack = player.getStackInHand(hand);

		if(EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0 && !stack.isEmpty() && !world.isClient) {
			if (stack.getItem() == Items.BUCKET) {
				player.incrementStat(Stats.USE_CAULDRON);
				this.setLevel(world, pos, state, 0);
				world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(ActionResult.SUCCESS);
			}
			if (stack.getItem() == Items.WATER_BUCKET) {
				player.incrementStat(Stats.FILL_CAULDRON);
				this.setLevel(world, pos, state, 3);
				world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				info.setReturnValue(ActionResult.SUCCESS);
			}
		}
	}

}
