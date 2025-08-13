package com.github.KantraCity.structure_block_unlimiter.client;

import com.github.KantraCity.structure_block_unlimiter.net.OpenStructureBlockGUIPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPacketHandler {
    public static void handleOpenStructureBlockGui(final OpenStructureBlockGUIPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            BlockEntity be = Minecraft.getInstance().level.getBlockEntity(packet.pos());
            if (be instanceof StructureBlockEntity sbe) {
                Minecraft.getInstance().setScreen(new StructureBlockEditScreen(sbe));
            }
        });
    }
}
