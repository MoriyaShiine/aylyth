package moriyashiine.aylyth.api.interfaces;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface HindPledgeHolder {
    static Optional<HindPledgeHolder> of(Object context) {
        if (context instanceof HindPledgeHolder) {
            return Optional.of(((HindPledgeHolder) context));
        }
        return Optional.empty();
    }

    UUID getHindUuid();

    void setHindUuid(@Nullable UUID uuid);
}
