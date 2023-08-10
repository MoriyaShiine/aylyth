package moriyashiine.aylyth.common.item;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import moriyashiine.aylyth.client.network.packet.SpawnShuckParticlesPacket;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.*;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.*;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.*;

public class YmpeDaggerItem extends SwordItem {
	private static final EntityAttributeModifier REACH_MODIFIER = new EntityAttributeModifier(UUID.fromString("ccec5f1b-1597-4994-aa5f-c8848721897d"), "Weapon modifier", -0.5, EntityAttributeModifier.Operation.ADDITION);

	public YmpeDaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		Multimap<EntityAttribute, EntityAttributeModifier> map = LinkedHashMultimap.create(super.getAttributeModifiers(slot));
		if (slot == EquipmentSlot.MAINHAND) {
			map.put(ReachEntityAttributes.ATTACK_RANGE, REACH_MODIFIER);
		}
		return map;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target instanceof MobEntity mob && attacker.getWorld() instanceof ServerWorld serverWorld) {
			ItemStack offhand = attacker.getOffHandStack();
			if (offhand.isOf(ModItems.SHUCKED_YMPE_FRUIT)) {
				if ((!offhand.hasNbt() || !offhand.getNbt().contains("StoredEntity")) && !target.getType().isIn(ModTags.SHUCK_BLACKLIST)) {
					if (attacker instanceof ServerPlayerEntity serverPlayer) {
						ModCriteria.SHUCKING.trigger(serverPlayer, target);
					}
					target.setHealth(target.getMaxHealth());
					target.clearStatusEffects();
					target.extinguish();
					target.setFrozenTicks(0);
					target.setVelocity(Vec3d.ZERO);
					target.fallDistance = 0;
					target.knockbackVelocity = 0;
					ModComponents.PREVENT_DROPS.get(mob).setPreventsDrops(true);
					PlayerLookup.tracking(target).forEach(trackingPlayer -> SpawnShuckParticlesPacket.send(trackingPlayer, target));
					serverWorld.playSound(null, target.getBlockPos(), ModSoundEvents.ENTITY_GENERIC_SHUCKED, target.getSoundCategory(), 1, target.getSoundPitch());
					NbtCompound entityCompound = new NbtCompound();
					target.saveSelfNbt(entityCompound);
					offhand.getOrCreateNbt().put("StoredEntity", entityCompound);
					target.remove(Entity.RemovalReason.DISCARDED);
				}
			}
		}

		return true;
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
					if(world.getBlockState(saplingPos).isOf(ModBlocks.YMPE_BLOCKS.sapling)) {
						list.add(saplingPos);
					}
				}
			}
		}
		return list;
	}
}
