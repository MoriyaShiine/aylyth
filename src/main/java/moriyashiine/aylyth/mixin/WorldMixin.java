package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.api.interfaces.AylythDamageSourcesProvider;
import moriyashiine.aylyth.common.world.AylythDamageSources;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class WorldMixin implements AylythDamageSourcesProvider {

    @Unique
    private AylythDamageSources aylythDamageSources;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void aylyth$initModDamageSources(MutableWorldProperties properties, RegistryKey registryRef, DynamicRegistryManager registryManager, RegistryEntry dimensionEntry, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates, CallbackInfo ci) {
        aylythDamageSources = new AylythDamageSources(registryManager);
    }

    @Override
    public AylythDamageSources aylythDamageSources() {
        return aylythDamageSources;
    }
}
