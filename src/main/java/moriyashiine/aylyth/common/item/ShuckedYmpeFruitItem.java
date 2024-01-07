package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.registry.ModComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
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
		if (ShuckedYmpeFruitItem.hasStoredEntity(stack)) {
			if (!world.isClient) {
				NbtCompound entityCompound = ShuckedYmpeFruitItem.getStoredEntity(stack);
				BlockPos pos = context.getBlockPos().offset(context.getSide());
				if (Registry.ENTITY_TYPE.get(new Identifier(entityCompound.getString("id"))).create((ServerWorld) world, null, null, null, pos, SpawnReason.SPAWN_EGG, true, false) instanceof MobEntity mob) {
					double x = mob.getX(), y = mob.getY(), z = mob.getZ();
					mob.readNbt(entityCompound);
					mob.setUuid(UUID.randomUUID());
					mob.teleport(x, y, z);
					ModComponents.PREVENT_DROPS.get(mob).setPreventsDrops(false);
					world.spawnEntity(mob);
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
		if (ShuckedYmpeFruitItem.hasStoredEntity(stack)) {
			Text name;
			NbtCompound entityCompound = ShuckedYmpeFruitItem.getStoredEntity(stack);
			if (entityCompound.contains("CustomName")) {
				name = Text.Serializer.fromJson(entityCompound.getString("CustomName"));
			}
			else {
				name = Registry.ENTITY_TYPE.get(new Identifier(entityCompound.getString("id"))).getName();
			}
			if (name != null) {
				tooltip.add(((MutableText) name).formatted(Formatting.GRAY));
			}
		}
	}

	public static boolean hasStoredEntity(ItemStack shuckedStack) {
		return shuckedStack.hasNbt() && shuckedStack.getNbt().contains("StoredEntity");
	}

	public static NbtCompound getStoredEntity(ItemStack shuckedStack) {
		return shuckedStack.getNbt().getCompound("StoredEntity");
	}

	public static void setStoredEntity(ItemStack shuckedStack, MobEntity mob) {
		NbtCompound entityCompound = new NbtCompound();
		mob.saveSelfNbt(entityCompound);
		shuckedStack.getOrCreateNbt().put("StoredEntity", entityCompound);
	}
}
