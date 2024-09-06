package com.ryankshah.skyrimcraft.entity.boss.dragon;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Random;

public class DragonMoveController extends MoveControl {
    private final SkyrimDragon dragon;
    private final Random random = new Random();
    private int phaseTimer;
    private int currentPhase;

    public DragonMoveController(SkyrimDragon dragon) {
        super(dragon);
        this.dragon = dragon;
        this.currentPhase = 0; // Start in phase 0
        setNextPhaseTimer();
    }

    private void setNextPhaseTimer() {
        this.phaseTimer = 100 + random.nextInt(100); // Random time between 5 to 10 seconds
    }

    @Override
    public void tick() {
        phaseTimer--;

        if (phaseTimer <= 0) {
            switchPhase();
            setNextPhaseTimer();
        }

        if (!dragon.isFlying()) {
            super.tick();
            return;
        }

        switch (currentPhase) {
            case 0: // Idle in the air
                // Implement idle logic if needed
                break;
            case 1: // Fly towards target
                moveToTarget();
                break;
            case 2: // Attack with shout
                // Implement shout attack logic
                break;
            case 3: // Land
                tryLanding();
                break;
            default:
                // Handle additional phases if needed
                break;
        }
    }

    private void switchPhase() {
        currentPhase = (currentPhase + 1) % 4; // Cycle through phases
        // Additional logic for each phase if needed
    }

    private void moveToTarget() {
        if (operation == Operation.MOVE_TO) {
            operation = Operation.WAIT;
            double xDif = wantedX - mob.getX();
            double yDif = wantedY - mob.getY();
            double zDif = wantedZ - mob.getZ();
            double sq = xDif * xDif + yDif * yDif + zDif * zDif;
            if (sq < 2.5E-7F) {
                mob.setYya(0.0F);
                mob.setZza(0.0F);
                return;
            }

            float speed = (float) (speedModifier * mob.getAttributeValue(Attributes.FLYING_SPEED));
            double distSq = Math.sqrt(xDif * xDif + zDif * zDif);
            mob.setSpeed(speed);
            if (Math.abs(yDif) > 1.0E-5F || Math.abs(distSq) > 1.0E-5F) {
                mob.setYya(yDif > 0d ? speed : -speed);
            }

            float yaw = (float) (Mth.atan2(zDif, xDif) * (180F / Math.PI)) - 90.0F;
            mob.setYRot(rotlerp(mob.getYRot(), yaw, 6));
        } else {
            mob.setYya(0);
            mob.setZza(0);
        }
    }

    private void tryLanding() {
        // Logic to initiate landing
        if (!dragon.isFlying()) {
            dragon.setPrevAnimationState(dragon.getAnimationState());
            dragon.setAnimationState(0); // Switch to idle phase if landed
        }
    }
}