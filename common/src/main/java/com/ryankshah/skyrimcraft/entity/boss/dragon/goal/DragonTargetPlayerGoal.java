package com.ryankshah.skyrimcraft.entity.boss.dragon.goal;

import com.ryankshah.skyrimcraft.entity.boss.dragon.SkyrimDragon;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;
import java.util.List;

public class DragonTargetPlayerGoal extends Goal
{
    private final SkyrimDragon dragon;

    public DragonTargetPlayerGoal(SkyrimDragon dragon) {
        this.dragon = dragon;
        // Set the flags for this goal, like targeting
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        // Only run if there's no current target or target is out of range
        if (dragon.getTarget() != null && dragon.getTarget().isAlive()) {
            return false;
        }

        // Find closest player in range
        List<Player> players = dragon.level().getEntitiesOfClass(Player.class, dragon.getBoundingBox().inflate(50)); // 50-block range

        Player closestPlayer = null;
        double closestDistance = Double.MAX_VALUE;

        for (Player player : players) {
            // Only target players in survival or adventure mode
            if (player.isAlive() && !player.isCreative()) {
                double distance = dragon.distanceTo(player);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPlayer = player;
                }
            }
        }

        // If a player is found, set them as the target
        if (closestPlayer != null) {
            dragon.setTarget(closestPlayer);
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        if (dragon.getTarget() != null) {
            double distanceToTarget = dragon.distanceTo(dragon.getTarget());

            // If close enough to the target, land and prepare for melee combat
            if (distanceToTarget < 20.0D) {
                dragon.tryLanding();
            }

            // Continue looking at the target even when flying
            dragon.getLookControl().setLookAt(dragon.getTarget(), 30.0F, 30.0F);
        }
    }
}