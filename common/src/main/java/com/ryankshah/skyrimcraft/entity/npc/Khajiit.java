package com.ryankshah.skyrimcraft.entity.npc;

import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.util.DialogueManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

public class Khajiit extends Villager
{
    private static final EntityDataAccessor<Boolean> SEX = SynchedEntityData.defineId(Khajiit.class, EntityDataSerializers.BOOLEAN);

    public Khajiit(EntityType<? extends Villager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.getNavigation().setCanFloat(true);
        this.setCanPickUpLoot(true);

        this.setPathfindingMalus(PathType.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0, 60));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.FOLLOW_RANGE, 12.0D);
    }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(SEX, false);
    }

    public void addAdditionalSaveData(CompoundTag p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putBoolean("Sex", this.getSex());
    }

    public void readAdditionalSaveData(CompoundTag p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        this.setSex(p_70037_1_.getBoolean("Sex"));
    }

    public boolean getSex() {
        return this.entityData.get(SEX);
    }

    public void setSex(boolean isMale) {
        this.entityData.set(SEX, isMale);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_213386_1_, DifficultyInstance p_213386_2_, MobSpawnType p_213386_3_, SpawnGroupData p_213386_4_) {
        this.setSex(random.nextBoolean());
        return super.finalizeSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_);
    }

    @Override
    public Khajiit getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        double d0 = this.random.nextDouble();

        Khajiit khajiit = new Khajiit(EntityRegistry.KHAJIIT.get(), pLevel);
        khajiit.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(khajiit.blockPosition()), MobSpawnType.BREEDING, null);
        return khajiit;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.level().isClientSide && (this.getVillagerData().getProfession() == VillagerProfession.NONE || this.getVillagerData().getProfession() == VillagerProfession.NITWIT)) {
            DialogueManager.VillageElderDialogues.showInitialDialogue();
        }
        return super.mobInteract(player, hand);
    }

//    @Override
//    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
//        if (!player.level().isClientSide) {
//            DialogueManager.VillageElderDialogues.showInitialDialogue();
//        }
//        return InteractionResult.SUCCESS;
//    }
}
