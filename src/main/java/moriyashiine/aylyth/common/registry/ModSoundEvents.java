package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModSoundEvents {
	private static final Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();

	public static final SoundEvent BLOCK_YMPE_LOG_PICK_FRUIT = register("block.ympe_log.pick_fruit");
	public static final SoundEvent BLOCK_STREWN_LEAVES_STEP = register("block.strewn_leaves.step");
	public static final SoundEvent BLOCK_STREWN_LEAVES_PILE_DESTROY = register("block.strewn_leaves.pile_destroy");
	public static final SoundEvent BLOCK_STREWN_LEAVES_PILE_STEP = register( "block.strewn_leaves.pile_step");
	public static final SoundEvent BLOCK_STICK_BREAK = register( "block.stick_break");

	public static final SoundEvent ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE = register("entity.player.increase_ympe_infestation_stage");
	public static final SoundEvent ENTITY_GENERIC_SHUCKED = register( "entity.generic.shucked");
	
	public static final SoundEvent ENTITY_AYLYTHIAN_AMBIENT = register( "entity.aylythian.ambient");
	public static final SoundEvent ENTITY_AYLYTHIAN_HURT = register("entity.aylythian.hurt");
	public static final SoundEvent ENTITY_AYLYTHIAN_DEATH = register( "entity.aylythian.death");
	
	public static final SoundEvent ENTITY_ELDER_AYLYTHIAN_AMBIENT = register( "entity.elder_aylythian.ambient");
	public static final SoundEvent ENTITY_ELDER_AYLYTHIAN_HURT = register( "entity.elder_aylythian.hurt");
	public static final SoundEvent ENTITY_ELDER_AYLYTHIAN_DEATH = register( "entity.elder_aylythian.death");
	
	public static final SoundEvent AMBIENT_FOREST_ADDITIONS = register("ambient.forest.additions");

	public static final SoundEvent ENTITY_SOULMOULD_AMBIENT = register( "entity.soulmould.ambient");
	public static final SoundEvent ENTITY_SOULMOULD_ATTACK = register("entity.soulmould.attack");
	public static final SoundEvent ENTITY_SOULMOULD_HURT = register("entity.soulmould.hurt");
	public static final SoundEvent ENTITY_SOULMOULD_DEATH = register("entity.soulmould.death");

	public static final SoundEvent ENTITY_SCION_AMBIENT = register("entity.scion.ambient");
	public static final SoundEvent ENTITY_SCION_HURT = register("entity.scion.hurt");
	public static final SoundEvent ENTITY_SCION_DEATH = register( "entity.scion.death");

	public static final SoundEvent ENTITY_WREATHED_HIND_AMBIENT = register("entity.wreathed_hind.ambient");
	public static final SoundEvent ENTITY_WREATHED_HIND_HURT = register( "entity.wreathed_hind.hurt");
	public static final SoundEvent ENTITY_WREATHED_HIND_DEATH = register("entity.wreathed_hind.death");

	public static final SoundEvent ENTITY_FAUNAYLYTHIAN_AMBIENT = register("entity.faunaylythian.ambient");
	public static final SoundEvent ENTITY_FAUNAYLYTHIAN_HURT = register( "entity.faunaylythian.hurt");
	public static final SoundEvent ENTITY_FAUNAYLYTHIAN_DEATH = register("entity.faunaylythian.death");

	public static final BlockSoundGroup STREWN_LEAVES = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP);
	public static final BlockSoundGroup LEAF_PILES = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_PILE_DESTROY, BLOCK_STREWN_LEAVES_PILE_STEP, BLOCK_STREWN_LEAVES_PILE_DESTROY, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_PILE_DESTROY);
	public static final BlockSoundGroup STREWN_LEAVES_STICK = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_STEP, BLOCK_STICK_BREAK, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_STEP);
	public static final BlockSoundGroup LEAF_PILES_STICK = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_PILE_DESTROY, BLOCK_STICK_BREAK, BLOCK_STREWN_LEAVES_PILE_DESTROY, BLOCK_STREWN_LEAVES_STEP, BLOCK_STREWN_LEAVES_PILE_DESTROY);


	private static SoundEvent register(String name) {
		Identifier id = AylythUtil.id(name);
		SoundEvent soundEvent = new SoundEvent(id);
		SOUND_EVENTS.put(soundEvent, id);
		return soundEvent;
	}

	public static void init() {
		SOUND_EVENTS.keySet().forEach(soundEvent -> Registry.register(Registry.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent));
	}
}
