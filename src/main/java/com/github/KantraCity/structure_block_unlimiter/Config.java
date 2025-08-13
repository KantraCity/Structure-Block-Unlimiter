package com.github.KantraCity.structure_block_unlimiter;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;


    public static final ModConfigSpec.IntValue MAX_STRUCTURE_SIZE;
    public static final ModConfigSpec.IntValue RENDER_DISTANCE;

    static {
        BUILDER.push("Structure Block Settings");

        MAX_STRUCTURE_SIZE = BUILDER
                .comment("The maximum size (per axis) that a Structure Block can save or load.",
                        "Vanilla default is 48.")
                .translation("config.osc.max_structure_size")
                .defineInRange("max_structure_size", 512, 1, Integer.MAX_VALUE);

        RENDER_DISTANCE = BUILDER
                .comment("The distance in blocks from which the structure's bounding box is visible.",
                        "Vanilla default is 96.")
                .translation("config.osc.render_distance")
                .defineInRange("render_distance", 256, 16, Integer.MAX_VALUE);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
