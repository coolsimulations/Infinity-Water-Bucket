package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockCauldron;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(BlockCauldron.class)
public abstract class CauldronBlockMixin {

	@Shadow
	public abstract void setWaterLevel(World world, int x, int y, int z, int level);

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "onBlockActivated", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.inventory.getCurrentItem();

		if(EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 && stack != null && !world.isRemote) {
			if (stack.getItem() == Items.water_bucket) {
				this.setWaterLevel(world, x, y, z, 3);
				info.setReturnValue(true);
			}
		}
	}

}