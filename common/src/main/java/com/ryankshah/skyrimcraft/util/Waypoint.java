package com.ryankshah.skyrimcraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class Waypoint
{
    private BlockPos blockPos;
    private String name;

    public static Codec<Waypoint> CODEC = RecordCodecBuilder.create(cf -> cf.group(
            Codec.STRING.fieldOf("name").forGetter(Waypoint::getName),
            BlockPos.CODEC.fieldOf("blockPos").forGetter(Waypoint::getBlockPos)
    ).apply(cf, Waypoint::new));

    public static StreamCodec<ByteBuf, Waypoint> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            Waypoint::getName,
            BlockPos.STREAM_CODEC,
            Waypoint::getBlockPos,
            Waypoint::new
    );

    public static final int ICON_WIDTH = 12, ICON_HEIGHT = 16;

    public Waypoint(String name, BlockPos blockPos) {
        this.blockPos = blockPos;
        this.name = name;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public String getName() {
        return name;
    }

    public CompoundTag serialise() {
        CompoundTag nbt = new CompoundTag();

        nbt.putString("name", name);
        nbt.putInt("xPos", blockPos.getX());
        nbt.putInt("yPos", blockPos.getY());
        nbt.putInt("zPos", blockPos.getZ());

        return nbt;
    }

    public static Waypoint deserialise(CompoundTag nbt) {
        String id = nbt.getString("name");
        int x = nbt.getInt("xPos");
        int y = nbt.getInt("yPos");
        int z = nbt.getInt("zPos");
        BlockPos blockPos = new BlockPos(x, y, z);
        return new Waypoint(id, blockPos);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Waypoint))
            return false;

        Waypoint featureToCompare = (Waypoint)obj;
        return this.blockPos.getX() == featureToCompare.getBlockPos().getX() && this.getBlockPos().getZ() == featureToCompare.getBlockPos().getZ();
    }
}