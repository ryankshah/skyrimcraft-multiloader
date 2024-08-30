package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.network.spell.AddToKnownSpells;
import commonnetwork.api.Dispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ShoutBlock extends Block {
    private Random random = new Random();

    public static final BooleanProperty SHOUT_GIVEN;

    public static final List<BlockPos> PARTICLE_OFFSETS = BlockPos.betweenClosedStream(-2, 0, -2, 2, 1, 2)
            .filter(p_207914_ -> Math.abs(p_207914_.getX()) == 2 || Math.abs(p_207914_.getZ()) == 2)
            .map(BlockPos::immutable)
            .toList();

    public ShoutBlock() {
        super(Properties.ofFullCopy(Blocks.DIAMOND_ORE).strength(-1.0F, 3600000.0F).randomTicks());
        this.registerDefaultState(getStateDefinition().any().setValue(SHOUT_GIVEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        // this is where the properties are actually added to the state
        pBuilder.add(SHOUT_GIVEN);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(SHOUT_GIVEN, false);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if(!pLevel.isClientSide) {
            ServerPlayer player = (ServerPlayer)pPlayer;
            List<BlockState> nearbyShoutBlocks = pLevel.getBlockStates(new AABB(pPos.getX() - 5, pPos.getY() - 5, pPos.getZ() - 5,
                    pPos.getX() + 5, pPos.getY() + 5, pPos.getZ() + 5)).collect(Collectors.toList());
            if(!pState.getValue(SHOUT_GIVEN)) {
                for(BlockState state : nearbyShoutBlocks) {
                    if(state.hasProperty(SHOUT_GIVEN) && state.getValue(SHOUT_GIVEN)) {
                        pPlayer.displayClientMessage(Component.translatable("skyrimcraft.shoutblock.used"), false);
                        return InteractionResult.FAIL;
                    }
                }

                Character character = Character.get(pPlayer);

                //TODO: Later on we will make use of dragon souls to unlock shouts...
                List<Spell> knownSpells = character.getKnownSpells();
//                System.out.println("Current Known Spells: " + knownSpells);
                if (knownSpells.contains(SpellRegistry.UNRELENTING_FORCE.get())) {
                    List<Spell> shouts = SpellRegistry.SPELLS.getEntries().stream().filter(spell -> spell.get().getType() == Spell.SpellType.SHOUT && spell != SpellRegistry.UNRELENTING_FORCE && !knownSpells.contains(spell.get())).map(r -> r.asHolder().value()).toList();
                    System.out.println(shouts);
                    if (!shouts.isEmpty()) {
                        Spell shout = shouts.get(random.nextInt(shouts.size()));
                        System.out.println(shout);

                        final AddToKnownSpells addSpell = new AddToKnownSpells(SpellRegistry.SPELLS_REGISTRY.getResourceKey(shout).get());
                        Dispatcher.sendToServer(addSpell);
//                        PacketDistributor.SERVER.noArg().send(addSpell);

                        pLevel.setBlockAndUpdate(pPos, pState.setValue(SHOUT_GIVEN, true));
                        for(BlockState state : nearbyShoutBlocks) {
                            if(state.hasProperty(SHOUT_GIVEN) && !state.getValue(SHOUT_GIVEN)) {
                                state.setValue(SHOUT_GIVEN, true);
                            }
                        }

                    } else {
                        pPlayer.displayClientMessage(Component.translatable("shoutblock.allshoutsknown"), false);
                    }
                } else {
                    final AddToKnownSpells addSpell = new AddToKnownSpells(SpellRegistry.SPELLS_REGISTRY.getResourceKey(SpellRegistry.UNRELENTING_FORCE.get()).get());
                    Dispatcher.sendToServer(addSpell);
//                    PacketDistributor.SERVER.noArg().send(addSpell);

                    pLevel.setBlockAndUpdate(pPos, pState.setValue(SHOUT_GIVEN, true));
                    for(BlockState state : nearbyShoutBlocks) {
                        if(state.hasProperty(SHOUT_GIVEN) && !state.getValue(SHOUT_GIVEN)) {
                            state.setValue(SHOUT_GIVEN, true);
                        }
                    }
                }
            } else {
                pPlayer.displayClientMessage(Component.translatable("shoutblock.used"), false);
            }

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);

        for (BlockPos blockpos : PARTICLE_OFFSETS) {
            if (pRandom.nextInt(16) == 0) {
                pLevel.addParticle(
                        ParticleTypes.ENCHANT,
                        (double) pPos.getX() + 0.5,
                        (double) pPos.getY() + 2.0,
                        (double) pPos.getZ() + 0.5,
                        (double) ((float) blockpos.getX() + pRandom.nextFloat()) - 0.5,
                        (double) ((float) blockpos.getY() - pRandom.nextFloat() - 1.0F),
                        (double) ((float) blockpos.getZ() + pRandom.nextFloat()) - 0.5
                );
            }
        }
    }

    static {
        SHOUT_GIVEN = BooleanProperty.create("shout_given");
    }
}