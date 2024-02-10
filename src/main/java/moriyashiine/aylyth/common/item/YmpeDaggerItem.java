package moriyashiine.aylyth.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
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
		if(!world.isClient()){
			BlockPos blockPos = context.getBlockPos();
			if(world.getBlockState(blockPos).isOf(Blocks.SOUL_CAMPFIRE) && world.getBlockEntity(blockPos) instanceof CampfireBlockEntity campfireBlockEntity){
				DefaultedList<ItemStack> items = campfireBlockEntity.getItemsBeingCooked();
				List<String> campList = new ArrayList<>(Arrays.asList(
						items.get(0).toString(),
						items.get(1).toString(),
						items.get(2).toString(),
						items.get(3).toString()
				));

				List<String> matchList = new ArrayList<>(Arrays.asList(
						ModItems.AYLYTHIAN_HEART.getDefaultStack().toString(),
						ModItems.WRONGMEAT.getDefaultStack().toString(),
						ModItems.WRONGMEAT.getDefaultStack().toString(),
						ModItems.SHUCKED_YMPE_FRUIT.getDefaultStack().toString()
				));
				Collections.sort(campList);
				Collections.sort(matchList);
				if(campList.equals(matchList)){
					items.forEach(itemStack -> {
						if(hasSaplings(world, blockPos).size() == 4){
							world.playSound(null, blockPos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1, 1);
							ItemScatterer.spawn(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(ModItems.CORIC_SEED));
							CampfireBlock.extinguish(null, world, blockPos, world.getBlockState(blockPos));
							world.setBlockState(blockPos, world.getBlockState(blockPos).with(CampfireBlock.LIT, false), Block.NOTIFY_ALL);
							List<BlockPos> saplingList = hasSaplings(world, blockPos);
							saplingList.forEach(sapling -> world.breakBlock(sapling, false));
							campfireBlockEntity.getItemsBeingCooked().clear();

						}
					});
					return ActionResult.CONSUME;
				}
		}





			/* For if this needs to be data driven
			SimpleInventory test = new SimpleInventory(4);
			items.forEach(test::addStack);
			SoulCampfireRecipe recipe = world.getRecipeManager().listAllOfType(ModRecipeTypes.SOULFIRE_RECIPE_TYPE).stream().filter(soulCampfireRecipe -> soulCampfireRecipe.matches(test, world)).findFirst().orElse(null);
			if (recipe != null && recipe.input.size() == items.size()) {
				ItemStack recipeOutput = recipe.getOutput();
				if(recipeOutput != null){
					world.playSound(null, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1, 1);
					ItemScatterer.spawn(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), recipeOutput);
				}
			}
			 */
		}
		return super.useOnBlock(context);
	}


	private List<BlockPos> hasSaplings(World world, BlockPos blockPos){
		List<BlockPos> list = new ArrayList<>();
		for(int x = -1; x <= 1; x++){
			for(int z = -1; z <= 1; z++){
				if(x == 0 || z == 0){
					BlockPos saplingPos = blockPos.add(x ,0, z);
					if(world.getBlockState(saplingPos).isOf(ModBlocks.YMPE_SAPLING)) {
						list.add(saplingPos);
					}
				}
			}
		}
		return list;
	}
}
