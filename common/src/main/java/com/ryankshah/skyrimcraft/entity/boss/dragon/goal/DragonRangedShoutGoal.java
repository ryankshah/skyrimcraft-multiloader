package com.ryankshah.skyrimcraft.entity.boss.dragon.goal;

import com.ryankshah.skyrimcraft.entity.boss.dragon.SkyrimDragon;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;

public class DragonRangedShoutGoal extends Goal {
    private final SkyrimDragon dragon;
    private int shoutCooldown = 0;  // Cooldown between shouts

    public DragonRangedShoutGoal(SkyrimDragon dragon) {
        this.dragon = dragon;
    }

    @Override
    public boolean canUse() {
        Player target = (Player) dragon.getTarget();
        // Use this goal only if the dragon is flying and the target is within a range
        return dragon.isFlying() && target != null && dragon.distanceTo(target) > 10.0D && shoutCooldown == 0;
    }

    @Override
    public void start() {
        shoutAtTarget();
        shoutCooldown = 100;  // 5 seconds cooldown
    }

    @Override
    public void tick() {
        if (shoutCooldown > 0) {
            shoutCooldown--;
        }
    }

    private void shoutAtTarget() {
        Player target = (Player) dragon.getTarget();
        if (target == null) return;

        // Create a fireball (you can customize this for other projectiles or shouts)
        double x = target.getX() - dragon.getX();
        double y = target.getY(0.5D) - dragon.getY(0.5D);
        double z = target.getZ() - dragon.getZ();

        LargeFireball fireball = new LargeFireball(dragon.level(), dragon, new Vec3(x, y, z), 2);
        fireball.setPos(dragon.getX(), dragon.getY(0.5D) + 0.5D, dragon.getZ());
        fireball.shoot(x, y, z, 1.5F, 1.0F);
        dragon.level().addFreshEntity(fireball);
    }
}