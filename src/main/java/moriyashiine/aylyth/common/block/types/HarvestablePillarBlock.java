package moriyashiine.aylyth.common.block.types;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

@SuppressWarnings({"deprecation", "UnstableApiUsage"})
public abstract class HarvestablePillarBlock extends PillarBlock {
    private final Supplier<ItemVariant> harvestVariant;

    public HarvestablePillarBlock(Supplier<ItemVariant> harvest, Settings settings) {
        super(settings);
        this.harvestVariant = harvest;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (canBeHarvested(state)) {
            if (!world.isClient) {
                PlayerInventoryStorage storage = PlayerInventoryStorage.of(player);
                try (Transaction transaction = Transaction.openOuter()) {
                    storage.offerOrDrop(harvestVariant.get(), 1, transaction);
                    transaction.commit();
                }
            }
            world.setBlockState(pos, getStateAfterHarvest(state, pos, world));
            world.playSound(null, pos, getHarvestSound(state).value(), SoundCategory.BLOCKS, 1, 1);
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public abstract boolean canBeHarvested(BlockState state);

    public abstract BlockState getStateAfterHarvest(BlockState state, BlockPos pos, World world);

    public abstract RegistryEntry<SoundEvent> getHarvestSound(BlockState state);
}
