package moriyashiine.aylyth.common.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

import java.util.UUID;

public class ScionEntity extends HostileEntity {
    private static UUID playerSkinUUID;

    public ScionEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.playerSkinUUID = null;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 35)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
                .add(EntityAttributes.GENERIC_ARMOR, 2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    public static UUID getStoredPlayerUUID() {
        return playerSkinUUID;
    }
}
