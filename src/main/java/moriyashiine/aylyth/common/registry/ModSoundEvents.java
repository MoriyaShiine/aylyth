package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModSoundEvents {
	public static final RegistryEntry<SoundEvent> BLOCK_YMPE_LOG_PICK_FRUIT = register("block.ympe_log.pick_fruit");
	public static final RegistryEntry<SoundEvent> BLOCK_STREWN_LEAVES_STEP = register("block.strewn_leaves.step");
	public static final RegistryEntry<SoundEvent> BLOCK_STREWN_LEAVES_PILE_DESTROY = register("block.strewn_leaves.pile_destroy");
	public static final RegistryEntry<SoundEvent> BLOCK_STREWN_LEAVES_PILE_STEP = register( "block.strewn_leaves.pile_step");
	public static final RegistryEntry<SoundEvent> BLOCK_STICK_BREAK = register( "block.stick_break");

	public static final RegistryEntry<SoundEvent> ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE = register("entity.player.increase_ympe_infestation_stage");
	public static final RegistryEntry<SoundEvent> ENTITY_GENERIC_SHUCKED = register( "entity.generic.shucked");
	
	public static final RegistryEntry<SoundEvent> ENTITY_AYLYTHIAN_AMBIENT = register( "entity.aylythian.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_AYLYTHIAN_HURT = register("entity.aylythian.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_AYLYTHIAN_DEATH = register( "entity.aylythian.death");
	
	public static final RegistryEntry<SoundEvent> ENTITY_ELDER_AYLYTHIAN_AMBIENT = register( "entity.elder_aylythian.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_ELDER_AYLYTHIAN_HURT = register( "entity.elder_aylythian.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_ELDER_AYLYTHIAN_DEATH = register( "entity.elder_aylythian.death");
	
	public static final RegistryEntry<SoundEvent> AMBIENT_FOREST_ADDITIONS = register("ambient.forest.additions");

	public static final RegistryEntry<SoundEvent> ENTITY_SOULMOULD_AMBIENT = register( "entity.soulmould.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_SOULMOULD_ATTACK = register("entity.soulmould.attack");
	public static final RegistryEntry<SoundEvent> ENTITY_SOULMOULD_HURT = register("entity.soulmould.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_SOULMOULD_DEATH = register("entity.soulmould.death");

	public static final RegistryEntry<SoundEvent> ENTITY_SCION_AMBIENT = register("entity.scion.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_SCION_HURT = register("entity.scion.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_SCION_DEATH = register( "entity.scion.death");

	public static final RegistryEntry<SoundEvent> ENTITY_WREATHED_HIND_AMBIENT = register("entity.wreathed_hind.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_WREATHED_HIND_HURT = register( "entity.wreathed_hind.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_WREATHED_HIND_DEATH = register("entity.wreathed_hind.death");

	public static final RegistryEntry<SoundEvent> ENTITY_FAUNAYLYTHIAN_AMBIENT = register("entity.faunaylythian.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_FAUNAYLYTHIAN_HURT = register( "entity.faunaylythian.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_FAUNAYLYTHIAN_DEATH = register("entity.faunaylythian.death");

	public static final BlockSoundGroup STREWN_LEAVES = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value());
	public static final BlockSoundGroup LEAF_PILES = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_PILE_DESTROY.value(), BLOCK_STREWN_LEAVES_PILE_STEP.value(), BLOCK_STREWN_LEAVES_PILE_DESTROY.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_PILE_DESTROY.value());
	public static final BlockSoundGroup STREWN_LEAVES_STICK = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STICK_BREAK.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value());
	public static final BlockSoundGroup LEAF_PILES_STICK = new BlockSoundGroup(1.0F, 1.0F, BLOCK_STREWN_LEAVES_PILE_DESTROY.value(), BLOCK_STICK_BREAK.value(), BLOCK_STREWN_LEAVES_PILE_DESTROY.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_PILE_DESTROY.value());


	private static RegistryEntry<SoundEvent> register(String name) {
		Identifier id = AylythUtil.id(name);
		SoundEvent soundEvent = SoundEvent.of(id);
		RegistryEntry<SoundEvent> registered = Registry.registerReference(Registries.SOUND_EVENT, AylythUtil.id(name), soundEvent);
		return registered;
	}

	public static void init() {}
}
