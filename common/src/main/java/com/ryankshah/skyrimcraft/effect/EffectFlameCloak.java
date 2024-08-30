package com.ryankshah.skyrimcraft.effect;

import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public class EffectFlameCloak extends MobEffect
{
    protected EffectFlameCloak(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    // Whether the effect should apply this tick. Used e.g. by the Regeneration effect that only applies
    // once every x ticks, depending on the tick count and amplifier.
    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        return tickCount % 2 == 0; //tickCount % 2 == 0; // replace this with whatever check you want
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        if(livingEntity.level() instanceof ServerLevel) {
            ServerLevel level = (ServerLevel)livingEntity.level();
            Vec3 loc = livingEntity.position().add(0, 1, 0);
            double radius = 4D;

            Set<Vec3> sphereSet = ClientUtil.sphere(100);
            for(Vec3 point : sphereSet) {
                sendParticles(livingEntity, level, ParticleTypes.SMALL_FLAME, loc.x + point.x, loc.y + point.y, loc.z + point.z, 1, 0, 0, 0, 0);
                sendParticles(livingEntity, level, ParticleTypes.WHITE_ASH, loc.x + point.x, loc.y + point.y, loc.z + point.z, 1, 0, 0, 0, 0);
            }

            for(Entity entity : level.getEntities(livingEntity, new AABB(livingEntity.position(), livingEntity.position().multiply(radius, radius, radius)))) {
                if(entity instanceof LivingEntity target) {
                    target.setRemainingFireTicks(20*2);
                }
            }
        }
        return super.applyEffectTick(livingEntity, p_76394_2_);
    }

    public <T extends ParticleOptions> int sendParticles(
            LivingEntity entity, ServerLevel level, T pType, double pPosX, double pPosY, double pPosZ, int pParticleCount, double pXOffset, double pYOffset, double pZOffset, double pSpeed
    ) {
        ClientboundLevelParticlesPacket clientboundlevelparticlespacket = new ClientboundLevelParticlesPacket(
                pType, false, pPosX, pPosY, pPosZ, (float)pXOffset, (float)pYOffset, (float)pZOffset, (float)pSpeed, pParticleCount
        );
        int i = 0;

        for(int j = 0; j < level.players().size(); ++j) {
            ServerPlayer serverplayer = level.players().get(j);
            if(!(serverplayer.getUUID().equals(entity.getUUID()))) {
                if (sendParticles(level, serverplayer, false, pPosX, pPosY, pPosZ, clientboundlevelparticlespacket)) {
                    ++i;
                }
            }
        }

        return i;
    }

    private boolean sendParticles(ServerLevel level, ServerPlayer pPlayer, boolean pLongDistance, double pPosX, double pPosY, double pPosZ, Packet<?> pPacket) {
        if (pPlayer.level() != level) {
            return false;
        } else {
            BlockPos blockpos = pPlayer.blockPosition();
            if (blockpos.closerToCenterThan(new Vec3(pPosX, pPosY, pPosZ), pLongDistance ? 512.0 : 32.0)) {
                pPlayer.connection.send(pPacket);
                return true;
            } else {
                return false;
            }
        }
    }
}
