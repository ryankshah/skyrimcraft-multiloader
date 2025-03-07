package com.ryankshah.skyrimcraft.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.registry.ParticleRegistry;
import com.ryankshah.skyrimcraft.registry.ParticleRenderTypeRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Locale;

public class LightningParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected LightningParticle(ClientLevel pLevel, double pX, double pY, double pZ, LightningParticleOptions options, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ);

        Vector3f color = options.color();
        this.rCol = color.x;
        this.gCol = color.y;
        this.bCol = color.z;

        this.quadSize = Math.max(options.scalar(), (this.random.nextFloat() - 0.5F) * options.scalar());
        this.lifetime = options.lifetime();

        this.sprites = pSprites;

        this.setSprite(this.sprites.get(this.random));
    }

    @Override
    public void tick() {
        super.tick();

        this.setSprite(this.sprites.get(this.random));
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderTypeRegistry.ADDITIVE;
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        float f = ((float) this.age + pPartialTick) / (float) this.lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        int i = super.getLightColor(pPartialTick);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int) (f * 15.0F * 16.0F);

        if (j > 240) {
            j = 240;
        }
        return j | k << 16;
    }

    public record LightningParticleOptions(Vector3f color, float scalar, int lifetime) implements ParticleOptions {
//        public static Deserializer<LightningParticleOptions> DESERIALIZER = new Deserializer<>() {
//            public @NotNull LightningParticleOptions fromCommand(@NotNull ParticleType<LightningParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
//                Vector3f color = LightningParticleOptions.readColorVector3f(reader);
//                reader.expect(' ');
//                return new LightningParticleOptions(color, reader.readFloat(), reader.readInt());
//            }
//
//            public @NotNull LightningParticleOptions fromNetwork(@NotNull ParticleType<LightningParticleOptions> type, @NotNull FriendlyByteBuf buf) {
//                return new LightningParticleOptions(LightningParticleOptions.readColorFromNetwork(buf), buf.readFloat(), buf.readInt());
//            }
//        };

//        public static final MapCodec<LightningParticle.LightningParticleOptions> CODEC = RecordCodecBuilder.mapCodec(
//                p_235952_ -> p_235952_.group(
//                        Vec3.CODEC.fieldOf("color").forGetter(p_235954_ -> p_235954_.color)
//                ).apply(p_235952_, LightningParticle.LightningParticleOptions::new)
//        );

        public static final StreamCodec<RegistryFriendlyByteBuf, LightningParticle.LightningParticleOptions> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VECTOR3F,
                LightningParticle.LightningParticleOptions::getColor,
                ByteBufCodecs.FLOAT,
                LightningParticle.LightningParticleOptions::getScalar,
                ByteBufCodecs.INT,
                LightningParticle.LightningParticleOptions::getLifetime,
                LightningParticle.LightningParticleOptions::new
        );

        public Vector3f getColor() {
            return color;
        }

        public float getScalar() {
            return scalar;
        }

        public int getLifetime() {
            return lifetime;
        }

        @Override
        public @NotNull ParticleType<?> getType() {
            return ParticleRegistry.LIGHTNING.get();
        }

        public static Vector3f readColorVector3f(StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f0 = reader.readFloat();
            reader.expect(' ');
            float f1 = reader.readFloat();
            reader.expect(' ');
            float f2 = reader.readFloat();
            return new Vector3f(f0, f1, f2);
        }

        public static Vector3f readColorFromNetwork(FriendlyByteBuf buf) {
            return new Vector3f(buf.readFloat(), buf.readFloat(), buf.readFloat());
        }

//        @Override
//        public void writeToNetwork(FriendlyByteBuf buf) {
//            buf.writeFloat(this.color.x);
//            buf.writeFloat(this.color.y);
//            buf.writeFloat(this.color.z);
//            buf.writeFloat(this.scalar);
//            buf.writeInt(this.lifetime);
//        }
//
//        @Override
//        public @NotNull String writeToString() {
//            return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %d", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()),
//                    this.color.x, this.color.y, this.color.z, this.scalar, this.lifetime);
//        }
    }

    public static class Provider implements ParticleProvider<LightningParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSpriteSet) {
            this.sprites = pSpriteSet;
        }

        @Override
        public LightningParticle createParticle(@NotNull LightningParticleOptions options, @NotNull ClientLevel level, double x, double y, double z,
                                                double xSpeed, double ySpeed, double zSpeed) {
            return new LightningParticle(level, x, y, z, options, this.sprites);
        }
    }
}