package moriyashiine.aylyth.common.registry;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSources {
	public static final DamageSource YMPE = new YmpeDamageSource("ympe");
	
	private static class YmpeDamageSource extends DamageSource {
		public YmpeDamageSource(String name) {
			super(name);
			setBypassesArmor();
			setOutOfWorld();
		}
	}
}
