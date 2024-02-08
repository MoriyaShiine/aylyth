package moriyashiine.aylyth.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import moriyashiine.aylyth.common.entity.projectile.YmpeLanceEntity;
import moriyashiine.aylyth.common.registry.ModToolMaterials;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class YmpeLanceItem extends Item implements Vanishable {
	public static final UUID BASE_REACH_MODIFIER = UUID.fromString("ccec5f1b-1597-4994-aa5f-c8848721897d");
	private static final EntityAttributeModifier ATTACK_DAMAGE_MODIFIER = new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 7, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier ATTACK_SPEED_MODIFIER = new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.5, EntityAttributeModifier.Operation.ADDITION);
	private static final EntityAttributeModifier REACH_MODIFIER = new EntityAttributeModifier(BASE_REACH_MODIFIER, "Weapon modifier", 1, EntityAttributeModifier.Operation.ADDITION);
	private static final int MIN_CHARGE_TICKS = 10;
	private final Multimap<EntityAttribute, EntityAttributeModifier> modifiers;

	public YmpeLanceItem(int durability, Settings settings) {
		super(settings.maxCount(1).maxDamage(durability));
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER);
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, ATTACK_SPEED_MODIFIER);
		builder.put(ReachEntityAttributes.ATTACK_RANGE, REACH_MODIFIER);
		builder.put(ReachEntityAttributes.REACH, REACH_MODIFIER);
		modifiers = builder.build();
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);

		if(itemStack.getDamage() >= itemStack.getMaxDamage() - 1)
			return TypedActionResult.fail(itemStack);
		else if(EnchantmentHelper.getRiptide(itemStack) > 0 && !user.isTouchingWaterOrRain())
			return TypedActionResult.fail(itemStack);

		user.setCurrentHand(hand);
		return TypedActionResult.consume(itemStack);
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if(user instanceof PlayerEntity player) {
			int chargeTicks = this.getMaxUseTime(stack) - remainingUseTicks;

			if(chargeTicks >= MIN_CHARGE_TICKS) {
				if(!world.isClient) {
					stack.damage(1, player, (player1) -> player1.sendToolBreakStatus(user.getActiveHand()));

					YmpeLanceEntity lanceEntity = new YmpeLanceEntity(world, player, stack);
					lanceEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0F, 2.5F, 1F);

					if(player.getAbilities().creativeMode)
						lanceEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;

					world.spawnEntity(lanceEntity);
					world.playSoundFromEntity(null, lanceEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1F, 1F);

					if(!player.getAbilities().creativeMode)
						player.getInventory().removeOne(stack);
				}

				player.incrementStat(Stats.USED.getOrCreateStat(this));
			}
		}

		super.onStoppedUsing(stack, world, user, remainingUseTicks);
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative();
	}

	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return ModToolMaterials.NEPHRITE.getRepairIngredient().test(ingredient);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, (livingEntity) -> livingEntity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

		return true;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if(state.getHardness(world, pos) != 0.0)
			stack.damage(2, miner, (livingEntity) -> livingEntity.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));

		return true;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? modifiers : super.getAttributeModifiers(slot);
	}
}
