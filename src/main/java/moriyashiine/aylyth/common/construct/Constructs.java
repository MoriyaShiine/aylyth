package moriyashiine.aylyth.common.construct;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import moriyashiine.aylyth.common.entity.type.mob.BoneflyEntity;
import moriyashiine.aylyth.common.entity.type.mob.SoulmouldEntity;
import moriyashiine.aylyth.common.entity.type.mob.TulpaEntity;
import moriyashiine.aylyth.common.registry.AylythBlocks;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.registry.AylythItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class Constructs {
    private static final Map<Block, Construct<?>> CONSTRUCTS = Util.make(new Reference2ObjectOpenHashMap<>(), map -> {
        map.put(Blocks.SOUL_SOIL,
                Construct.of(
                        ConstructPattern.builder()
                                .layer("S")
                                .layer("H")
                                .layer("W")
                                .layer("W")
                                .where('S', 'H', cachedBlockPosition -> cachedBlockPosition.getBlockState().isOf(Blocks.SOUL_SOIL))
                                .where('W', cachedBlockPosition -> cachedBlockPosition.getBlockState().isOf(AylythBlocks.LARGE_WOODY_GROWTH))
                                .build(),
                        (serverWorld, usageContext, groundPos) -> {
                            TulpaEntity tulpaEntity = new TulpaEntity(AylythEntityTypes.TULPA, serverWorld);
                            tulpaEntity.refreshPositionAndAngles(groundPos, usageContext.getSide().asRotation(), 0.0F);
                            tulpaEntity.setOwner(usageContext.getPlayer());
                            return tulpaEntity;
                        }
                )
        );
        map.put(AylythBlocks.ESSTLINE_BLOCK,
                Construct.of(
                        ConstructPattern.builder()
                                .layer(" D ")
                                .layer("DED")
                                .whereSummonerIs('E')
                                .where('D', cachedBlockPosition -> cachedBlockPosition.getBlockState().isOf(Blocks.POLISHED_DEEPSLATE))
                                .where('E', cachedBlockPosition -> cachedBlockPosition.getBlockState().isOf(AylythBlocks.ESSTLINE_BLOCK))
                                .build(),
                        (serverWorld, usageContext, groundPos) -> {
                            SoulmouldEntity soulmouldEntity = new SoulmouldEntity(AylythEntityTypes.SOULMOULD, serverWorld);
                            soulmouldEntity.refreshPositionAndAngles(groundPos, usageContext.getSide().asRotation(), 0.0F);
                            soulmouldEntity.setOwner(usageContext.getPlayer());
                            soulmouldEntity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(AylythItems.YMPE_GLAIVE));
                            soulmouldEntity.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 0.0F;
                            return soulmouldEntity;
                        }
                )
        );
        map.put(Blocks.BONE_BLOCK,
                Construct.of(
                        ConstructPattern.builder()
                                .layer("HSSSSSB")
                                .layer(" BSS   ")
                                .where('B', 'H', cachedBlockPosition -> cachedBlockPosition.getBlockState().isOf(Blocks.BONE_BLOCK))
                                .where('S', cachedBlockPosition -> cachedBlockPosition.getBlockState().isOf(Blocks.SOUL_SOIL))
                                .build(),
                        (serverWorld, usageContext, groundPos) ->
                                BoneflyEntity.create(serverWorld, groundPos, usageContext.getSide().asRotation(), 0.0f, usageContext.getPlayer())
                )
        );
    });

    public static Construct<?> get(Block summonBlock) {
        return CONSTRUCTS.get(summonBlock);
    }

    /**
     *
     * @param pattern
     * @param factory
     * @param <T>
     */
    public record Construct<T extends Entity>(ConstructPattern pattern, ConstructFactory<T> factory) {
        public static <T extends Entity> Construct<T> of(ConstructPattern pattern, ConstructFactory<T> factory) {
            return new Construct<>(pattern, factory);
        }

        public boolean test(World world, BlockPos clickedPos) {
            for (Direction dir : Direction.Type.HORIZONTAL) {
                if (pattern.test(world, clickedPos, dir)) {
                    return true;
                }
            }
            return false;
        }

        @Nullable
        public Entity constructIfMatching(ServerWorld world, ItemUsageContext usageContext, BlockPos clickedPos) {
            for (Direction dir : Direction.Type.HORIZONTAL) {
                List<BlockPos> matches = pattern.collectIfMatching(world, clickedPos, dir);
                if (!matches.isEmpty()) {
                    matches.forEach(blockPos -> {
                        BlockState blockState = world.getBlockState(blockPos);
                        if (blockState.contains(Properties.DOUBLE_BLOCK_HALF)) {
                            TallPlantBlock.onBreakInCreative(world, blockPos, blockState, null);
                        } else {
                            world.removeBlock(blockPos, false);
                        }
                    });
                    return factory.create(world, usageContext, clickedPos.add(0, -pattern.getRelativeSummonBlock().getY(), 0));
                }
            }
            return null;
        }
    }

    public interface ConstructFactory<T extends Entity> {
        T create(ServerWorld serverWorld, ItemUsageContext context, BlockPos groundPos);
    }
}
