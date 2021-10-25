package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
	public static final SoundEvent BLOCK_YMPE_LOG_PICK_FRUIT = new SoundEvent(new Identifier(Aylyth.MOD_ID, "block.ympe_log.pick_fruit"));
	
	public static final SoundEvent ENTITY_GENERIC_SHUCKED = new SoundEvent(new Identifier(Aylyth.MOD_ID, "entity.generic.shucked"));
	public static final SoundEvent AMBIENT_FOREST_ADDITIONS = new SoundEvent(new Identifier(Aylyth.MOD_ID, "ambient.forest.additions"));
	
	public static void init() {
		Registry.register(Registry.SOUND_EVENT, BLOCK_YMPE_LOG_PICK_FRUIT.getId(), BLOCK_YMPE_LOG_PICK_FRUIT);
		Registry.register(Registry.SOUND_EVENT, ENTITY_GENERIC_SHUCKED.getId(), ENTITY_GENERIC_SHUCKED);
	}
}
