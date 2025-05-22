package moriyashiine.aylyth.client.api.interfaces;

public interface ExtendedPlayerEntityRenderState {
    default void aylyth$setCuirassStage(int stage) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
    default int aylyth$cuirassStage() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
    default void aylyth$setInfestationStage(int stage) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
    default int aylyth$infestationStage() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
