package com.ryankshah.skyrimcraft.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.registry.AdvancementTriggersRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import java.util.Optional;

public final class LearnSpellTrigger extends SimpleCriterionTrigger<LearnSpellTrigger.Instance> {
    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player, Holder<Spell> spell) {
        trigger(player, instance -> instance.matches(spell));
    }

    public static Criterion<Instance> onLearn(@Nullable ContextAwarePredicate conditions, @Nullable Holder<Spell> spell) {
        return AdvancementTriggersRegistry.LEARN_SPELL.get().createCriterion(new Instance(Optional.ofNullable(conditions), spell != null ? Optional.of(spell) : Optional.empty()));
    }

    public static Criterion<Instance> onLearn(@Nullable Holder<Spell> spell) {
        return onLearn(null, spell);
    }


    public record Instance(Optional<ContextAwarePredicate> player, Optional<Holder<Spell>> spell) implements SimpleInstance {
        private static final Codec<Instance> CODEC = RecordCodecBuilder.create(codec -> codec.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
                SpellRegistry.SPELLS_REGISTRY.holderByNameCodec().optionalFieldOf("skill").forGetter(Instance::spell)
        ).apply(codec, Instance::new));

        public boolean matches(Holder<Spell> spell) {
            return spell().isEmpty() || spell().get() == spell;
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return player;
        }
    }
}