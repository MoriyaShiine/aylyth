package moriyashiine.aylyth.common.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ShuckedYmpeFruitItem extends Item {
	public ShuckedYmpeFruitItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		ItemStack stack = context.getStack();
		if (stack.hasNbt() && stack.getNbt().contains("StoredEntity")) {
			if (!world.isClient) {
				NbtCompound entityCompound = stack.getNbt().getCompound("StoredEntity");
				BlockPos pos = context.getBlockPos().offset(context.getSide());
				LivingEntity entity = (LivingEntity) Registry.ENTITY_TYPE.get(new Identifier(entityCompound.getString("id"))).create((ServerWorld) world, null, null, null, pos, SpawnReason.SPAWN_EGG, true, false);
				if (entity != null) {
					double x = entity.getX(), y = entity.getY(), z = entity.getZ();
					entity.readNbt(entityCompound);
					entity.setUuid(UUID.randomUUID());
					entity.teleport(x, y, z);
					world.spawnEntity(entity);
					world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1, 1);
					if (context.getPlayer() == null || !context.getPlayer().isCreative()) {
						stack.decrement(1);
					}
				}
			}
			return ActionResult.success(world.isClient);
		}
		return super.useOnBlock(context);
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		if (stack.hasNbt() && stack.getNbt().contains("StoredEntity")) {
			Text name;
			NbtCompound entityCompound = stack.getNbt().getCompound("StoredEntity");
			if (entityCompound.contains("CustomName")) {
				name = Text.Serializer.fromJson(entityCompound.getString("CustomName"));
			}
			else {
				name = Registry.ENTITY_TYPE.get(new Identifier(entityCompound.getString("id"))).getName();
			}
			tooltip.add(((MutableText) name).formatted(Formatting.GRAY));
		}
	}
}
