package moriyashiine.aylyth.common.block.types;

import com.google.common.base.Supplier;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@SuppressWarnings({"deprecation", "UnstableApiUsage"})
public class GrowingHarvestablePillarBlock extends HarvestablePillarBlock {
	public static final Property<Integer> AGE = IntProperty.of("age", 0, 4);

	public GrowingHarvestablePillarBlock(Supplier<ItemVariant> harvest, Settings settings) {
		super(harvest, settings);
		setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y).with(AGE, 4));
	}

	@Override
	public boolean canBeHarvested(BlockState state) {
		return isMature(state);
	}

	@Override
	public BlockState getStateAfterHarvest(BlockState state, BlockPos pos, World world) {
		return state.with(AGE, 0);
	}

	@Override
	public RegistryEntry<SoundEvent> getHarvestSound(BlockState state) {
		return AylythSoundEvents.BLOCK_YMPE_LOG_PICK_FRUIT;
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return !isMature(state);
	}
	
	private boolean isMature(BlockState state) {
		return state.get(AGE) == getMaxAge();
	}
	
	private int getMaxAge() {
		return 4;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int age = state.get(AGE);
		if (age < getMaxAge() && random.nextInt(5) == 0) {
			world.setBlockState(pos, state.with(AGE, age + 1), Block.NOTIFY_LISTENERS);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder.add(AGE));
	}
}
