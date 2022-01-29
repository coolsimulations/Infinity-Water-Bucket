package net.coolsimulations.InfinityWaterBucket.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(CauldronBlock.class)
public abstract class CauldronBlockMixin extends Block {

	protected CauldronBlockMixin(int id, Material material) {
		super(id, material);
	}

	@Inject(at = @At(value = "HEAD", ordinal = 0), method = "method_421", cancellable = true)
	public void iwb$stopCauldronFromUsingInfinityWaterBucket(World world, int i, int j, int k, PlayerEntity player, int l, float f, float g, float h, CallbackInfoReturnable<Boolean> info) {
		ItemStack stack = player.inventory.getMainHandStack();

		if(EnchantmentHelper.method_3519(Enchantment.INIFINITY.id, stack) > 0 && !stack.isEmpty() && !world.isClient) {
			if (stack.getItem() == Item.WATER_BUCKET) {
				world.method_3672(i, j, k, 3);
				info.setReturnValue(true);
			}
		}
	}

}
