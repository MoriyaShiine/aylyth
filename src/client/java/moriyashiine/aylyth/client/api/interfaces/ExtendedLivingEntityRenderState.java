package moriyashiine.aylyth.client.api.interfaces;

public interface ExtendedLivingEntityRenderState {
    default void aylyth$setThornsStage(int stage) {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
    default int aylyth$thornsStage() {
        throw new UnsupportedOperationException("Implemented via mixin.");
    }
}
