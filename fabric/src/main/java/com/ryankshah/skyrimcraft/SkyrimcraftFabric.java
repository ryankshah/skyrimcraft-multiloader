package com.ryankshah.skyrimcraft;

import com.mojang.serialization.Codec;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
import com.ryankshah.skyrimcraft.character.attachment.StatIncreases;
import com.ryankshah.skyrimcraft.network.character.OpenCharacterCreationScreen;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.world.CommonSpawning;
import commonnetwork.api.Dispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.impl.attachment.AttachmentRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SkyrimcraftFabric implements ModInitializer
{
    public static AttachmentType<Character> CHARACTER_DATA =
            AttachmentRegistryImpl.<Character>builder()
                    .initializer(Character::new)
                    .persistent(Character.CODEC)
                    .buildAndRegister(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "character"));

    public static AttachmentType<ExtraCharacter> EXTRA_CHARACTER_DATA =
            AttachmentRegistryImpl.<ExtraCharacter>builder()
                    .initializer(ExtraCharacter::new)
                    .persistent(ExtraCharacter.CODEC)
                    .buildAndRegister(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "extra_character"));

    public static AttachmentType<LevelUpdates> LEVEL_UPDATES_DATA =
            AttachmentRegistryImpl.<LevelUpdates>builder()
                    .initializer(LevelUpdates::new)
                    .persistent(LevelUpdates.CODEC)
                    .buildAndRegister(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "level_updates"));

    public static AttachmentType<StatIncreases> STAT_INCREASES_DATA =
            AttachmentRegistryImpl.<StatIncreases>builder()
                    .initializer(StatIncreases::new)
                    .persistent(StatIncreases.CODEC)
                    .buildAndRegister(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "stat_increases"));

    public static AttachmentType<Long> CONJURE_FAMILIAR_SPELL_DATA =
            AttachmentRegistryImpl.<Long>builder()
                    .initializer(() -> 0L)
                    .persistent(Codec.LONG)
                    .buildAndRegister(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "conjure_familiar_spell_data"));

    @Override
    public void onInitialize() {
        SkyrimcraftCommon.init();

        EntityRegistry.registerEntityAttributes(FabricDefaultAttributeRegistry::register);

        initAttachments();

        // Open the character creation screen if first login / world created
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Player player = handler.player;
            if(player instanceof ServerPlayer serverPlayer) {
                Character character = Character.get(serverPlayer);
                if (!character.getHasSetup()) {
                    final OpenCharacterCreationScreen packet = new OpenCharacterCreationScreen(character.getHasSetup());
                    Dispatcher.sendToClient(packet, serverPlayer);
                }
            }
        });

        ServerPlayerEvents.

//        ServerTickEvents.END_SERVER_TICK.register(server -> {
//            ServerPlayer serverPlayer = server.getPlayerList().
//        });

        CommonSpawning.placements();

        // TODO Below: refer to fieldtofork fabric
        //LootTableEvents.MODIFY.register((lootTableResourceKey, lootBuilder, lootTableSource) -> { });
    }

    public static void initAttachments() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Character.playerJoinWorld(handler.player);
            ExtraCharacter.playerJoinWorld(handler.player);
            LevelUpdates.playerJoinWorld(handler.player);
            StatIncreases.playerJoinWorld(handler.player);
        });
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if(entity instanceof Player player) {
                Character.playerDeath(player);
                ExtraCharacter.playerDeath(player);
                LevelUpdates.playerDeath(player);
                StatIncreases.playerDeath(player);
            }
        });
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            Character.playerChangedDimension(player);
            ExtraCharacter.playerChangedDimension(player);
            LevelUpdates.playerChangedDimension(player);
            StatIncreases.playerChangedDimension(player);
        });
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            Character.playerClone(alive, newPlayer, oldPlayer);
            ExtraCharacter.playerClone(alive, newPlayer, oldPlayer);
            LevelUpdates.playerClone(alive, newPlayer, oldPlayer);
            StatIncreases.playerClone(alive, newPlayer, oldPlayer);
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            Character.playerClone(alive, newPlayer, oldPlayer);
            ExtraCharacter.playerClone(alive, newPlayer, oldPlayer);
            LevelUpdates.playerClone(alive, newPlayer, oldPlayer);
            StatIncreases.playerClone(alive, newPlayer, oldPlayer);
        });
        EntityTrackingEvents.START_TRACKING.register((trackedEntity, player) -> {
            Character.playerStartTracking(player);
            ExtraCharacter.playerStartTracking(player);
            LevelUpdates.playerStartTracking(player);
            StatIncreases.playerStartTracking(player);
        });

    }
}
