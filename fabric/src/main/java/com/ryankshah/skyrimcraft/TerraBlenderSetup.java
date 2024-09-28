package com.ryankshah.skyrimcraft;

import terrablender.api.TerraBlenderApi;

public class TerraBlenderSetup implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        SkyrimcraftCommon.setupTerraBlender();
    }
}