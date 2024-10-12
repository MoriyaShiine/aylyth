package moriyashiine.aylyth.common.recipe;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.recipe.types.ShuckingRecipe;
import moriyashiine.aylyth.common.recipe.types.SoulCampfireRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythRecipeTypes {

	RecipeSerializer<ShuckingRecipe> SHUCKING_SERIALIZER = registerSerializer("shucking", new ShuckingRecipe.Serializer());

	RecipeSerializer<SoulCampfireRecipe> SOULFIRE_SERIALIZER = registerSerializer("soul_ritual", new SoulCampfireRecipe.Serializer());
	RecipeType<SoulCampfireRecipe> SOULFIRE_TYPE = registerType("soul_ritual");

	private static <R extends Recipe<?>> RecipeSerializer<R> registerSerializer(String name, RecipeSerializer<R> serializer) {
		return Registry.register(Registries.RECIPE_SERIALIZER, Aylyth.id(name), serializer);
	}

	private static <R extends Recipe<?>> RecipeType<R> registerType(String name) {
		var id = Aylyth.id(name);
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
