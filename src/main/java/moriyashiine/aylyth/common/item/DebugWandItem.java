package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.api.interfaces.Vital;
import moriyashiine.aylyth.common.block.entity.WoodyGrowthCacheBlockEntity;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DebugWandItem extends Item {
    public DebugWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var pos = context.getBlockPos();
        var player = context.getPlayer();
        if (player != null && player.getStackInHand(Hand.OFF_HAND).isOf(ModItems.WOODY_GROWTH_CACHE)) {
            var bItem = (BlockItem) ModItems.WOODY_GROWTH_CACHE;
            var ret = bItem.useOnBlock(context);
            if (!world.isClient() && world.getBlockEntity(pos.offset(context.getSide())) instanceof WoodyGrowthCacheBlockEntity be) {
                be.fill(Registry.ITEM.getRandom(world.random).map(itemRegistryEntry -> itemRegistryEntry.value().getDefaultStack()).orElse(ItemStack.EMPTY));
            }
            return ret;
        }
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()){
            if(user.isSneaking()){
                Vital.of(user).ifPresent(vital -> {
                    if(vital.getVitalThuribleLevel() == 0){
                        vital.setVitalThuribleLevel(5);
                    }else{
                        vital.setVitalThuribleLevel(0);
                    }
                });
            }else{
                ScionEntity.summonPlayerScion(user);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Use to Summon a Scion-copy of you"));
        tooltip.add(Text.literal("Shift and Use to give you VitalThurible Buff"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
