package moriyashiine.aylyth.common.block;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@SuppressWarnings({"deprecated", "UnstableApiUsage"})
public class FruitBearingYmpeLogBlock extends PillarBlock {
	public static final Property<Integer> AGE = IntProperty.of("age", 0, 4);

	public static final Supplier<ItemVariant> YMPE_FRUIT_VARIANT = Suppliers.memoize(() -> ItemVariant.of(ModItems.YMPE_FRUIT));
	
	public FruitBearingYmpeLogBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y).with(AGE, 4));
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (isMature(state)) {
			if (!world.isClient) {
				world.setBlockState(pos, state.with(AGE, 0));
				world.playSound(null, pos, ModSoundEvents.BLOCK_YMPE_LOG_PICK_FRUIT.value(), SoundCategory.BLOCKS, 1, 1);
				PlayerInventoryStorage storage = PlayerInventoryStorage.of(player);
				try (Transaction transaction = Transaction.openOuter()) {
					storage.offerOrDrop(YMPE_FRUIT_VARIANT.get(), 1, transaction);
					transaction.commit();
				}
			}
			return ActionResult.success(world.isClient);
		}
		return super.onUse(state, world, pos, player, hand, hit);
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
