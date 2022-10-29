package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.statuseffect.CimmerianStatusEffect;
import moriyashiine.aylyth.common.statuseffect.MortechisStatusEffect;
import moriyashiine.aylyth.common.statuseffect.WyrdedStatusEffect;
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
	public static final StatusEffect MORTECHIS_EFFECT = new MortechisStatusEffect();
	public static final Potion MORTECHIS_POTION = new Potion("mortechis", new StatusEffectInstance(MORTECHIS_EFFECT, 3600), new StatusEffectInstance(StatusEffects.NAUSEA, 100, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));
	public static final Potion LONG_MORTECHIS_POTION = new Potion("mortechis", new StatusEffectInstance(MORTECHIS_EFFECT, 9600), new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0), new StatusEffectInstance(StatusEffects.REGENERATION, 400, 0));
	public static final Potion STRONG_MORTECHIS_POTION = new Potion("mortechis", new StatusEffectInstance(MORTECHIS_EFFECT, 1800, 1), new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1), new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));

	public static final StatusEffect CIMMERIAN_EFFECT = new CimmerianStatusEffect();
	public static final Potion CIMMERIAN_POTION = new Potion("cimmerian", new StatusEffectInstance(CIMMERIAN_EFFECT, 3600));
	public static final Potion LONG_CIMMERIAN_POTION = new Potion("cimmerian", new StatusEffectInstance(CIMMERIAN_EFFECT, 9600));
	public static final Potion STRONG_CIMMERIAN_POTION = new Potion("cimmerian", new StatusEffectInstance(CIMMERIAN_EFFECT, 1800, 1));

	public static final StatusEffect WYRDED_EFFECT = new WyrdedStatusEffect();
	public static final Potion WYRDED_POTION = new Potion("wyrded", new StatusEffectInstance(WYRDED_EFFECT, 3600));
	public static final Potion LONG_WYRDED_POTION = new Potion("wyrded", new StatusEffectInstance(WYRDED_EFFECT, 9600));
	public static final Potion STRONG_WYRDED_POTION = new Potion("wyrded", new StatusEffectInstance(WYRDED_EFFECT, 1800, 1));
	
	public static void init() {
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Aylyth.MOD_ID, "mortechis"), MORTECHIS_EFFECT);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "mortechis"), MORTECHIS_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "long_mortechis"), LONG_MORTECHIS_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "strong_mortechis"), STRONG_MORTECHIS_POTION);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Aylyth.MOD_ID, "cimmerian"), CIMMERIAN_EFFECT);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "cimmerian"), CIMMERIAN_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "long_cimmerian"), LONG_CIMMERIAN_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "strong_cimmerian"), STRONG_CIMMERIAN_POTION);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Aylyth.MOD_ID, "wyrded"), WYRDED_EFFECT);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "wyrded"), WYRDED_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "long_wyrded"), LONG_WYRDED_POTION);
		Registry.register(Registry.POTION, new Identifier(Aylyth.MOD_ID, "strong_wyrded"), STRONG_WYRDED_POTION);
		BrewingRecipeRegistryAccessor.callRegisterPotionRecipe(Potions.AWKWARD, ModItems.NYSIAN_GRAPES, MORTECHIS_POTION);
		BrewingRecipeRegistryAccessor.callRegisterPotionRecipe(MORTECHIS_POTION, Items.FERMENTED_SPIDER_EYE, Potions.THICK);
		BrewingRecipeRegistryAccessor.callRegisterPotionRecipe(MORTECHIS_POTION, Items.REDSTONE, LONG_MORTECHIS_POTION);
		BrewingRecipeRegistryAccessor.callRegisterPotionRecipe(MORTECHIS_POTION, Items.GLOWSTONE_DUST, STRONG_MORTECHIS_POTION);
	}
}
