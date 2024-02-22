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
import net.minecraft.util.Identifier;

public class ModRecipeTypes {
	public static final RecipeSerializer<ShuckingRecipe> SHUCKING_RECIPE_SERIALIZER = registerSerializer("shucking", new ShuckingRecipe.Serializer());
	
	public static final RecipeSerializer<YmpeDaggerDropRecipe> YMPE_DAGGER_DROP_RECIPE_SERIALIZER = registerSerializer("ympe_dagger_drop", new YmpeDaggerDropRecipe.Serializer());
	public static final RecipeType<YmpeDaggerDropRecipe> YMPE_DAGGER_DROP_RECIPE_TYPE = registerType("ympe_dagger_drop");

	public static final RecipeSerializer<SoulCampfireRecipe> SOULFIRE_RECIPE_SERIALIZER = registerSerializer("soul_ritual", new SoulCampfireRecipe.Serializer());
	public static final RecipeType<SoulCampfireRecipe> SOULFIRE_RECIPE_TYPE = registerType("soul_ritual");

	private static <T extends Recipe<?>> RecipeType<T> registerType(String id) {
		Identifier identifier = AylythUtil.id(id);
		return Registry.register(Registries.RECIPE_TYPE, identifier, new RecipeType<>() {
			@Override
			public String toString() {
				return identifier.toString();
			}
		});
	}

	private static <T extends Recipe<?>> RecipeSerializer<T> registerSerializer(String id, RecipeSerializer<T> serializer) {
		Registry.register(Registries.RECIPE_SERIALIZER, AylythUtil.id(id), serializer);
		return serializer;
	}
	
	public static void init() {}
}
