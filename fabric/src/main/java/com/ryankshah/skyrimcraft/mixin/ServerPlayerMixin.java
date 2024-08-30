package com.ryankshah.skyrimcraft.mixin;

import com.mojang.authlib.GameProfile;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.network.character.AddToCompassFeatures;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.AttributeRegistry;
import com.ryankshah.skyrimcraft.registry.TagsRegistry;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import commonnetwork.api.Dispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player
{
    @Shadow public abstract ServerLevel serverLevel();

    @Unique
    private static boolean flag = false;

    private ServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void tick(CallbackInfo callbackInfo) {
        Character character = Services.PLATFORM.getCharacter(this);
        if ((hasEffect(ModEffects.SPECTRAL.asHolder()) || hasEffect(ModEffects.ETHEREAL.asHolder())) && !flag) {
            flag = true;
            setInvisible(true);
            flag = false;
        } else {
            setInvisible(hasEffect(MobEffects.INVISIBILITY));
        }

        if (!hasEffect(ModEffects.MAGICKA_REGEN.asHolder()))
            getAttribute(AttributeRegistry.MAGICKA_REGEN.asHolder()).removeModifiers();

        if (character.getMagicka() < character.getMaxMagicka()) {
            if (tickCount % 20 == 0) {
                // If in combat, regenerate 1% of max magicka, else 3%
                if (character.getCurrentTarget() != -1)
                    character.setMagicka(character.getMagicka() + ((0.01f * character.getMaxMagicka()) * character.getMagickaRegenModifier()));
                else
                    character.setMagicka(character.getMagicka() + ((0.03f * character.getMaxMagicka()) * character.getMagickaRegenModifier()));
            }
        }

        if(serverLevel().isLoaded(blockPosition())) { //&& event.side == LogicalSide.SERVER) {
            ServerLevel level = serverLevel();

//                level.registryAccess().registry(QuestRegistry.QUESTS_REGISTRY_KEY).ifPresent(
//                        registry -> System.out.println(registry.stream().toList())
//                );

            // TODO: see below...
            //                if(!PositionTrigger.Instance.located(LocationPredicate.inFeature(Structure.VILLAGE)).matches(world, player.getX(), player.getY(), player.getZ())) {
            //                    return;
            //                }

            // TODO: check if structures can even generate.
            List<ResourceKey<Structure>> structuresList = List.of(BuiltinStructures.VILLAGE_DESERT, BuiltinStructures.VILLAGE_TAIGA,
                    BuiltinStructures.VILLAGE_PLAINS, BuiltinStructures.VILLAGE_SNOWY, BuiltinStructures.VILLAGE_SAVANNA, BuiltinStructures.SHIPWRECK, BuiltinStructures.SHIPWRECK_BEACHED,
                    BuiltinStructures.FORTRESS, BuiltinStructures.MINESHAFT, BuiltinStructures.MINESHAFT_MESA);

            List<TagKey<Structure>> structureTags = List.of(StructureTags.VILLAGE, StructureTags.MINESHAFT, TagsRegistry.StructureTagsInit.NETHER_FORTRESS, StructureTags.SHIPWRECK);

            // TODO: We should do this check after we do the player bounds check...
            for (TagKey<Structure> structure : structureTags) {
                BlockPos featureStartPos = locateFeatureStartChunkFromPlayerBlockPos(level, blockPosition(), structure);
                if (featureStartPos != null && structure != null) {
                    System.out.println("Found Structure: " + structure.toString());
                    List<CompassFeature> playerCompassFeatures = character.getCompassFeatures();
                    CompassFeature compassFeature = new CompassFeature(UUID.randomUUID().toString(), structure, featureStartPos);
                    if (playerCompassFeatures.isEmpty() || playerCompassFeatures.stream().noneMatch(feature -> feature.equals(compassFeature))) {
//                                System.out.println(playerCompassFeatures);
//                            character.addCompassFeature(compassFeature);
//                            playerCompassFeatures.add(compassFeature);
                        final AddToCompassFeatures features = new AddToCompassFeatures(compassFeature.getId(), compassFeature.getFeature().location(), compassFeature.getBlockPos());
//                            character.setCompassFeatures(playerCompassFeatures);
                        Dispatcher.sendToServer(features);
//                            PacketDistributor.SERVER.noArg().send(features);

//                                System.out.println(playerCompassFeatures);
                    }
                }
            }
        }

        // check ethereal
        if (!hasEffect(ModEffects.ETHEREAL.asHolder())) {
            if (isInvulnerable() && (!isCreative() || !isSpectator()))
                setInvulnerable(false);
        }
    }

    @Unique
    private static BlockPos locateFeatureStartChunkFromPlayerBlockPos(ServerLevel world, BlockPos pos, TagKey<Structure> feature) {
        // use 2 since based on min spacing, or we can use 7 in case user makes village.json spacing at every chunk
        BlockPos blockpos1 = world.findNearestMapStructure(feature, pos, 2, true);
        if (blockpos1 != null) {
            return blockpos1;
        } else {
            return null;
        }
    }
}
