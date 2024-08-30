package com.ryankshah.skyrimcraft;

import com.mojang.serialization.Codec;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
import com.ryankshah.skyrimcraft.character.attachment.StatIncreases;
import com.ryankshah.skyrimcraft.data.DataGenerators;
import com.ryankshah.skyrimcraft.data.loot_table.SkyrimLootModifiers;
import com.ryankshah.skyrimcraft.entity.creature.AbeceanLongfin;
import com.ryankshah.skyrimcraft.entity.creature.CyrodilicSpadetail;
import com.ryankshah.skyrimcraft.entity.creature.Draugr;
import com.ryankshah.skyrimcraft.entity.creature.DwarvenSpider;
import com.ryankshah.skyrimcraft.registry.AttributeRegistry;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.world.CommonSpawning;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@Mod(Constants.MODID)
public class SkyrimcraftNeoForge
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Constants.MODID);

    public static final Supplier<AttachmentType<Character>> CHARACTER = ATTACHMENT_TYPES.register(
            "character", () -> AttachmentType.builder(Character::new).serialize(Character.CODEC).copyOnDeath().build());
    public static final Supplier<AttachmentType<ExtraCharacter>> EXTRA_CHARACTER = ATTACHMENT_TYPES.register(
            "extra_character", () -> AttachmentType.builder(() -> new ExtraCharacter()).serialize(ExtraCharacter.CODEC).copyOnDeath().build());
    public static final Supplier<AttachmentType<LevelUpdates>> LEVEL_UPDATES = ATTACHMENT_TYPES.register(
            "level_updates", () -> AttachmentType.builder(() -> new LevelUpdates()).serialize(LevelUpdates.CODEC).copyOnDeath().build());
    public static final Supplier<AttachmentType<StatIncreases>> STAT_INCREASES = ATTACHMENT_TYPES.register(
            "stat_increases", () -> AttachmentType.builder(StatIncreases::new).serialize(StatIncreases.CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<Long>> CONJURE_FAMILIAR_SPELL_DATA = ATTACHMENT_TYPES.register(
            "conjure_familiar_spell_data", () -> AttachmentType.builder(() -> 0L).serialize(Codec.LONG).build());


    public SkyrimcraftNeoForge(IEventBus eventBus) {
        SkyrimcraftCommon.init();

        SkyrimLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
        eventBus.addListener(DataGenerators::gatherData);

        ATTACHMENT_TYPES.register(eventBus);

        eventBus.addListener(this::registerEntityAttributes);
        eventBus.addListener(this::modifyEntityAttributes);
    }

    public void registerEntityAttributes(EntityAttributeCreationEvent event) {
        EntityRegistry.registerEntityAttributes(event::put);
        CommonSpawning.placements();
    }

    // TODO add this in fabric as well...
    public void modifyEntityAttributes(EntityAttributeModificationEvent e) {
        e.getTypes().forEach(entity -> {
            e.add(entity, AttributeRegistry.MAX_MAGICKA.asHolder());
            e.add(entity, AttributeRegistry.MAGICKA_REGEN.asHolder());
            e.add(entity, AttributeRegistry.MAX_STAMINA.asHolder());
            e.add(entity, AttributeRegistry.POISON_RESIST.asHolder());
            e.add(entity, AttributeRegistry.SHOCK_RESIST.asHolder());
            e.add(entity, AttributeRegistry.FIRE_RESIST.asHolder());
            e.add(entity, AttributeRegistry.FROST_RESIST.asHolder());
            e.add(entity, AttributeRegistry.POISON_POWER.asHolder());
            e.add(entity, AttributeRegistry.SHOCK_POWER.asHolder());
            e.add(entity, AttributeRegistry.FIRE_POWER.asHolder());
            e.add(entity, AttributeRegistry.FROST_POWER.asHolder());
        });
    }

    //TODO: add spawn placements
//    public static void addSpawnPlacements(SpawnPlacementRegisterEvent event) {
//        event.register(DWARVEN_SPIDER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DwarvenSpider::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
//        event.register(DRAUGR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Draugr::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
//        event.register(ABECEAN_LONGFIN.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbeceanLongfin::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
//        event.register(CYRODILIC_SPADETAIL.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CyrodilicSpadetail::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
//        event.register(BLUE_BUTTERFLY.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
//        event.register(BLUE_DARTWING.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
//        event.register(MONARCH_BUTTERFLY.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
//        event.register(ORANGE_DARTWING.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
//        event.register(LUNAR_MOTH.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
//    }
}