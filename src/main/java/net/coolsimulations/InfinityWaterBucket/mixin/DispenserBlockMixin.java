package net.coolsimulations.InfinityWaterBucket.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.Item;

@Mixin(DispenserBlock.class)
public interface DispenserBlockMixin {

	@Accessor("BEHAVIORS")
	public static Map<Item, DispenserBehavior> getRegistry() {
		throw new AssertionError();
	}
}
