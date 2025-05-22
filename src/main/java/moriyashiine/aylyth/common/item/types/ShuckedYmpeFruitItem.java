package moriyashiine.aylyth.common.item.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapDecoder;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.item.potion.AylythPotions;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.UseRemainderComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ShuckedYmpeFruitItem extends Item {
	public static final MapDecoder<String> CUSTOM_NAME_LENS = Codec.STRING.fieldOf("CustomName");

	public ShuckedYmpeFruitItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		ItemStack stack = context.getStack();
		NbtComponent storedEntity = stack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
		if (storedEntity.isEmpty() || player == null) {
			return super.useOnBlock(context);
		}
		ItemStack blightPotion = getBlightPotion(player.getInventory());
		if (blightPotion.isEmpty()) {
			return super.useOnBlock(context);
		}
		if (world instanceof ServerWorld serverWorld) {
			BlockPos pos = context.getBlockPos().offset(context.getSide());
			EntityType<?> entityType = storedEntity.getRegistryValueOfId(serverWorld.getRegistryManager(), RegistryKeys.ENTITY_TYPE);
			if (entityType == null) {
				return super.useOnBlock(context);
			}
			if (entityType.create(serverWorld, storedEntity::applyToEntity, pos, SpawnReason.SPAWN_ITEM_USE, true, false) instanceof MobEntity mob) {
				stack.remove(DataComponentTypes.ENTITY_DATA);
				mob.removeAttached(AylythEntityAttachmentTypes.PREVENT_DROPS);
				world.spawnEntity(mob);
				world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1, 1);
				// consume the entity-holding shucked fruit, give back an empty fruit to the player if in creative
				stack.decrement(1);
				if (player.getAbilities().creativeMode && !player.getInventory().containsAny(Set.of(AylythItems.SHUCKED_YMPE_FRUIT))) {
					player.getInventory().offer(new ItemStack(AylythItems.SHUCKED_YMPE_FRUIT), true);
				}
				// consume a blight potion and return bottle
				blightPotion.decrementUnlessCreative(1, player);
				player.getInventory().offerOrDrop(
						Optional.ofNullable(blightPotion.get(DataComponentTypes.USE_REMAINDER))
								.map(UseRemainderComponent::convertInto)
								.orElse(new ItemStack(Items.GLASS_BOTTLE))
				);
				// deal a bit of damage to the player
				player.damage(serverWorld, player.getWorld().aylythDamageSources().shucking(), 1);
				// prevent healing for 2 minutes
				player.addStatusEffect(new StatusEffectInstance(AylythStatusEffects.CRIMSON_CURSE, 20 * 120));
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		// TODO: Check this works
		super.appendTooltip(stack, context, tooltip, type);
		NbtComponent storedEntity = stack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
		if (storedEntity.isEmpty()) {
			return;
		}
		Text name = storedEntity.get(CUSTOM_NAME_LENS).result()
				.map(s -> (Text) Text.literal(s))
				.orElseGet(() -> {
					EntityType<?> entityType = storedEntity.getRegistryValueOfId(context.getRegistryLookup(), RegistryKeys.ENTITY_TYPE);
					if (entityType != null) {
						return entityType.getName();
					}
					return null;
				});
		if (name != null) {
			tooltip.add(((MutableText) name).formatted(Formatting.GRAY));
		}
	}

	public static ItemStack getBlightPotion(PlayerInventory inventory) {
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.getStack(i);
			if (stack.isOf(Items.POTION)) {
				PotionContentsComponent potionContents = stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
				if (potionContents.potion().map(entry -> entry.matches(AylythPotions.BLIGHT)).orElse(false)) {
					return stack;
				}
			}
		}

		return ItemStack.EMPTY;
	}
}
