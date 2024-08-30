package com.ryankshah.skyrimcraft.data.sound;

import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class SkyrimSoundsProvider extends SoundDefinitionsProvider
{
    public SkyrimSoundsProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    @Override
    public void registerSounds() {
        this.add(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "swamp_ambient"),
                definition().subtitle("sound." + Constants.MODID + ".swamp_ambient").with(
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "swamp_ambient"))
                                .weight(4) // has 4/5 = 80% change of playing
                                .volume(0.75)
                                .stream()
                )
        );
        this.add(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "skyrim_caves"),
                definition().subtitle("sound." + Constants.MODID + ".skyrim_caves").with(
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "cave_ambience_1"))
                                .weight(2) // has 4/5 = 80% change of playing
                                .volume(0.75)
                                .stream(),
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "cave_ambience_2"))
                                .weight(2) // has 4/5 = 80% change of playing
                                .volume(0.75)
                                .stream(),
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "cave_ambience_3"))
                                .weight(2) // has 4/5 = 80% change of playing
                                .volume(0.75)
                                .stream(),
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "cave_ambience_4"))
                                .weight(2) // has 4/5 = 80% change of playing
                                .volume(0.75)
                                .stream(),
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "cave_ambience_5"))
                                .weight(2) // has 4/5 = 80% change of playing
                                .volume(0.75)
                                .stream()
                )
        );
        this.add(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "fire_spell_cast"),
                definition().subtitle("sound." + Constants.MODID + ".fire_spell_cast").with(
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "fire_spell_cast"))
                )
        );
        this.add(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "unrelenting_force_cast"),
                definition().subtitle("sound." + Constants.MODID + ".unrelenting_force_cast").with(
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "unrelenting_force_cast"))
                )
        );
        this.add(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "freeze_spell_hit"),
                definition().subtitle("sound." + Constants.MODID + ".freeze_spell_hit").with(
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "freeze_spell_hit"))
                )
        );
        this.add(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "draugr_grunt"),
                definition().subtitle("sound." + Constants.MODID + ".draugr_grunt").with(
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "draugr_grunt"))
                )
        );
        this.add(
                ResourceLocation.fromNamespaceAndPath(Constants.MODID, "draugr_attack"),
                definition().subtitle("sound." + Constants.MODID + ".draugr_attack").with(
                        sound(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "draugr_attack"))
                )
        );
    }
}
