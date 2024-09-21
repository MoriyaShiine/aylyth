package moriyashiine.aylyth.common.world;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;

public interface AylythSoundEvents {

	RegistryEntry<SoundEvent> BLOCK_YMPE_LOG_PICK_FRUIT = register("block.ympe_log.pick_fruit");
	RegistryEntry<SoundEvent> BLOCK_STREWN_LEAVES_STEP = register("block.strewn_leaves.step");
	RegistryEntry<SoundEvent> BLOCK_STREWN_LEAVES_PILE_DESTROY = register("block.strewn_leaves.pile_destroy");
	RegistryEntry<SoundEvent> BLOCK_STREWN_LEAVES_PILE_STEP = register( "block.strewn_leaves.pile_step");
	RegistryEntry<SoundEvent> BLOCK_STICK_BREAK = register( "block.stick_break");

	RegistryEntry<SoundEvent> ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE = register("entity.player.increase_ympe_infestation_stage");
	RegistryEntry<SoundEvent> ENTITY_GENERIC_SHUCKED = register( "entity.generic.shucked");

	RegistryEntry<SoundEvent> AMBIENT_FOREST_ADDITIONS = register("ambient.forest.additions");

	RegistryEntry<SoundEvent> AMBIENT_MUSIC = register("music.aylyth.ambient");
	RegistryEntry<SoundEvent> BOSS_FIGHT = register("music.aylyth.boss.fight");
	RegistryEntry<SoundEvent> BOSS_FIGHT_VICTORY = register("music.aylyth.boss.victory");
	RegistryEntry<SoundEvent> POMEGRANATE_MUSIC_DISC = register("music_disc.aylyth.pomegranate");

	RegistryEntry<SoundEvent> ENTITY_AYLYTHIAN_AMBIENT = register( "entity.aylythian.ambient");
	RegistryEntry<SoundEvent> ENTITY_AYLYTHIAN_HURT = register("entity.aylythian.hurt");
	RegistryEntry<SoundEvent> ENTITY_AYLYTHIAN_DEATH = register( "entity.aylythian.death");
	
	RegistryEntry<SoundEvent> ENTITY_ELDER_AYLYTHIAN_AMBIENT = register( "entity.elder_aylythian.ambient");
	RegistryEntry<SoundEvent> ENTITY_ELDER_AYLYTHIAN_HURT = register( "entity.elder_aylythian.hurt");
	RegistryEntry<SoundEvent> ENTITY_ELDER_AYLYTHIAN_DEATH = register( "entity.elder_aylythian.death");
	
	RegistryEntry<SoundEvent> ENTITY_SOULMOULD_AMBIENT = register( "entity.soulmould.ambient");
	RegistryEntry<SoundEvent> ENTITY_SOULMOULD_ATTACK = register("entity.soulmould.attack");
	RegistryEntry<SoundEvent> ENTITY_SOULMOULD_HURT = register("entity.soulmould.hurt");
	RegistryEntry<SoundEvent> ENTITY_SOULMOULD_DEATH = register("entity.soulmould.death");

	RegistryEntry<SoundEvent> ENTITY_SCION_AMBIENT = register("entity.scion.ambient");
	RegistryEntry<SoundEvent> ENTITY_SCION_HURT = register("entity.scion.hurt");
	RegistryEntry<SoundEvent> ENTITY_SCION_DEATH = register( "entity.scion.death");

	RegistryEntry<SoundEvent> ENTITY_WREATHED_HIND_AMBIENT = register("entity.wreathed_hind.ambient");
	RegistryEntry<SoundEvent> ENTITY_WREATHED_HIND_HURT = register( "entity.wreathed_hind.hurt");
	RegistryEntry<SoundEvent> ENTITY_WREATHED_HIND_DEATH = register("entity.wreathed_hind.death");

	RegistryEntry<SoundEvent> ENTITY_FAUNAYLYTHIAN_AMBIENT = register("entity.faunaylythian.ambient");
	RegistryEntry<SoundEvent> ENTITY_FAUNAYLYTHIAN_HURT = register( "entity.faunaylythian.hurt");
	RegistryEntry<SoundEvent> ENTITY_FAUNAYLYTHIAN_DEATH = register("entity.faunaylythian.death");

	BlockSoundGroup STREWN_LEAVES = new BlockSoundGroup(1f, 1f, BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value());
	BlockSoundGroup LEAF_PILES = new BlockSoundGroup(1f, 1f, BLOCK_STREWN_LEAVES_PILE_DESTROY.value(), BLOCK_STREWN_LEAVES_PILE_STEP.value(), BLOCK_STREWN_LEAVES_PILE_DESTROY.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_PILE_DESTROY.value());
	BlockSoundGroup STREWN_LEAVES_STICK = new BlockSoundGroup(1f, 1f, BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STICK_BREAK.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_STEP.value());
	BlockSoundGroup LEAF_PILES_STICK = new BlockSoundGroup(1f, 1f, BLOCK_STREWN_LEAVES_PILE_DESTROY.value(), BLOCK_STICK_BREAK.value(), BLOCK_STREWN_LEAVES_PILE_DESTROY.value(), BLOCK_STREWN_LEAVES_STEP.value(), BLOCK_STREWN_LEAVES_PILE_DESTROY.value());

	private static RegistryEntry<SoundEvent> register(String name) {
		var id = Aylyth.id(name);
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	// Load static initializer
	static void register() {}
}
