package moriyashiine.aylyth.common.advancement.renderdata;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.registry.AylythRegistries;

public interface AdvancementRendererDataType<T extends AdvancementRendererData> {
    Codec<AdvancementRendererData> CODEC = AylythRegistries.ADVANCEMENT_RENDERER_DATA_TYPE.getCodec().dispatch(AdvancementRendererData::getType, AdvancementRendererDataType::getCodec);

    Codec<T> getCodec();
}
