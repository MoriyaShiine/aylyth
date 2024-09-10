package moriyashiine.aylyth.common.entity.projectile;

import moriyashiine.aylyth.common.item.ThornFlechetteItem;
import moriyashiine.aylyth.common.registry.AylythEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ThornFlechetteEntity extends PersistentProjectileEntity {
    private static final TrackedData<ItemStack> STACK = DataTracker.registerData(ThornFlechetteEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public ThornFlechetteEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThornFlechetteEntity(LivingEntity owner, World world) {
        super(AylythEntityTypes.THORN_FLECHETTE, owner, world);
        this.setOwner(owner);
    }

    public void setStack(ItemStack stack) {
        this.dataTracker.set(STACK, stack);
    }

    public ItemStack getStack() {
        return this.dataTracker.get(STACK);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(STACK, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        super.tick();
        this.addVelocity(0, -0.05, 0);
    }

    @Override
    protected void onHit(LivingEntity target) {
        if (getStack().getItem() instanceof ThornFlechetteItem item) {
            if (item.getEffect() != null) {
                if (random.nextFloat() < item.getEffect().chance()) {
                    target.addStatusEffect(new StatusEffectInstance(item.getEffect().statusEffectInstance()));
                }
            }
        }
    }

    @Override
    public ItemStack asItemStack() {
        return getStack();
    }
}
