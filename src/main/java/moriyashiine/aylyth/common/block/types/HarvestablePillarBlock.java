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
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings({"deprecation", "UnstableApiUsage"})
public abstract class HarvestablePillarBlock extends PillarBlock {
    private final RegistryKey<LootTable> harvestLootTable;

    public HarvestablePillarBlock(RegistryKey<LootTable> harvestLootTable, Settings settings) {
        super(settings);
        this.harvestLootTable = harvestLootTable;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (canBeHarvested(state)) {
            if (world instanceof ServerWorld serverWorld) {
                PlayerInventoryStorage storage = PlayerInventoryStorage.of(player);
                ObjectList<ItemStack> harvested = getHarvestLoot(serverWorld, pos, state, stack);
                if (!harvested.isEmpty()) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        for (ItemStack harvestStack : harvested) {
                            storage.offerOrDrop(ItemVariant.of(harvestStack), harvestStack.getCount(), transaction);
                        }
                        transaction.commit();
                    }
                }
            }
            world.setBlockState(pos, getStateAfterHarvest(state, pos, world));
            world.playSound(null, pos, getHarvestSound(state).value(), SoundCategory.BLOCKS, 1, 1);
            return ActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    public ObjectList<ItemStack> getHarvestLoot(ServerWorld serverWorld, BlockPos origin, BlockState state, @Nullable ItemStack tool) {
        LootTable table = serverWorld.getServer().getReloadableRegistries().getLootTable(harvestLootTable);
        if (table == null) {
            return ObjectLists.emptyList();
        }
        return table.generateLoot(new LootWorldContext.Builder(serverWorld)
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
