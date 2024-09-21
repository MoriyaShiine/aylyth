package moriyashiine.aylyth.common.block.types;

import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings({"deprecation", "UnstableApiUsage"})
public abstract class HarvestablePillarBlock extends PillarBlock {
    private final Identifier harvestLootTable;

    public HarvestablePillarBlock(Identifier harvestLootTable, Settings settings) {
        super(settings);
        this.harvestLootTable = harvestLootTable;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (canBeHarvested(state)) {
            if (world instanceof ServerWorld serverWorld) {
                PlayerInventoryStorage storage = PlayerInventoryStorage.of(player);
                ObjectList<ItemStack> harvested = getHarvestLoot(serverWorld, pos, state, player.getStackInHand(hand));
                if (!harvested.isEmpty()) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        for (ItemStack stack : harvested) {
                            storage.offerOrDrop(ItemVariant.of(stack), stack.getCount(), transaction);
                        }
                        transaction.commit();
                    }
                }
            }
            world.setBlockState(pos, getStateAfterHarvest(state, pos, world));
            world.playSound(null, pos, getHarvestSound(state).value(), SoundCategory.BLOCKS, 1, 1);
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public ObjectList<ItemStack> getHarvestLoot(ServerWorld serverWorld, BlockPos origin, BlockState state, @Nullable ItemStack tool) {
        LootTable table = serverWorld.getServer().getLootManager().getElement(LootDataType.LOOT_TABLES, harvestLootTable);
        if (table == null) {
            return ObjectLists.emptyList();
        }
        return table.generateLoot(new LootContextParameterSet.Builder(serverWorld)
                .add(LootContextParameters.ORIGIN, origin.toCenterPos())
                .add(LootContextParameters.BLOCK_STATE, state)
                .addOptional(LootContextParameters.TOOL, tool)
                .build(AylythLootContextTypes.HARVEST)
        );
    }

    public abstract boolean canBeHarvested(BlockState state);

    public abstract BlockState getStateAfterHarvest(BlockState state, BlockPos pos, World world);

    public abstract RegistryEntry<SoundEvent> getHarvestSound(BlockState state);
}
