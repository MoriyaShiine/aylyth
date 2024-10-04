package moriyashiine.aylyth.api.interfaces;

import moriyashiine.aylyth.common.world.AylythDamageSources;

public interface AylythDamageSourcesProvider {
    default AylythDamageSources aylythDamageSources() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
