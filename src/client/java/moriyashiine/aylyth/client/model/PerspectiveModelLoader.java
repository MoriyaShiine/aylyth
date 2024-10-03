package moriyashiine.aylyth.client.model;

import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.client.model.item.PerspectiveModel;
import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PerspectiveModelLoader {
    private PerspectiveModelLoader() {}

    public static final Codec<UnbakedPerspectiveModel> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Identifier.CODEC.fieldOf("fallback").forGetter(unbakedPerspectiveModel -> unbakedPerspectiveModel.fallbackModelId),
                    Codec.unboundedMap(ModelTransformationMode.CODEC, Identifier.CODEC).fieldOf("perspectives").forGetter(unbakedPerspectiveModel -> unbakedPerspectiveModel.models)
            ).apply(instance, UnbakedPerspectiveModel::new)
    );
    public static final ResourceFinder PERSPECTIVE_MODELS = ResourceFinder.json("models/perspective");

    public static CompletableFuture<Map<Identifier, UnbakedPerspectiveModel>> load(ResourceManager manager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> PERSPECTIVE_MODELS.findResources(manager).entrySet(), executor)
                .thenCompose(entries -> {
                    List<CompletableFuture<Pair<Identifier, UnbakedPerspectiveModel>>> pairs = new ObjectArrayList<>();
                    for (Map.Entry<Identifier, Resource> entry : entries) {
                        pairs.add(CompletableFuture.supplyAsync(() -> {
                            try (BufferedReader reader = entry.getValue().getReader()) {
                                JsonObject jsonObject = JsonHelper.deserialize(reader, true);
                                UnbakedPerspectiveModel model = CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, Aylyth.LOGGER::error);
                                return Pair.of(PERSPECTIVE_MODELS.toResourceId(entry.getKey()), model);
                            } catch (IOException | RuntimeException e) {
                                Aylyth.LOGGER.error("Could not load model", e);
                            }
                            return null;
                        }));
                    }
                    return Util.combineSafe(pairs).thenApply(pairs1 -> pairs1.stream().filter(Objects::nonNull).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));
                });
    }
    public static void apply(Map<Identifier, UnbakedPerspectiveModel> data, ModelLoadingPlugin.Context pluginContext) {
        pluginContext.resolveModel().register(context -> data.get(context.id()));
    }
    public static class UnbakedPerspectiveModel implements UnbakedModel {
        private final Identifier fallbackModelId;
        private final Map<ModelTransformationMode, Identifier> models;
        public UnbakedPerspectiveModel(Identifier fallbackModel, Map<ModelTransformationMode, Identifier> models) {
            this.fallbackModelId = fallbackModel;
            this.models = models;
        }
        @Override
        public Collection<Identifier> getModelDependencies() {
            List<Identifier> unbakedModels = new ObjectArrayList<>(models.values());
            unbakedModels.add(fallbackModelId);
            return unbakedModels;
        }
        @Override
        public void setParents(Function<Identifier, UnbakedModel> modelLoader) {
            modelLoader.apply(fallbackModelId).setParents(modelLoader);
            models.values().forEach(identifier -> modelLoader.apply(identifier).setParents(modelLoader));
        }
        @Nullable
        @Override
        public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
            Map<ModelTransformationMode, BakedModel> bakedBuilder = new EnumMap<>(ModelTransformationMode.class);
            BakedModel fallback = baker.bake(fallbackModelId, rotationContainer);
            if (fallback == null) {
                return null;
            }
            for (ModelTransformationMode mode : ModelTransformationMode.values()) {
                Identifier id = models.get(mode);
                BakedModel model;
                if (id != null) {
                    model = baker.bake(id, rotationContainer);
                    if (model == null) {
                        return null;
                    }
                } else {
                    model = fallback;
                }
                bakedBuilder.put(mode, model);
            }
            return new PerspectiveModel(Collections.unmodifiableMap(bakedBuilder));
        }
    }
}