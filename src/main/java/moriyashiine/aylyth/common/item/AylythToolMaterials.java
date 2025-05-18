package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

public interface AylythToolMaterials {
    ToolMaterial NEPHRITE = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 400, 8.0f, 3.5f, 22, AylythItemTags.NEPHRITE_TOOL_MATERIALS);
    ToolMaterial NEPHRITE_SPECIAL = new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 800, 8.0f, 3.5f, 22, AylythItemTags.NEPHRITE_TOOL_MATERIALS);
}
