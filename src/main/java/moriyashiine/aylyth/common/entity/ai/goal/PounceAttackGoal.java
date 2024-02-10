package moriyashiine.aylyth.common.entity.ai.goal;

import moriyashiine.aylyth.common.entity.mob.FaunaylythianEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class PounceAttackGoal extends Goal {
    private final FaunaylythianEntity faunaylythian;
    private LivingEntity target;
    private final float velocity;

    public PounceAttackGoal(FaunaylythianEntity faunaylythian, float velocity) {
        this.faunaylythian = faunaylythian;
        this.velocity = velocity;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.faunaylythian.hasPassengers()) {
            return false;
        } else {
            this.target = this.faunaylythian.getTarget();
            if (this.target == null) {
                return false;
            } else {
                double d = this.faunaylythian.squaredDistanceTo(this.target);
                if (d < 8.0 || d > 24.0) {
                    return false;
                } else if (!this.faunaylythian.isOnGround()) {
                    return false;
                } else {
                    return this.faunaylythian.getRandom().nextInt(toGoalTicks(5)) == 0;
                }
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.faunaylythian.isOnGround();
    }

    @Override
    public void start() {
        this.faunaylythian.getDataTracker().set(FaunaylythianEntity.POUNCING, true);
        Vec3d vec3d = this.faunaylythian.getVelocity();
        Vec3d vec3d2 = new Vec3d(this.target.getX() - this.faunaylythian.getX(), 0.0, this.target.getZ() - this.faunaylythian.getZ());
        if (vec3d2.lengthSquared() > 1.0E-7) {
            vec3d2 = vec3d2.normalize().multiply(0.4).add(vec3d.multiply(0.2));
        }

        this.faunaylythian.setVelocity(1.5F * vec3d2.x, this.velocity, 1.5F * vec3d2.z);
    }

    @Override
    public void stop() {
        this.faunaylythian.getDataTracker().set(FaunaylythianEntity.POUNCING, false);
        super.stop();
    }
}
