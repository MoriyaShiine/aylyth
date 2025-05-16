package moriyashiine.aylyth.common.entity.ai;

import io.netty.buffer.ByteBuf;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum BasicAttackType {
    NONE(0),
    MELEE(1),
    RANGED(2);

    public static final IntFunction<BasicAttackType> ID_TO_VALUE_FUNCTION = ValueLists.createIdToValueFunction(
            BasicAttackType::getIndex, values(), ValueLists.OutOfBoundsHandling.WRAP
    );
    public static final PacketCodec<ByteBuf, BasicAttackType> PACKET_CODEC = PacketCodecs.indexed(ID_TO_VALUE_FUNCTION, BasicAttackType::getIndex);

    private final int index;

    BasicAttackType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
