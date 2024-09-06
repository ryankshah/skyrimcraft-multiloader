package com.ryankshah.skyrimcraft.entity.boss.dragon.goal;

import com.ryankshah.skyrimcraft.entity.boss.dragon.SkyrimDragon;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public class DragonLandingGoal extends Goal {
    private final SkyrimDragon dragon;

    public DragonLandingGoal(SkyrimDragon dragon) {
        this.dragon = dragon;
    }

    @Override
    public boolean canUse() {
        // Land if the dragon is flying and close enough to the target
        Player target = (Player) dragon.getTarget();
        return dragon.isFlying() && target != null && dragon.distanceTo(target) < 20.0D;
    }

    @Override
    public void start() {
        dragon.tryLanding();  // Start the landing sequence
    }
}