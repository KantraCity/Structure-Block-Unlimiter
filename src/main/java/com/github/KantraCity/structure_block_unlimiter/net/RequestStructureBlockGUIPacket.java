package com.github.KantraCity.structure_block_unlimiter.net;

import com.github.KantraCity.structure_block_unlimiter.Structure_block_unlimiter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record RequestStructureBlockGUIPacket(BlockPos pos) implements CustomPacketPayload {
    public static final Type<RequestStructureBlockGUIPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Structure_block_unlimiter.MODID, "req_structure_block_gui"));
    public static final StreamCodec<FriendlyByteBuf, RequestStructureBlockGUIPacket> STREAM_CODEC = StreamCodec.of(
            RequestStructureBlockGUIPacket::write, RequestStructureBlockGUIPacket::read);

    public static void write(FriendlyByteBuf buf, RequestStructureBlockGUIPacket packet) {
        buf.writeBlockPos(packet.pos);
    }

    public static RequestStructureBlockGUIPacket read(FriendlyByteBuf buf) {
        return new RequestStructureBlockGUIPacket(buf.readBlockPos());
    }

    public static void handle(final RequestStructureBlockGUIPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            BlockPos pos = packet.pos();
                BlockEntity be = player.level().getBlockEntity(pos);
                if (be instanceof StructureBlockEntity && player.canUseGameMasterBlocks()) {
                    PacketDistributor.sendToPlayer(player, new OpenStructureBlockGUIPacket(pos));
                }
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
