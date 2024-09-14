package moriyashiine.aylyth.common.block.types;

import moriyashiine.aylyth.common.world.effects.AylythSoundEvents;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@SuppressWarnings("UnstableApiUsage")
public class OneTimeHarvestablePillarBlock extends HarvestablePillarBlock {
    public static final BooleanProperty HARVESTABLE = BooleanProperty.of("harvestable");
    private final UnaryOperator<BlockState> harvestState;

    public OneTimeHarvestablePillarBlock(Supplier<ItemVariant> harvest, UnaryOperator<BlockState> harvestState, Settings settings) {
        super(harvest, settings);
        this.harvestState = harvestState;
        setDefaultState(getDefaultState().with(HARVESTABLE, true));
    }

    @Override
    public boolean canBeHarvested(BlockState state) {
        return state.get(HARVESTABLE);
    }

    @Override
    public BlockState getStateAfterHarvest(BlockState state, BlockPos pos, World world) {
        return harvestState.apply(state);
    }

    @Override
    public RegistryEntry<SoundEvent> getHarvestSound(BlockState state) {
        return AylythSoundEvents.BLOCK_YMPE_LOG_PICK_FRUIT;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(HARVESTABLE));
    }
}
