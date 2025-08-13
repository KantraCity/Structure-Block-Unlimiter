package com.github.KantraCity.structure_block_unlimiter.mixin;

import com.github.KantraCity.structure_block_unlimiter.Config;
import net.minecraft.client.renderer.blockentity.StructureBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructureBlockRenderer.class)
public class StructureBlockRendererMixin {
    @Inject(
            method = "getViewDistance()I",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onViewDistance(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Config.RENDER_DISTANCE.get());
    }
}
