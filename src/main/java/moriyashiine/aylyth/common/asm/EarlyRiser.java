package moriyashiine.aylyth.common.asm;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable {
    @Override
    public void run() {
        MappingResolver mapper = FabricLoader.getInstance().getMappingResolver();

        String mappedClass = mapper.mapClassName("intermediary", "net.minecraft.class_4763$class_5486");
        ClassTinkerers.enumBuilder(mappedClass, String.class)
                .addEnumSubclass("AYLYTH_NOISE", "moriyashiine.aylyth.common.asm.AylythNoiseGrassColorMod", "aylyth_noise")
                .build();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            // TODO: Revisit. FRAPI wraps it in net.fabricmc.fabric.api.renderer.v1.material.GlintMode
            ClassTinkerers.enumBuilder(mapper.mapClassName("intermediary", "net.minecraft.class_10444$class_10445"))
                    .addEnum("AYLYTH_TINT")
                    .build();
        }
    }
}
