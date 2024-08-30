package com.ryankshah.skyrimcraft.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClientUtil
{
    public static ClientLevel getClientLevel() {
        return Minecraft.getInstance().level;
    }

    public static AbstractClientPlayer getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public static Set<Vec3> circle(Vec3 origin, Vec3 normal, double radius, double amount){
        normal.normalize();
        //First tangent vector is derived from positive y, giving "up" from the player's view.
        //Subtracting off the normal vector removes the part of the vector that is not in the plane
        //It's unlikely the player will be looking straight up, but if they are this will fail
        ///since it will give the zero vector. You should add some additional logic to detect
        //if the player is "looking up" and use x or z instead.
        Vec3 tangent1 = new Vec3(0, 1, 0).subtract(normal).normalize();
        //Second tangent is simply orthogonal to both normal and tangent vectors.
        //This is obtained by a cross product. There is no need to normalize again
        //since the cross product of orthonormal vectors produces a unit vector
        Vec3 tangent2 = normal.cross(tangent1);

        Set<Vec3> circlePoints = new HashSet<>();
        for(double angle = 0.0D; angle < 2 * Math.PI; angle += amount / 180D * (2 * Math.PI)) {
            //Iterate over angles and use the tangent vectors as your coordinates
            //Think of these vectors as hands on a clock rotating around to produce the circle.
            Vec3 x = tangent1.scale(Math.cos(angle) * radius); //Think of this as x, but projected in the plane
            Vec3 y = tangent2.scale(Math.sin(angle) * radius); //Think of this as y, but projected in the plane
            //Add these contributions to the origin, making a circle around the point defined by origin
            circlePoints.add(origin.add(x).add(y));
        }
        return circlePoints;
    }

    public static Set<Vec3> sphere(int samples){
        Set<Vec3> spherePoints = new HashSet<>();
        float phi = Mth.PI * (Mth.sqrt(5f) - 1f); // golden angle in rads

        for (int i = 0; i < samples; i++)
        {
            float y = 1 - ((float) i / (samples - 1)) * 2;
            float radius = Mth.sqrt(1 - y * y);
            float theta = phi * i;
            float x = Mth.cos(theta) * radius;
            float z = Mth.sin(theta) * radius;
            spherePoints.add(new Vec3(x, y, z));
        }
        return spherePoints;
    }

    public static int[] getRGBAArrayFromHexColor(int color)  {
        int[] ints = new int[4];
        ints[0] = (color >> 24 & 255);
        ints[1] = (color >> 16 & 255);
        ints[2] = (color >>  8 & 255);
        ints[3] = (color       & 255);
        return ints;
    }

    public static boolean canPlayerBeSeen() {
        Player clientPlayer = getClientPlayer();
        double maxSeenBound = 20D;
        List<LivingEntity> entityList = clientPlayer.level().getEntities(clientPlayer, new AABB(clientPlayer.getX() - (double)maxSeenBound, clientPlayer.getY() - (double)maxSeenBound, clientPlayer.getZ() - (double)maxSeenBound, clientPlayer.getX() + (double)maxSeenBound, clientPlayer.getY() + (double)maxSeenBound, clientPlayer.getZ() + (double)maxSeenBound)).stream().filter(entity -> entity instanceof LivingEntity).map(LivingEntity.class::cast).collect(Collectors.toList());

        return entityList.stream().anyMatch(e -> canEntitySee(e, clientPlayer));
    }

    public static boolean canEntitySee(LivingEntity viewer, LivingEntity beingViewed) {
        double dx = beingViewed.getX() - viewer.getX();
        double dz;
        for (dz = beingViewed.getX() - viewer.getZ(); dx * dx + dz * dz < 1.0E-4D; dz = (Math.random() - Math.random()) * 0.01D) {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        while (viewer.getYRot() > 360) {
            viewer.setYRot(viewer.getYRot() - 360);
        }
        while (viewer.getYRot() < -360) {
            viewer.setYRot(viewer.getYRot() + 360);
        }
        float yaw = (float) (Math.atan2(dz, dx) * 180.0D / Math.PI) - viewer.getYRot();
        yaw = yaw - 90;
        while (yaw < -180) {
            yaw += 360;
        }
        while (yaw >= 180) {
            yaw -= 360;
        }

        return yaw < 60 && yaw > -60 && BehaviorUtils.canSee(viewer, beingViewed);
    }

    public static BlockPos blockPos(double pX, double pY, double pZ) {
        return new BlockPos(Mth.floor(pX), Mth.floor(pY), Mth.floor(pZ));
    }
    public static BlockPos blockPos(Vec3 pVec3) {
        return blockPos(pVec3.x, pVec3.y, pVec3.z);
    }

    /**Wraps a source String into a series of lines having a maximum specified length.  The source is
     * wrapped at: spaces, horizontal tabs, system newLine characters, or a specified newLine character
     * sequence.  Existing newLine character sequences in the source string, whether they be the system
     * newLine or the specified newLine, are honored.  Existing whitespace (spaces and horizontal tabs)
     * is preserved.
     * <p>
     * When <tt>wrapLongWords</tt> is true, words having a length greater than the specified
     * <tt>lineLength</tt> will be broken, the specified <tt>longWordBreak</tt> terminator appended,
     * and a new line initiated with the text of the specified <tt>longWordLinePrefix</tt> string.  The
     * position of the break will be unceremoniously chosen such that <tt>ineLength</tt> is honored.
     * One use of <tt>longWordLinePrefix</tt> is to effect "hanging indents"  by specifying a series of
     * spaces for this parameter.  This parameter can contain the lineFeed character(s).  Although
     * <tt>longWordLinePrefix</tt> can contain the horizontal tab character, the results are not
     * guaranteed because no attempt is made to determine the quantity of character positions occupied by a
     * horizontal tab.</p>
     * <p>
     * Example usage:
     * <pre>
     * wrap( "  A very long word is Abracadabra in my book", 11, "\n", true, "-", "  ");</pre>
     * returns (note the effect of the single-character lineFeed):
     * <pre>
     *   A very
     * long word
     * is Abraca-
     *   dabra in
     * my book</pre>
     * Whereas, the following:
     * <pre>
     * wrap( "  A very long word is Abracadabra in my book", 11, null, true, null, "  ");</pre>
     * returns (due to the 2-character system linefeed):
     * <pre>
     *   A very
     * long
     * word is A
     *   bracada
     *   bra in
     * my book</pre></p>
     *
     * @param src  the String to be word wrapped, may be null
     * @param lineLength the maximum line length, including the length of <tt>newLineStr</tt> and, when
     *        applicable, <tt>longWordLinePrefix</tt>.  If the value is insufficient to accommodate
     *        these two parameters + 1 character, it will be increased accordingly.
     * @param newLineStr the string to insert for a new line, or <code>null</code> to use the value
     *        reported as the system line separator by the JVM
     * @param wrapLongWords  when <tt>false</tt>, words longer than <tt>wrapLength</t> will not be broken
     * @param longWordBreak string with which to precede <tt>newLineStr</tt> on each line of a broken word,
     *        excepting the last line, or <tt>null</tt> if this feature is not to be used
     * @param longWordLinePrefix string with which to prefix each line of a broken word, subsequent
     *        to the first line, or <tt>null</tt> if no prefix is to be used
     * @return a line with newlines inserted, or <code>null</code> if <tt>src</tt> is null
     */
    public static String wrap( String src, int lineLength, String newLineStr, boolean wrapLongWords,
                               String longWordBreak, String longWordLinePrefix ) {
        // Trivial case
        if ( src == null ) return null;

        if ( newLineStr == null )
            newLineStr = System.getProperty( "line.separator" );

        if ( longWordBreak == null )
            longWordBreak = "";

        if ( longWordLinePrefix == null )
            longWordLinePrefix = "";

        // Adjust maximum line length to accommodate the newLine string
        lineLength -= newLineStr.length();
        if ( lineLength < 1 )
            lineLength = 1;

        // Guard for long word break or prefix that would create an infinite loop
        if ( wrapLongWords && lineLength - longWordBreak.length() - longWordLinePrefix.length() < 1 )
            lineLength += longWordBreak.length() + longWordLinePrefix.length();

        int
                remaining = lineLength,
                breakLength = longWordBreak.length();

        Matcher m = Pattern.compile( ".+?[ \\t]|.+?(?:" + newLineStr + ")|.+?$" ).matcher( src );

        StringBuilder cache = new StringBuilder();

        while ( m.find() ) {
            String word = m.group();

            // Breakup long word
            while ( wrapLongWords && word.length() > lineLength ) {
                cache
                        .append( word.substring( 0, remaining - breakLength ) )
                        .append( longWordBreak )
                        .append( newLineStr );
                word = longWordLinePrefix + word.substring( remaining - breakLength );
                remaining = lineLength;
            } // if

            // Linefeed if word exceeds remaining space
            if ( word.length() > remaining ) {
                cache
                        .append( newLineStr )
                        .append( word );
                remaining = lineLength;
            } // if

            // Word fits in remaining space
            else
                cache.append( word );

            remaining -= word.length();
        } // while

        return cache.toString();
    } // wrap()
}