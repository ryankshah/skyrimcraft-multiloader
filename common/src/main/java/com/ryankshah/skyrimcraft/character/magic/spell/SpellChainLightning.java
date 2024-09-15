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
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SpellChainLightning extends Spell
{
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
        return 1.0f;
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
            Vec3 target = result.getLocation().add(0, 0.5f, 0);
            Vec3 playerPos = getCaster().position().add(0, 0.5f, 0);

            Vec3 dist = playerPos.subtract(target);
            double distance = dist.length();
            double particleIntervalInBlocks = 1.25; // every X blocks spawn a particle
            Vec3 unitVector = dist.normalize().multiply(-particleIntervalInBlocks , -particleIntervalInBlocks , -particleIntervalInBlocks );

            Vec3 startPos = playerPos;

            // keep stepping forward until we go the whole distance
            for(double step = 0; step < distance; step += particleIntervalInBlocks) {
                startPos = startPos.add(unitVector);
                level.sendParticles(new LightningParticle.LightningParticleOptions(ParticleColors.WHITE_LIGHTNING, 0.5F, 1), startPos.x, startPos.y, startPos.z, 2, 0, 0, 0, 0);
            }

            if(result.getType() == HitResult.Type.ENTITY) {
                level.playSound(null, target.x, target.y, target.z,
                        SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.MASTER, 1.0F, 0.5F + ProjectileHelper.RANDOM.nextFloat() * 0.2F);

                for (int i = 0; i < 32; i++) {
                    double offsetX = ProjectileHelper.RANDOM.nextGaussian() * 1.5D;
                    double offsetY = ProjectileHelper.RANDOM.nextGaussian() * 1.5D;
                    double offsetZ = ProjectileHelper.RANDOM.nextGaussian() * 1.5D;
                    level.sendParticles(new LightningParticle.LightningParticleOptions(ParticleColors.WHITE_LIGHTNING, 0.25F, 1),
                            target.x + offsetX, target.y + offsetY, target.z + offsetZ,
                            0, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                super.onCast();
            }
        }
    }

//    public void vertex(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal, float pX, float pY, float pZ, int pRed, int pGreen, int pBlue, float pU, float pV) {
//        pConsumer.vertex(pPose, pX, pY, pZ).color(pRed, pGreen, pBlue, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
//    }
}