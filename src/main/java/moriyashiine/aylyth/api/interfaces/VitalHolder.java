package moriyashiine.aylyth.api.interfaces;

import java.util.Optional;

public interface VitalHolder {
    static Optional<VitalHolder> of(Object context) {
        if (context instanceof VitalHolder) {
            return Optional.of(((VitalHolder) context));
        }
        return Optional.empty();
    }

    int getVitalThuribleLevel();

    void setVitalThuribleLevel(int vital);

}
