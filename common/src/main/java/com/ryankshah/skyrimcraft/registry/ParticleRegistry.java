package com.ryankshah.skyrimcraft.registry;

import com.example.examplemod.registration.RegistrationProvider;
import com.example.examplemod.registration.RegistryObject;
import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.particle.EmittingLightningParticle;
import com.ryankshah.skyrimcraft.particle.LightningParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class ParticleRegistry
{
    public static void init() {}

    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(BuiltInRegistries.PARTICLE_TYPE, Constants.MODID);

    public static RegistryObject<ParticleType<?>,ParticleType<EmittingLightningParticle.EmittingLightningParticleOptions>> EMITTING_LIGHTNING = PARTICLE_TYPES.register("emitting_lightning", () ->
            new ParticleType<>(false) {
                @Override
                public MapCodec<EmittingLightningParticle.EmittingLightningParticleOptions> codec() {
                    return null;
                }

                @Override
                public StreamCodec<? super RegistryFriendlyByteBuf, EmittingLightningParticle.EmittingLightningParticleOptions> streamCodec() {
                    return EmittingLightningParticle.EmittingLightningParticleOptions.STREAM_CODEC;
                }
            });

    public static RegistryObject<ParticleType<?>,ParticleType<LightningParticle.LightningParticleOptions>> LIGHTNING = PARTICLE_TYPES.register("lightning", () ->
            new ParticleType<>(false) {
                @Override
                public MapCodec<LightningParticle.LightningParticleOptions> codec() {
                    return null;
                }

                @Override
                public StreamCodec<? super RegistryFriendlyByteBuf, LightningParticle.LightningParticleOptions> streamCodec() {
                    return LightningParticle.LightningParticleOptions.STREAM_CODEC;
                }
            });

//    public static RegistryObject<ParticleType<?>,ParticleType<FireParticle.FireParticleOptions>> FIRE = PARTICLE_TYPES.register("fire", () ->
//            new ParticleType<>(false, FireParticle.FireParticleOptions.DESERIALIZER) {
//                @Override
//                public Codec<FireParticle.FireParticleOptions> codec() {
//                    return null;
//                }
//
//                @Override
//                public StreamCodec<? super RegistryFriendlyByteBuf, FireParticle.FireParticleOptions> streamCodec() {
//                    return null;
//                }
//            });
}