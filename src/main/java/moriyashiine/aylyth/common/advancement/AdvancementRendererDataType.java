package moriyashiine.aylyth.common.advancement;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.other.custom.CustomRegistries;

public interface AdvancementRendererDataType<T extends AdvancementRendererData> {
    Codec<AdvancementRendererData> CODEC = CustomRegistries.ADVANCEMENT_RENDERER_DATA_TYPE.getCodec().dispatch(AdvancementRendererData::getType, AdvancementRendererDataType::getCodec);

    Codec<T> getCodec();
}
