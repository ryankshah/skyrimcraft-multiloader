package com.ryankshah.skyrimcraft.journeymap;

import com.ryankshah.skyrimcraft.Constants;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.client.JourneyMapPlugin;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@JourneyMapPlugin(apiVersion = "2.0.0")
public class SkyrimcraftJourneymapPlugin implements IClientPlugin
{
    // API reference
    private IClientAPI jmAPI = null;
    private static SkyrimcraftJourneymapPlugin INSTANCE;

    public SkyrimcraftJourneymapPlugin() {
        INSTANCE = this;
    }

    public static SkyrimcraftJourneymapPlugin getInstance() {
        return INSTANCE;
    }

    @Override
    public void initialize(IClientAPI jmClientApi) {
        this.jmAPI = jmClientApi;
    }

    @Override
    public String getModId() {
        return Constants.MODID;
    }
}
