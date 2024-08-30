package com.ryankshah.skyrimcraft.util;

import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class ParticleColors {
    public static Vector3f DARK_BLUE = Vec3.fromRGB24(2511359).toVector3f();
    public static Vector3f LIGHT_BLUE = Vec3.fromRGB24(6205439).toVector3f();
    public static Vector3f DARK_RED = Vec3.fromRGB24(16721446).toVector3f();
    public static Vector3f LIGHT_RED = Vec3.fromRGB24(16735838).toVector3f();
    public static Vector3f DARK_PURPLE = Vec3.fromRGB24(7283199).toVector3f();
    public static Vector3f LIGHT_PURPLE = Vec3.fromRGB24(10379007).toVector3f();
    public static Vector3f RCT = Vec3.fromRGB24(16776670).toVector3f();
    public static Vector3f SIMPLE_DOMAIN = Vec3.fromRGB24(9756159).toVector3f();
    public static Vector3f FALLING_BLOSSOM_EMOTION = Vec3.fromRGB24(8454141).toVector3f();
    public static Vector3f PURPLE_LIGHTNING = Vec3.fromRGB24(9267447).toVector3f();
    public static Vector3f WHITE_LIGHTNING = Vec3.fromRGB24(16776447).toVector3f();
    public static Vector3f BLACK_FLASH = Vec3.fromRGB24(16188677).toVector3f();
    public static Vector3f BLUE_FLASH = Vec3.fromRGB24(14614526).toVector3f();
    public static Vector3f FIRE_ORANGE = Vec3.fromRGB24(16734720).toVector3f();
    public static Vector3f BLUE_FIRE = Vec3.fromRGB24(7530229).toVector3f();

    public static Vector3f getBright() {
        Vector3f color = BLUE_FLASH;
        Vector3f white = new Vector3f(1.0F, 1.0F, 1.0F);
        return color.lerp(white, 0.5F);
    }
}