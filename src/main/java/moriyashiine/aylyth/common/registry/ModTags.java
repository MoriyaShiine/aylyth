package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ModTags {
	public static final Tag<Block> SEEPS = TagFactory.BLOCK.create(new Identifier(Aylyth.MOD_ID, "seeps"));
	
	public static final Tag<Item> YMPE_FOODS = TagFactory.ITEM.create(new Identifier(Aylyth.MOD_ID, "ympe_foods"));
}
