package com.ryankshah.skyrimcraft.character.magic.spell;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.network.spell.UpdateTelekinesisItem;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.util.ProjectileHelper;
import commonnetwork.api.Dispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SpellTelekinesis extends Spell
{
    private static final double PULL_SPEED = 0.5;
    private static final double PICKUP_DISTANCE = 1.5;
    private static final double MAX_DISTANCE = 20.0;

    private ItemEntity targetItem;
    private Vec3 lastKnownPosition;

    public SpellTelekinesis(int identifier) {
        super(identifier, "telekinesis");
    }

    @Override
    public String getName() {
        return "Telekinesis";
    }

    @Override
    public List<String> getDescription() {
        List<String> desc = new ArrayList<>();
        desc.add("Can pull an object to you");
        desc.add("from distance. Add it to");
        desc.add("your inventory or throw it.");
        return desc;
    }

    @Override
    public ResourceLocation getDisplayAnimation() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/blue_spell.png");
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(Constants.MODID, "spells/icons/telekinesis.png");
    }

    @Override
    public SoundEvent getSound() {
        return SoundEvents.ENCHANTMENT_TABLE_USE;
    }

    @Override
    public float getCost() {
        return 2.0f;
    }

    @Override
    public float getCooldown() {
        return 0f;
    }

    @Override
    public SpellType getType() {
        return SpellType.ALTERATION;
    }

    @Override
    public SpellDifficulty getDifficulty() {
        return SpellDifficulty.ADEPT;
    }

    @Override
    public int getBaseXp() {
        return 8;
    }

    @Override
    public boolean isContinuous() {
        return true;
    }

    @Override
    public void onCast() {
        if (!(getCaster() instanceof Player player) || !(player.level() instanceof ServerLevel level)) {
            return;
        }

        if (targetItem == null || !targetItem.isAlive()) {
            // Find a new target
            HitResult result = ProjectileHelper.getLookAtHit(player, MAX_DISTANCE);
            if (result.getType() == HitResult.Type.ENTITY && ((EntityHitResult) result).getEntity() instanceof ItemEntity) {
                targetItem = (ItemEntity) ((EntityHitResult) result).getEntity();
                lastKnownPosition = targetItem.position();
                targetItem.setNoGravity(true);  // Disable gravity when pulling
            }
        }

        if (targetItem != null) {
            Vec3 playerPos = player.getEyePosition();
            Vec3 itemPos = targetItem.position();
            Vec3 direction = playerPos.subtract(itemPos).normalize();

            // Move the item towards the player
            Vec3 newPos = itemPos.add(direction.scale(PULL_SPEED));
            targetItem.setPos(newPos);
            lastKnownPosition = newPos;

            // Cancel any existing motion
            targetItem.setDeltaMovement(Vec3.ZERO);

            // Update client-side position
            Dispatcher.sendToAllClients(new UpdateTelekinesisItem(targetItem.getId(), newPos.toVector3f()), level.getServer());

            // Check if the item is close enough to pick up
            if (playerPos.distanceTo(itemPos) <= PICKUP_DISTANCE) {
                ItemStack stack = targetItem.getItem().copy();
                if (player.getInventory().add(stack)) {
                    targetItem.discard();
                    targetItem = null;
                }
            }
        }
    }



    @Override
    public void onSpellCancel() {
        if (targetItem != null) {
            // Apply gravity to the item
            targetItem.setNoGravity(false);

            // Reset the item's motion
            targetItem.setDeltaMovement(Vec3.ZERO);

            // Update clients about the item's new state
            Dispatcher.sendToAllClients(new UpdateTelekinesisItem(targetItem.getId(), lastKnownPosition.toVector3f()), getCaster().level().getServer());

            targetItem = null;
        }
    }

    private boolean hasSufficientMagicka() {
        return getCaster() instanceof Player player &&
                Services.PLATFORM.getCharacter(player).getMagicka() >= getCost();
    }
}