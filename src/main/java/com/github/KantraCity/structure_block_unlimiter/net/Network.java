package com.github.KantraCity.structure_block_unlimiter.net;

import com.github.KantraCity.structure_block_unlimiter.Structure_block_unlimiter;
import com.github.KantraCity.structure_block_unlimiter.client.ClientPacketHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class Network {
    public static void registerServerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Structure_block_unlimiter.MODID).versioned("1");

        registrar.playToServer(
                CustomSetStructureBlockPacket.TYPE,
                CustomSetStructureBlockPacket.STREAM_CODEC,
                CustomSetStructureBlockPacket::handle
        );

        registrar.playToServer(
                RequestStructureBlockGUIPacket.TYPE,
                RequestStructureBlockGUIPacket.STREAM_CODEC,
                RequestStructureBlockGUIPacket::handle
        );

        registrar.playToServer(
                ResizeStructureBlockPacket.TYPE,
                ResizeStructureBlockPacket.STREAM_CODEC,
                ResizeStructureBlockPacket::handle
        );

        registrar.playToServer(
                ShiftStructureBlockPacket.TYPE,
                ShiftStructureBlockPacket.STREAM_CODEC,
                ShiftStructureBlockPacket::handle
        );

        if (FMLEnvironment.dist == Dist.CLIENT) {
            registrar.playToClient(
                    OpenStructureBlockGUIPacket.TYPE,
                    OpenStructureBlockGUIPacket.STREAM_CODEC,
                    ClientPacketHandler::handleOpenStructureBlockGui
            );
        } else {
            registrar.playToClient(
                    OpenStructureBlockGUIPacket.TYPE,
                    OpenStructureBlockGUIPacket.STREAM_CODEC,
                    null
            );
        }
    }
}
