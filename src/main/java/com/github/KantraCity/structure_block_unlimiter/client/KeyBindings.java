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

    public static final KeyMapping INCREASE_GRID_IN_VIEW_DIRECTION = new KeyMapping(
            "key.structure_block_unlimiter.increase_grid",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UP,
            "key.categories.structure_block_unlimiter"
    );

    public static final KeyMapping DECREASE_GRID_IN_VIEW_DIRECTION = new KeyMapping(
            "key.structure_block_unlimiter.decrease_grid",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_DOWN,
            "key.categories.structure_block_unlimiter"
    );

    public static final KeyMapping SHIFT_GRID_FORWARD = new KeyMapping(
            "key.structure_block_unlimiter.shift_grid_forward",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.categories.structure_block_unlimiter"
    );


    public static void register(RegisterKeyMappingsEvent event) {
        event.register(OPEN_LAST_STRUCTURE_BLOCK);
        event.register(INCREASE_GRID_IN_VIEW_DIRECTION);
        event.register(DECREASE_GRID_IN_VIEW_DIRECTION);
        event.register(SHIFT_GRID_FORWARD);
    }
}
