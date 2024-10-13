package com.ryankshah.skyrimcraft.character.skill;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public class Perk
{
    protected String name;
    protected String description;
    protected int levelRequirement;
    protected List<String> parents;
    protected boolean unlocked;

    public static Codec<Perk> CODEC = RecordCodecBuilder.create(perk -> perk.group(
            Codec.STRING.fieldOf("name").forGetter(Perk::getName),
            Codec.STRING.fieldOf("description").forGetter(Perk::getDescription),
            Codec.INT.fieldOf("levelRequirement").forGetter(Perk::getLevelRequirement),
            Codec.STRING.listOf().fieldOf("parents").forGetter(Perk::getParents),
            Codec.BOOL.fieldOf("unlocked").forGetter(Perk::isUnlocked)
    ).apply(perk, Perk::new));

    public static StreamCodec<RegistryFriendlyByteBuf, Perk> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            Perk::getName,
            ByteBufCodecs.STRING_UTF8,
            Perk::getDescription,
            ByteBufCodecs.INT,
            Perk::getLevelRequirement,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
            Perk::getParents,
            ByteBufCodecs.BOOL,
            Perk::isUnlocked,
            Perk::new
    );

    public Perk(String name, String description, int levelRequirement, List<String> parents, boolean unlocked) {
        this.name = name;
        this.description = description;
        this.levelRequirement = levelRequirement;
        this.parents = parents;
        this.unlocked = unlocked;
    }

    public Perk(Perk perk) {
        this(perk.name, perk.description, perk.levelRequirement, perk.parents, perk.unlocked);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() { return this.description; }

    public int getLevelRequirement() {
        return this.levelRequirement;
    }

    public List<String> getParents() {
        return this.parents;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void unlock() {
        this.unlocked = true;
    }
}
