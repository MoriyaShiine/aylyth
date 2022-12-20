package moriyashiine.aylyth.api.interfaces;

import java.util.Optional;

public interface ProlongedDeath {
    static Optional<ProlongedDeath> of(Object context) {
        if (context instanceof ProlongedDeath) {
            return Optional.of(((ProlongedDeath) context));
        }
        return Optional.empty();
    }

    int getDeathAnimationTime();
}
