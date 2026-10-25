package com.mehmetclient;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MehmetClient implements ModInitializer {
    public static final String MOD_ID = "mehmetclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Mehmet Client 0.2.0 common entrypoint loaded.");
    }
}
