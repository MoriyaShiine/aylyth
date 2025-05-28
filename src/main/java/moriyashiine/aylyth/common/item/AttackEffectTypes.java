package moriyashiine.aylyth.common.item;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.item.attack.ApplyEffects;
import moriyashiine.aylyth.common.item.attack.AttackEffect;
import moriyashiine.aylyth.common.item.attack.Heal;
import moriyashiine.aylyth.common.item.attack.ModifyAbsorption;
import moriyashiine.aylyth.common.item.attack.ModifyDamage;
import moriyashiine.aylyth.common.item.attack.SpawnParticlesAround;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registry;

public interface AttackEffectTypes {
    AttackEffect.Type<SpawnParticlesAround> SPAWN_PARTICLES_AROUND = register("spawn_particles_around", SpawnParticlesAround.CODEC, SpawnParticlesAround.PACKET_CODEC);
    AttackEffect.Type<ModifyAbsorption> MODIFY_ABSORPTION = register("modify_absorption", ModifyAbsorption.CODEC, ModifyAbsorption.PACKET_CODEC);
    AttackEffect.Type<ApplyEffects> APPLY_EFFECTS = register("apply_effects", ApplyEffects.CODEC, ApplyEffects.PACKET_CODEC);
    AttackEffect.Type<ModifyDamage> MODIFY_DAMAGE = register("modify_damage", ModifyDamage.CODEC, ModifyDamage.PACKET_CODEC);
    AttackEffect.Type<Heal> HEAL = register("heal", Heal.CODEC, Heal.PACKET_CODEC);

    private static <T extends AttackEffect> AttackEffect.Type<T> register(String id, MapCodec<T> codec, PacketCodec<RegistryByteBuf, T> packetCodec) {
        return Registry.register(AylythRegistries.ATTACK_EFFECT_TYPE, Aylyth.id(id), new AttackEffect.Type<>(codec, packetCodec));
    }

    static void register() {}
}
