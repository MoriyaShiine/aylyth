package moriyashiine.aylyth.common.item.consume;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.YmpeInfestation;
import moriyashiine.aylyth.common.item.AylythConsumeEffectTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.world.World;

public record AddBranches(int stages, float probability) implements ConsumeEffect {
    public static final MapCodec<AddBranches> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("stages").forGetter(AddBranches::stages),
                    Codec.FLOAT.fieldOf("probability").forGetter(AddBranches::probability)
            ).apply(instance, AddBranches::new)
    );
    public static final PacketCodec<RegistryByteBuf, AddBranches> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, AddBranches::stages,
            PacketCodecs.FLOAT, AddBranches::probability,
            AddBranches::new
    );

    @Override
    public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
        if (user.hasAttached(AylythEntityAttachmentTypes.YMPE_INFESTATION)) {
            if (user.getRandom().nextFloat() <= probability) {
                YmpeInfestation infestation = user.getAttachedOrThrow(AylythEntityAttachmentTypes.YMPE_INFESTATION);
                infestation.setStage((byte) (infestation.getStage() + stages));
                infestation.setInfestationTimer((short) 0);
                user.setAttached(AylythEntityAttachmentTypes.YMPE_INFESTATION, infestation);
                return true;
            }
        }
        return false;
    }

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return AylythConsumeEffectTypes.ADD_BRANCHES;
    }
}
