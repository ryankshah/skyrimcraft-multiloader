package com.ryankshah.skyrimcraft.world.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;
import java.util.Optional;

public class NbtFeature extends Feature<NbtFeatureConfig> {

    public NbtFeature() {
        super(NbtFeatureConfig.CODEC);
    }

    private final BlockIgnoreProcessor ignoreStructureVoid = new BlockIgnoreProcessor(ImmutableList.of(Blocks.STRUCTURE_VOID));
    private final StructurePlaceSettings placementSettings = (new StructurePlaceSettings()).setMirror(Mirror.NONE).addProcessor(ignoreStructureVoid).setIgnoreEntities(false);

    @Override
    public boolean place(FeaturePlaceContext<NbtFeatureConfig> context) {
        // move to top land block below position
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(context.origin());
        for (mutable.move(Direction.UP); context.level().isEmptyBlock(mutable) && mutable.getY() > 2; ) {
            mutable.move(Direction.DOWN);
        }

        //check to make sure spot is valid and not a single block ledge
        if (!context.level().getBlockState(mutable).isAir() &&
                !context.level().isEmptyBlock(mutable.below()) &&
                !context.level().isEmptyBlock(mutable.below(2))) {

            //Creates the well centered on our spot
            mutable.move(Direction.DOWN);
        }
        else{
            return false;
        }

        // Person wants an empty feature for some reason.
        if (context.config().nbtResourcelocationsAndWeights.size() == 0) {
            return false;
        }

        BlockPos.MutableBlockPos blockpos$Mutable = new BlockPos.MutableBlockPos();
        StructureTemplateManager templatemanager = context.level().getLevel().getServer().getStructureManager();
        ResourceLocation nbtRL = getRandomEntry(context.config().nbtResourcelocationsAndWeights, context.random());
        Optional<StructureTemplate> template = templatemanager.get(nbtRL);

        if (template.isEmpty()) {
            Constants.LOG.warn(context.config().nbtResourcelocationsAndWeights.toString() + " NTB does not exist!");
            return false;
        }

        int radius = template.get().getSize().getX() / 2;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if ((x * x) + (z * z) < radius * radius + 1) {
                    blockpos$Mutable.set(context.origin()).move(x, 0, z);

                    // Makes sure it doesn't generate in liquid if stated to not to.
                    if (!context.config().allowInWater && !context.level().getFluidState(blockpos$Mutable).isEmpty()) {
                        return false;
                    }
                    // No spawning on slopes
                    else if(context.level().getBlockState(blockpos$Mutable.move(Direction.UP)).canOcclude() ||
                            !context.level().getBlockState(blockpos$Mutable.move(Direction.DOWN, 3)).canOcclude()) {
                        return false;
                    }

                    //context.getWorld().setBlockState(blockpos$Mutable.up(), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
                }
            }
        }

        BlockPos halfLengths = new BlockPos(template.get().getSize().getX() / 2, 0, template.get().getSize().getZ() / 2);
        placementSettings.setRotation(Rotation.getRandom(context.random())).setRotationPivot(halfLengths).setIgnoreEntities(false);
        if(context.config().processor != null) {
            context.level().registryAccess().registryOrThrow(Registries.PROCESSOR_LIST)
                    .getOptional(context.config().processor).ifPresent(processor -> processor.list().forEach(placementSettings::addProcessor));
        }
        blockpos$Mutable.set(context.origin());
        BlockPos offset = new BlockPos(-template.get().getSize().getX() / 2, context.config().heightOffset, -template.get().getSize().getZ() / 2);
        template.get().placeInWorld(context.level(), blockpos$Mutable.offset(offset), blockpos$Mutable.offset(offset), placementSettings, context.random(), Block.UPDATE_CLIENTS);

        return true;
    }

    public static <T> T getRandomEntry(List<Pair<T, Integer>> rlList, RandomSource random) {
        double totalWeight = 0.0;

        // Compute the total weight of all items together.
        for (Pair<T, Integer> pair : rlList) {
            totalWeight += pair.getSecond();
        }

        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = random.nextFloat() * totalWeight; index < rlList.size() - 1; ++index) {
            randomWeightPicked -= rlList.get(index).getSecond();
            if (randomWeightPicked <= 0.0) break;
        }

        return rlList.get(index).getFirst();
    }
}