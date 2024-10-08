package moriyashiine.aylyth.datagen.client.model;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectFunction;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PerspectiveModelData {
    public static final Codec<PerspectiveModelFile> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Identifier.CODEC.fieldOf("fallback").forGetter(unbakedPerspectiveModel -> unbakedPerspectiveModel.fallback),
                    Codec.unboundedMap(ModelDisplayType.CODEC, Identifier.CODEC).fieldOf("perspectives").forGetter(unbakedPerspectiveModel -> unbakedPerspectiveModel.perspectiveModels)
            ).apply(instance, PerspectiveModelFile::new)
    );

    private final ModelKey fallback;
    private final Map<ModelDisplayType, ModelKey> perspectiveModels;

    public PerspectiveModelData(ModelKey fallback, Map<ModelDisplayType, ModelKey> perspectiveModels) {
        this.fallback = fallback;
        this.perspectiveModels = perspectiveModels;
    }

    public Resolver resolver() {
        return new Resolver();
    }

    public void upload(Item item, Object2ObjectFunction<ModelKey, Identifier> keyToId, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
        upload(ModelIds.getItemModelId(item.asItem()), keyToId, modelCollector);
    }

    public void upload(Identifier id, Object2ObjectFunction<ModelKey, Identifier> keyToId, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
        modelCollector.accept(id.withPrefixedPath("perspective/"), () -> this.toData(keyToId, JsonOps.INSTANCE).getOrThrow(false, Aylyth.LOGGER::error));
    }

    public <T> DataResult<T> toData(Object2ObjectFunction<ModelKey, Identifier> keyToId, DynamicOps<T> dynamicOps) {
        // TODO: better errors for null map values
        var modelMap = perspectiveModels.entrySet().stream().map(entry -> Map.entry(entry.getKey(), keyToId.get(entry.getValue()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        PerspectiveModelFile model = new PerspectiveModelFile(keyToId.get(fallback), modelMap);
        return CODEC.encodeStart(dynamicOps, model);
    }

    public static Builder builder(ModelKey fallback) {
        return new Builder(fallback);
    }

    public record PerspectiveModelFile(Identifier fallback, Map<ModelDisplayType, Identifier> perspectiveModels) {}

    public class Resolver {
        private final Object2ObjectMap<ModelKey, Identifier> keyToId;

        Resolver() {
            this.keyToId = new Object2ObjectOpenHashMap<>();
        }

        public Resolver with(ModelKey key, Identifier id) {
            keyToId.put(key, id);
            return this;
        }

        public void upload(Identifier id, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
            PerspectiveModelData.this.upload(id, keyToId, modelCollector);
        }
    }

    public static class Builder {
        private final ModelKey fallback;
        private final Map<ModelDisplayType, ModelKey> perspectiveModels;

        Builder(ModelKey fallback) {
            this.fallback = fallback;
            this.perspectiveModels = new EnumMap<>(ModelDisplayType.class);
        }

        public Builder withModel(ModelDisplayType transformationMode, ModelKey identifier) {
            this.perspectiveModels.put(transformationMode, identifier);
            return this;
        }

        public PerspectiveModelData build() {
            return new PerspectiveModelData(fallback, perspectiveModels);
        }
    }

    public record ModelKey(String name) {}
}
