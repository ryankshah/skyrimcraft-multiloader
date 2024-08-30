package com.ryankshah.skyrimcraft.data.loot_table.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.data.loot_table.condition.type.SkyrimcraftLootConditionTypes;
import com.ryankshah.skyrimcraft.data.loot_table.predicate.SkillPredicate;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class MatchSkillLevel implements LootItemCondition
{
    private final SkillPredicate skillPredicate;

    public MatchSkillLevel(SkillPredicate skillPredicate) {
        this.skillPredicate = skillPredicate;
    }

    public static final MapCodec<MatchSkillLevel> CODEC = RecordCodecBuilder.mapCodec(
            p_298173_ -> p_298173_.group(
                    SkillPredicate.CODEC.fieldOf("skillPredicate").forGetter(MatchSkillLevel::getPredicate)
            ).apply(p_298173_, MatchSkillLevel::new)
    );

//    @Override
//    public Set<LootParameter<?>> getReferencedContextParams() {
//        return ImmutableSet.of(LootParameters.THIS_ENTITY);
//    }

    public SkillPredicate getPredicate() {
        return this.skillPredicate;
    }

    @Override
    public LootItemConditionType getType() {
        return SkyrimcraftLootConditionTypes.MATCH_SKILL.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        if(entity instanceof ServerPlayer player) {
            float successChance = player.getRandom().nextFloat();
            Character character = Character.get(player);
            return this.skillPredicate.matches(character.getSkills().get(skillPredicate.getSkill().getID()).getSkill(), successChance);
        }
        return false;
    }

    public static Builder skillMatches(SkillPredicate.Builder builder) {
        return () -> {
            return new MatchSkillLevel(builder.build());
        };
    }

//    public static class MatchSkillLevelSerializer implements Serializer<MatchSkillLevel> {
//        public void serialize(JsonObject obj, MatchSkillLevel condition, JsonSerializationContext context) {
//            obj.add("predicate", condition.skillPredicate.serializeToJson());
//        }
//
//        public MatchSkillLevel deserialize(JsonObject obj, JsonDeserializationContext context) {
//            SkillPredicate skillPredicate = SkillPredicate.fromJson(obj.get("predicate"));
//            return new MatchSkillLevel(skillPredicate);
//        }
//    }
}