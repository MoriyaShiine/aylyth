package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.recipe.ShuckingRecipe;
import moriyashiine.aylyth.common.recipe.SoulCampfireRecipe;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythRecipeTypes {

	RecipeSerializer<ShuckingRecipe> SHUCKING_SERIALIZER = registerSerializer("shucking", new ShuckingRecipe.Serializer());

	RecipeSerializer<YmpeDaggerDropRecipe> YMPE_DAGGER_DROP_SERIALIZER = registerSerializer("ympe_dagger_drop", new YmpeDaggerDropRecipe.Serializer());
	RecipeType<YmpeDaggerDropRecipe> YMPE_DAGGER_DROP_TYPE = registerType("ympe_dagger_drop");

	RecipeSerializer<SoulCampfireRecipe> SOULFIRE_SERIALIZER = registerSerializer("soul_ritual", new SoulCampfireRecipe.Serializer());
	RecipeType<SoulCampfireRecipe> SOULFIRE_TYPE = registerType("soul_ritual");

	private static <R extends Recipe<?>> RecipeSerializer<R> registerSerializer(String name, RecipeSerializer<R> serializer) {
		return Registry.register(Registries.RECIPE_SERIALIZER, AylythUtil.id(name), serializer);
	}

	private static <R extends Recipe<?>> RecipeType<R> registerType(String name) {
		var id = AylythUtil.id(name);
		return Registry.register(Registries.RECIPE_TYPE, id, new RecipeType<>() {
			@Override
			public String toString() {
				return id.toString();
			}
		});
	}


	// Load static initializer
	static void register() {}
}
