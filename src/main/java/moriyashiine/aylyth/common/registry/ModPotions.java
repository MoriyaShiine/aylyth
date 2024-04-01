package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModPotions {
	public static final Potion MORTECHIS_POTION = register("mortechis", new Potion(new StatusEffectInstance(ModStatusEffects.MORTECHIS, 3600), new StatusEffectInstance(StatusEffects.NAUSEA, 100, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0)));
	public static final Potion LONG_MORTECHIS_POTION = register("long_mortechis", new Potion("mortechis", new StatusEffectInstance(ModStatusEffects.MORTECHIS, 9600), new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 400, 0)));
	public static final Potion STRONG_MORTECHIS_POTION = register("strong_mortechis", new Potion("mortechis", new StatusEffectInstance(ModStatusEffects.MORTECHIS, 1800, 1), new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0)));

	public static final Potion CIMMERIAN_POTION = register("cimmerian", new Potion(new StatusEffectInstance(ModStatusEffects.CIMMERIAN, 3600)));
	public static final Potion LONG_CIMMERIAN_POTION = register("long_cimmerian", new Potion("cimmerian", new StatusEffectInstance(ModStatusEffects.CIMMERIAN, 9600)));
	public static final Potion STRONG_CIMMERIAN_POTION = register("strong_cimmerian", new Potion("cimmerian", new StatusEffectInstance(ModStatusEffects.CIMMERIAN, 1800, 1)));

	public static final Potion WYRDED_POTION = register("wyrded", new Potion(new StatusEffectInstance(ModStatusEffects.WYRDED, 3600)));
	public static final Potion LONG_WYRDED_POTION = register("long_wyrded", new Potion("wyrded", new StatusEffectInstance(ModStatusEffects.WYRDED, 9600)));
	public static final Potion STRONG_WYRDED_POTION = register("strong_wyrded", new Potion("wyrded", new StatusEffectInstance(ModStatusEffects.WYRDED, 1800, 1)));

	public static final Potion BLIGHT_POTION = register("blight", new Potion(new StatusEffectInstance(ModStatusEffects.BLIGHT, 3600)));
	public static final Potion LONG_BLIGHT_POTION = register("long_blight", new Potion("blight", new StatusEffectInstance(ModStatusEffects.BLIGHT, 9600)));
	public static final Potion STRONG_BLIGHT_POTION = register("strong_blight", new Potion("blight", new StatusEffectInstance(ModStatusEffects.BLIGHT, 1800, 1)));

	private static Potion register(String id, Potion potion) {
		Registry.register(Registries.POTION, AylythUtil.id(id), potion);
		return potion;
	}

	public static void init() {
		FabricBrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Ingredient.ofItems(ModItems.NYSIAN_GRAPES), MORTECHIS_POTION);
		FabricBrewingRecipeRegistry.registerPotionRecipe(MORTECHIS_POTION, Ingredient.ofItems(Items.FERMENTED_SPIDER_EYE), Potions.THICK);
		FabricBrewingRecipeRegistry.registerPotionRecipe(MORTECHIS_POTION, Ingredient.ofItems(Items.REDSTONE), LONG_MORTECHIS_POTION);
		FabricBrewingRecipeRegistry.registerPotionRecipe(MORTECHIS_POTION, Ingredient.ofItems(Items.GLOWSTONE_DUST), STRONG_MORTECHIS_POTION);
	}
}
