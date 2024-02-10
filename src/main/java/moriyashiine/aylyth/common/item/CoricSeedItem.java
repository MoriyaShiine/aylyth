package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.construct.Constructs;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoricSeedItem extends Item {
    public CoricSeedItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        if (player == null) {
            return ActionResult.FAIL;
        }
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        ItemStack stack = ctx.getStack();

        Constructs.Construct<?> construct = Constructs.get(world.getBlockState(pos).getBlock());
        if (construct != null) {
            if (!(world instanceof ServerWorld serverWorld)) {
                if (construct.test(world, pos)) {
                    return ActionResult.CONSUME;
                }
            } else {
                Entity entity = construct.constructIfMatching(serverWorld, ctx, pos);
                if (entity != null) {
                    world.spawnEntity(entity);
                    AylythUtil.decreaseStack(stack, player);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.useOnBlock(ctx);
    }
}
