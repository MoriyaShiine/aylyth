package moriyashiine.aylyth.common.entity.type;

import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public class RootPropEntity extends EvokerFangsEntity {
    private int warmup;
    private boolean startedAttack;
    private int ticksLeft;
    private boolean playingAnimation;

    public RootPropEntity(EntityType<? extends EvokerFangsEntity> entityType, World world) {
        super(entityType, world);
        this.ticksLeft = 22;
    }

    public RootPropEntity(World world, double x, double y, double z, float yaw, int warmup, LivingEntity owner) {
        this(AylythEntityTypes.ROOT_PROP, world);
        this.warmup = warmup;
        this.setYaw(yaw * 57.295776F);
        this.setPosition(x, y, z);
    }


    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) {
            if (this.playingAnimation) {
                --this.ticksLeft;
                if (this.ticksLeft == 14) {
                    for(int i = 0; i < 12; ++i) {
                        double d = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getWidth() * 0.5;
                        double e = this.getY() + 0.05 + this.random.nextDouble();
                        double f = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getWidth() * 0.5;
                        double g = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
                        double h = 0.3 + this.random.nextDouble() * 0.3;
                        double j = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
                        this.getWorld().addParticle(ParticleTypes.SOUL, d, e + 1.0, f, g, h, j);
                    }
                }
            }
        } else if (--this.warmup < 0) {
            if (this.warmup == -8) {
                List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(0.2, 0.0, 0.2));

                for (LivingEntity livingEntity : list) {
                    this.damage(livingEntity);
                }
            }

            if (!this.startedAttack) {
                this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_ATTACK_SOUND);
                this.startedAttack = true;
            }

            if (--this.ticksLeft < 0) {
                this.discard();
            }
        }

    }

    private void damage(LivingEntity target) {
        LivingEntity livingEntity = this.getOwner();
        if (target.isAlive() && !target.isInvulnerable() && target != livingEntity) {
            if (livingEntity == null) {
                target.damage(getDamageSources().magic(), 6.0F);
            } else {
                if (livingEntity.isTeammate(target)) {
                    return;
                }
                target.damage(getDamageSources().indirectMagic(this, livingEntity), 6.0F);
            }

        }
    }

    @Override
    public void handleStatus(byte status) {
        super.handleStatus(status);
        if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
            this.playingAnimation = true;
            if (!this.isSilent()) {
                this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_EVOKER_FANGS_ATTACK, this.getSoundCategory(), 1.0F, this.random.nextFloat() * 0.2F + 0.85F, false);
            }
        }

    }

    @Override
    public float getAnimationProgress(float tickDelta) {
        if (!this.playingAnimation) {
            return 0.0F;
        } else {
            int i = this.ticksLeft - 2;
            return i <= 0 ? 1.0F : 1.0F - ((float)i - tickDelta) / 20.0F;
        }
    }
}
