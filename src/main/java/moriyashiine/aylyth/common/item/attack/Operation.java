package moriyashiine.aylyth.common.item.attack;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum Operation implements StringIdentifiable {
    ADD(0, "add"),
    MULTIPLY(1, "multiply"),
    SET(2, "set");

    public static final IntFunction<Operation> BY_ID = ValueLists.createIdToValueFunction(
            Operation::getId, values(), ValueLists.OutOfBoundsHandling.ZERO
    );
    public static final PacketCodec<ByteBuf, Operation> PACKET_CODEC = PacketCodecs.indexed(
            BY_ID, Operation::getId
    );
    public static final Codec<Operation> CODEC = StringIdentifiable.createCodec(Operation::values);
    private final int id;
    private final String name;

    Operation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }

    public int getId() {
        return id;
    }
}