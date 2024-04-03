package moriyashiine.aylyth.api.interfaces;

import moriyashiine.aylyth.api.AylythEntityApi;
import net.minecraft.entity.Entity;

import java.util.Optional;

public interface VitalHealthHolder {
    static Optional<VitalHealthHolder> of(Entity context) {
        return Optional.ofNullable(AylythEntityApi.VITAL_HOLDER.find(context, null));
    }

    static VitalHealthHolder find(Entity context) {
        return AylythEntityApi.VITAL_HOLDER.find(context, null);
    }

    float get();

    void set(float vital);
}
