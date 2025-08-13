package com.github.KantraCity.structure_block_unlimiter.mixin;

import com.github.KantraCity.structure_block_unlimiter.Config;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(StructureBlockEntity.class)
public abstract class StructureBlockEntityMixin {


    @ModifyArg(
            method = "loadAdditional(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I"),
            index = 1
    )
    private int modifyOffsetMin(int originalMin) {
        if (originalMin == -48) {
            return -Config.MAX_STRUCTURE_SIZE.get();
        }
        return originalMin;
    }

    @ModifyArg(
            method = "loadAdditional(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/core/HolderLookup$Provider;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(III)I"),
            index = 2
    )
    private int modifyOffsetMax(int originalMax) {
        if (originalMax == 48) {
            return Config.MAX_STRUCTURE_SIZE.get();
        }
        return originalMax;
    }
}
