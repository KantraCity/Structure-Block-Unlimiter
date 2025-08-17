package com.github.KantraCity.structure_block_unlimiter.client;

import com.github.KantraCity.structure_block_unlimiter.Structure_block_unlimiter;
import com.github.KantraCity.structure_block_unlimiter.net.RequestStructureBlockGUIPacket;
import com.github.KantraCity.structure_block_unlimiter.net.ResizeStructureBlockPacket;
import com.github.KantraCity.structure_block_unlimiter.net.ShiftStructureBlockPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class ClientEvents {
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        KeyBindings.register(event);
    }

    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        Player player = mc.player;
        if (player == null) return;
        if(KeyBindings.OPEN_LAST_STRUCTURE_BLOCK.isDown()) {
            if (mc.screen == null && ClientData.lastStructureBlockPos != null) {
                    PacketDistributor.sendToServer(new RequestStructureBlockGUIPacket(ClientData.lastStructureBlockPos));
            }
        }
        else if(KeyBindings.INCREASE_GRID_IN_VIEW_DIRECTION.consumeClick()) {
            if (mc.screen == null && ClientData.lastStructureBlockPos != null) {
                if (mc.player.isShiftKeyDown()) {
                    PacketDistributor.sendToServer(new ResizeStructureBlockPacket(ClientData.lastStructureBlockPos, 5));
                } else {
                    PacketDistributor.sendToServer(new ResizeStructureBlockPacket(ClientData.lastStructureBlockPos, 1));
                }
            }
        } else if (KeyBindings.DECREASE_GRID_IN_VIEW_DIRECTION.consumeClick()) {
            if (mc.screen == null && ClientData.lastStructureBlockPos != null) {
                if (mc.player.isShiftKeyDown()) {
                    PacketDistributor.sendToServer(new ResizeStructureBlockPacket(ClientData.lastStructureBlockPos, -5));
                } else {
                    PacketDistributor.sendToServer(new ResizeStructureBlockPacket(ClientData.lastStructureBlockPos, -1));
                }
            }
        } else if (KeyBindings.SHIFT_GRID_FORWARD.consumeClick()) {
            if (mc.screen == null && ClientData.lastStructureBlockPos != null) {
                if (mc.player.isShiftKeyDown()) {
                    PacketDistributor.sendToServer(new ShiftStructureBlockPacket(ClientData.lastStructureBlockPos, 5));
                } else {
                    PacketDistributor.sendToServer(new ShiftStructureBlockPacket(ClientData.lastStructureBlockPos, 1));
                }
            }
        }
    }
}
