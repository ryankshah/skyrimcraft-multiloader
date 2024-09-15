package com.ryankshah.skyrimcraft.character.magic.shout;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.particle.LightningParticle;
import com.ryankshah.skyrimcraft.util.ParticleColors;
import com.ryankshah.skyrimcraft.util.ProjectileHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShoutFireBreath extends Spell //implements IForgeRegistryEntry<ISpell>
{
    private static final float DAMAGE = 7.5F;
    private static final double RANGE = 10.0D;

    public ShoutFireBreath(int identifier) {
        super(identifier, "fire_breath");
    }

    @Override
    public String getName() {
        return "Fire Breath";
    }

    @Override
    public String getShoutName() {
        return "Yol Toor Shul";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Inhale air, exhale flame,");
        desc.add("and behold the Thu'um as");
        desc.add("inferno");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.DRAGON_FIREBALL_EXPLODE;
    }

    @Override
    public float getSoundLength() {
        return 1f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/fire_breath.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/fire_breath.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 100.0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.SHOUT;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.NA;
    }

    @Override
    public void onCast() {
        if (getCaster().level() instanceof ServerLevel serverLevel) {
            int fireBreathTicks = 40;
            Random random = new Random();
            double coneAngle = 0.6; // Angle to spread particles (width of the cone)
            double coneRadius = 6.0; // Adjust the range of the fire breath

            // Loop to simulate ticks
            for (int tick = 0; tick < fireBreathTicks; tick++) {
                // Ensure the caster is facing forward
                Vec3 lookDirection = ProjectileHelper.getTargetAdjustedLookAngle(getCaster()).normalize();
                Vec3 start = getCaster().position().add(lookDirection.scale(2.0)); // Starting point in front of the caster

                // Emit particles in a cone shape in front of the player
                for (int i = 0; i < 20; i++) { // Number of particles per tick
                    // Randomly spread particles in a cone by altering the direction
                    double yaw = (random.nextDouble() - 0.5) * coneAngle;  // Horizontal spread
                    double pitch = (random.nextDouble() - 0.5) * coneAngle; // Vertical spread

                    // Adjust the direction slightly based on random yaw and pitch
                    Vec3 randomDir = lookDirection
                            .add(getCaster().getLookAngle().yRot((float) yaw).xRot((float) pitch))
                            .normalize();

                    // Position the particle within the range of the fire breath (from 2.0 to 6.0 blocks away)
                    Vec3 particlePos = start.add(randomDir.scale(random.nextDouble() * (coneRadius - 2.0) + 2.0));

                    // Spawn fire particles in the cone area in front of the caster
                    serverLevel.sendParticles(ParticleTypes.FLAME, particlePos.x, particlePos.y, particlePos.z, 4, 0, 0, 0, 0.05);
                }

                // Check for entities in the fire breath cone and set them on fire
                AABB boundingBox = new AABB(start, start.add(lookDirection.scale(coneRadius))).inflate(2.0);
                for (Entity entity : serverLevel.getEntities(getCaster(), boundingBox)) {
                    if (entity instanceof LivingEntity && entity != getCaster()) {
                        // Check if entity is in the fire breath cone
                        Vec3 toEntity = entity.position().subtract(start).normalize();
                        double angle = Math.acos(lookDirection.dot(toEntity));

                        if (angle <= coneAngle) {
                            // Set entities on fire briefly
                            ((LivingEntity) entity).setRemainingFireTicks(60);
                        }
                    }
                }
            }
        }

        super.onCast();
    }
}