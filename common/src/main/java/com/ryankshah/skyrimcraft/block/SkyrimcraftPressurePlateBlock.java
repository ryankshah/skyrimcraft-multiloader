package com.ryankshah.skyrimcraft.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class SkyrimcraftPressurePlateBlock extends BasePressurePlateBlock
{
    public static final MapCodec<SkyrimcraftPressurePlateBlock> CODEC = RecordCodecBuilder.mapCodec((p_308833_) -> {
        return p_308833_.group(BlockSetType.CODEC.fieldOf("block_set_type").forGetter((p_304917_) -> {
            return p_304917_.type;
        }), propertiesCodec()).apply(p_308833_, SkyrimcraftPressurePlateBlock::new);
    });
    public static final BooleanProperty POWERED;

    public MapCodec<SkyrimcraftPressurePlateBlock> codec() {
        return CODEC;
    }

    public SkyrimcraftPressurePlateBlock(BlockSetType p_273284_, BlockBehaviour.Properties p_273571_) {
        super(p_273571_, p_273284_);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(POWERED, false));
    }

    protected int getSignalForState(BlockState pState) {
        return (Boolean)pState.getValue(POWERED) ? 15 : 0;
    }

    protected BlockState setSignalForState(BlockState pState, int pStrength) {
        return (BlockState)pState.setValue(POWERED, pStrength > 0);
    }

    protected int getSignalStrength(Level pLevel, BlockPos pPos) {
        Class var10000;
        switch (this.type.pressurePlateSensitivity()) {
            case EVERYTHING:
                var10000 = Entity.class;
                break;
            case MOBS:
                var10000 = LivingEntity.class;
                break;
            default:
                throw new MatchException((String)null, (Throwable)null);
        }

        Class<? extends Entity> oclass = var10000;
        return getEntityCount(pLevel, TOUCH_AABB.move(pPos), oclass) > 0 ? 15 : 0;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{POWERED});
    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }
}
