package moriyashiine.aylyth.datagen.common;

import moriyashiine.aylyth.common.block.AylythBlocks;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;

public interface AylythBlockFamilies {
    BlockFamily YMPE = BlockFamilies.register(AylythBlocks.YMPE_PLANKS).button(AylythBlocks.YMPE_BUTTON).fence(AylythBlocks.YMPE_FENCE).fenceGate(AylythBlocks.YMPE_FENCE_GATE).pressurePlate(AylythBlocks.YMPE_PRESSURE_PLATE).sign(AylythBlocks.YMPE_SIGN, AylythBlocks.YMPE_WALL_SIGN).slab(AylythBlocks.YMPE_SLAB).stairs(AylythBlocks.YMPE_STAIRS).door(AylythBlocks.YMPE_DOOR).trapdoor(AylythBlocks.YMPE_TRAPDOOR).group("wooden").unlockCriterionName("has_planks").build();
    BlockFamily POMEGRANATE = BlockFamilies.register(AylythBlocks.POMEGRANATE_PLANKS).button(AylythBlocks.POMEGRANATE_BUTTON).fence(AylythBlocks.POMEGRANATE_FENCE).fenceGate(AylythBlocks.POMEGRANATE_FENCE_GATE).pressurePlate(AylythBlocks.POMEGRANATE_PRESSURE_PLATE).sign(AylythBlocks.POMEGRANATE_SIGN, AylythBlocks.POMEGRANATE_WALL_SIGN).slab(AylythBlocks.POMEGRANATE_SLAB).stairs(AylythBlocks.POMEGRANATE_STAIRS).door(AylythBlocks.POMEGRANATE_DOOR).trapdoor(AylythBlocks.POMEGRANATE_TRAPDOOR).group("wooden").unlockCriterionName("has_planks").build();
    BlockFamily WRITHEWOOD = BlockFamilies.register(AylythBlocks.WRITHEWOOD_PLANKS).button(AylythBlocks.WRITHEWOOD_BUTTON).fence(AylythBlocks.WRITHEWOOD_FENCE).fenceGate(AylythBlocks.WRITHEWOOD_FENCE_GATE).pressurePlate(AylythBlocks.WRITHEWOOD_PRESSURE_PLATE).sign(AylythBlocks.WRITHEWOOD_SIGN, AylythBlocks.WRITHEWOOD_WALL_SIGN).slab(AylythBlocks.WRITHEWOOD_SLAB).stairs(AylythBlocks.WRITHEWOOD_STAIRS).door(AylythBlocks.WRITHEWOOD_DOOR).trapdoor(AylythBlocks.WRITHEWOOD_TRAPDOOR).group("wooden").unlockCriterionName("has_planks").build();
}
