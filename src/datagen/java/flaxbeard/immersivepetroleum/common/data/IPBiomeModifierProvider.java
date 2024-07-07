package flaxbeard.immersivepetroleum.common.data;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import flaxbeard.immersivepetroleum.ImmersivePetroleum;
import flaxbeard.immersivepetroleum.common.util.ResourceUtils;
import flaxbeard.immersivepetroleum.common.world.IPWorldGen;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddFeaturesBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import net.neoforged.neoforge.registries.holdersets.AnyHolderSet;

public class IPBiomeModifierProvider{
	public static void method(PackOutput output, CompletableFuture<HolderLookup.Provider> vanillaRegistries, Consumer<DataProvider> add){
		final RegistryAccess registryAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		
		final Registry<Biome> biomeReg = registryAccess.registryOrThrow(Registries.BIOME);
		final Registry<PlacedFeature> featureReg = registryAccess.registryOrThrow(Registries.PLACED_FEATURE);
		
		final AnyHolderSet<Biome> anyBiome = new AnyHolderSet<Biome>(biomeReg.asLookup());
		
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Keys.BIOME_MODIFIERS, ctx -> {
			for(Entry<String, Holder<PlacedFeature>> entry:IPWorldGen.features.entrySet()){
				ResourceLocation name = ResourceUtils.ip(entry.getKey());

				ResourceKey<PlacedFeature> key = ResourceKey.create(Registries.PLACED_FEATURE, name);
				Holder<PlacedFeature> featureHolder = featureReg.getHolderOrThrow(key);

				AddFeaturesBiomeModifier modifier = new AddFeaturesBiomeModifier(anyBiome, HolderSet.direct(featureHolder), Decoration.UNDERGROUND_ORES);
				ctx.register(ResourceKey.create(Keys.BIOME_MODIFIERS, name), modifier);
			}
		});
		
		add.accept(new DatapackBuiltinEntriesProvider(output, vanillaRegistries, registryBuilder, Set.of(ImmersivePetroleum.MODID)));
	}
}
