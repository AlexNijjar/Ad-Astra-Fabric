package earth.terrarium.adastra.datagen.provider.server.tags;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.tags.ModFluidTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagProvider extends TagsProvider<Fluid> {

    public ModFluidTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.FLUID, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModFluidTags.OXYGEN).add(TagEntry.element(BuiltInRegistries.FLUID.getKey(ModFluids.OXYGEN.get())));
        tag(ModFluidTags.HYDROGEN).add(TagEntry.element(BuiltInRegistries.FLUID.getKey(ModFluids.HYDROGEN.get())));
        tag(ModFluidTags.OIL).add(TagEntry.element(BuiltInRegistries.FLUID.getKey(ModFluids.OIL.get())));
        tag(ModFluidTags.FUEL).add(TagEntry.element(BuiltInRegistries.FLUID.getKey(ModFluids.FUEL.get())));
        tag(ModFluidTags.FUEL).add(TagEntry.element(BuiltInRegistries.FLUID.getKey(ModFluids.CRYO_FUEL.get())));
        tag(ModFluidTags.EFFICIENT_FUEL).add(TagEntry.element(BuiltInRegistries.FLUID.getKey(ModFluids.CRYO_FUEL.get())));

        tag(ModFluidTags.TIER_1_ROCKET_FUEL).add(TagEntry.tag(ModFluidTags.FUEL.location()));
        tag(ModFluidTags.TIER_2_ROCKET_FUEL).add(TagEntry.tag(ModFluidTags.FUEL.location()));
        tag(ModFluidTags.TIER_3_ROCKET_FUEL).add(TagEntry.tag(ModFluidTags.FUEL.location()));
        tag(ModFluidTags.TIER_4_ROCKET_FUEL).add(TagEntry.tag(ModFluidTags.FUEL.location()));
        tag(ModFluidTags.TIER_1_ROVER_FUEL).add(TagEntry.tag(ModFluidTags.FUEL.location()));

        tag(ModFluidTags.ZIP_GUN_PROPELLANTS).addTag(ModFluidTags.OXYGEN).addTag(ModFluidTags.HYDROGEN);

        tag(ModFluidTags.FREEZES_IN_SPACE).add(TagEntry.element(BuiltInRegistries.FLUID.getKey(Fluids.WATER)));
        tag(ModFluidTags.EVAPORATES_IN_SPACE).add(TagEntry.element(BuiltInRegistries.FLUID.getKey(Fluids.WATER)));

        tag(ModFluidTags.OXYGEN).add(TagEntry.optionalTag(new ResourceLocation("c:oxygen")));
        tag(ModFluidTags.OXYGEN).add(TagEntry.optionalTag(new ResourceLocation("forge:oxygen")));

        tag(ModFluidTags.HYDROGEN).add(TagEntry.optionalTag(new ResourceLocation("c:hydrogen")));
        tag(ModFluidTags.HYDROGEN).add(TagEntry.optionalTag(new ResourceLocation("forge:hydrogen")));

        tag(ModFluidTags.FUEL).add(TagEntry.optionalTag(new ResourceLocation("c:diesel")));
        tag(ModFluidTags.FUEL).add(TagEntry.optionalTag(new ResourceLocation("forge:diesel")));
        tag(ModFluidTags.FUEL).add(TagEntry.optionalTag(new ResourceLocation("c:biodiesel")));
        tag(ModFluidTags.FUEL).add(TagEntry.optionalTag(new ResourceLocation("forge:biodiesel")));
        tag(ModFluidTags.FUEL).add(TagEntry.optionalTag(new ResourceLocation("forge:biodiesel")));

        tag(ModFluidTags.OIL).add(TagEntry.optionalElement(new ResourceLocation("techreborn:oil")));
    }
}