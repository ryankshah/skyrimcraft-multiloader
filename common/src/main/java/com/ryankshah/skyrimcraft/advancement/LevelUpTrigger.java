package com.ryankshah.skyrimcraft.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.character.skill.SkillWrapper;
import com.ryankshah.skyrimcraft.registry.AdvancementTriggersRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import java.util.Optional;

public final class LevelUpTrigger extends SimpleCriterionTrigger<LevelUpTrigger.Instance> {
    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, Optional<SkillWrapper> skill, Optional<Integer> newLevel) {
        trigger(player, instance -> instance.test(skill, newLevel));
    }

    public static Criterion<Instance> onLevelUp(@Nullable ContextAwarePredicate conditions, @Nullable Optional<SkillWrapper> skill, Optional<Integer> level) {
        return AdvancementTriggersRegistry.LEVEL_UP.get().createCriterion(new Instance(Optional.ofNullable(conditions), skill != null ? skill : Optional.empty(), level));
    }

    public static Criterion<Instance> onLevelUp(@Nullable Optional<SkillWrapper> skill, Optional<Integer> level) {
        return onLevelUp(null, skill, level);
    }

//    public static Criterion<Instance> onLevelUp(@Nullable Holder<Optional<SkillWrapper>> skill) {
//        return onLevelUp(skill, Holder.direct(Optional.of(0)));
//    }

    public static Criterion<Instance> onLevelUp(Optional<Integer> level) {
        return onLevelUp(null, level);
    }

    public record Instance(Optional<ContextAwarePredicate> player, Optional<SkillWrapper> skill, Optional<Integer> levelReq) implements SimpleInstance {
        private static final Codec<Instance> CODEC = RecordCodecBuilder.create(codec -> codec.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
                SkillWrapper.CODEC.optionalFieldOf("skill").forGetter(Instance::skill),
                Codec.INT.optionalFieldOf("level").forGetter(Instance::levelReq)
        ).apply(codec, Instance::new));

        public boolean test(Optional<SkillWrapper> skill, Optional<Integer> level) {
            return (skill().isEmpty() || skill() == skill) && (levelReq().isEmpty() || level.get() >= levelReq().get());
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}