package com.github.KantraCity.structure_block_unlimiter.net;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenStructureBlockGUIPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<OpenStructureBlockGUIPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("osc", "open_structure_block_gui"));
    public static final StreamCodec<FriendlyByteBuf, OpenStructureBlockGUIPacket> STREAM_CODEC = StreamCodec.of(
            OpenStructureBlockGUIPacket::write, OpenStructureBlockGUIPacket::read);

    public static void write(FriendlyByteBuf buf, OpenStructureBlockGUIPacket packet) {
        buf.writeBlockPos(packet.pos);
    }

    public static OpenStructureBlockGUIPacket read(FriendlyByteBuf buf) {
        return new OpenStructureBlockGUIPacket(buf.readBlockPos());
    }

    public static void handle(final OpenStructureBlockGUIPacket packet, final IPayloadContext context) {
        return;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
