package com.github.KantraCity.structure_block_unlimiter;

import com.github.KantraCity.structure_block_unlimiter.client.ClientEvents;
import com.github.KantraCity.structure_block_unlimiter.net.Network;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(Structure_block_unlimiter.MODID)
public class Structure_block_unlimiter {
    public static final String MODID = "structure_block_unlimiter";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Structure_block_unlimiter(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(Network::registerServerPayloadHandler);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientEvents::onKeyRegister);
            NeoForge.EVENT_BUS.addListener(ClientEvents::onKeyInput);
        }
    }
}
