package com.ryankshah.skyrimcraft.world.surface_rule;

import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.world.biome.SkyrimcraftBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class AshWastesSurfaceRule
{
    private static final SurfaceRules.RuleSource STONE_5 = makeStateRule(BlockRegistry.STONE_5.get());
    private static final SurfaceRules.RuleSource STONE_4 = makeStateRule(BlockRegistry.STONE_4.get());

    public static SurfaceRules.RuleSource makeRules()
    {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(SkyrimcraftBiomes.ASH_WASTES),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, STONE_5)),
                        SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, STONE_4));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}