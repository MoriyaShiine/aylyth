package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.status_effect.MortechusStatusEffect;
import moriyashiine.aylyth.mixin.BrewingRecipeRegistryAccessor;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModPotions {
	public static StatusEffect MORTECHUS_EFFECT = new MortechusStatusEffect();
	public static Potion MORTECHUS_POTION = new Potion("mortechus", new StatusEffectInstance(MORTECHUS_EFFECT, 3600), new StatusEffectInstance(StatusEffects.NAUSEA, 100, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));
	public static Potion LONG_MORTECHUS_POTION = new Potion("mortechus", new StatusEffectInstance(MORTECHUS_EFFECT, 9600), new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 400, 0));
	public static Potion STRONG_MORTECHUS_POTION = new Potion("mortechus", new StatusEffectInstance(MORTECHUS_EFFECT, 1800, 1), new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));

	public static void init() {
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Aylyth.MOD_ID, "mortechus"), MORTECHUS_EFFECT);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "mortechus"), MORTECHUS_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "long_mortechus"), LONG_MORTECHUS_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "strong_mortechus"), STRONG_MORTECHUS_POTION);
		BrewingRecipeRegistryAccessor.invokeRegisterPotionRecipe(Potions.AWKWARD, ModItems.NYSIAN_GRAPES, MORTECHUS_POTION);
		BrewingRecipeRegistryAccessor.invokeRegisterPotionRecipe(MORTECHUS_POTION, Items.FERMENTED_SPIDER_EYE, Potions.THICK);
		BrewingRecipeRegistryAccessor.invokeRegisterPotionRecipe(MORTECHUS_POTION, Items.REDSTONE, LONG_MORTECHUS_POTION);
		BrewingRecipeRegistryAccessor.invokeRegisterPotionRecipe(MORTECHUS_POTION, Items.GLOWSTONE_DUST, STRONG_MORTECHUS_POTION);
	}
}
