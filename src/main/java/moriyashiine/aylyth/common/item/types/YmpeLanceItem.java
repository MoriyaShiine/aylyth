package moriyashiine.aylyth.common.item.types;

import moriyashiine.aylyth.common.entity.AylythAttributes;
import moriyashiine.aylyth.common.entity.types.projectile.YmpeLanceEntity;
import moriyashiine.aylyth.common.item.AylythToolMaterials;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TODO: Make vanishable with tag
public class YmpeLanceItem extends Item {
	private static final int MIN_CHARGE_TICKS = 10;

	public YmpeLanceItem(Settings settings) {
		super(settings);
	}

	public static AttributeModifiersComponent createAttributeModifiers() {
		return AttributeModifiersComponent.builder()
				.add(
						EntityAttributes.ATTACK_DAMAGE,
						new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, 7, EntityAttributeModifier.Operation.ADD_VALUE),
						AttributeModifierSlot.MAINHAND
				)
				.add(
						EntityAttributes.ATTACK_SPEED,
						new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, -2.5, EntityAttributeModifier.Operation.ADD_VALUE),
						AttributeModifierSlot.MAINHAND
				)
				.add(
						EntityAttributes.BLOCK_INTERACTION_RANGE,
						new EntityAttributeModifier(AylythAttributes.BASE_BLOCK_INTERACTION_RANGE, 1, EntityAttributeModifier.Operation.ADD_VALUE),
						AttributeModifierSlot.MAINHAND
				)
				.add(
						EntityAttributes.ENTITY_INTERACTION_RANGE,
						new EntityAttributeModifier(AylythAttributes.BASE_ENTITY_INTERACTION_RANGE, 1, EntityAttributeModifier.Operation.ADD_VALUE),
						AttributeModifierSlot.MAINHAND
				)
				.build();
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);

		if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
			return ActionResult.FAIL;
		} else if (EnchantmentHelper.getTridentSpinAttackStrength(itemStack, user) > 0 && !user.isTouchingWaterOrRain()) {
			return ActionResult.FAIL;
		}

		return ItemUsage.consumeHeldItem(world, user, hand);
	}

	@Override
	public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (user instanceof PlayerEntity player) {
			int chargeTicks = this.getMaxUseTime(stack, user) - remainingUseTicks;

			if (chargeTicks >= MIN_CHARGE_TICKS) {
				if (!world.isClient) {
					stack.damage(1, player);

					YmpeLanceEntity lanceEntity = new YmpeLanceEntity(world, player, stack);
					lanceEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0F, 2.5F, 1F);

					if (player.getAbilities().creativeMode) {
						lanceEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
					}

					world.spawnEntity(lanceEntity);
					world.playSoundFromEntity(null, lanceEntity, SoundEvents.ITEM_TRIDENT_THROW.value(), SoundCategory.PLAYERS, 1F, 1F);

					stack.decrementUnlessCreative(1, player);
				}

				player.incrementStat(Stats.USED.getOrCreateStat(this));
				return true;
			}
		}

		return super.onStoppedUsing(stack, world, user, remainingUseTicks);
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative();
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}

	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 72000;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(1, attacker, EquipmentSlot.MAINHAND);

		return true;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (state.getHardness(world, pos) != 0.0)
			stack.damage(2, miner, EquipmentSlot.MAINHAND);

		return true;
	}
}
