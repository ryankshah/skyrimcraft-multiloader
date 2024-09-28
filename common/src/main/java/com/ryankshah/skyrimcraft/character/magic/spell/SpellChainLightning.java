package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.particle.LightningParticle;
import com.ryankshah.skyrimcraft.util.ParticleColors;
import com.ryankshah.skyrimcraft.util.ProjectileHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SpellChainLightning extends Spell
{
    private static final int MAX_CHAIN_DISTANCE = 5;
    private static final int PARTICLE_FREQUENCY = 1; // Particles every tick for smoother effect
    private static final int DAMAGE_INTERVAL = 20; // Damage every 20 ticks (1 second)
    private int ticksSinceCast = 0;

    public SpellChainLightning(int identifier) {
        super(identifier, "chain_lightning");
    }

    @Override
    public String getName() {
        return "Chain Lightning";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Lightning bolt that does");
        desc.add("shock damage to health and");
        desc.add("half to magicka");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/purple_spell.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/lightning_icon.png");
    }

    public SoundEvent getSound() {
        return SoundEvents.LIGHTNING_BOLT_IMPACT;
    }

    @Override
    public float getCost() {
        return 4.0f;
    }

    @Override
    public float getCooldown() {
        return 0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.DESTRUCTION;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    @Override
    public int getBaseXp() {
        return 6;
    }

    @Override
    public CastReference getCastReference() {
        return CastReference.HOLD;
    }

    @Override
    public boolean isContinuous() { return true; }

    @Override
    public void onCast() {
        if (getCaster().level() instanceof ServerLevel level) {
            HitResult result = ProjectileHelper.getLookAtHit(getCaster(), 16D);
            if (result.getType() == HitResult.Type.ENTITY) {
                Entity mainTarget = ((EntityHitResult) result).getEntity();
                streamLightning(level, getCaster().getEyePosition(), mainTarget.position().add(0, mainTarget.getEyeHeight() / 2, 0));

                // Chain to nearby entities
                List<Entity> chainedEntities = level.getEntities(mainTarget, mainTarget.getBoundingBox().inflate(MAX_CHAIN_DISTANCE),
                        entity -> entity instanceof LivingEntity && entity != getCaster() && entity != mainTarget);

                for (Entity chainedEntity : chainedEntities) {
                    streamLightning(level, mainTarget.position(), chainedEntity.position().add(0, chainedEntity.getEyeHeight() / 2, 0));
                }

                // Apply damage every DAMAGE_INTERVAL ticks
                if (ticksSinceCast % DAMAGE_INTERVAL == 0) {
                    applyDamage(mainTarget);
                    for (Entity chainedEntity : chainedEntities) {
                        applyDamage(chainedEntity);
                    }
                }
            }
            ticksSinceCast++;
        }
    }

    private void streamLightning(ServerLevel level, Vec3 start, Vec3 end) {
        if (level.getGameTime() % PARTICLE_FREQUENCY != 0) return;

        Vec3 diff = end.subtract(start);
        int steps = (int) (diff.length() * 4); // 4 particles per block for denser effect
        Vec3 step = diff.normalize().scale(0.25); // 0.25 block step size

        for (int i = 0; i < steps; i++) {
            Vec3 pos = start.add(step.scale(i));
            double offsetX = level.random.nextGaussian() * 0.05;
            double offsetY = level.random.nextGaussian() * 0.05;
            double offsetZ = level.random.nextGaussian() * 0.05;
            level.sendParticles(new LightningParticle.LightningParticleOptions(ParticleColors.WHITE_LIGHTNING, 0.5F, 1),
                    pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ, 1, 0, 0, 0, 0);
        }
    }

    private void applyDamage(Entity target) {
        if (target instanceof LivingEntity livingEntity) {
            // Apply your damage logic here
            // For example:
            livingEntity.hurt(livingEntity.damageSources().magic(), 5.0f);
            // You can also add logic here to reduce the target's magicka if applicable
        }
    }
}