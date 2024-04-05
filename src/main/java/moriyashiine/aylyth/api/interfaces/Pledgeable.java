package moriyashiine.aylyth.api.interfaces;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Pledgeable {
    @Nullable
    UUID getPledgedPlayerUUID();

    void removePledge();
}
