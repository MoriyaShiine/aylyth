package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {
    @Inject(method = "<init>(JZZLnet/minecraft/util/registry/SimpleRegistry;Ljava/util/Optional;)V",
            at = @At(value = "RETURN"))
    private void aylyth_cacheSeed(long seed, boolean generateStructures, boolean bonusChest, SimpleRegistry<DimensionOptions> options, Optional<String> legacyCustomOptions, CallbackInfo ci) {
        AylythBiomeSource.SEED_HOLDER = seed;
    }
}
