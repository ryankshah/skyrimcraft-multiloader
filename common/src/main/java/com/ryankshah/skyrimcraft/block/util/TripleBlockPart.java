package com.ryankshah.skyrimcraft.block.util;

import net.minecraft.util.StringRepresentable;

public enum TripleBlockPart implements StringRepresentable {
    UPPER,
    MIDDLE,
    LOWER;

    public String toString() { return this.getSerializedName(); }

    public String getSerializedName() {
        return this == UPPER ? "upper" : this == MIDDLE ? "middle" : "lower";
    }

}