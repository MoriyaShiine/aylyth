package moriyashiine.aylyth.common.item.types;

import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.entity.AylythAttributes;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class DaggerItem extends SwordItem {
    public static final RegistryKey<LootTable> FALLBACK_STRIP_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Aylyth.id("strip/bark_from_logs_or_wood"));

    public DaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float attackReach, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        settings.attributeModifiers(
                AttributeModifiersComponent.builder()
                        .add(
                                EntityAttributes.ATTACK_DAMAGE,
                                new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, attackDamage + toolMaterial.attackDamageBonus(), EntityAttributeModifier.Operation.ADD_VALUE),
                                AttributeModifierSlot.MAINHAND
                        )
                        .add(
                                EntityAttributes.ATTACK_SPEED,
                                new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                                AttributeModifierSlot.MAINHAND
                        )
                        .add(
                                EntityAttributes.ENTITY_INTERACTION_RANGE,
                                new EntityAttributeModifier(AylythAttributes.BASE_ENTITY_INTERACTION_RANGE, attackReach, EntityAttributeModifier.Operation.ADD_VALUE),
                                AttributeModifierSlot.MAINHAND
                        )
                        .build()
        );
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack itemStack = context.getStack();
        if (!itemStack.isIn(AylythItemTags.STRIPS_OFF_BARK)) {
            return super.useOnBlock(context);
        }

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block strippedBlock = AxeItemAccessor.getStrippedBlocks().get(state.getBlock());
        if (strippedBlock == null) {
            return super.useOnBlock(context);
        }

        if (context.getPlayer() != null) {
            if (world instanceof ServerWorld serverWorld) {
                RegistryKey<LootTable> lootTableId = state.getBlock().getLootTableKey().map(key -> RegistryKey.of(key.getRegistryRef(), key.getValue().withPrefixedPath("strip/"))).orElse(null);
                if (lootTableId != null) {
                    ObjectList<ItemStack> loot = getStripLoot(lootTableId, serverWorld, context.getBlockPos(), state, itemStack);
                    if (!loot.isEmpty()) {
                        PlayerInventoryStorage storage = PlayerInventoryStorage.of(context.getPlayer());
                        try (Transaction transaction = Transaction.openOuter()) {
                            for (ItemStack stack : loot) {
                                storage.offerOrDrop(ItemVariant.of(stack), stack.getCount(), transaction);
                            }
                            transaction.commit();
                        }
                    }
                }
            }
            itemStack.damage(1, context.getPlayer(), context.getHand() == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }
        BlockState strippedState = strippedBlock.getStateWithProperties(state);
        world.setBlockState(pos, strippedState);
        world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return ActionResult.SUCCESS;
    }

    public ObjectList<ItemStack> getStripLoot(RegistryKey<LootTable> lootTable, ServerWorld serverWorld, BlockPos origin, BlockState state, @Nullable ItemStack tool) {
        LootTable table = serverWorld.getServer().getReloadableRegistries().getLootTable(lootTable);
        if (table == null) {
            table = serverWorld.getServer().getReloadableRegistries().getLootTable(FALLBACK_STRIP_TABLE);
            if (table == null) {
                return ObjectLists.emptyList();
            }
        }
        return table.generateLoot(new LootWorldContext.Builder(serverWorld)
                .add(LootContextParameters.ORIGIN, origin.toCenterPos())
                .add(LootContextParameters.BLOCK_STATE, state)
                .addOptional(LootContextParameters.TOOL, tool)
                .build(AylythLootContextTypes.STRIP)
        );
    }
}
