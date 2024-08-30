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
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ShoutFrostBreath extends Spell //implements IForgeRegistryEntry<ISpell>
{
    private static final float DAMAGE = 7.5F;
    private static final double RANGE = 10.0D;

    public ShoutFrostBreath(int identifier) {
        super(identifier, "frost_breath");
    }

    @Override
    public String getName() {
        return "Frost Breath";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Your breath is winter, you");
        desc.add("Thu'um a blizzard");
        return desc;
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.SNOW_BREAK;
    }

    @Override
    public float getSoundLength() {
        return 0f;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/frost_breath.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/ice_spell_icon.png");
    }

    @Override
    public float getCost() {
        return 0f;
    }

    @Override
    public float getCooldown() {
        return 120.0f;
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
        Vec3 look = ProjectileHelper.getTargetAdjustedLookAngle(getCaster());

        if (getCaster().level() instanceof ServerLevel level) {
            float scale = 1.0F;

            Vec3 start = getCaster().getEyePosition().subtract(0.0D, scale / 2, 0.0D).add(look);

            Vec3 end = ProjectileHelper.getHitResult(getCaster(), start, start.add(look.scale(RANGE))).getLocation();

            for (int i = 0; i < 32; i++) {
                double theta = ProjectileHelper.RANDOM.nextDouble() * 2 * Math.PI;
                double phi = ProjectileHelper.RANDOM.nextDouble() * Math.PI;
                double r = ProjectileHelper.RANDOM.nextDouble() * RANGE * 0.75D;
                double x = r * Math.sin(phi) * Math.cos(theta);
                double y = r * Math.sin(phi) * Math.sin(theta);
                double z = r * Math.cos(phi);
                Vec3 offset = end.add(x, y, z);
                Vec3 speed = start.subtract(offset).scale(1.0D / 20).reverse();
                level.sendParticles(new LightningParticle.LightningParticleOptions(ParticleColors.PURPLE_LIGHTNING, 0.5F, 1),
                        start.x, start.y, start.z, 4, //new FireParticle.FireParticleOptions(scale, true, 20)
                        speed.x, speed.y, speed.z, 0.5D);
                level.sendParticles(ParticleTypes.CLOUD, start.x, start.y, start.z, 4, //new FireParticle.FireParticleOptions(scale, true, 20)
                        speed.x, speed.y, speed.z, 0.5D);
            }

//            AABB bounds = AABB.ofSize(end, 1.0D, 1.0D, 1.0D).inflate(1.0D);

//            for (Entity entity : getCaster().level().getEntitiesOfClass(LivingEntity.class, bounds, entity -> entity != getCaster())) {
////                if (!entity.hurt(DamageSourceInit.directSpellAttack(getCaster(), this), DAMAGE)) continue;
//                entity.setSecondsOnFire(5);
//            }

//            BlockPos.betweenClosedStream(bounds).forEach(pos -> {
//                if (ProjectileHelper.RANDOM.nextInt(3) != 0) return;
//
//                BlockState state = getCaster().level().getBlockState(pos);
//
//                if (state.isFlammable(getCaster().level(), pos, getCaster().getDirection())) {
//                    getCaster().level().setBlockAndUpdate(pos, BaseFireBlock.getState(getCaster().level(), pos));
//                }
//            });
        }

        super.onCast();
//        } else {
//            getCaster().displayClientMessage(Component.translatable("skyrimcraft.shout.notarget"), false);
//        }
    }
}