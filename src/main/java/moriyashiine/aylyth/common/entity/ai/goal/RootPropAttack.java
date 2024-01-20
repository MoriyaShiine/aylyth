package moriyashiine.aylyth.common.entity.ai.goal;

import moriyashiine.aylyth.common.entity.RootPropEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

public class RootPropAttack extends Goal {
    protected final PathAwareEntity mob;

    public RootPropAttack(PathAwareEntity mob) {
        this.mob = mob;
    }

    protected int spellCooldown;
    protected int startTime;

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = mob.getTarget();
        if (livingEntity != null && livingEntity.isAlive()) {
            return mob.age >= this.startTime;
        } else {
            return false;
        }
    }

    public boolean shouldContinue() {
        LivingEntity livingEntity = mob.getTarget();
        return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0;
    }

    public void start() {
        this.spellCooldown = this.getTickCount(this.getInitialCooldown());
        this.startTime = mob.age + this.startTimeDelay();
        SoundEvent soundEvent = this.getSoundPrepare();
        if (soundEvent != null) {
            mob.playSound(soundEvent, 1.0F, 1.0F);
        }
    }

    public void tick() {
        --this.spellCooldown;
        if (this.spellCooldown == 0) {
            this.castSpell();
            mob.playSound(getCastSpellSound(), 1.0F, 1.0F);
        }

    }

    protected void castSpell() {
        LivingEntity livingEntity = mob.getTarget();
        double d = Math.min(livingEntity.getY(), mob.getY());
        double e = Math.max(livingEntity.getY(), mob.getY()) + 1.0;
        float f = (float) MathHelper.atan2(livingEntity.getZ() - mob.getZ(), livingEntity.getX() - mob.getX());

        int i;
        for (i = 0; i < 8; i++) {
            float angle = 2 * 3.1415927F * (i / 8F);
            this.spawnRoot(
                    livingEntity.getX() + (double) MathHelper.cos(angle) ,
                    livingEntity.getZ() + (double) MathHelper.sin(angle) ,
                    livingEntity.getY() + 2,
                    d,
                    f + (float)i * 3.1415927F * 0.4F,
                    0);
        }
    }

    private void spawnRoot(double x, double z, double maxY, double y, float yaw, int warmup) {
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        boolean bl = false;
        double d = 0.0;

        do {
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState = mob.getWorld().getBlockState(blockPos2);
            if (blockState.isSideSolidFullSquare(mob.getWorld(), blockPos2, Direction.UP)) {
                if (!mob.getWorld().isAir(blockPos)) {
                    BlockState blockState2 = mob.getWorld().getBlockState(blockPos);
                    VoxelShape voxelShape = blockState2.getCollisionShape(mob.getWorld(), blockPos);
                    if (!voxelShape.isEmpty()) {
                        d = voxelShape.getMax(Direction.Axis.Y);
                    }
                }

                bl = true;
                break;
            }

            blockPos = blockPos.down();
        } while(blockPos.getY() >= MathHelper.floor(maxY) - 1);

        if (bl) {
            mob.getWorld().spawnEntity(new RootPropEntity(mob.getWorld(), x, (double)blockPos.getY() + d, z, yaw, warmup, mob));
        }

    }

    protected SoundEvent getCastSpellSound() {
        return SoundEvents.ENTITY_TURTLE_EGG_BREAK;
    }

    protected SoundEvent getSoundPrepare() {
        return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
    }

    protected int getInitialCooldown() {
        return 20;
    }

    protected int startTimeDelay() {
        return 100;
    }
}
