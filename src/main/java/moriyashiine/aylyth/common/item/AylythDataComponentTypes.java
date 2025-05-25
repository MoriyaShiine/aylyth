package moriyashiine.aylyth.common.item;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.item.components.ThornFlechetteEffect;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

import java.util.function.UnaryOperator;

public interface AylythDataComponentTypes {
    ComponentType<ThornFlechetteEffect> THORN_FLECHETTE_EFFECT = register("thorn_flechette_effect", builder -> builder.codec(ThornFlechetteEffect.CODEC).packetCodec(ThornFlechetteEffect.PACKET_CODEC));
    ComponentType<Integer> FLASK_CHARGES = register("flask_charges", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
    ComponentType<Integer> MAX_FLASK_CHARGES = register("max_flask_charges", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
    ComponentType<Unit> YELLOW_TINTED = register("tinted", builder -> builder.codec(Unit.CODEC).packetCodec(PacketCodec.unit(Unit.INSTANCE)));

    private static <I> ComponentType<I> register(String name, UnaryOperator<ComponentType.Builder<I>> builder) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Aylyth.id(name), builder.apply(new ComponentType.Builder<>()).build());
    }

    static void register() {}
}
