package moriyashiine.aylyth.common.item;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.item.consume.AddBranches;
import moriyashiine.aylyth.common.item.consume.CompositeHealthBased;
import moriyashiine.aylyth.common.item.consume.RestoreVitalHealth;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythConsumeEffectTypes {

    ConsumeEffect.Type<CompositeHealthBased> COMPOSITE_HEALTH_BASED = register("composite_health_based", CompositeHealthBased.CODEC, CompositeHealthBased.PACKET_CODEC);
    ConsumeEffect.Type<RestoreVitalHealth> RESTORE_VITAL_HEALTH = register("restore_vital_health", RestoreVitalHealth.CODEC, RestoreVitalHealth.PACKET_CODEC);
    ConsumeEffect.Type<AddBranches> ADD_BRANCHES = register("add_branches", AddBranches.CODEC, AddBranches.PACKET_CODEC);

    private static <T extends ConsumeEffect> ConsumeEffect.Type<T> register(String id, MapCodec<T> codec, PacketCodec<RegistryByteBuf, T> packetCodec) {
        return Registry.register(Registries.CONSUME_EFFECT_TYPE, Aylyth.id(id), new ConsumeEffect.Type<>(codec, packetCodec));
    }

    static void register() {}
}
