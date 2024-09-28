package moriyashiine.aylyth.datagen.common.util.biome;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: add method variants w/ a Weight arg
 * @see net.minecraft.util.collection.Weight
 */
public class SpawnSettingsBuilder {

    SpawnSettings.Builder delegate;

    SpawnSettingsBuilder(@NotNull SpawnSettings.Builder builder) {
        this.delegate = builder;
    }

    public static SpawnSettingsBuilder builder() {
        return new SpawnSettingsBuilder(new SpawnSettings.Builder());
    }

    public static SpawnSettings none() {
        return SpawnSettings.INSTANCE;
    }

    public SpawnSettingsBuilder monster(@NotNull SpawnSettings.SpawnEntry spawnEntry) {
        delegate.spawn(SpawnGroup.MONSTER, spawnEntry);
        return this;
    }

    public SpawnSettingsBuilder monster(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return monster(new SpawnSettings.SpawnEntry(type, weight, minGroupSize, maxGroupSize));
    }

    public SpawnSettingsBuilder creature(@NotNull SpawnSettings.SpawnEntry spawnEntry) {
        delegate.spawn(SpawnGroup.CREATURE, spawnEntry);
        return this;
    }

    public SpawnSettingsBuilder creature(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return creature(new SpawnSettings.SpawnEntry(type, weight, minGroupSize, maxGroupSize));
    }

    public SpawnSettingsBuilder ambient(@NotNull SpawnSettings.SpawnEntry spawnEntry) {
        delegate.spawn(SpawnGroup.AMBIENT, spawnEntry);
        return this;
    }

    public SpawnSettingsBuilder ambient(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {

        return ambient(new SpawnSettings.SpawnEntry(type, weight, minGroupSize, maxGroupSize));
    }

    public SpawnSettingsBuilder axolotls(@NotNull SpawnSettings.SpawnEntry spawnEntry) {
        delegate.spawn(SpawnGroup.AXOLOTLS, spawnEntry);
        return this;
    }

    public SpawnSettingsBuilder axolotls(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return axolotls(new SpawnSettings.SpawnEntry(type, weight, minGroupSize, maxGroupSize));
    }

    public SpawnSettingsBuilder undergroundWaterCreature(@NotNull SpawnSettings.SpawnEntry spawnEntry) {
        delegate.spawn(SpawnGroup.UNDERGROUND_WATER_CREATURE, spawnEntry);
        return this;
    }

    public SpawnSettingsBuilder undergroundWaterCreature(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return undergroundWaterCreature(new SpawnSettings.SpawnEntry(type, weight, minGroupSize, maxGroupSize));
    }

    public SpawnSettingsBuilder waterCreature(@NotNull SpawnSettings.SpawnEntry spawnEntry) {
        delegate.spawn(SpawnGroup.WATER_CREATURE, spawnEntry);
        return this;
    }

    public SpawnSettingsBuilder waterCreature(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return waterCreature(new SpawnSettings.SpawnEntry(type, weight, minGroupSize, maxGroupSize));
    }

    public SpawnSettingsBuilder waterAmbient(@NotNull SpawnSettings.SpawnEntry spawnEntry) {
        delegate.spawn(SpawnGroup.WATER_AMBIENT, spawnEntry);
        return this;
    }

    public SpawnSettingsBuilder waterAmbient(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return waterAmbient(new SpawnSettings.SpawnEntry(type, weight, minGroupSize, maxGroupSize));
    }

    public SpawnSettingsBuilder misc(@NotNull SpawnSettings.SpawnEntry spawnEntry) {
        delegate.spawn(SpawnGroup.MISC, spawnEntry);
        return this;
    }

    public SpawnSettingsBuilder misc(@NotNull EntityType<?> type, int weight, int minGroupSize, int maxGroupSize) {
        return misc(new SpawnSettings.SpawnEntry(type, weight, minGroupSize, maxGroupSize));
    }

    public SpawnSettingsBuilder spawnCost(@NotNull EntityType<?> entityType, double mass, double gravityLimit) {
        delegate.spawnCost(entityType, mass, gravityLimit);
        return this;
    }

    public SpawnSettingsBuilder spawnChance(float probability) {
        delegate.creatureSpawnProbability(probability);
        return this;
    }

    public SpawnSettings build() {
        return delegate.build();
    }
}
