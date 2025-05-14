package moriyashiine.aylyth.common.entity.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public class YmpeInfestation {
	public static final Codec<YmpeInfestation> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
				Codec.BYTE.fieldOf("stage").forGetter(YmpeInfestation::getStage),
				Codec.SHORT.fieldOf("infestation_timer").forGetter(YmpeInfestation::getInfestationTimer)
			).apply(instance, YmpeInfestation::new)
	);
	public static final PacketCodec<? super RegistryByteBuf, YmpeInfestation> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.BYTE, YmpeInfestation::getStage,
			PacketCodecs.SHORT, YmpeInfestation::getInfestationTimer,
			YmpeInfestation::new
	);
	public static final short TIME_UNTIL_STAGE_INCREASES = 20 * 120;

	private byte stage;
	private short infestationTimer;

	public YmpeInfestation() {
		this((byte) 0, (short) 0);
	}

	public YmpeInfestation(byte stage, short infestationTimer) {
		this.stage = stage;
		this.infestationTimer = infestationTimer;
	}

	public byte getStage() {
		return stage;
	}
	
	public void setStage(byte stage) {
		this.stage = stage;
	}
	
	public short getInfestationTimer() {
		return infestationTimer;
	}
	
	public void setInfestationTimer(short infestationTimer) {
		this.infestationTimer = infestationTimer;
	}
}
