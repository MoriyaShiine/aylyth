package moriyashiine.aylyth.datagen.client;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.TextureKey;

import java.util.Optional;

public interface AylythModels {
    TextureKey OVERLAY = TextureKey.of("overlay");
    Model STREWN_LEAVES_TEMPLATE = block("strewn_leaves_template", TextureKey.TOP);
    Model BRANCH_TEMPLATE = block("branch_template", TextureKey.SIDE);
    Model CUBE_BOTTOM_TOP_WITH_OVERLAY = block("cube_bottom_top_with_overlay", TextureKey.TOP, TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.PARTICLE, OVERLAY);
    Model SEEP_LOG_BOTTOM = block("seep_log_bottom", "_bottom", TextureKey.END, TextureKey.SIDE);
    Model SEEP_LOG_SINGLE = block("seep_log_single", "_single", TextureKey.END, TextureKey.SIDE);
    Model SEEP_LOG_TOP = block("seep_log_top", "_top", TextureKey.END, TextureKey.SIDE);
    Model HANDHELD_ROTATED = item("handheld_rotated", TextureKey.LAYER0);

    private static Model block(String parent, TextureKey... requiredKeys) {
        return new Model(Optional.of(Aylyth.id("block/" + parent)), Optional.empty(), requiredKeys);
    }

    private static Model block(String parent, String variant, TextureKey... requiredKeys) {
        return new Model(Optional.of(Aylyth.id("block/" + parent)), Optional.of(variant), requiredKeys);
    }

    private static Model item(String parent, TextureKey... requiredKeys) {
        return new Model(Optional.of(Aylyth.id("item/" + parent)), Optional.empty(), requiredKeys);
    }
}
