package moriyashiine.aylyth.api.interfaces;

import moriyashiine.aylyth.common.world.ModDamageSources;

public interface ModDamageSourcesProvider {
    default ModDamageSources modDamageSources() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
