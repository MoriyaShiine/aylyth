package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.item.AylythianHeartItem;
import moriyashiine.aylyth.common.item.ShuckedYmpeFruitItem;
import moriyashiine.aylyth.common.item.YmpeDaggerItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(Aylyth.MOD_ID, Aylyth.MOD_ID), () -> new ItemStack(ModItems.YMPE_DAGGER));
	
	public static final Item STRIPPED_YMPE_LOG = new BlockItem(ModBlocks.STRIPPED_YMPE_LOG, settings());
	public static final Item STRIPPED_YMPE_WOOD = new BlockItem(ModBlocks.STRIPPED_YMPE_WOOD, settings());
	public static final Item YMPE_LOG = new BlockItem(ModBlocks.YMPE_LOG, settings());
	public static final Item FRUIT_BEARING_YMPE_LOG = new BlockItem(ModBlocks.FRUIT_BEARING_YMPE_LOG, settings());
	public static final Item YMPE_WOOD = new BlockItem(ModBlocks.YMPE_WOOD, settings());
	public static final Item YMPE_LEAVES = new BlockItem(ModBlocks.YMPE_LEAVES, settings());
	public static final Item YMPE_SAPLING = new BlockItem(ModBlocks.YMPE_SAPLING, settings());
	public static final Item YMPE_PLANKS = new BlockItem(ModBlocks.YMPE_PLANKS, settings());
	public static final Item YMPE_STAIRS = new BlockItem(ModBlocks.YMPE_STAIRS, settings());
	public static final Item YMPE_SLAB = new BlockItem(ModBlocks.YMPE_SLAB, settings());
	public static final Item YMPE_FENCE = new BlockItem(ModBlocks.YMPE_FENCE, settings());
	public static final Item YMPE_FENCE_GATE = new BlockItem(ModBlocks.YMPE_FENCE_GATE, settings());
	public static final Item YMPE_PRESSURE_PLATE = new BlockItem(ModBlocks.YMPE_PRESSURE_PLATE, settings());
	public static final Item YMPE_BUTTON = new BlockItem(ModBlocks.YMPE_BUTTON, settings());
	public static final Item YMPE_TRAPDOOR = new BlockItem(ModBlocks.YMPE_TRAPDOOR, settings());
	public static final Item YMPE_DOOR = new TallBlockItem(ModBlocks.YMPE_DOOR, settings());
	public static final Item YMPE_SIGN = new SignItem(settings().maxCount(16), ModBlocks.YMPE_SIGN, ModBlocks.YMPE_WALL_SIGN);
	
	public static final Item AYLYTH_BUSH = new BlockItem(ModBlocks.AYLYTH_BUSH, settings());
	
	public static final Item OAK_SEEP = new BlockItem(ModBlocks.OAK_SEEP, settings());
	public static final Item SPRUCE_SEEP = new BlockItem(ModBlocks.SPRUCE_SEEP, settings());
	public static final Item DARK_OAK_SEEP = new BlockItem(ModBlocks.DARK_OAK_SEEP, settings());
	public static final Item YMPE_SEEP = new BlockItem(ModBlocks.YMPE_SEEP, settings());
	
	public static final Item YMPE_DAGGER = new YmpeDaggerItem(ToolMaterials.NETHERITE, 1, -2, settings());
	
	public static final Item YMPE_FRUIT = new Item(settings().food(ModFoodComponents.YMPE_FRUIT));
	public static final Item SHUCKED_YMPE_FRUIT = new ShuckedYmpeFruitItem(settings().maxCount(1));
	
	public static final Item NYSIAN_GRAPES = new Item(settings().food(ModFoodComponents.NYSIAN_GRAPES));
	
	public static final Item AYLYTHIAN_HEART = new AylythianHeartItem(settings());
	
	public static Item PILOT_LIGHT_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.PILOT_LIGHT, 0xFFD972, 0x9FD9F6, settings());
	public static Item AYLYTHIAN_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.AYLYTHIAN, 0x6A4831, 0xE58E03, settings());
	public static Item ELDER_AYLYTHIAN_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.ELDER_AYLYTHIAN, 0x513425, 0xFFDC9B, settings());
	
	private static Item.Settings settings() {
		return new FabricItemSettings().group(GROUP);
	}
	
	public static void init() {
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "stripped_ympe_log"), STRIPPED_YMPE_LOG);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "stripped_ympe_wood"), STRIPPED_YMPE_WOOD);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_log"), YMPE_LOG);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "fruit_bearing_ympe_log"), FRUIT_BEARING_YMPE_LOG);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_wood"), YMPE_WOOD);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_leaves"), YMPE_LEAVES);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_sapling"), YMPE_SAPLING);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_planks"), YMPE_PLANKS);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_stairs"), YMPE_STAIRS);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_slab"), YMPE_SLAB);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_fence"), YMPE_FENCE);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_fence_gate"), YMPE_FENCE_GATE);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_pressure_plate"), YMPE_PRESSURE_PLATE);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_button"), YMPE_BUTTON);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_trapdoor"), YMPE_TRAPDOOR);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_door"), YMPE_DOOR);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_sign"), YMPE_SIGN);
		
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "aylyth_bush"), AYLYTH_BUSH);
		
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "oak_seep"), OAK_SEEP);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "spruce_seep"), SPRUCE_SEEP);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "dark_oak_seep"), DARK_OAK_SEEP);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_seep"), YMPE_SEEP);
		
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_dagger"), YMPE_DAGGER);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_fruit"), YMPE_FRUIT);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "shucked_ympe_fruit"), SHUCKED_YMPE_FRUIT);
		
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "nysian_grapes"), NYSIAN_GRAPES);
		
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "aylythian_heart"), AYLYTHIAN_HEART);
		
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "pilot_light_spawn_egg"), PILOT_LIGHT_SPAWN_EGG);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "aylythian_spawn_egg"), AYLYTHIAN_SPAWN_EGG);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "elder_aylythian_spawn_egg"), ELDER_AYLYTHIAN_SPAWN_EGG);
		
		FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
		fuelRegistry.add(YMPE_FENCE, 300);
		fuelRegistry.add(YMPE_FENCE_GATE, 300);
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;
		compostRegistry.add(YMPE_LEAVES, 0.3f);
		compostRegistry.add(YMPE_SAPLING, 0.3f);
	}
}
