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

import java.util.Optional;

public record ResizeStructureBlockPacket(BlockPos pos, int amount) implements CustomPacketPayload {
    public static final Type<ResizeStructureBlockPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Structure_block_unlimiter.MODID, "resize_structure_block"));
    public static final StreamCodec<FriendlyByteBuf, ResizeStructureBlockPacket> STREAM_CODEC = StreamCodec.of(ResizeStructureBlockPacket::write, ResizeStructureBlockPacket::read);

    public static void write(FriendlyByteBuf buf, ResizeStructureBlockPacket packet) {
        buf.writeBlockPos(packet.pos);
        buf.writeInt(packet.amount);
    }

    public static ResizeStructureBlockPacket read(FriendlyByteBuf buf) {
        return new ResizeStructureBlockPacket(buf.readBlockPos(), buf.readInt());
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final ResizeStructureBlockPacket msg, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            BlockEntity be = player.level().getBlockEntity(msg.pos());
            if (!(be instanceof StructureBlockEntity sbe)) return;

            Vec3 lookVec = player.getLookAngle();

            Direction hitSide = Direction.getNearest(lookVec.x, lookVec.y, lookVec.z);


            int amount = msg.amount();
            BlockPos currentPos = sbe.getStructurePos();
            Vec3i currentSize = sbe.getStructureSize();

            BlockPos newPos = currentPos;
            Vec3i newSize = currentSize;

            switch (hitSide) {
                case EAST:
                    newSize = new Vec3i(currentSize.getX() + amount, currentSize.getY(), currentSize.getZ());
                    break;
                case UP:
                    newSize = new Vec3i(currentSize.getX(), currentSize.getY() + amount, currentSize.getZ());
                    break;
                case SOUTH:
                    newSize = new Vec3i(currentSize.getX(), currentSize.getY(), currentSize.getZ() + amount);
                    break;

                case WEST:
                    newPos = new BlockPos(currentPos.getX() - amount, currentPos.getY(), currentPos.getZ());
                    newSize = new Vec3i(currentSize.getX() + amount, currentSize.getY(), currentSize.getZ());
                    break;
                case DOWN:
                    newPos = new BlockPos(currentPos.getX(), currentPos.getY() - amount, currentPos.getZ());
                    newSize = new Vec3i(currentSize.getX(), currentSize.getY() + amount, currentSize.getZ());
                    break;
                case NORTH:
                    newPos = new BlockPos(currentPos.getX(), currentPos.getY(), currentPos.getZ() - amount);
                    newSize = new Vec3i(currentSize.getX(), currentSize.getY(), currentSize.getZ() + amount);
                    break;
            }

            if (newSize.getX() < 1 || newSize.getY() < 1 || newSize.getZ() < 1) {
                return;
            }

            sbe.setStructurePos(newPos);
            sbe.setStructureSize(newSize);
            player.connection.send(sbe.getUpdatePacket());
            sbe.setChanged();
        });
    }
}
