package moriyashiine.aylyth.datagen;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AylythNoiseTypes {

    public static void init() {}

    public static final NoiseRegistryPair OFFSET = register("offset", -3, 1.0, 1.0, 1.0, 0.0);
    public static final NoiseRegistryPair RIDGE = register("ridge", -7, 1.0, 2.0, 1.0, 0.0, 0.0, 0.0);
    public static final NoiseRegistryPair JAGGED = register("jagged", -16, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
    public static final NoiseRegistryPair CONTINENTS = register("continents", -9, 1.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0);
    public static final NoiseRegistryPair TEMPERATURE = register("temperature", -10, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0);
    public static final NoiseRegistryPair VEGETATION = register("vegetation", -8, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0);
    public static final NoiseRegistryPair EROSION = register("erosion", -9, 1.0, 1.0, 0.0, 0.0, 1.0, 1.0);
    public static final NoiseRegistryPair BASE_LAYER = register("base_layer", -8, 1.0, 1.0, 1.0, 1.0);
    public static final NoiseRegistryPair CAVE_LAYER = register("cave_layer", -8, 1.0);
    public static final NoiseRegistryPair CAVE_CHEESE = register("cave_cheese", -8, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 0.0, 2.0, 0.0);
    public static final NoiseRegistryPair SPAGHETTI_2D = register("spaghetti_2d", -7, 1.0);
    public static final NoiseRegistryPair SPAGHETTI_2D_MODULATOR = register("spaghetti_2d_modulator", -11, 1.0);
    public static final NoiseRegistryPair SPAGHETTI_2D_ROUGHNESS = register("spaghetti_2d_roughness", -5, 1.0);
    public static final NoiseRegistryPair SPAGHETTI_2D_THICKNESS = register("spaghetti_2d_thickness", -11, 1.0);
    public static final NoiseRegistryPair SPAGHETTI_2D_ELEVATION = register("spaghetti_2d_elevation", -8, 1.0);
    public static final NoiseRegistryPair SPAGHETTI_3D_1 = register("spaghetti_3d_1", -7, 1.0);
    public static final NoiseRegistryPair SPAGHETTI_3D_2 = register("spaghetti_3d_2", -7, 1.0);
    public static final NoiseRegistryPair SPAGHETTI_3D_RARITY = register("spaghetti_3d_rareness", - 11, 1.0);
    public static final NoiseRegistryPair SPAGHETTI_3D_THICKNESS = register("spaghetti_3d_thickness", -11, 1.0);
    public static final NoiseRegistryPair NOODLE = register("noodle", -8, 1.0);
    public static final NoiseRegistryPair NOODLE_THICKNESS = register("noodle_thickness", -8, 1.0);
    public static final NoiseRegistryPair NOODLE_RIDGE_A = register("noodle_ridge_a", -7, 1.0);
    public static final NoiseRegistryPair NOODLE_RIDGE_B = register("noodle_ridge_b", -7, 1.0);
    public static final NoiseRegistryPair PILLAR = register("pillar", -7, 1.0, 1.0);
    public static final NoiseRegistryPair PILLAR_RARENESS = register("pillar_rareness", -8, 1.0);
    public static final NoiseRegistryPair PILLAR_THICKNESS = register("pillar_thickness", -8, 1.0);
    public static final NoiseRegistryPair CAVE_ENTRANCES = register("cave_entrances", -7, 0.4, 0.5, 1.0);

    private static NoiseRegistryPair register(String id, int firstOctave, double... amplitudes) {
        return NoiseRegistryPair.createAndRegister(id, firstOctave, amplitudes);
    }

    static class NoiseRegistryPair implements RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> {
        public final RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> registryKey;
        public final RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> registryEntry;

        private NoiseRegistryPair(RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> registryKey, RegistryEntry<DoublePerlinNoiseSampler.NoiseParameters> registryEntry) {
            this.registryKey = registryKey;
            this.registryEntry = registryEntry;
        }

        static NoiseRegistryPair createAndRegister(String id, int firstOctave, double... amplitudes) {
            return createAndRegister(new Identifier(Aylyth.MOD_ID, id), firstOctave, amplitudes);
        }

        public static NoiseRegistryPair createAndRegister(Identifier identifier, int firstOctave, double... amplitudes) {
            var registryKey = RegistryKey.of(Registry.NOISE_KEY, identifier);
            var noiseParams = new DoublePerlinNoiseSampler.NoiseParameters(firstOctave, DoubleList.of(amplitudes));
            var registryEntry = BuiltinRegistries.add(BuiltinRegistries.NOISE_PARAMETERS, registryKey, noiseParams);
            return new NoiseRegistryPair(registryKey, registryEntry);
        }

        @Override
        public DoublePerlinNoiseSampler.NoiseParameters value() {
            return registryEntry.value();
        }

        @Override
        public boolean hasKeyAndValue() {
            return registryEntry.hasKeyAndValue();
        }

        @Override
        public boolean matchesId(Identifier id) {
            return registryEntry.matchesId(id);
        }

        @Override
        public boolean matchesKey(RegistryKey key) {
            return registryEntry.matchesKey(key);
        }

        @Override
        public boolean isIn(TagKey tag) {
            return registryEntry.isIn(tag);
        }

        @Override
        public Stream<TagKey<DoublePerlinNoiseSampler.NoiseParameters>> streamTags() {
            return registryEntry.streamTags();
        }

        @Override
        public Either<RegistryKey<DoublePerlinNoiseSampler.NoiseParameters>, DoublePerlinNoiseSampler.NoiseParameters> getKeyOrValue() {
            return registryEntry.getKeyOrValue();
        }

        @Override
        public Optional<RegistryKey<DoublePerlinNoiseSampler.NoiseParameters>> getKey() {
            return registryEntry.getKey();
        }

        @Override
        public Type getType() {
            return registryEntry.getType();
        }

        @Override
        public boolean matchesRegistry(Registry registry) {
            return registryEntry.matchesRegistry(registry);
        }

        @Override
        public boolean matches(Predicate predicate) {
            return registryEntry.matches(predicate);
        }
    }
}
