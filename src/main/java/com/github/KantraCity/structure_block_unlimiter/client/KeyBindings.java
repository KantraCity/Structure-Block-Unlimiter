package com.github.KantraCity.structure_block_unlimiter.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static final KeyMapping OPEN_LAST_STRUCTURE_BLOCK = new KeyMapping(
            "key.structure_block_unlimiter.open_last_structure_block",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "key.categories.structure_block_unlimiter"
    );

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(OPEN_LAST_STRUCTURE_BLOCK);
    }
}
