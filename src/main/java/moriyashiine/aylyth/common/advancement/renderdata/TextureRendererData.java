package moriyashiine.aylyth.common.advancement.renderdata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

public record TextureRendererData(Identifier texture, int color) implements AdvancementRendererData {
    public static final Codec<TextureRendererData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Identifier.CODEC.fieldOf("texture").forGetter(TextureRendererData::texture),
                    Codec.INT.optionalFieldOf("color", 0xFFFFFFFF).forGetter(TextureRendererData::color)
            ).apply(instance, TextureRendererData::new)
    );

    public static TextureRendererData standard(Identifier texture) {
        return new TextureRendererData(texture, 0xFFFFFFFF);
    }

    @Override
    public AdvancementRendererDataType<?> getType() {
        return AdvancementRendererDataTypes.TEXTURE_RENDERER_DATA;
    }
}
