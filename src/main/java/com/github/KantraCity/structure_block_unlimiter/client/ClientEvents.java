package com.github.KantraCity.structure_block_unlimiter.client;

import com.github.KantraCity.structure_block_unlimiter.net.RequestStructureBlockGUIPacket;
import net.minecraft.client.Minecraft;
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
            if (mc.screen == null) {
                if (ClientData.lastStructureBlockPos != null) {
                    PacketDistributor.sendToServer(new RequestStructureBlockGUIPacket(ClientData.lastStructureBlockPos));
                }
            }
        }
    }
}
