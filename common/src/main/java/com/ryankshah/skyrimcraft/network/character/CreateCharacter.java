package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.feature.Race;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.function.Supplier;

public record CreateCharacter(int raceID, boolean fin) //(int raceID, String raceName, Map<Integer, IntList> skills)
{
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "createcharacter");

    public static final StreamCodec<FriendlyByteBuf, CreateCharacter> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            CreateCharacter::raceID,
            ByteBufCodecs.BOOL,
            CreateCharacter::fin,
            CreateCharacter::new
    );

    public CreateCharacter(final FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBoolean()); //, buf.readUtf(), buf.readMap(FriendlyByteBuf::readInt, FriendlyByteBuf::readIntIdList));
    }

    public static void handle(PacketContext<CreateCharacter> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<CreateCharacter> context) {
        ServerPlayer player = context.sender();
        Race race = Race.getRaces().stream().filter(r -> r.getId() == context.message().raceID()).findFirst().get();
        Character character = Character.get(player);

        character.setRace(race);
        character.setSkills(new ArrayList<>(character.getStartingSkills(race)));
        character.setHasSetup(true);

        if(context.message().fin) {
            for (Supplier<Spell> spell : SpellRegistry.getPowersForRace(race)) {
                character.addNewSpell(spell.get());
            }
        }

        final UpdateCharacter sendToClient = new UpdateCharacter(character);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<CreateCharacter> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = minecraft.player;

            Race race = Race.getRaces().stream().filter(r -> r.getId() == context.message().raceID()).findFirst().get();
            Character character = Character.get(player);

            character.setRace(race);
            character.setSkills(new ArrayList<>(character.getStartingSkills(race)));
            character.setHasSetup(true);

            if(context.message().fin) {
                for (Supplier<Spell> spell : SpellRegistry.getPowersForRace(race)) {
                    character.addNewSpell(spell.get());
                }
            }
        });
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(TYPE);
    }
}