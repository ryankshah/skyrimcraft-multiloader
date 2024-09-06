package com.ryankshah.skyrimcraft;

import com.mojang.serialization.Codec;
import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.goal.DismayGoal;
import com.ryankshah.skyrimcraft.goal.UndeadFleeGoal;
import com.ryankshah.skyrimcraft.item.SkyrimArmor;
import com.ryankshah.skyrimcraft.item.SkyrimTwoHandedSword;
import com.ryankshah.skyrimcraft.loot.SkyrimcraftLootTables;
import com.ryankshah.skyrimcraft.network.character.AddToTargetingEntities;
import com.ryankshah.skyrimcraft.network.character.OpenCharacterCreationScreen;
import com.ryankshah.skyrimcraft.network.character.UpdateCurrentTarget;
import com.ryankshah.skyrimcraft.network.skill.AddXpToSkill;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.world.CommonSpawning;
import commonnetwork.api.Dispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.impl.attachment.AttachmentRegistryImpl;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.biome.Biomes;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

public class SkyrimcraftFabric implements ModInitializer
{
    protected static List<EntityType<?>> pickPocketableEntities = StreamSupport.stream(SkyrimcraftCommon.getPickpocketableEntities().spliterator(), false).toList();

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

    public static AttachmentType<PlayerQuests> QUEST_DATA =
            AttachmentRegistryImpl.<PlayerQuests>builder()
                    .initializer(PlayerQuests::new)
                    .persistent(PlayerQuests.CODEC)
                    .buildAndRegister(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "quests"));

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

        CommonSpawning.PLAINS_FLYING_MOB_SPAWNS.forEach(spawnerData ->
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW), MobCategory.CREATURE, spawnerData.type, spawnerData.getWeight().asInt(), spawnerData.minCount, spawnerData.maxCount)
        );
        CommonSpawning.DRIPSTONE_MOB_SPAWNS.forEach(spawnerData ->
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES, Biomes.LUSH_CAVES), MobCategory.CREATURE, spawnerData.type, spawnerData.getWeight().asInt(), spawnerData.minCount, spawnerData.maxCount)
        );
        CommonSpawning.SNOW_MOB_SPAWNS.forEach(spawnerData ->
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SNOWY_BEACH, Biomes.SNOWY_PLAINS, Biomes.SNOWY_SLOPES, Biomes.SNOWY_TAIGA), MobCategory.MONSTER, spawnerData.type, spawnerData.getWeight().asInt(), spawnerData.minCount, spawnerData.maxCount)
        );
        CommonSpawning.PLAINS_MOB_SPAWNS.forEach(spawnerData ->
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.PLAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU), MobCategory.MONSTER, spawnerData.type, spawnerData.getWeight().asInt(), spawnerData.minCount, spawnerData.maxCount)
        );
        CommonSpawning.CAVE_MOB_SPAWNS.forEach(spawnerData ->
                BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), MobCategory.MONSTER, spawnerData.type, spawnerData.getWeight().asInt(), spawnerData.minCount, spawnerData.maxCount)
        );
        CommonSpawning.WATER_MOB_SPAWNS.forEach(spawnerData ->
                BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.BEACH, Biomes.RIVER, Biomes.COLD_OCEAN, Biomes.OCEAN), MobCategory.WATER_CREATURE, spawnerData.type, spawnerData.getWeight().asInt(), spawnerData.minCount, spawnerData.maxCount)
        );

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
        ServerEntityEvents.ENTITY_LOAD.register(((entity, world) -> {
            if (pickPocketableEntities.contains(entity.getType())) {
                entity.addTag(EntityRegistry.PICKPOCKET_TAG);
            }

            if (entity instanceof PathfinderMob mob) {
                mob.goalSelector.addGoal(0, new DismayGoal(mob, 16.0F, 0.8D, 1.33D));
            }

            if (entity instanceof Monster monsterEntity) {
                if (monsterEntity.getType().is(EntityTypeTags.UNDEAD)) {
                    monsterEntity.goalSelector.addGoal(0, new UndeadFleeGoal(monsterEntity, 16.0F, 0.8D, 1.33D));
                }
            }
        }));

        ServerLivingEntityEvents.ALLOW_DAMAGE.register(SkyrimcraftFabric::handleLivingEntityDamage);

        CommonSpawning.placements();

        // TODO Below: refer to fieldtofork fabric
        //LootTableEvents.MODIFY.register((lootTableResourceKey, lootBuilder, lootTableSource) -> { });
        SkyrimcraftLootTables.addLootTables();
    }

    public static void initAttachments() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            Character.playerJoinWorld(handler.player);
            ExtraCharacter.playerJoinWorld(handler.player);
            LevelUpdates.playerJoinWorld(handler.player);
            StatIncreases.playerJoinWorld(handler.player);
            PlayerQuests.playerJoinWorld(handler.player);
        });
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            if(entity instanceof Player player) {
                Character.playerDeath(player);
                ExtraCharacter.playerDeath(player);
                LevelUpdates.playerDeath(player);
                StatIncreases.playerDeath(player);
                PlayerQuests.playerDeath(player);
            }
        });
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            Character.playerChangedDimension(player);
            ExtraCharacter.playerChangedDimension(player);
            LevelUpdates.playerChangedDimension(player);
            StatIncreases.playerChangedDimension(player);
            PlayerQuests.playerChangedDimension(player);
        });
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            Character.playerClone(alive, newPlayer, oldPlayer);
            ExtraCharacter.playerClone(alive, newPlayer, oldPlayer);
            LevelUpdates.playerClone(alive, newPlayer, oldPlayer);
            StatIncreases.playerClone(alive, newPlayer, oldPlayer);
            PlayerQuests.playerClone(alive, newPlayer, oldPlayer);
        });
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            Character.playerClone(alive, newPlayer, oldPlayer);
            ExtraCharacter.playerClone(alive, newPlayer, oldPlayer);
            LevelUpdates.playerClone(alive, newPlayer, oldPlayer);
            StatIncreases.playerClone(alive, newPlayer, oldPlayer);
            PlayerQuests.playerClone(alive, newPlayer, oldPlayer);
        });
//        EntityTrackingEvents.START_TRACKING.register((trackedEntity, player) -> {
//            Character.playerStartTracking(player);
//            ExtraCharacter.playerStartTracking(player);
//            LevelUpdates.playerStartTracking(player);
//            StatIncreases.playerStartTracking(player);
//        });

    }

    public static boolean handleLivingEntityDamage(LivingEntity entity, DamageSource source, float amount) {
        if (source.getDirectEntity() != null && source.getDirectEntity() instanceof ServerPlayer && amount > 0) {
            ServerPlayer player = (ServerPlayer) source.getDirectEntity();
            Character character = Character.get(player);
            List<Integer> targetingEntities = character.getTargets();
            if (!targetingEntities.contains(entity.getId()) //&& cap.getTargetingEntities().size() <= 10
                    && entity.isAlive()) {
                targetingEntities.add(entity.getId());
                final AddToTargetingEntities targets = new AddToTargetingEntities(entity.getId());
                character.addTarget(entity.getId());
                Dispatcher.sendToClient(targets, player);
            }
        }

        if (source.getDirectEntity() != null && entity instanceof ServerPlayer player && amount > 0) {
            Character character = Character.get(player);
            final UpdateCurrentTarget currentTarget = new UpdateCurrentTarget(source.getDirectEntity().getId());
            character.addTarget(source.getDirectEntity().getId());
            Dispatcher.sendToClient(currentTarget, player);
        }

        if (source.getEntity() instanceof Player) {
            Player playerEntity = (Player) source.getEntity();
            Character character = Character.get(playerEntity);

            if (entity != null) {
                if (playerEntity.hasEffect(ModEffects.ETHEREAL.asHolder()))
                    playerEntity.removeEffect(ModEffects.ETHEREAL.asHolder());

                if (playerEntity.hasEffect(ModEffects.FLAME_CLOAK.asHolder()) && character.getSpellCooldown(SpellRegistry.ANCESTORS_WRATH.get()) > 0) {
                    entity.setRemainingFireTicks(2 * 20);
                }

                if (playerEntity.isCrouching() && !playerEntity.canBeSeenAsEnemy()) {
                    final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.SNEAK.get()).get(), (int) amount);
                    Dispatcher.sendToServer(xpToSkill);
                }

                if (playerEntity.getMainHandItem().getItem() instanceof ProjectileWeaponItem) {
                    final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.ARCHERY.get()).get(), (int) amount);
                    Dispatcher.sendToServer(xpToSkill);
                } else if (playerEntity.getMainHandItem().getItem() instanceof SwordItem) {
                    if (playerEntity.getMainHandItem().getItem() instanceof SkyrimTwoHandedSword) {
                        final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.TWO_HANDED.get()).get(), (int) amount);
                        Dispatcher.sendToServer(xpToSkill);
                    } else {
                        final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.ONE_HANDED.get()).get(), (int) amount);
                        Dispatcher.sendToServer(xpToSkill);
                    }
                }

                if (entity.isAlive()) {
                    final UpdateCurrentTarget target = new UpdateCurrentTarget(entity.getId());
                    Dispatcher.sendToServer(target);
                } else {
                    final UpdateCurrentTarget target = new UpdateCurrentTarget(-1);
                    Dispatcher.sendToServer(target);
                }
            }
        } else if (entity instanceof Player) {
            Player playerEntity = (Player) entity;
            if (playerEntity.isDamageSourceBlocked(source)) {
                final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.BLOCK.get()).get(), SkillRegistry.BASE_BLOCK_XP);
                Dispatcher.sendToServer(xpToSkill);
            }

            if (playerEntity.getArmorValue() > 0) {
                AtomicInteger heavySlots = new AtomicInteger();
                for (Iterator<ItemStack> it = playerEntity.getArmorSlots().iterator(); it.hasNext(); ) {
                    ItemStack itemStack = it.next();
                    if (itemStack.getItem() instanceof ArmorItem) {
                        ArmorItem item = (ArmorItem) itemStack.getItem();
                        if ((item instanceof SkyrimArmor && ((SkyrimArmor) item).isHeavy()) || item.getMaterial() == ArmorMaterials.NETHERITE)
                            heavySlots.set(heavySlots.get() + 1);
                    }
                }

                if (heavySlots.get() >= 3) {
                    final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.HEAVY_ARMOR.get()).get(), (int) (playerEntity.getArmorValue() * playerEntity.getArmorCoverPercentage()));
                    Dispatcher.sendToServer(xpToSkill);
                } else {
                    final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.LIGHT_ARMOR.get()).get(), (int) (playerEntity.getArmorValue() * playerEntity.getArmorCoverPercentage()));
                    Dispatcher.sendToServer(xpToSkill);
                }
            }
        }
        return true;
    }
}
