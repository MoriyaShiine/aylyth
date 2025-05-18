package moriyashiine.aylyth.common.entity.types.projectile;

import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.item.AylythDataComponentTypes;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.item.components.ThornFlechetteEffect;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ThornFlechetteEntity extends PersistentProjectileEntity {
    private static final TrackedData<ItemStack> STACK = DataTracker.registerData(ThornFlechetteEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public ThornFlechetteEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThornFlechetteEntity(LivingEntity owner, World world, ItemStack stack) {
        super(AylythEntityTypes.THORN_FLECHETTE, owner, world, stack, null);
        this.setOwner(owner);
    }

    public void setStack(ItemStack stack) {
        this.dataTracker.set(STACK, stack);
    }

    public ItemStack getStack() {
        return this.dataTracker.get(STACK);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder.add(STACK, ItemStack.EMPTY));
    }

    @Override
    public void tick() {
        super.tick();
        this.addVelocity(0, -0.05, 0);
    }

    @Override
    protected void onHit(LivingEntity target) {
        ThornFlechetteEffect effect = getStack().get(AylythDataComponentTypes.THORN_FLECHETTE_EFFECT);
        if (effect != null) {
            effect.apply(target);
        }
    }

    @Override
    public ItemStack asItemStack() {
        return getStack();
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return AylythItems.THORN_FLECHETTE.getDefaultStack();
    }
}
