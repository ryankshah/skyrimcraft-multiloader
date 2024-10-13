package com.ryankshah.skyrimcraft.block.util;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SCBlockStateProperties
{
    public static final EnumProperty<TripleBlockPart> TRIPLE_BLOCK_THIRD = EnumProperty.create("third", TripleBlockPart.class);
    public static final EnumProperty<Orientation> ORIENTATION = EnumProperty.create("orientation", Orientation.class);
    public static final IntegerProperty OPENING_PROGRESS = IntegerProperty.create("tall_lead_progress", 0, 2);
    public static final BooleanProperty VISIBLE = BooleanProperty.create("visible");
}
