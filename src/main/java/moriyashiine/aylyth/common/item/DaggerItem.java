package moriyashiine.aylyth.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import moriyashiine.aylyth.common.registry.AylythItems;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("UnstableApiUsage")
public class DaggerItem extends SwordItem {
    public DaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float attackReach, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.attributeModifiers);
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(YmpeLanceItem.BASE_REACH_MODIFIER, "Weapon modifier", attackReach, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Block strippedBlock = AxeItemAccessor.getStrippedBlocks().get(world.getBlockState(pos).getBlock());
        if (strippedBlock != null) {
            BlockState strippedState = strippedBlock.getStateWithProperties(world.getBlockState(pos));
            world.setBlockState(pos, strippedState);
            if (context.getPlayer() != null) {
                context.getStack().damage(1, context.getPlayer(), player -> player.sendToolBreakStatus(context.getHand()));
                try (Transaction transaction = Transaction.openOuter()) {
                    PlayerInventoryStorage storage = PlayerInventoryStorage.of(context.getPlayer());
                    storage.offerOrDrop(ItemVariant.of(AylythItems.BARK), 1, transaction);
                    transaction.commit();
                }
            }
            world.playSound(null, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }
}
