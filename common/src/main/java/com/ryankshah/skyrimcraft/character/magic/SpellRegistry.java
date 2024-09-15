package com.ryankshah.skyrimcraft.character.magic;

import com.example.examplemod.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.magic.power.*;
import com.ryankshah.skyrimcraft.character.magic.shout.*;
import com.ryankshah.skyrimcraft.character.magic.spell.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SpellRegistry
{
    /// -- Ticks in mc day (one day cooldown) = 24000 (1200 seconds)
    public static final int DAY_COOLDOWN = 1200;

    public static final ResourceKey<Registry<Spell>> SPELLS_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells_key"));
    public static final RegistrationProvider<Spell> SPELLS = RegistrationProvider.get(SPELLS_KEY, Constants.MODID);
    public static final Registry<Spell> SPELLS_REGISTRY = SPELLS.registryBuilder().build();

    // Shouts
    public static Supplier<Spell> EMPTY_SPELL = SPELLS.register("empty_spell", EmptySpell::new);
    public static Supplier<Spell> UNRELENTING_FORCE = SPELLS.register("unrelenting_force", () -> new ShoutUnrelentingForce(1));
    public static Supplier<Spell> STORM_CALL = SPELLS.register("storm_call", () -> new ShoutStormCall(2));
    public static Supplier<Spell> WHIRLWIND_SPRINT = SPELLS.register("whirlwind_sprint", () -> new ShoutWhirlwindSprint(3));
    public static Supplier<Spell> ICE_FORM = SPELLS.register("ice_form", () -> new ShoutIceForm(4));
    public static Supplier<Spell> DISARM = SPELLS.register("disarm", () -> new ShoutDisarm(5));
    public static Supplier<Spell> BECOME_ETHEREAL = SPELLS.register("become_ethereal", () -> new ShoutBecomeEthereal(6));
    public static Supplier<Spell> DRAGONREND = SPELLS.register("dragonrend", () -> new ShoutDragonrend(7));
    public static Supplier<Spell> CLEAR_SKIES = SPELLS.register("clear_skies", () -> new ShoutClearSkies(8));
    public static Supplier<Spell> FROST_BREATH = SPELLS.register("frost_breath", () -> new ShoutFrostBreath(9));
    public static Supplier<Spell> DRAGON_ASPECT = SPELLS.register("dragon_aspect", () -> new ShoutDragonAspect(10));
    public static Supplier<Spell> ELEMENTAL_FURY = SPELLS.register("elemental_fury", () -> new ShoutElementalFury(11));
    public static Supplier<Spell> FIRE_BREATH = SPELLS.register("fire_breath", () -> new ShoutFireBreath(12));

    // Spells
    public static Supplier<Spell> FIREBALL = SPELLS.register("fireball", () -> new SpellFireball(20));
    public static Supplier<Spell> TURN_UNDEAD = SPELLS.register("turn_undead", () -> new SpellTurnUndead(21));
    public static Supplier<Spell> CONJURE_FAMILIAR = SPELLS.register("conjure_familiar", () -> new SpellConjureFamiliar(22));
    public static Supplier<Spell> HEALING = SPELLS.register("healing", () -> new SpellHealing(23));
    public static Supplier<Spell> LIGHTNING = SPELLS.register("lightning", () -> new SpellChainLightning(24));
    public static Supplier<Spell> FLAME_CLOAK = SPELLS.register("flame_cloak", () -> new SpellFlameCloak(25));
    public static Supplier<Spell> ICE_SPIKE = SPELLS.register("ice_spike", () -> new SpellIceSpike(26));
    public static Supplier<Spell> CONJURE_ZOMBIE = SPELLS.register("conjure_zombie", () -> new SpellConjureZombie(27));
    public static Supplier<Spell> DETECT_LIFE = SPELLS.register("detect_life", () -> new SpellDetectLife(28));
    public static Supplier<Spell> CANDLELIGHT = SPELLS.register("candlelight", () -> new SpellCandlelight(29));
    public static Supplier<Spell> WATERBREATHING = SPELLS.register("waterbreathing", () -> new SpellWaterbreathing(30));
    public static Supplier<Spell> OAKFLESH = SPELLS.register("oakflesh", () -> new SpellOakflesh(31));
    // public static Supplier<Spell> WATERBREATHING = SPELLS.register("waterbreathing", () -> new SpellWaterbreathing(32));

    // Powers
    public static Supplier<Spell> HIGHBORN = SPELLS.register("highborn", () -> new PowerHighborn(80));
    public static Supplier<Spell> HISTSKIN = SPELLS.register("histskin", () -> new PowerHistskin(81));
    //command animal
    //dragonskin
    public static Supplier<Spell> ANCESTORS_WRATH = SPELLS.register("ancestors_wrath", () -> new PowerAncestorsWrath(84));
    public static Supplier<Spell> VOICE_OF_THE_EMPEROR = SPELLS.register("voice_of_the_emperor", () -> new PowerVoiceOfTheEmperor(85));
    public static Supplier<Spell> BATTLE_CRY = SPELLS.register("battle_cry", () -> new PowerBattleCry(86));
    public static Supplier<Spell> BERSERKER_RAGE = SPELLS.register("berserker_rage", () -> new PowerBerserkerRage(87));
    public static Supplier<Spell> ADRENALINE_RUSH = SPELLS.register("adrenaline_rush", () -> new PowerAdrenalineRush(88));
    //embraceofshadows (vampire)
    //vampire's seduction
    //vampire's servant
    //vampire lord
    //beast form (werewolf)
    //ring of hircine (werewolf)
    public static Supplier<Spell> NIGHT_EYE = SPELLS.register("night_eye", () -> new PowerNightEye(95));
    // TODO: add factions and their faction powers too

    public static List<Supplier<Spell>> getPowersForRace(Race race) {
        List<Supplier<Spell>> spells = new ArrayList<>();

        if(race.getId() == Race.ALTMER.getId())
            spells.add(HIGHBORN);
        else if(race.getId() == Race.ARGONIAN.getId())
            spells.add(HISTSKIN);
        else if(race.getId() == Race.DUNMER.getId())
            spells.add(ANCESTORS_WRATH);
        else if(race.getId() == Race.IMPERIAL.getId())
            spells.add(VOICE_OF_THE_EMPEROR);
        else if(race.getId() == Race.ORSIMER.getId())
            spells.add(BERSERKER_RAGE);
        else if(race.getId() == Race.REDGUARD.getId())
            spells.add(ADRENALINE_RUSH);
        else if(race.getId() == Race.NORD.getId())
            spells.add(BATTLE_CRY);
        else if(race.getId() == Race.KHAJIIT.getId())
            spells.add(NIGHT_EYE);

        return spells;
    }
}