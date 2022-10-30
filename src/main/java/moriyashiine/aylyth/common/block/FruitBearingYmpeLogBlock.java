package moriyashiine.aylyth.common.block;

import com.terraformersmc.terraform.wood.block.StrippableLogBlock;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
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

public class FruitBearingYmpeLogBlock extends StrippableLogBlock {
	private static final Property<Integer> AGE = IntProperty.of("age", 0, 4);
	
	public FruitBearingYmpeLogBlock() {
		super(() -> ModBlocks.STRIPPED_YMPE_LOG, MapColor.BROWN, Settings.copy(ModBlocks.YMPE_LOG));
		setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y).with(AGE, 4));
	}
	
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (isMature(state)) {
			if (!world.isClient) {
				world.setBlockState(pos, state.with(AGE, 0));
				world.playSound(null, pos, ModSoundEvents.BLOCK_YMPE_LOG_PICK_FRUIT, SoundCategory.BLOCKS, 1, 1);
				player.giveItemStack(new ItemStack(ModItems.YMPE_FRUIT));
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
