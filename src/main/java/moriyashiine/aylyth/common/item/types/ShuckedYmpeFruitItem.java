package moriyashiine.aylyth.common.item.types;

import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.AylythEntityComponents;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.item.potion.AylythPotions;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShuckedYmpeFruitItem extends Item {
	public ShuckedYmpeFruitItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		ItemStack stack = context.getStack();
		if (!ShuckedYmpeFruitItem.hasStoredEntity(stack) || player == null) {
			return super.useOnBlock(context);
		}
		ItemStack blightPotion = getBlightPotion(player.getInventory());
		if (blightPotion.isEmpty()) {
			return super.useOnBlock(context);
		}
		if (!world.isClient) {
			NbtCompound entityCompound = ShuckedYmpeFruitItem.getStoredEntity(stack);
			BlockPos pos = context.getBlockPos().offset(context.getSide());
			if (Registries.ENTITY_TYPE.get(new Identifier(entityCompound.getString("id"))).create((ServerWorld) world, entityCompound, null, pos, SpawnReason.SPAWN_EGG, true, false) instanceof MobEntity mob) {
				double x = mob.getX(), y = mob.getY(), z = mob.getZ();
				mob.readNbt(entityCompound);
				mob.teleport(x, y, z);
				mob.removeAttached(AylythEntityAttachmentTypes.PREVENT_DROPS);
				world.spawnEntity(mob);
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1, 1);
				// consume the entity-holding shucked fruit, give back an empty fruit to the player if in creative
				stack.decrement(1);
				if (player.getAbilities().creativeMode) {
					player.getInventory().offer(new ItemStack(AylythItems.SHUCKED_YMPE_FRUIT), true);
				}
				// consume a blight potion and return bottle
				AylythUtil.decreaseStack(blightPotion, player);
				player.getInventory().offerOrDrop(new ItemStack(Items.GLASS_BOTTLE));
				// deal a bit of damage to the player
				player.damage(player.getWorld().aylythDamageSources().shucking(), 1);
				// prevent healing for 2 minutes
				player.addStatusEffect(new StatusEffectInstance(AylythStatusEffects.CRIMSON_CURSE, 20 * 120));
			}
		}
		return ActionResult.success(world.isClient);
	}

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
				name = Registries.ENTITY_TYPE.get(new Identifier(entityCompound.getString("id"))).getName();
			}
			if (name != null) {
				tooltip.add(((MutableText) name).formatted(Formatting.GRAY));
			}
		}
	}

	public static ItemStack getBlightPotion(PlayerInventory inventory) {
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.getStack(i);
			if (stack.isOf(Items.POTION) && PotionUtil.getPotion(stack) == AylythPotions.BLIGHT) {
				return stack;
			}
		}

		return ItemStack.EMPTY;
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
