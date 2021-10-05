package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.registry.ModDimensions;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class AylythianHeartItem extends Item {
	public AylythianHeartItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			if (user.isCreative() || user.experienceLevel >= 5) {
				if (user.world.getRegistryKey() != ModDimensions.AYLYTH) {
					return ItemUsage.consumeHeldItem(world, user, hand);
				}
			}
		}
		return super.use(world, user, hand);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!world.isClient && user instanceof PlayerEntity player && world.getRegistryKey() != ModDimensions.AYLYTH) {
			if (player.isCreative() || player.experienceLevel >= 5) {
				FabricDimensions.teleport(user, world.getServer().getWorld(ModDimensions.AYLYTH), new TeleportTarget(Vec3d.of(getSafePosition(world, user.getBlockPos())), Vec3d.ZERO, user.headYaw, user.getPitch()));
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
				user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200));
				if (!player.isCreative()) {
					player.addExperience(-55);
					stack.decrement(1);
				}
			}
		}
		return super.finishUsing(stack, world, user);
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 40;
	}
	
	//todo put the player in a safe position
	public static BlockPos getSafePosition(World world, BlockPos pos) {
		return pos;
	}
}
