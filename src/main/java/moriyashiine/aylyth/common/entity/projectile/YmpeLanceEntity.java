package moriyashiine.aylyth.common.entity.projectile;

import moriyashiine.aylyth.common.component.entity.YmpeThornsComponent;
import moriyashiine.aylyth.common.registry.ModComponents;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class YmpeLanceEntity extends PersistentProjectileEntity {
	private ItemStack stack = new ItemStack(ModItems.YMPE_LANCE);
	private LivingEntity target;
	private int timeStuck = 0;
	private boolean dealtDamage = false;

	public YmpeLanceEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public YmpeLanceEntity(World world, PlayerEntity owner, ItemStack stack) {
		super(ModEntityTypes.YMPE_LANCE, owner, world);
		this.stack = stack.copy();
	}

	@Override
	public void tick() {
		if(inGroundTime > 4) {
			dealtDamage = true;
			timeStuck = 140;
		}

		if(((dealtDamage && timeStuck >= 140) || isNoClip()) && getOwner() != null) {
			if(!isOwnerAlive()) {
				if(!getWorld().isClient && pickupType == PickupPermission.ALLOWED)
					dropStack(asItemStack(), 0.1F);

				discard();
			} else {
				setNoClip(true);
				Vec3d ownerDistance = getOwner().getEyePos().subtract(getPos());
				setPos(getX(), getY() + ownerDistance.y * 0.045 * 3, getZ());

				if(getWorld().isClient)
					lastRenderY = getY();

				setVelocity(getVelocity().multiply(0.95).add(ownerDistance.normalize().multiply(0.15)));
			}
		}

		if(target != null && getVehicle() != target)
			startRiding(target);

		super.tick();
	}

	@Override
	public ItemStack asItemStack() {
		return stack;
	}

	@Override
	@Nullable
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		if(dealtDamage || timeStuck >= 140)
			return null;

		return super.getEntityCollision(currentPosition, nextPosition);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity owner = getOwner();
		Entity target = entityHitResult.getEntity() instanceof EnderDragonPart part ? part.owner : entityHitResult.getEntity();
		float damage = 8F;

		if(target instanceof LivingEntity livingTarget) {
			damage += EnchantmentHelper.getAttackDamage(stack, livingTarget.getGroup());
		}

		DamageSource damageSource = getDamageSources().trident(this, owner == null ? this : owner);
		dealtDamage = true;

		if(target.damage(damageSource, damage)) {
			if(target.getType() == EntityType.ENDERMAN) {
				return;
			}

			if(target instanceof LivingEntity livingTarget) {
				this.target = livingTarget;
				if(owner instanceof LivingEntity livingOwner) {
					EnchantmentHelper.onUserDamaged(livingTarget, livingOwner);
					EnchantmentHelper.onTargetDamaged(livingOwner, livingTarget);
				}

				onHit(livingTarget);
			}
			startRiding(target, target.getType() != EntityType.ENDER_DRAGON);
			playSound(SoundEvents.ENTITY_ARROW_HIT, 1F, 1F);
		}
	}

	@Override
	public void tickRiding() {
		super.tickRiding();

		if (target != null && target == getVehicle() && !getWorld().isClient()) {
			YmpeThornsComponent thornsComponent = ModComponents.YMPE_THORNS.get(target);

			if (timeStuck >= 140 || target.isDead()) {
				target = null;
				stopRiding();
				return;
			}

			if (timeStuck % 40 == 0 && timeStuck > 0) {
				target.damage(getWorld().modDamageSources().ympe(), 4);

				if (thornsComponent.getThornProgress() < 3) {
					thornsComponent.incrementThornProgress(1);
				}
			}

			timeStuck++;
		}
	}

	@Override
	protected boolean tryPickup(PlayerEntity player) {
		return super.tryPickup(player) || isNoClip() && isOwner(player) && player.getInventory().insertStack(asItemStack());
	}

	@Override
	protected SoundEvent getHitSound() {
		return SoundEvents.ENTITY_ARROW_HIT;
	}

	@Override
	public void onPlayerCollision(PlayerEntity player) {
		if(isOwner(player) || getOwner() == null)
			super.onPlayerCollision(player);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);

		if(tag.contains("Lance", NbtElement.COMPOUND_TYPE))
			stack = ItemStack.fromNbt(tag.getCompound("Lance"));

		if(getWorld() instanceof ServerWorld serverWorld && tag.containsUuid("Target") && serverWorld.getEntity(tag.getUuid("Target")) instanceof LivingEntity targetEntity)
			target = targetEntity;

		dealtDamage = tag.getBoolean("HasDealtDamage");
		timeStuck = tag.getInt("TimeStuck");
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.put("Lance", stack.writeNbt(new NbtCompound()));

		if(target != null)
			tag.putUuid("Target", target.getUuid());

		tag.putBoolean("HasDealtDamage", this.dealtDamage);
		tag.putInt("TimeStuck", timeStuck);
	}

	private boolean isOwnerAlive() {
		Entity entity = getOwner();

		if(entity == null || !entity.isAlive())
			return false;

		return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
	}
}
