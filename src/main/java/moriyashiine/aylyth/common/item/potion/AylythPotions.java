package moriyashiine.aylyth.common.item.potion;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public interface AylythPotions {

	RegistryEntry<Potion> MORTECHIS = registerBase(
			"mortechis",
			new StatusEffectInstance(AylythStatusEffects.MORTECHIS, 3600),
			new StatusEffectInstance(StatusEffects.NAUSEA, 100, 0),
			new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0)
	);
	RegistryEntry<Potion> LONG_MORTECHIS = registerUpgraded(
			"long_mortechis",
			"mortechis",
			new StatusEffectInstance(AylythStatusEffects.MORTECHIS, 9600),
			new StatusEffectInstance(StatusEffects.NAUSEA, 400, 0),
			new StatusEffectInstance(StatusEffects.REGENERATION, 400, 0)
	);
	RegistryEntry<Potion> STRONG_MORTECHIS = registerUpgraded(
			"strong_mortechis",
			"mortechis",
			new StatusEffectInstance(AylythStatusEffects.MORTECHIS, 1800, 1),
			new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1),
			new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0)
	);

	RegistryEntry<Potion> CIMMERIAN = registerBase("cimmerian", new StatusEffectInstance(AylythStatusEffects.CIMMERIAN, 3600));
	RegistryEntry<Potion> LONG_CIMMERIAN = registerUpgraded("long_cimmerian", "cimmerian", new StatusEffectInstance(AylythStatusEffects.CIMMERIAN, 9600));
	RegistryEntry<Potion> STRONG_CIMMERIAN = registerUpgraded("strong_cimmerian", "cimmerian", new StatusEffectInstance(AylythStatusEffects.CIMMERIAN, 1800, 1));

	RegistryEntry<Potion> WYRDED = registerBase("wyrded", new StatusEffectInstance(AylythStatusEffects.WYRDED, 3600));
	RegistryEntry<Potion> LONG_WYRDED = registerUpgraded("long_wyrded", "wyrded", new StatusEffectInstance(AylythStatusEffects.WYRDED, 9600));
	RegistryEntry<Potion> STRONG_WYRDED = registerUpgraded("strong_wyrded", "wyrded", new StatusEffectInstance(AylythStatusEffects.WYRDED, 1800, 1));

	RegistryEntry<Potion> BLIGHT = registerBase("blight", new StatusEffectInstance(AylythStatusEffects.BLIGHT, 3600));
	RegistryEntry<Potion> LONG_BLIGHT = registerUpgraded("long_blight", "blight", new StatusEffectInstance(AylythStatusEffects.BLIGHT, 9600));
	RegistryEntry<Potion> STRONG_BLIGHT = registerUpgraded("strong_blight", "blight", new StatusEffectInstance(AylythStatusEffects.BLIGHT, 1800, 1));

	private static RegistryEntry<Potion> register(String name, Potion potion) {
		return Registry.registerReference(Registries.POTION, Aylyth.id(name), potion);
	}

	private static RegistryEntry<Potion> registerBase(String name, StatusEffectInstance... effects) {
		return register(name, new Potion(name, effects));
	}

	private static RegistryEntry<Potion> registerUpgraded(String registryName, String potionName, StatusEffectInstance... effects) {
		return register(registryName, new Potion(potionName, effects));
	}

	// Load static initializer
	static void register() {}
}
