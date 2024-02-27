package moriyashiine.aylyth.common.entity.projectile;

import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModParticles;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SphereEntity extends ProjectileEntity {
    public SphereEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }


    public SphereEntity(World world, WreathedHindEntity owner) {
        this(ModEntityTypes.SPHERE_ENTITY, world);
        this.setOwner(owner);
        this.setPosition(
                owner.getX() - (double)(owner.getWidth() + 1.0F) * 0.5 * (double) MathHelper.sin(owner.bodyYaw * MathHelper.RADIANS_PER_DEGREE),
                owner.getEyeY() - 0.1F,
                owner.getZ() + (double)(owner.getWidth() + 1.0F) * 0.5 * (double) MathHelper.cos(owner.bodyYaw * MathHelper.RADIANS_PER_DEGREE)
        );
    }



    @Override
    public void tick() {
        super.tick();
        getWorld().addParticle(ModParticles.PILOT_LIGHT, getParticleX(0.25), getY() + 0.125F + MathHelper.nextDouble(random, -0.125, 0.125), getParticleZ(0.25), 0.25, 0.25, 1);


        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        float g = 0.99F;
        float h = 0.06F;
        if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.discard();
        } else if (this.isInsideWaterOrBubbleColumn()) {
            this.discard();
        } else {
            this.setVelocity(vec3d.multiply(g));
            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0, -h, 0.0));
            }

            this.setPosition(d, e, f);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            entityHitResult.getEntity().damage(getDamageSources().magic(), 7.0F);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        double d = packet.getVelocityX();
        double e = packet.getVelocityY();
        double f = packet.getVelocityZ();

        for(int i = 0; i < 7; ++i) {
            double g = 0.4 + 0.1 * (double)i;
            this.getWorld().addParticle(ModParticles.PILOT_LIGHT, this.getX(), this.getY(), this.getZ(), d * g, e, f * g);
        }

        this.setVelocity(d, e, f);
    }
    
    @Override
    protected void initDataTracker() {
        
    }
}
