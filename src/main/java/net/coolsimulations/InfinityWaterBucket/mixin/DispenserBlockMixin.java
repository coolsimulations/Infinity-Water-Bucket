package net.coolsimulations.InfinityWaterBucket.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;

@Mixin(DispenserBlock.class)
public interface DispenserBlockMixin {

	@Accessor("DISPENSER_REGISTRY")
	public static Map<Item, DispenseItemBehavior> getRegistry() {
		throw new AssertionError();
	}
}
