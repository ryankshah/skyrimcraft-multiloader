package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.network.character.*;
import com.ryankshah.skyrimcraft.network.recipe.FinishAlchemyRecipe;
import com.ryankshah.skyrimcraft.network.recipe.FinishForgeRecipe;
import com.ryankshah.skyrimcraft.network.recipe.FinishOvenRecipe;
import com.ryankshah.skyrimcraft.network.skill.AddXpToSkill;
import com.ryankshah.skyrimcraft.network.skill.HandlePickpocket;
import com.ryankshah.skyrimcraft.network.spell.*;
import commonnetwork.api.Network;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class Networking
{
    public static void load() {
        Network.registerPacket(AddToLevelUpdates.type(), AddToLevelUpdates.class, AddToLevelUpdates.CODEC, AddToLevelUpdates::handle);
        Network.registerPacket(AddXpToSkill.type(), AddXpToSkill.class,AddXpToSkill.CODEC, AddXpToSkill::handle);
        Network.registerPacket(HandlePickpocket.type(), HandlePickpocket.class,  HandlePickpocket.CODEC, HandlePickpocket::handle);
        Network.registerPacket(AddToTargetingEntities.type(), AddToTargetingEntities.class,  AddToTargetingEntities.CODEC, AddToTargetingEntities::handle);
        Network.registerPacket(RemoveFromTargetingEntities.type(), RemoveFromTargetingEntities.class,  RemoveFromTargetingEntities.CODEC, RemoveFromTargetingEntities::handle);
        Network.registerPacket(UpdateCurrentTarget.type(), UpdateCurrentTarget.class,  UpdateCurrentTarget.CODEC, UpdateCurrentTarget::handle);
        Network.registerPacket(AddToCompassFeatures.type(), AddToCompassFeatures.class,  AddToCompassFeatures.CODEC, AddToCompassFeatures::handle);
        Network.registerPacket(AddToKnownSpells.type(), AddToKnownSpells.class,  AddToKnownSpells.CODEC, AddToKnownSpells::handle);
        Network.registerPacket(UpdateSelectedSpell.type(), UpdateSelectedSpell.class,  UpdateSelectedSpell.CODEC, UpdateSelectedSpell::handle);
        Network.registerPacket(UpdateShoutCooldown.type(), UpdateShoutCooldown.class,  UpdateShoutCooldown.CODEC, UpdateShoutCooldown::handle);
        Network.registerPacket(CastSpell.type(), CastSpell.class, CastSpell.CODEC, CastSpell::handle);

        Network.registerPacket(DetectLife.type(), DetectLife.class, DetectLife.CODEC, DetectLife::handle);

        Network.registerPacket(ReplenishMagicka.type(), ReplenishMagicka.class, ReplenishMagicka.CODEC, ReplenishMagicka::handle);
        Network.registerPacket(ConsumeMagicka.type(), ConsumeMagicka.class, ConsumeMagicka.CODEC, ConsumeMagicka::handle);
        Network.registerPacket(UpdateMagicka.type(), UpdateMagicka.class, UpdateMagicka.CODEC, UpdateMagicka::handle);
        Network.registerPacket(OpenCharacterCreationScreen.type(), OpenCharacterCreationScreen.class, OpenCharacterCreationScreen.CODEC, OpenCharacterCreationScreen::handle);

        Network.registerPacket(CreateCharacter.type(), CreateCharacter.class, CreateCharacter.CODEC, CreateCharacter::handle);
        Network.registerPacket(UpdateCharacter.type(), UpdateCharacter.class, UpdateCharacter.CODEC, UpdateCharacter::handle);
        Network.registerPacket(UpdateExtraCharacter.type(), UpdateExtraCharacter.class, UpdateExtraCharacter.CODEC, UpdateExtraCharacter::handle);
        Network.registerPacket(UpdateLevelUpdates.type(), UpdateLevelUpdates.class, UpdateLevelUpdates.CODEC, UpdateLevelUpdates::handle);
        Network.registerPacket(UpdateStatIncreases.type(), UpdateStatIncreases.class, UpdateStatIncreases.CODEC, UpdateStatIncreases::handle);
        Network.registerPacket(UpdatePlayerQuests.type(), UpdatePlayerQuests.class, UpdatePlayerQuests.CODEC, UpdatePlayerQuests::handle);
        Network.registerPacket(FinishAlchemyRecipe.type(), FinishAlchemyRecipe.class,  FinishAlchemyRecipe.CODEC, FinishAlchemyRecipe::handle);
        Network.registerPacket(FinishOvenRecipe.type(), FinishOvenRecipe.class,  FinishOvenRecipe.CODEC, FinishOvenRecipe::handle);
        Network.registerPacket(FinishForgeRecipe.type(), FinishForgeRecipe.class,  FinishForgeRecipe.CODEC, FinishForgeRecipe::handle);
    }
}