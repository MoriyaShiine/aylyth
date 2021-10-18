package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.status_effect.MortechisStatusEffect;
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
	public static StatusEffect MORTECHIS_EFFECT = new MortechisStatusEffect();
	public static Potion MORTECHIS_POTION = new Potion("mortechis", new StatusEffectInstance(MORTECHIS_EFFECT, 3600), new StatusEffectInstance(StatusEffects.NAUSEA, 100, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));
	public static Potion LONG_MORTECHIS_POTION = new Potion("mortechis", new StatusEffectInstance(MORTECHIS_EFFECT, 9600), new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 400, 0));
	public static Potion STRONG_MORTECHIS_POTION = new Potion("mortechis", new StatusEffectInstance(MORTECHIS_EFFECT, 1800, 1), new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));

	public static void init() {
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Aylyth.MOD_ID, "mortechis"), MORTECHIS_EFFECT);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "mortechis"), MORTECHIS_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "long_mortechis"), LONG_MORTECHIS_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "strong_mortechis"), STRONG_MORTECHIS_POTION);
		BrewingRecipeRegistryAccessor.invokeRegisterPotionRecipe(Potions.AWKWARD, ModItems.NYSIAN_GRAPES, MORTECHIS_POTION);
		BrewingRecipeRegistryAccessor.invokeRegisterPotionRecipe(MORTECHIS_POTION, Items.FERMENTED_SPIDER_EYE, Potions.THICK);
		BrewingRecipeRegistryAccessor.invokeRegisterPotionRecipe(MORTECHIS_POTION, Items.REDSTONE, LONG_MORTECHIS_POTION);
		BrewingRecipeRegistryAccessor.invokeRegisterPotionRecipe(MORTECHIS_POTION, Items.GLOWSTONE_DUST, STRONG_MORTECHIS_POTION);
	}
}
