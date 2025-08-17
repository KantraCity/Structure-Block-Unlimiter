package com.github.KantraCity.structure_block_unlimiter.net;

import com.github.KantraCity.structure_block_unlimiter.Structure_block_unlimiter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CustomSetStructureBlockPacket(
        BlockPos pos,
        StructureBlockEntity.UpdateType updateType,
        StructureMode mode,
        String name,
        BlockPos offset,
        Vec3i size,
        Mirror mirror,
        Rotation rotation,
        String data,
        boolean ignoreEntities,
        boolean showAir,
        boolean showBoundingBox,
        float integrity,
        long seed
) implements CustomPacketPayload {

    public static final Type<CustomSetStructureBlockPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Structure_block_unlimiter.MODID, "set_structure_block"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, CustomSetStructureBlockPacket> STREAM_CODEC = StreamCodec.of(
            CustomSetStructureBlockPacket::write, CustomSetStructureBlockPacket::read);

    private static void write(FriendlyByteBuf buf, CustomSetStructureBlockPacket packet) {
        buf.writeBlockPos(packet.pos());
        buf.writeEnum(packet.updateType());
        buf.writeEnum(packet.mode());
        buf.writeUtf(packet.name());
        buf.writeInt(packet.offset().getX());
        buf.writeInt(packet.offset().getY());
        buf.writeInt(packet.offset().getZ());
        buf.writeInt(packet.size().getX());
        buf.writeInt(packet.size().getY());
        buf.writeInt(packet.size().getZ());
        buf.writeEnum(packet.mirror());
        buf.writeEnum(packet.rotation());
        buf.writeUtf(packet.data());
        buf.writeBoolean(packet.ignoreEntities());
        buf.writeBoolean(packet.showAir());
        buf.writeBoolean(packet.showBoundingBox());
        buf.writeFloat(packet.integrity());
        buf.writeVarLong(packet.seed());
    }

    private static CustomSetStructureBlockPacket read(FriendlyByteBuf buf) {
        return new CustomSetStructureBlockPacket(
                buf.readBlockPos(),
                buf.readEnum(StructureBlockEntity.UpdateType.class),
                buf.readEnum(StructureMode.class),
                buf.readUtf(),
                new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()),
                new Vec3i(buf.readInt(), buf.readInt(), buf.readInt()),
                buf.readEnum(Mirror.class),
                buf.readEnum(Rotation.class),
                buf.readUtf(128),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readFloat(),
                buf.readVarLong()
        );
    }

    public static void handle(final CustomSetStructureBlockPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player == null || !player.canUseGameMasterBlocks()) {
                return;
            }

            ServerLevel level = player.serverLevel();
            BlockPos pos = packet.pos();
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof StructureBlockEntity sbe) {
                sbe.setMode(packet.mode());
                sbe.setStructureName(packet.name());
                sbe.setStructurePos(packet.offset());
                sbe.setStructureSize(packet.size());
                sbe.setMirror(packet.mirror());
                sbe.setRotation(packet.rotation());
                sbe.setMetaData(packet.data());
                sbe.setIgnoreEntities(packet.ignoreEntities());
                sbe.setShowAir(packet.showAir());
                sbe.setShowBoundingBox(packet.showBoundingBox());
                sbe.setIntegrity(packet.integrity());
                sbe.setSeed(packet.seed());

                if (sbe.hasStructureName()) {
                    String s = sbe.getStructureName();
                    switch (packet.updateType()) {
                        case SAVE_AREA:
                            if (sbe.saveStructure()) {
                                player.displayClientMessage(Component.translatable("structure_block.save_success", s), false);
                            } else {
                                player.displayClientMessage(Component.translatable("structure_block.save_failure", s), false);
                            }
                            break;
                        case LOAD_AREA:
                            if (!sbe.isStructureLoadable()) {
                                player.displayClientMessage(Component.translatable("structure_block.load_not_found", s), false);
                            } else if (sbe.placeStructureIfSameSize(level)) {
                                player.displayClientMessage(Component.translatable("structure_block.load_success", s), false);
                            } else {
                                player.displayClientMessage(Component.translatable("structure_block.load_prepare", s), false);
                            }
                            break;
                        case SCAN_AREA:
                            if (sbe.detectSize()) {
                                player.displayClientMessage(Component.translatable("structure_block.size_success", s), false);
                            } else {
                                player.displayClientMessage(Component.translatable("structure_block.size_failure"), false);
                            }
                            break;
                        case UPDATE_DATA:
                            break;
                    }
                } else {
                    player.displayClientMessage(Component.translatable("structure_block.invalid_structure_name", packet.name()), false);
                }

                sbe.setChanged();
                level.sendBlockUpdated(pos, sbe.getBlockState(), sbe.getBlockState(), 3);
            }
        });
    }
}
