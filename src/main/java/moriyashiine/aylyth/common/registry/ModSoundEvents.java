package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
	public static final SoundEvent BLOCK_YMPE_LOG_PICK_FRUIT = new SoundEvent(new Identifier(Aylyth.MOD_ID, "block.ympe_log.pick_fruit"));
	
	public static final SoundEvent ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE = new SoundEvent(new Identifier(Aylyth.MOD_ID, "entity.player.increase_ympe_infestation_stage"));
	public static final SoundEvent ENTITY_GENERIC_SHUCKED = new SoundEvent(new Identifier(Aylyth.MOD_ID, "entity.generic.shucked"));
	
	public static final SoundEvent ENTITY_AYLYTHIAN_AMBIENT = new SoundEvent(new Identifier(Aylyth.MOD_ID, "entity.aylythian.ambient"));
	public static final SoundEvent ENTITY_AYLYTHIAN_HURT = new SoundEvent(new Identifier(Aylyth.MOD_ID, "entity.aylythian.hurt"));
	public static final SoundEvent ENTITY_AYLYTHIAN_DEATH = new SoundEvent(new Identifier(Aylyth.MOD_ID, "entity.aylythian.death"));
	
	public static final SoundEvent ENTITY_ELDER_AYLYTHIAN_AMBIENT = new SoundEvent(new Identifier(Aylyth.MOD_ID, "entity.elder_aylythian.ambient"));
	public static final SoundEvent ENTITY_ELDER_AYLYTHIAN_HURT = new SoundEvent(new Identifier(Aylyth.MOD_ID, "entity.elder_aylythian.hurt"));
	public static final SoundEvent ENTITY_ELDER_AYLYTHIAN_DEATH = new SoundEvent(new Identifier(Aylyth.MOD_ID, "entity.elder_aylythian.death"));
	
	public static final SoundEvent AMBIENT_FOREST_ADDITIONS = new SoundEvent(new Identifier(Aylyth.MOD_ID, "ambient.forest.additions"));
	
	public static void init() {
		Registry.register(Registry.SOUND_EVENT, BLOCK_YMPE_LOG_PICK_FRUIT.getId(), BLOCK_YMPE_LOG_PICK_FRUIT);
		Registry.register(Registry.SOUND_EVENT, ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE.getId(), ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE);
		Registry.register(Registry.SOUND_EVENT, ENTITY_GENERIC_SHUCKED.getId(), ENTITY_GENERIC_SHUCKED);
		Registry.register(Registry.SOUND_EVENT, ENTITY_AYLYTHIAN_AMBIENT.getId(), ENTITY_AYLYTHIAN_AMBIENT);
		Registry.register(Registry.SOUND_EVENT, ENTITY_AYLYTHIAN_HURT.getId(), ENTITY_AYLYTHIAN_HURT);
		Registry.register(Registry.SOUND_EVENT, ENTITY_AYLYTHIAN_DEATH.getId(), ENTITY_AYLYTHIAN_DEATH);
		Registry.register(Registry.SOUND_EVENT, ENTITY_ELDER_AYLYTHIAN_AMBIENT.getId(), ENTITY_ELDER_AYLYTHIAN_AMBIENT);
		Registry.register(Registry.SOUND_EVENT, ENTITY_ELDER_AYLYTHIAN_HURT.getId(), ENTITY_ELDER_AYLYTHIAN_HURT);
		Registry.register(Registry.SOUND_EVENT, ENTITY_ELDER_AYLYTHIAN_DEATH.getId(), ENTITY_ELDER_AYLYTHIAN_DEATH);
		Registry.register(Registry.SOUND_EVENT, AMBIENT_FOREST_ADDITIONS.getId(), AMBIENT_FOREST_ADDITIONS);
	}
}
