package moriyashiine.aylyth.common.item.types;

import com.google.common.collect.ImmutableMultimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class DaggerItem extends SwordItem {
    public static final Identifier FALLBACK_STRIP_TABLE = Aylyth.id("strip/bark_from_logs_or_wood");

    public DaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float attackReach, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.attributeModifiers);
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(YmpeLanceItem.BASE_REACH_MODIFIER, "Weapon modifier", attackReach, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
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
                Identifier lootTableId = Registries.BLOCK.getId(state.getBlock()).withPrefixedPath("strip/");
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
            itemStack.damage(1, context.getPlayer(), player -> player.sendToolBreakStatus(context.getHand()));
        }
        BlockState strippedState = strippedBlock.getStateWithProperties(state);
        world.setBlockState(pos, strippedState);
        world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return ActionResult.success(world.isClient);
    }

    public ObjectList<ItemStack> getStripLoot(Identifier lootTable, ServerWorld serverWorld, BlockPos origin, BlockState state, @Nullable ItemStack tool) {
        LootTable table = serverWorld.getServer().getLootManager().getElement(LootDataType.LOOT_TABLES, lootTable);
        if (table == null) {
            table = serverWorld.getServer().getLootManager().getElement(LootDataType.LOOT_TABLES, FALLBACK_STRIP_TABLE);
            if (table == null) {
                return ObjectLists.emptyList();
            }
        }
        return table.generateLoot(new LootContextParameterSet.Builder(serverWorld)
                .add(LootContextParameters.ORIGIN, origin.toCenterPos())
                .add(LootContextParameters.BLOCK_STATE, state)
                .addOptional(LootContextParameters.TOOL, tool)
                .build(AylythLootContextTypes.STRIP)
        );
    }
}
