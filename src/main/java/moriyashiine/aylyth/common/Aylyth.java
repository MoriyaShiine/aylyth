package moriyashiine.aylyth.common;

import moriyashiine.aylyth.client.network.packet.SpawnShuckParticlesPacket;
import moriyashiine.aylyth.common.recipe.YmpeDaggerDropRecipe;
import moriyashiine.aylyth.common.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.Vec3d;

public class Aylyth implements ModInitializer {
	public static final String MOD_ID = "aylyth";
	
	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModItems.init();
		ModSoundEvents.init();
		ModRecipeTypes.init();
		ModWorldGenerators.init();
		ModBoatTypes.init();
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
			if (entity instanceof LivingEntity living && living.getMainHandStack().isOf(ModItems.YMPE_DAGGER)) {
				boolean shucked = false;
				if (!(killedEntity instanceof PlayerEntity)) {
					ItemStack offhand = living.getOffHandStack();
					if (offhand.isOf(ModItems.SHUCKED_YMPE_FRUIT)) {
						if (!offhand.hasNbt() || !offhand.getNbt().contains("StoredEntity")) {
							killedEntity.setHealth(killedEntity.getMaxHealth());
							killedEntity.clearStatusEffects();
							killedEntity.extinguish();
							killedEntity.setFrozenTicks(0);
							killedEntity.setVelocity(Vec3d.ZERO);
							killedEntity.fallDistance = 0;
							killedEntity.knockbackVelocity = 0;
							PlayerLookup.tracking(killedEntity).forEach(trackingPlayer -> SpawnShuckParticlesPacket.send(trackingPlayer, killedEntity));
							world.playSound(null, killedEntity.getBlockPos(), ModSoundEvents.ENTITY_GENERIC_SHUCKED, killedEntity.getSoundCategory(), 1, killedEntity.getSoundPitch());
							NbtCompound entityCompound = new NbtCompound();
							killedEntity.saveSelfNbt(entityCompound);
							offhand.getOrCreateNbt().put("StoredEntity", entityCompound);
							killedEntity.remove(Entity.RemovalReason.DISCARDED);
							shucked = true;
						}
					}
				}
				if (!shucked) {
					for (YmpeDaggerDropRecipe recipe : world.getRecipeManager().listAllOfType(ModRecipeTypes.YMPE_DAGGER_DROP_RECIPE_TYPE)) {
						if (recipe.entity_type.equals(killedEntity.getType()) && world.random.nextFloat() < recipe.chance * (EnchantmentHelper.getLooting(living) + 1)) {
							ItemStack drop = recipe.getOutput().copy();
							if (recipe.entity_type == EntityType.PLAYER) {
								drop.getOrCreateNbt().putString("SkullOwner", killedEntity.getName().getString());
							}
							ItemScatterer.spawn(world, killedEntity.getX() + 0.5, killedEntity.getY() + 0.5, killedEntity.getZ() + 0.5, drop);
						}
					}
				}
			}
		});
	}
}
