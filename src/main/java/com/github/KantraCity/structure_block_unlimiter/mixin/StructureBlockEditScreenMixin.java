package com.github.KantraCity.structure_block_unlimiter.mixin;

import com.github.KantraCity.structure_block_unlimiter.client.ClientData;
import com.github.KantraCity.structure_block_unlimiter.net.CustomSetStructureBlockPacket;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ServerboundSetStructureBlockPacket;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StructureBlockEditScreen.class)
public class StructureBlockEditScreenMixin {

    @Redirect(
            method = "sendToServer(Lnet/minecraft/world/level/block/entity/StructureBlockEntity$UpdateType;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V"
            )
    )
    private void redirectSendPacket(ClientPacketListener connection, Packet<?> packet) {
        if (packet instanceof ServerboundSetStructureBlockPacket vanillaPacket) {
            CustomSetStructureBlockPacket customPacket = new CustomSetStructureBlockPacket(
                    vanillaPacket.getPos(),
                    vanillaPacket.getUpdateType(),
                    vanillaPacket.getMode(),
                    vanillaPacket.getName(),
                    vanillaPacket.getOffset(),
                    vanillaPacket.getSize(),
                    vanillaPacket.getMirror(),
                    vanillaPacket.getRotation(),
                    vanillaPacket.getData(),
                    vanillaPacket.isIgnoreEntities(),
                    vanillaPacket.isShowAir(),
                    vanillaPacket.isShowBoundingBox(),
                    vanillaPacket.getIntegrity(),
                    vanillaPacket.getSeed()
            );

            connection.send(new ServerboundCustomPayloadPacket(customPacket));

        } else {
            connection.send(packet);
        }
    }

    @Shadow
    @Final
    private StructureBlockEntity structure;

    @Inject(method = "<init>(Lnet/minecraft/world/level/block/entity/StructureBlockEntity;)V", at = @At("RETURN"))
    private void onInit(StructureBlockEntity blockEntity, CallbackInfo ci) {
        ClientData.lastStructureBlockPos = this.structure.getBlockPos();
    }
}
