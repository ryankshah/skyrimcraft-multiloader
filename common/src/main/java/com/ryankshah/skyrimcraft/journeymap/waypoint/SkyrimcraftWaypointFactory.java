package com.ryankshah.skyrimcraft.journeymap.waypoint;

import com.ryankshah.skyrimcraft.Constants;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.common.waypoint.Waypoint;
import journeymap.api.v2.common.waypoint.WaypointFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.awt.*;

public class SkyrimcraftWaypointFactory
{
    /**
     * ExampleMod will create a waypoint for the bed slept in at the provided coordinates.
     *
     * @param bedLocation
     * @param dimension
     */
    static Waypoint createBedWaypoint(IClientAPI jmAPI, BlockPos bedLocation, ResourceKey<Level> dimension)
    {
        Waypoint bedWaypoint = null;
        try {
            bedWaypoint = WaypointFactory.createClientWaypoint(Constants.MODID, bedLocation, dimension, true);
            bedWaypoint.setColor(Color.BLUE.getRGB());
            bedWaypoint.setIconResourceLoctaion(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "images/bed_marker.png"));
            bedWaypoint.setIconTextureSize(32, 32);
            bedWaypoint.setIconColor(0x00ffff);
            ;
            // Add or update
            jmAPI.addWaypoint(Constants.MODID, bedWaypoint);

        }
        catch (Throwable t) {
            Constants.LOG.error(t.getMessage(), t);
        }

        return bedWaypoint;
    }
}