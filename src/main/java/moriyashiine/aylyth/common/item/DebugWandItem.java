package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.api.interfaces.Vital;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DebugWandItem extends Item {
    public DebugWandItem(Settings settings) {
        super(settings);
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
