package moriyashiine.aylyth.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.recipe.SoulCampfireRecipe;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModRecipeTypes;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class YmpeDaggerItem extends SwordItem {
	public YmpeDaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float attackReach, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(this.attributeModifiers);
		builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(YmpeLanceItem.BASE_REACH_MODIFIER, "Weapon modifier", attackReach, EntityAttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		if (!world.isClient()) {
			BlockPos blockPos = context.getBlockPos();
			if (world.getBlockState(blockPos).isOf(Blocks.SOUL_CAMPFIRE) && world.getBlockEntity(blockPos) instanceof CampfireBlockEntity campfireBlockEntity) {
				DefaultedList<ItemStack> items = campfireBlockEntity.getItemsBeingCooked();
				SimpleInventory inv = new SimpleInventory(4);
				items.forEach(inv::addStack);
				SoulCampfireRecipe recipe = world.getRecipeManager().getFirstMatch(ModRecipeTypes.SOULFIRE_RECIPE_TYPE, inv, world).orElse(null);
				List<BlockPos> saplingsAround = getSaplingsAround(world, blockPos);
				if (recipe != null && !saplingsAround.isEmpty()) {
					world.playSound(null, blockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1, 1);
					ItemStack recipeOutput = recipe.craft(inv, world.getRegistryManager());
					ItemScatterer.spawn(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), recipeOutput);
					CampfireBlock.extinguish(null, world, blockPos, world.getBlockState(blockPos));
					world.setBlockState(blockPos, world.getBlockState(blockPos).with(CampfireBlock.LIT, false), Block.NOTIFY_ALL);
					saplingsAround.forEach(sapling -> world.breakBlock(sapling, false));
					campfireBlockEntity.getItemsBeingCooked().clear();
					return ActionResult.success(world.isClient);
				}
			}
		}
		return super.useOnBlock(context);
	}

	private List<BlockPos> getSaplingsAround(World world, BlockPos blockPos) {
		List<BlockPos> saplings = new ObjectArrayList<>();
		for (Direction dir : Direction.Type.HORIZONTAL) {
			BlockPos saplingPos = blockPos.add(dir.getOffsetX() ,0, dir.getOffsetZ());
			if (!world.getBlockState(saplingPos).isOf(ModBlocks.YMPE_SAPLING)) {
				return List.of();
			}
			saplings.add(saplingPos);
		}
		return saplings;
	}
}
