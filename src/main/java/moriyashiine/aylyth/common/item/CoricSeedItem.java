package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import moriyashiine.aylyth.common.entity.mob.SoulmouldEntity;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CoricSeedItem extends Item {
    public CoricSeedItem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        BlockPos pos = ctx.getBlockPos();
        Direction dir = ctx.getPlayerFacing();
        World world = ctx.getWorld();
        Direction side = ctx.getSide();
        ItemStack stack = ctx.getStack();

        trySummonBoneFly(world, player, pos, stack, dir, side);
        trySummonSoulMould(world, player, pos, stack, dir, side);

        return super.useOnBlock(ctx);
    }

    private void trySummonSoulMould(World world, PlayerEntity player, BlockPos pos, ItemStack stack, Direction dir, Direction side) {
        if (player != null && world.getBlockState(pos).getBlock().equals(Blocks.NETHERITE_BLOCK) && isValidForSoulMould(world, pos)) {
            world.breakBlock(pos, false);
            world.breakBlock(pos.down(), false);
            world.breakBlock(pos.down().down(), false);
            SoulmouldEntity soulmouldEntity = new SoulmouldEntity(ModEntityTypes.SOULMOULD, world);
            soulmouldEntity.refreshPositionAndAngles(pos.down().down(), 0.0F, 0.0F);
            soulmouldEntity.setOwner(player);
            soulmouldEntity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.GLAIVE));
            soulmouldEntity.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 0.0F;
            world.spawnEntity(soulmouldEntity);
            stack.decrement(1);
        }
    }



    private void trySummonBoneFly(World world, PlayerEntity player, BlockPos pos, ItemStack stack, Direction dir, Direction side) {
        if (player != null && world.getBlockState(pos).getBlock().equals(Blocks.BONE_BLOCK) && isValidForBoneFly(world, pos, dir) && player.getOffHandStack().getItem().equals(Items.PHANTOM_MEMBRANE) && player.getOffHandStack().getCount() > 15) {
            world.breakBlock(pos, false);

            int i;
            for (i = 0; i < 7; ++i) {
                world.breakBlock(pos.offset(dir, i), false);
            }

            for (i = 0; i < 4; ++i) {
                world.breakBlock(pos.offset(dir, i).offset(Direction.UP), false);
            }

            BoneflyEntity bonefly = new BoneflyEntity(ModEntityTypes.BONEFLY, world);
            bonefly.refreshPositionAndAngles(pos.offset(side), 0.0F, 0.0F);
            bonefly.setOwner(player);
            world.spawnEntity(bonefly);
            player.getOffHandStack().decrement(16);
            stack.decrement(1);
        }
    }

    private boolean isValidForSoulMould(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock().equals(Blocks.SOUL_SOIL)
                && (world.getBlockState(pos.down().down()).getBlock().equals(Blocks.DEEPSLATE)
                || world.getBlockState(pos.down().down()).getBlock().equals(Blocks.DEEPSLATE_BRICKS)
                || world.getBlockState(pos.down().down()).getBlock().equals(Blocks.DEEPSLATE_TILES)
                || world.getBlockState(pos.down().down()).getBlock().equals(Blocks.CHISELED_DEEPSLATE)
                || world.getBlockState(pos.down().down()).getBlock().equals(Blocks.COBBLED_DEEPSLATE));
    }

    private boolean isValidForBoneFly(World world, BlockPos pos, Direction dir) {
        return world.getBlockState(pos.offset(dir)).getBlock().equals(Blocks.SOUL_SOIL)
                && world.getBlockState(pos.offset(dir).offset(Direction.UP)).getBlock().equals(Blocks.BONE_BLOCK)
                && world.getBlockState(pos.offset(dir, 2).offset(Direction.UP)).getBlock().equals(Blocks.SOUL_SOIL)
                && world.getBlockState(pos.offset(dir, 3).offset(Direction.UP)).getBlock().equals(Blocks.SOUL_SOIL)
                && world.getBlockState(pos.offset(dir, 2)).getBlock().equals(Blocks.SOUL_SOIL)
                && world.getBlockState(pos.offset(dir, 3)).getBlock().equals(Blocks.SOUL_SOIL)
                && world.getBlockState(pos.offset(dir, 4)).getBlock().equals(Blocks.SOUL_SOIL)
                && world.getBlockState(pos.offset(dir, 5)).getBlock().equals(Blocks.SOUL_SOIL)
                && world.getBlockState(pos.offset(dir, 6)).getBlock().equals(Blocks.BONE_BLOCK);
    }
}
