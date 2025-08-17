package com.github.KantraCity.structure_block_unlimiter.net;

import com.github.KantraCity.structure_block_unlimiter.Structure_block_unlimiter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ShiftStructureBlockPacket (BlockPos pos, int amount) implements CustomPacketPayload {
    public static final Type<ShiftStructureBlockPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Structure_block_unlimiter.MODID, "shift_structure_block"));
    public static final StreamCodec<FriendlyByteBuf, ShiftStructureBlockPacket> STREAM_CODEC = StreamCodec.of(ShiftStructureBlockPacket::write, ShiftStructureBlockPacket::read);

    public static void write(FriendlyByteBuf buf, ShiftStructureBlockPacket packet) {
        buf.writeBlockPos(packet.pos);
        buf.writeInt(packet.amount);
    }

    public static ShiftStructureBlockPacket read(FriendlyByteBuf buf) {
        return new ShiftStructureBlockPacket(buf.readBlockPos(), buf.readInt());
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ShiftStructureBlockPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            BlockEntity be = player.level().getBlockEntity(msg.pos);
            if (!(be instanceof StructureBlockEntity sbe)) return;

            Vec3 lookVec = player.getLookAngle();
            Direction direction = Direction.getNearest(lookVec.x, lookVec.y, lookVec.z);
            int amount = msg.amount;

            BlockPos currentPos = sbe.getStructurePos();
            Vec3i currentSize = sbe.getStructureSize();

            BlockPos newPos = currentPos.relative(direction, amount);

            sbe.setStructurePos(newPos);
            player.connection.send(sbe.getUpdatePacket());
            sbe.setChanged();
        });
    }
}
