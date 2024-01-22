package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.api.interfaces.ModDamageSourcesProvider;
import moriyashiine.aylyth.common.world.ModDamageSources;
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

import java.util.function.Supplier;

@Mixin(World.class)
public class WorldMixin implements ModDamageSourcesProvider {

    @Unique
    private ModDamageSources modDamageSources;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void aylyth$initModDamageSources(MutableWorldProperties properties, RegistryKey registryRef, DynamicRegistryManager registryManager, RegistryEntry dimensionEntry, Supplier profiler, boolean isClient, boolean debugWorld, long biomeAccess, int maxChainedNeighborUpdates, CallbackInfo ci) {
        modDamageSources = new ModDamageSources(registryManager);
    }

    @Override
    public ModDamageSources modDamageSources() {
        return modDamageSources;
    }
}
