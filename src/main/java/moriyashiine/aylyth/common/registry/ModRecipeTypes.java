package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.recipe.ShuckingRecipe;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipeTypes {
	public static final RecipeSerializer<ShuckingRecipe> SHUCKING_RECIPE_SERIALIZER = new SpecialRecipeSerializer<>(ShuckingRecipe::new);
	
	public static final RecipeSerializer<YmpeDaggerDropRecipe> YMPE_DAGGER_DROP_RECIPE_SERIALIZER = new YmpeDaggerDropRecipe.Serializer();
	public static final RecipeType<YmpeDaggerDropRecipe> YMPE_DAGGER_DROP_RECIPE_TYPE = new RecipeType<>() {
		@Override
		public String toString() {
			return Aylyth.MOD_ID + ":ympe_dagger_drop";
		}
	};
	
	public static void init() {
		Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Aylyth.MOD_ID, "shucking"), SHUCKING_RECIPE_SERIALIZER);
		Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Aylyth.MOD_ID, "ympe_dagger_drop"), YMPE_DAGGER_DROP_RECIPE_SERIALIZER);
		Registry.register(Registry.RECIPE_TYPE, new Identifier(Aylyth.MOD_ID, "ympe_dagger_drop"), YMPE_DAGGER_DROP_RECIPE_TYPE);
	}
}
