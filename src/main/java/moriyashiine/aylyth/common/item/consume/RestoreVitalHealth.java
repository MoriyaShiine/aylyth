package moriyashiine.aylyth.common.item.consume;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.item.AylythConsumeEffectTypes;
import net.minecraft.component.type.Consumable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.world.World;

import java.util.Optional;

public record RestoreVitalHealth(float restoreBy) implements ConsumeEffect {
    public static final MapCodec<RestoreVitalHealth> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("restore_by").forGetter(RestoreVitalHealth::restoreBy)
            ).apply(instance, RestoreVitalHealth::new)
    );
    public static final PacketCodec<RegistryByteBuf, RestoreVitalHealth> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, RestoreVitalHealth::restoreBy,
            RestoreVitalHealth::new
    );

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return AylythConsumeEffectTypes.RESTORE_VITAL_HEALTH;
    }

    @Override
    public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
        Optional<VitalHealthHolder> holder = VitalHealthHolder.of(user);
        if (holder.isEmpty()) {
            return false;
        }
        holder.get().setCurrentVitalHealth(holder.get().getCurrentVitalHealth() + restoreBy);
        return true;
    }
}
