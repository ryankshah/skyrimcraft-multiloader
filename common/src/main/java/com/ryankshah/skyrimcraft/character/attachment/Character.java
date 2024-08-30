package com.ryankshah.skyrimcraft.character.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.character.skill.Skill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.character.skill.SkillWrapper;
import com.ryankshah.skyrimcraft.network.character.UpdateCharacter;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.util.CodecUtils;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import commonnetwork.api.Dispatcher;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Character
{
    public static Codec<Character> CODEC = RecordCodecBuilder.create(characterInstance -> characterInstance.group(
            Codec.BOOL.fieldOf("hasSetup").forGetter(Character::getHasSetup),
            Codec.FLOAT.fieldOf("magicka").forGetter(Character::getMagicka),
            Codec.FLOAT.fieldOf("maxMagicka").forGetter(Character::getMaxMagicka),
            Codec.FLOAT.fieldOf("magickaRegenModifier").forGetter(Character::getMagickaRegenModifier),
            Codec.INT.fieldOf("characterLevel").forGetter(Character::getCharacterLevel),
            Codec.INT.fieldOf("characterTotalXp").forGetter(Character::getCharacterTotalXp),
            SkillWrapper.CODEC.listOf().fieldOf("skills").forGetter(Character::getSkills),
            Race.RACE_CODEC.fieldOf("race").forGetter(Character::getRace),
            SpellRegistry.SPELLS_REGISTRY.byNameCodec().listOf().fieldOf("knownSpells").forGetter(Character::getKnownSpells),
            SpellRegistry.SPELLS_REGISTRY.byNameCodec().fieldOf("selectedSpell1").forGetter(Character::getSelectedSpell1),
            SpellRegistry.SPELLS_REGISTRY.byNameCodec().fieldOf("selectedSpell2").forGetter(Character::getSelectedSpell2),
            Codec.unboundedMap(SpellRegistry.SPELLS_REGISTRY.byNameCodec(), Codec.FLOAT).fieldOf("spellsOnCooldown").forGetter(Character::getSpellsOnCooldown),
            CompassFeature.CODEC.listOf().fieldOf("compassFeatures").forGetter(Character::getCompassFeatures),
            Codec.INT.listOf().fieldOf("targetingEntities").forGetter(Character::getTargets),
            Codec.INT.fieldOf("currentTarget").forGetter(Character::getCurrentTarget),
            Codec.INT.fieldOf("levelPerkPoints").forGetter(Character::getLevelPerkPoints)
    ).apply(characterInstance, Character::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Character> STREAM_CODEC = CodecUtils.composite16(
            ByteBufCodecs.BOOL,
            Character::getHasSetup,
            ByteBufCodecs.FLOAT,
            Character::getMagicka,
            ByteBufCodecs.FLOAT,
            Character::getMaxMagicka,
            ByteBufCodecs.FLOAT,
            Character::getMagickaRegenModifier,
            ByteBufCodecs.INT,
            Character::getCharacterLevel,
            ByteBufCodecs.INT,
            Character::getCharacterTotalXp,
            SkillWrapper.STREAM_CODEC.apply(ByteBufCodecs.list()),
            Character::getSkills,
            Race.STREAM_CODEC,
            Character::getRace,
            ByteBufCodecs.idMapper(SpellRegistry.SPELLS_REGISTRY).apply(ByteBufCodecs.list()),
            Character::getKnownSpells,
            ByteBufCodecs.idMapper(SpellRegistry.SPELLS_REGISTRY),
            Character::getSelectedSpell1,
            ByteBufCodecs.idMapper(SpellRegistry.SPELLS_REGISTRY),
            Character::getSelectedSpell2,
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.idMapper(SpellRegistry.SPELLS_REGISTRY), ByteBufCodecs.FLOAT),
            Character::getSpellsOnCooldown,
            CompassFeature.STREAM_CODEC.apply(ByteBufCodecs.list()),
            Character::getCompassFeatures,
            ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
            Character::getTargets,
            ByteBufCodecs.INT,
            Character::getCurrentTarget,
            ByteBufCodecs.INT,
            Character::getLevelPerkPoints,
            Character::new
    );

//    public static final StreamCodec<ByteBuf, UpdateCharacter> STREAM_CODEC = StreamCodec.composite(
//            ByteBufCodecs.BOOL,
//            Character::getHasSetup,
//
//            Character::new
//    );

    private boolean hasSetup;
    private float magicka, maxMagicka, magickaRegenModifier;
    private int characterLevel, characterTotalXp;

    private List<SkillWrapper> skills;
    private Race race;

    private List<Spell> knownSpells;
    private Spell selectedSpell1;
    private Spell selectedSpell2;
    private Map<Spell, Float> spellsOnCooldown;

    private List<CompassFeature> compassFeatures;

    private List<Integer> targetingEntities;
    private int currentTarget;
    private int levelPerkPoints;

    public Character() {
        this(
                false,
                50.0f,
                50.0f,
                1.0f,
                1,
                0,
                new ArrayList<>(),
                Race.NORD,
                new ArrayList<>(),
                SpellRegistry.EMPTY_SPELL.get(),
                SpellRegistry.EMPTY_SPELL.get(),
                new HashMap<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                -1,
                0
        );
    }

    public Character(
            boolean hasSetup,
            float magicka, float maxMagicka, float magickaRegenModifier,
            int characterLevel, int characterTotalXp,
            List<SkillWrapper> skills, Race race,
            List<Spell> spells, Spell selectedSpell1, Spell selectedSpell2, Map<Spell, Float> cooldowns,
            List<CompassFeature> features,
            List<Integer> targets, int current,
            int levelPerkPoints
            ) {
        this.hasSetup = hasSetup;
        this.magicka = magicka;
        this.maxMagicka = maxMagicka;
        this.magickaRegenModifier = magickaRegenModifier;
        this.characterLevel = characterLevel;
        this.characterTotalXp = characterTotalXp;
        this.knownSpells = new ArrayList<>(spells);
        this.selectedSpell1 = selectedSpell1;
        this.selectedSpell2 = selectedSpell2;
        this.spellsOnCooldown = new HashMap<>(cooldowns);
        this.skills = new ArrayList<>(skills);
        this.race = race;
        this.compassFeatures = new ArrayList<>(features);
        this.targetingEntities = new ArrayList<>(targets);
        this.currentTarget = current;
        this.levelPerkPoints = levelPerkPoints;
    }

    public void setHasSetup(boolean hasSetup) {
        this.hasSetup = hasSetup;
    }
    public boolean getHasSetup() {
        return this.hasSetup;
    }

    public int getLevelPerkPoints() { return this.levelPerkPoints; }
    public void addLevelPerkPoint() { this.levelPerkPoints += 1; }
    public void removeLevelPerkPoint() { this.levelPerkPoints -= 1; }
    public void addLevelPerkPoints(int amount) { this.levelPerkPoints += amount; }
    public void removeLevelPerkPoints(int amount) { this.levelPerkPoints -= amount; }

    public float getMagicka() {
        return this.magicka;
    }
    public void setMagicka(float magicka) {
        this.magicka = magicka;
    }

    public float getMaxMagicka() {
        return this.maxMagicka;
    }
    public void setMaxMagicka(float maxMagicka) {
        this.maxMagicka = maxMagicka;
    }

    public float getMagickaRegenModifier() {
        return this.magickaRegenModifier;
    }
    public void setMagickaRegenModifier(float modifier) {
        this.magickaRegenModifier = modifier;
    }

    public int getCharacterLevel() {
        return this.characterLevel;
    }
    public void setCharacterLevel(int level) {
        this.characterLevel = level;
    }

    public int getCharacterTotalXp() {
        return this.characterTotalXp;
    }
    public void setCharacterTotalXp(int xp) {
        this.characterTotalXp = xp;
    }

    public List<SkillWrapper> getStartingSkills(Race race) {
        if (race.equals(Race.ALTMER)) return createStartingSkillsFromStartingLevels(15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 25, 20, 20, 20, 20, 20);
        else if (race.equals(Race.ARGONIAN)) return createStartingSkillsFromStartingLevels(15, 15, 15, 15, 15, 15, 20, 20, 25, 20, 15, 15, 15, 15, 15, 20, 20, 15);
        else if (race.equals(Race.BOSMER)) return createStartingSkillsFromStartingLevels(15, 15, 15, 15, 15, 25, 20, 20, 20, 20, 15, 15, 15, 15, 15, 20, 20, 15);
        else if (race.equals(Race.BRETON)) return createStartingSkillsFromStartingLevels(15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 20, 20, 20, 25, 15, 20, 20, 15);
        else if (race.equals(Race.DUNMER)) return createStartingSkillsFromStartingLevels(15, 15, 15, 15, 15, 15, 20, 20, 15, 15, 15, 20, 20, 15, 25, 15, 20, 15);
        else if (race.equals(Race.IMPERIAL)) return createStartingSkillsFromStartingLevels(15, 20, 20, 15, 20, 15, 15, 15, 15, 15, 15, 15, 15, 15, 20, 25, 15, 20);
        else if (race.equals(Race.KHAJIIT)) return createStartingSkillsFromStartingLevels(15, 15, 15, 15, 20, 20, 15, 25, 20, 20, 15, 20, 15, 15, 15, 15, 15, 15);
        else if (race.equals(Race.NORD)) return createStartingSkillsFromStartingLevels(20, 15, 20, 25, 20, 15, 20, 15, 15, 15, 20, 15, 15, 15, 15, 15, 15, 15);
        else if (race.equals(Race.ORSIMER)) return createStartingSkillsFromStartingLevels(20, 25, 20, 20, 20, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 20);
        else if (race.equals(Race.REDGUARD)) return createStartingSkillsFromStartingLevels(20, 15, 20, 15, 25, 20, 15, 15, 15, 15, 15, 15, 15, 15, 20, 15, 20, 15);
        else return createStartingSkillsFromStartingLevels(20, 15, 20, 25, 20, 15, 20, 15, 15, 15, 20, 15, 15, 15, 15, 15, 15, 15);
    }

    private static List<SkillWrapper> createStartingSkillsFromStartingLevels(
            int smithing, int heavyarmor, int block, int twohand, int onehand,
            int archery, int lightarmor, int sneak, int lockpick, int pickpocket,
            int speech, int alchemy, int illusion, int conj, int destruct,
            int restoration, int alteration, int enchanting
    ) {
        List<Skill> skills = new ArrayList<>(SkillRegistry.SKILLS_REGISTRY.stream().toList());
        List<SkillWrapper> skillWrappers = new ArrayList<>(Stream.generate(SkillWrapper::new).limit(skills.size()).collect(Collectors.toList()));
        for(Skill skill : skills) {
            if(skill.getID() == SkillRegistry.SMITHING.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), smithing, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.HEAVY_ARMOR.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), heavyarmor, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.BLOCK.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), block, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.TWO_HANDED.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), twohand, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.ONE_HANDED.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), onehand, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.ARCHERY.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), archery, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.LIGHT_ARMOR.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), lightarmor, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.SNEAK.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), sneak, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.LOCKPICKING.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), lockpick, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.PICKPOCKET.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), pickpocket, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.SPEECH.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), speech, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.ALCHEMY.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), alchemy, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.ILLUSION.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), illusion, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.CONJURATION.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), conj, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.DESTRUCTION.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), destruct, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.RESTORATION.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), restoration, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.ALTERATION.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), alteration, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.ALCHEMY.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), alchemy, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));
            if(skill.getID() == SkillRegistry.ENCHANTING.get().getID())
                skillWrappers.set(skill.getID(), new SkillWrapper(skill, skill.getID(), enchanting, skill.getTotalXp(), skill.getXpProgress(), skill.getSkillUseMultiplier(), skill.getSkillUseOffset(), skill.getSkillImproveMultiplier(), skill.getSkillImproveOffset()));

//            skills.set(skill.getID(), skill);
        }

        return skillWrappers;
    }

    public void setSkills(List<SkillWrapper> skills) {
        this.skills = new ArrayList<>(skills);
    }
    public List<SkillWrapper> getSkills() {
        return skills;
    }
    public void addSkill(int index, SkillWrapper value) {
        this.skills.set(index, value);
    }

    public SkillWrapper getSkill(int id) {
        return this.skills.get(id);
    }

    public void giveExperiencePoints(int id, int xp) {
        SkillWrapper skill = getSkill(id).giveExperiencePoints(xp);
        this.skills.set(id, skill);
    }

    public void setRace(Race race) {
        this.race = race;
    }
    public Race getRace() {
        return race;
    }

    public void addNewSpell(Spell spell) {
        if(!this.knownSpells.contains(spell)) {
            this.knownSpells.add(spell);
        }
    }

    public Spell getSelectedSpell1() { return selectedSpell1; }
    public Spell getSelectedSpell2() { return selectedSpell2; }

    public void setSelectedSpell1(Spell spell) {
        this.selectedSpell1 = spell;
    }

    public void setSelectedSpell2(Spell spell) {
        this.selectedSpell2 = spell;
    }

    public void addSpellAndCooldown(Spell spell, float cooldown) {
        this.spellsOnCooldown.put(spell, cooldown);
    }

    public float getSpellCooldown(Spell shout) {
        return spellsOnCooldown.getOrDefault(shout, 0f);
    }

    public void setKnownSpells(List<Spell> spells) {
        this.knownSpells = spells;
    }

    public void setSpellsOnCooldown(Map<Spell, Float> cooldowns) {
        this.spellsOnCooldown = cooldowns;
    }

    public Map<Spell, Float> getSpellsOnCooldown() {
        return spellsOnCooldown;
    }

    public List<Spell> getKnownSpells() {
        return knownSpells;
    }

    public List<CompassFeature> getCompassFeatures() {
        return compassFeatures;
    }
    public void setCompassFeatures(List<CompassFeature> features) {
        this.compassFeatures = features;
    }
    public void addCompassFeature(CompassFeature feature) {
        this.compassFeatures.add(feature);
    }

    public void addTarget(int id) {
        this.targetingEntities.add(id);
        setCurrentTarget(id);
    }
    public void removeTarget(int id) {
        this.targetingEntities.remove(id);
    }
    public void setTargets(List<Integer> ids) {
        this.targetingEntities = ids;
    }
    public List<Integer> getTargets() {
        return targetingEntities;
    }

    public int getCurrentTarget() { return currentTarget; }

    public void setCurrentTarget(int id) { this.currentTarget = id; }

    public static Character get(Player player) {
        return Services.PLATFORM.getCharacter(player);
    }

    private void syncToSelf(Player owner) {
        syncTo(owner);
    }

    protected void syncTo(Player player) {
        Dispatcher.sendToClient(new UpdateCharacter(this), (ServerPlayer) player);
    }

//    protected void syncTo(PacketDistributor.PacketTarget target) {
//        target.send(new UpdateCharacter(this));
//    }

    public static void entityJoinLevel(Player player) {
        if (player.level().isClientSide)
            return;
        get(player).syncToSelf(player);
    }

    public static void playerJoinWorld(Player player) {
        if (player.level().isClientSide)
            return;
        get(player).syncToSelf(player);
    }

    public static void playerChangedDimension(Player player) {
        if (player.level().isClientSide)
            return;
        get(player).syncToSelf(player);
    }

    public static void playerStartTracking(Player player) {
        if (player.level().isClientSide)
            return;
        get(player).syncToSelf(player);
    }

    public static void playerDeath(Player player) {
        var newHandler = get(player);

        Services.PLATFORM.setCharacterData(player, Services.PLATFORM.getCharacter(player));
        Dispatcher.sendToClient(new UpdateCharacter(newHandler), (ServerPlayer) player); //.sendToPlayer((ServerPlayer) player, new UpdateCharacter(newHandler));
    }

    public static void playerClone(boolean isWasDeath, Player player, Player oldPlayer) {
        if(!isWasDeath)
            return;
//        oldPlayer.revive();
        Character oldHandler = Character.get(oldPlayer);
        Services.PLATFORM.setCharacterData(player, oldHandler);
        Character newHandler = Character.get(player);
        Dispatcher.sendToClient(new UpdateCharacter(newHandler), (ServerPlayer) player);
    }
}