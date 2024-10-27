package moriyashiine.aylyth.common.loot.predicates;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import moriyashiine.aylyth.common.entity.types.mob.ScionEntity;
import moriyashiine.aylyth.common.loot.AylythTypeSpecificPredicates;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.TypeSpecificPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class ScionPredicate implements TypeSpecificPredicate {
    public static final Codec<ScionPredicate> CODEC = Codec.BOOL.fieldOf("is_player")
            .xmap(ScionPredicate::new, scionPredicate -> scionPredicate.isPlayer)
            .codec();

    private final boolean isPlayer;

    public ScionPredicate(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    @Override
    public boolean test(Entity entity, ServerWorld world, @Nullable Vec3d pos) {
        if (entity instanceof ScionEntity scion) {
            return isPlayer == (scion.getStoredPlayerUUID() != null);
        }
        return false;
    }

    @Override
    public JsonObject typeSpecificToJson() {
        return CODEC.encode(this, JsonOps.INSTANCE, new JsonObject()).getOrThrow(false, s -> {}).getAsJsonObject();
    }

    @Override
    public TypeSpecificPredicate.Deserializer getDeserializer() {
        return AylythTypeSpecificPredicates.SCION;
    }

    public static class Deserializer implements TypeSpecificPredicate.Deserializer {
        @Override
        public TypeSpecificPredicate deserialize(JsonObject json) {
            return CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(false, s -> {});
        }
    }
}
