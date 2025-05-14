package moriyashiine.aylyth.common.entity.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;

public class YmpeThorns {
	public static final Codec<YmpeThorns> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
					Codec.INT.fieldOf("stage").forGetter(YmpeThorns::getStage),
					Codec.INT.fieldOf("timer").forGetter(YmpeThorns::getStageTimer)
			).apply(instance, YmpeThorns::new)
	);
	public static final PacketCodec<? super RegistryByteBuf, YmpeThorns> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.INTEGER, YmpeThorns::getStage,
			PacketCodecs.INTEGER, YmpeThorns::getStageTimer,
			YmpeThorns::new
	);

	public static final Identifier SPEED_MODIFIER = Aylyth.id("ympe_thorns_speed_debuff");

	private int stage = 0;
	private int timer = 0;

	public YmpeThorns() {
		this(0, 0);
	}

	public YmpeThorns(int stage, int timer) {
		this.stage = stage;
		this.timer = timer;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public void addStage(int progress) {
		this.stage += progress;
	}

	public int getStageTimer() {
		return timer;
	}

	public void setStageTimer(int timer) {
		this.timer = timer;
	}

	public void addStageTimer(int progress) {
		timer += progress;
	}
}
