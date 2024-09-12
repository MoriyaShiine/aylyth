package moriyashiine.aylyth.common.item.type;

import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.block.type.WoodyGrowthCacheBlock;
import moriyashiine.aylyth.common.component.entity.YmpeInfestationComponent;
import moriyashiine.aylyth.common.entity.type.mob.ScionEntity;
import moriyashiine.aylyth.common.registry.ModEntityComponents;
import moriyashiine.aylyth.common.item.AylythItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class DebugWandItem extends Item {
    public DebugWandItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        if (player != null && player.getStackInHand(Hand.OFF_HAND).isOf(AylythItems.WOODY_GROWTH_CACHE)) {
            if (!world.isClient()) {
                WoodyGrowthCacheBlock.spawnInventory(world, pos, player);
            }
        } else if (player.isSneaking()) {
            player.damage(world.modDamageSources().ympe(), Integer.MAX_VALUE);
        }
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()){
            if(user.isSneaking()){
                VitalHealthHolder.of(user).ifPresent(vital -> {
                    if(vital.getCurrentVitalHealth() == 0){
                        vital.setCurrentVitalHealth(10);
                    }else{
                        vital.setCurrentVitalHealth(0);
                    }
                });
            }else{
                if (user.getOffHandStack().isOf(AylythItems.YMPE_FRUIT)) {
                    Optional<YmpeInfestationComponent> optional = ModEntityComponents.YMPE_INFESTATION.maybeGet(user);
                    optional.ifPresent(ympeInfestationComponent -> {
                        ympeInfestationComponent.setStage((byte)(ympeInfestationComponent.getStage() + 1));
                        ympeInfestationComponent.setInfestationTimer((short)2400);
                    });
                } /*else if (user.getOffHandStack().isOf(ModItems.MYSTERIOUS_SKETCH)) {
                    ItemStack page = user.getOffHandStack();
                    if (page.hasNbt() && page.getNbt().contains("PageId")) {
                        String nextPage = switch (page.getNbt().getString("PageId")) {
                            case "aylyth:coric_seed" -> "aylyth:soulmould";
                            case "aylyth:soulmould" -> "aylyth:bonefly";
                            case "aylyth:bonefly" -> "aylyth:tulpa";
                            case "aylyth:tulpa" -> "aylyth:coric_seed";
                            default -> "aylyth:coric_seed";
                        };
                        page.getNbt().putString("PageId", nextPage);
                    } else {
                        page.setSubNbt("PageId", NbtString.of("aylyth:coric_seed"));
                    }
                }*/ else {
                    ScionEntity.summonPlayerScion(user);
                }
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Use to Summon a Scion-copy of you."));
        tooltip.add(Text.literal("Use on Block Woody growth in off-hand"));
        tooltip.add(Text.literal("to spawn cache"));
        tooltip.add(Text.literal("While pressing shift:"));
        tooltip.add(Text.literal("Use to give you VitalThurible Buff."));
        tooltip.add(Text.literal("Use on block to deal deal max Ympe damage to yourself"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
