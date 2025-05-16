package moriyashiine.aylyth.common.loot.predicates;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.entity.types.mob.ScionEntity;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntitySubPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class ScionPredicate implements EntitySubPredicate {
    public static final MapCodec<ScionPredicate> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("is_player").forGetter(scionPredicate -> scionPredicate.isPlayer)
            ).apply(instance, ScionPredicate::new)
    );

    private final boolean isPlayer;

    public ScionPredicate(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    @Override
    public MapCodec<? extends EntitySubPredicate> getCodec() {
        return CODEC;
    }

    @Override
    public boolean test(Entity entity, ServerWorld world, @Nullable Vec3d pos) {
        if (entity instanceof ScionEntity scion) {
            return isPlayer == (scion.getStoredPlayerUUID() != null);
        }
        return false;
    }
}
